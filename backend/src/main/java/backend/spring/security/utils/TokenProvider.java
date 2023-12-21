package backend.spring.security.utils;

import backend.spring.security.CustomUserDetails;
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
import java.time.Instant;
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

    public String generateToken(UserDetails userDetails) {
        long now = (new Date()).getTime();
        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + jwtExpirationInMs);
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("auth", userDetails.getAuthorities())
                .setExpiration(accessTokenExpiresIn)
                .signWith(jwtSecretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Authentication extractAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        String username = getUsernameFromToken(accessToken);

        // UserDetails 객체를 만들어서 Authentication 리턴
        CustomUserDetails customUserDetails = securityService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(jwtSecretKey).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public String getUsernameFromToken(String token) throws JwtException {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String token) throws JwtException {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtSecretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Date getExpirationDateFromToken(String token) throws JwtException {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

    public boolean isTokenExpired(String token) throws JwtException {
        Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(Date.from(Instant.now()));
    }
}

