package hr.card.management.api.domain.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface KafkaSender {
    String topic();
}
