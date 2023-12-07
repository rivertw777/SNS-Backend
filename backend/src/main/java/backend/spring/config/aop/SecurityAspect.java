package backend.spring.config.aop;

import backend.spring.config.annotation.TokenRequired;
import backend.spring.config.jwt.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@RequiredArgsConstructor
public class SecurityAspect {

    private final TokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;

    @Before("@annotation(tokenRequired)")
    public void authenticateWithToken(TokenRequired tokenRequired) {

        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request =requestAttributes.getRequest();

        // request header에 있는 토큰 체크하기
        String token = request.getHeader("token");
        System.out.println(token);
        if(!tokenProvider.validateToken(token)){
            throw new IllegalArgumentException("invalid token!");
        }

        // 인증 권한 받기
        Authentication authentication = authenticateWithToken(token);
        authenticationManager.authenticate(authentication);
    }

    private Authentication authenticateWithToken(String token) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(null, token)
        );
        return authentication;
    }

}
