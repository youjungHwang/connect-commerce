package com.apigatewayservice.filter;

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

//            if (!validateToken(token)) {
//                log.warn("유효하지 않은 AuthorizationHeaderFilter Token 입니다.");
//                return handleUnAuthorized(exchange);
//            }

            return chain.filter(exchange.mutate()
                    .request(request)
                    .build());
        });
    }

    private Mono<Void> handleUnAuthorized(ServerWebExchange exchange) {

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);

        return response.setComplete();
    }

//    public boolean validateToken(String token) {
//        try {
//            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
//            return true;
//        }catch(io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
//            log.info("잘못된 JWT 서면입니다.");
//        }catch(ExpiredJwtException e) {
//            log.info("만료된 JWT 토큰입니다.");
//        }catch(UnsupportedJwtException e) {
//            log.info("지원되지 않는 JWT토큰입니다.");
//        }catch (IllegalArgumentException e) {
//            log.info("JWT 토큰이 잘못되었습니다.");
//        }
//        return false;
//    }

    /**
     * 토큰이 유효한지 확인하는 메서드
     */
    public boolean validateToken(String token) {
        Claims claims = parseClaims(token);
        log.debug("AuthorizationHeaderFilter -  claim 내용: {}", claims);
        return claims.getExpiration().after(new Date());
    }

    /**
     * 토큰에서 Claims 추출하는 메서드
     */
    private Claims parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(secret).build()
                .parseClaimsJws(token)
                .getBody();
    }



}