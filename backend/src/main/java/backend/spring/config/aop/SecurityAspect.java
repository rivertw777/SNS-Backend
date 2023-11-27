package backend.spring.config.aop;

import backend.spring.config.annotation.TokenRequired;
import backend.spring.service.SecurityService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
        HttpServletRequest request =requestAttributes.getRequest();

        // request header에 있는 토큰 체크하기
        String token = request.getHeader("token");
        System.out.println(token);
        if (StringUtils.isEmpty(token)) {
            throw new IllegalArgumentException("token is empty");
        }

        // 토큰 유효성 검사
        String subject = securityService.getSubject(token);
        System.out.println(subject);
        if (StringUtils.isEmpty(subject)) {
            throw new IllegalArgumentException("Token Error!! Claims are null!!");
        }
    }

}


