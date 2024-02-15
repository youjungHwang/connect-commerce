package com.apigatewayservice.filter;

import java.net.URI;
import java.util.Date;
import java.util.Objects;

import com.apigatewayservice.TokenProvider;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;


@Slf4j
@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    private final TokenProvider tokenProvider;

    @Value("${jwt.secret}")
    private String secret;

    public AuthorizationHeaderFilter(TokenProvider tokenProvider) {
        super(Config.class);
        this.tokenProvider = tokenProvider;
    }
    public static class Config {}

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            // Request Header에 토큰이 존재하지 않을 때
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                log.debug("AuthorizationHeaderFilter Token이 존재하지 않습니다.");
                return handleUnAuthorized(exchange);
            }

            // Request Header에서 토큰 문자열 받아오기
            String authorization =
                    Objects.requireNonNull(request.getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
            String token = authorization.replace("Bearer", "").trim();
            log.debug("AuthorizationHeaderFilter - token:{}", token);


            // 토큰 검증
            Claims claims = tokenProvider.parseClaims(token);
            log.info("AuthorizationHeaderFilter - claims:{}", claims);

            /**
             *  쿼리파라미터를 추가한 새로운 URI, 새로운 ServerHttpRequest 에 담아서 다음 필터로 넘겨준다.
             *  ?member=127 (인증회원 id)
             */
            final Long principalId = tokenProvider.getUserIdFromToken(token);
            final URI newUri = addParam(request.getURI(), "member", principalId);
            final ServerHttpRequest modifiedRequest = exchange.getRequest().mutate().uri(newUri).build();
            log.info("AuthorizationHeaderFilter - modifiedRequest:{}", modifiedRequest);
            return chain.filter(exchange.mutate().request(modifiedRequest).build());
        });
    }

    private Mono<Void> handleUnAuthorized(ServerWebExchange exchange) {

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);

        return response.setComplete();
    }

    public boolean validateToken(String token) {
        Claims claims = parseClaims(token);
        log.debug("AuthorizationHeaderFilter -  claim 내용: {}", claims);
        return claims.getExpiration().after(new Date());
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(secret).build()
                .parseClaimsJws(token)
                .getBody();
    }

    private URI addParam(URI uri, String name, Long value) {
        return UriComponentsBuilder.fromUri(uri)
                .queryParam(name, value)
                .build(true)
                .toUri();
    }



}