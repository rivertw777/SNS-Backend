package backend.spring.service.impl;

import backend.spring.service.SecurityService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {
    private final Key secretKey;

    public SecurityServiceImpl(@Value("${security.jwt.secretKey}") String secretKey) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    @Override
    public String createToken(String subject, long ttlMillis) {
        if (ttlMillis <= 0) {
            throw new IllegalArgumentException("Expiry time must be greater than Zero: [" + ttlMillis + "]");
        }

        return Jwts.builder()
                .setSubject(subject)
                .signWith(secretKey)
                .setExpiration(new Date(System.currentTimeMillis() + ttlMillis))
                .compact();
    }

    @Override
    public String getSubject(String token) {
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);

        Claims claims = claimsJws.getBody();
        return claims.getSubject();
    }

}
