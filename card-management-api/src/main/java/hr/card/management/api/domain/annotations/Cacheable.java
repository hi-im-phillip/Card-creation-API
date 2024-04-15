package hr.card.management.api.domain.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static hr.card.management.api.domain.aspect.CacheableAspect.DEFAULT_MAX_CACHE_SIZE;
import static hr.card.management.api.domain.aspect.CacheableAspect.DEFAULT_MAX_HOURS_IN_CACHE;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cacheable {

    int maxSize() default DEFAULT_MAX_CACHE_SIZE; // Default maximum cache size

    int maxHours() default DEFAULT_MAX_HOURS_IN_CACHE;   // Default maximum age of cache entries in days
}
