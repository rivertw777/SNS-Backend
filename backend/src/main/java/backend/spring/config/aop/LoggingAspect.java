package backend.spring.config.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // 예외 발생 시 로깅
    @AfterThrowing(pointcut = "execution(* backend.spring.service.*.*(..)) || execution(* backend.spring.controller.*.*(..))", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String layer = getLayerName(className);
        logger.error("[{}] [Exception] {}.{}(): {}", layer, className, methodName, ex.getMessage());
    }

    // 메서드 실행 이전 로깅
    @Before("execution(* backend.spring.service.*.*(..)) || execution(* backend.spring.controller.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String layer = getLayerName(className);
        logger.info("[{}] [Executing] {}.{}()", layer, className, methodName);
    }

    // 메서드 실행 이후 로깅
    @After("execution(* backend.spring.service.*.*(..)) || execution(* backend.spring.controller.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String layer = getLayerName(className);
        logger.info("[{}] [Completed] {}.{}()", layer, className, methodName);
    }

    // 클래스 이름으로부터 계층(layer) 이름 추출
    private String getLayerName(String className) {
        if (className.contains("service")) {
            return "Service";
        } else if (className.contains("controller")) {
            return "Controller";
        } else {
            return "Unknown";
        }
    }
}
