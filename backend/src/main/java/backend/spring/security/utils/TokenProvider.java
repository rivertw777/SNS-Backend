package backend.spring.security.utils;

import backend.spring.security.model.CustomUserDetails;
import backend.spring.security.service.SecurityService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class TokenProvider {

    private final SecurityService securityService;
    private final Key jwtSecretKey;
    private final long jwtExpirationInMs;

    public TokenProvider(SecurityService securityService, @Value("${jwt.secret}") String secretKey, @Value("${jwt.expiration}") long jwtExpirationInMs) {
        this.securityService = securityService;
        this.jwtSecretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.jwtExpirationInMs = jwtExpirationInMs;
    }

    // 토큰 생성
    public String generateToken(UserDetails userDetails) {
        long now = (new Date()).getTime();
        // 접근 토큰 생성
        Date accessTokenExpiresIn = new Date(now + jwtExpirationInMs);
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("auth", userDetails.getAuthorities())
                .setExpiration(accessTokenExpiresIn)
                .signWith(jwtSecretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // 인증 정보 추출
    public Authentication extractAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 회원 이름 추출
        String name = getUsernameFromToken(accessToken);

        // UserDetails 객체를 만들어서 Authentication 리턴
        CustomUserDetails customUserDetails = securityService.loadUserByUsername(name);
        return new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
    }

    // 토큰 복호화
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(jwtSecretKey).build().parseClaimsJws(accessToken).getBody();
        // 토큰 만료시 예외 처리
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    // 토큰으로부터 이름 추출
    public String getUsernameFromToken(String token) throws JwtException {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    // 토큰 검증
    public boolean validateToken(String token) throws JwtException {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtSecretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtException("유효하지 않은 토큰입니다.");
        }
    }

}

