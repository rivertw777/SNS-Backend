package backend.spring.config.aop;

import backend.spring.config.annotation.TokenRequired;
import backend.spring.service.SecurityService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Aspect
@Component
public class SecurityAspect {

    private final SecurityService securityService;

    public SecurityAspect(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Before("@annotation(tokenRequired)")
    public void authenticateWithToken(TokenRequired tokenRequired) {
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        HttpServletResponse response = requestAttributes.getResponse();

        // request header에서 토큰 가져오기
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            throw new IllegalArgumentException("Token is empty");
        }

        // 토큰 유효성 검사
        String subject = securityService.getSubject(token);
        if (StringUtils.isEmpty(subject)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            throw new IllegalArgumentException("Invalid token");
        }
    }

}

