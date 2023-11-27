package backend.spring.config.aop;

import backend.spring.config.annotation.TokenRequired;
import backend.spring.service.SecurityService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Aspect
@Component
public class SecurityAspect {

    private final SecurityService securityService;

    public SecurityAspect(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Before("@annotation(tokenRequired)")
    public void authenticateWithToken(JoinPoint joinPoint, TokenRequired tokenRequired) {
        Object[] args = joinPoint.getArgs();

        if (args.length < 1) {
            throw new IllegalArgumentException("Token argument is missing");
        }

        String token = (String) args[0];

        if (StringUtils.isEmpty(token)) {
            throw new IllegalArgumentException("Token is empty");
        }

        String subject = securityService.getSubject(token);

        if (StringUtils.isEmpty(subject)) {
            throw new IllegalArgumentException("Token Error!! Claims are null!!");
        }

        System.out.println("토큰의 subject로 자체 인증해주세요.");
        // 추가적인 인증 로직을 수행할 수 있습니다.
    }
}

