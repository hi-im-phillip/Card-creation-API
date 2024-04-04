package hr.card.management.api.domain.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Aspect
@Component
@Slf4j
@ConditionalOnProperty(value = "performance-logger.enabled", havingValue = "true")
public class PerformanceLoggerAspect {

    @Pointcut("@annotation(hr.card.management.api.domain.annotations.PerformanceLogger)")
    public void performanceLoggerPointcut() {
    }

    @Around(value = "performanceLoggerPointcut()")
    public Object logPerformance(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        try {
            return proceedingJoinPoint.proceed();
        } finally {
            long finishTime = System.currentTimeMillis();
            Duration duration = Duration.ofMillis(finishTime - startTime);

            log.info(String.format("Duration of %s execution was %s", proceedingJoinPoint.getSignature(), duration));
        }
    }


}
