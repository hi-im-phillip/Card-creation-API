package hr.card.management.api.domain.aspect;

import hr.card.management.api.domain.exceptions.ValidationViolationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Aspect
@Component
public class ValidationCheckAspect {

    @Autowired
    private Validator validator;

    @Around("@annotation(hr.card.management.api.domain.annotations.ValidationCheck)")
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
