package hr.card.management.infrastructure.configuration;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "validation-check.enabled", havingValue = "true")
@Slf4j
public class ValidatorConfiguration {

    @Bean
    public Validator validator() {
        log.info("ValidatorConfiguration::validator Creating Validator");
        return Validation.byDefaultProvider().configure().buildValidatorFactory().getValidator();
    }
}
