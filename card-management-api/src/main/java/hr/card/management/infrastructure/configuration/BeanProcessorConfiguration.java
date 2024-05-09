package hr.card.management.infrastructure.configuration;

import hr.card.management.infrastructure.processor.CustomBeanFactoryPostProcessor;
import hr.card.management.infrastructure.processor.CustomBeanPostProcessor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@NoArgsConstructor
public class BeanProcessorConfiguration {

    @Bean
    @Profile("debug")
    public static BeanFactoryPostProcessor customBeanFactoryPostProcessor() {
        return new CustomBeanFactoryPostProcessor();
    }

    @Bean
    @Profile("debug")
    public static BeanPostProcessor customBeanPostProcessor() {
        return new CustomBeanPostProcessor();
    }
}
