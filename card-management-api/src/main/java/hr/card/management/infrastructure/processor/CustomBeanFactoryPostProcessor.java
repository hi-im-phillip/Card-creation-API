package hr.card.management.infrastructure.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Profile;

@Profile("debug")
@Slf4j
public class CustomBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        log.debug(getClass().getSimpleName() + "::postProcessBeanFactory Listing Beans Start");
        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            log.debug(beanDefinitionName);
        }
        log.debug(getClass().getSimpleName() + "::postProcessBeanFactory Listing Beans End\n");
    }
}
