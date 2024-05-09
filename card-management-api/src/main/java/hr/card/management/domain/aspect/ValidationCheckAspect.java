package hr.card.management.domain.aspect;

import hr.card.management.domain.exceptions.ValidationViolationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Set;

@Aspect
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "validation-check.enabled", havingValue = "true")
public class ValidationCheckAspect {

    private final Validator validator;

    @Pointcut("@annotation(hr.card.management.domain.annotations.ValidationCheck)")
    public void validationCheckPointcut() {
    }

    @Around(value = "validationCheckPointcut()")
    public Object validate(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        if (proceedingJoinPoint.getArgs().length == 0) {
            return proceedingJoinPoint.proceed();
        }
        Set<ConstraintViolation<Object>> violation = validator.validate(proceedingJoinPoint.getArgs()[0]);
        if (!violation.isEmpty()) {
            StringBuilder violationMessage = new StringBuilder("Found violation in class " + proceedingJoinPoint.getArgs()[0].getClass() + " Violations: ");
            violation.forEach(v -> violationMessage.append(v.getMessage()).append("; "));
            throw new ValidationViolationException(violationMessage.toString());
        }
        return proceedingJoinPoint.proceed();
    }
}
