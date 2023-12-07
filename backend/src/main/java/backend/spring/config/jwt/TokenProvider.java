package backend.spring.config.jwt;

import backend.spring.service.impl.UserServiceImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Component
public class TokenProvider {

    private UserServiceImpl userService;


    private final Key jwtSecretKey;
    private final long jwtExpirationInMs;

    public TokenProvider(UserServiceImpl userService, @Value("${jwt.expiration}") long jwtExpirationInMs) {
        this.userService = userService;
        this.jwtSecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        this.jwtExpirationInMs = jwtExpirationInMs;
    }

    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(jwtSecretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public Authentication extractAuthenticationFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(jwtSecretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();
            System.out.println(username);
            UserDetails user = userService.loadUserByUsername(username);

            List<GrantedAuthority> authorities = new ArrayList<>(); // 필요한 경우 권한 정보도 추출하여 설정할 수 있습니다.
            Authentication authentication = new UsernamePasswordAuthenticationToken(user, "1234", user.getAuthorities());
            return authentication;
        } catch (JwtException e) {
            // 토큰이 유효하지 않은 경우 null을 반환하거나 예외를 처리할 수 있습니다.
            return null;
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

