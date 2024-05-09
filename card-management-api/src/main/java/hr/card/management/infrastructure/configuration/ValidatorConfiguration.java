package hr.card.management.infrastructure.configuration;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "validation-check.enabled", havingValue = "true")
public class ValidatorConfiguration {

    @Bean
    public Validator validator() {
        return Validation.byDefaultProvider().configure().buildValidatorFactory().getValidator();
    }
}
