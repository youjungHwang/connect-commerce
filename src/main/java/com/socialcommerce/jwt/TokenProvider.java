package com.socialcommerce.jwt;

import com.socialcommerce.auth.CustomUserDetails;
import com.socialcommerce.user.User;
import com.socialcommerce.jwt.dto.TokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;
import io.jsonwebtoken.SignatureAlgorithm;

@Slf4j
@Component
public class TokenProvider {
    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30; // 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7; // 7일

    private SecretKey key;

    public TokenProvider(@Value("${jwt.secret}") String secret) {
        byte[] decodedKey = Base64.getDecoder().decode(secret);
        this.key = Keys.hmacShaKeyFor(decodedKey);
    }

    /*
    * AccessToken 생성
    * */
    public TokenDto generateTokenDto(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        long now = (new Date()).getTime();

        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getUser().getId();
        String userEmail = userDetails.getUsername();

        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .claim("userId", userId)
                .claim("email", userEmail)
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        /*
        * Refresh Token 생성
        * */
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return TokenDto.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
                .refreshToken(refreshToken)
                .build();

    }

    public Authentication getAuthentication(String accessToken) {
        log.debug("getAuthentication의 accessToken, accessToken: {}", accessToken);
        Claims claims = parseClaims(accessToken);

        if(claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        String authoritiesString = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .collect(Collectors.joining(","));

        String email = null;
        if(claims.get("email") != null) {
            email = claims.get("email").toString();
            log.debug("claims에 들어있는 email, email: {}", email);
        }

        Long userId = claims.get("userId", Long.class);
        log.debug("클레임에서 추출한 userId: {}", userId);

        User user = User.builder()
                .id(userId)
                .username(claims.getSubject())
                .email(email)
                .build();

        log.debug("User 객체 생성 후 정보: {}", user);

        CustomUserDetails principal = new CustomUserDetails(user);
        log.debug("CustomUserDetails 생성 후 ID: {}", principal.getId());
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        return authentication;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch(io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서면입니다.");
        }catch(ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        }catch(UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT토큰입니다.");
        }catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    private Claims parseClaims(String accessToken) {
        try{
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        }catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
