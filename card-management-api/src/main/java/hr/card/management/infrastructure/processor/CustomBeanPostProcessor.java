package hr.card.management.infrastructure.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Profile;

@Profile("debug")
@Slf4j
public class CustomBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        log.debug(String.format("%s::postProcessBeforeInitialization %s %s", getClass().getSimpleName(), bean.getClass().getSimpleName(), beanName));
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.debug(String.format("%s::postProcessAfterInitialization %s %s", getClass().getSimpleName(), bean.getClass().getSimpleName(), beanName));
        return bean;
    }
}
