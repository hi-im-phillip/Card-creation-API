package hr.card.management.api.domain.aspect;

import hr.card.management.api.domain.annotations.Cacheable;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Aspect
@Component
@Slf4j
public class CacheableAspect {

    public static final int DEFAULT_MAX_CACHE_SIZE = 100;
    public static final int DEFAULT_MAX_HOURS_IN_CACHE = 5;

    private final Map<CacheId, Map<CacheKey, Object>> caches = new HashMap<>();


    @Pointcut("@annotation(hr.card.management.api.domain.annotations.Cacheable)")
    public void cacheablePointcut() {
    }

    @Around(value = "cacheablePointcut()")
    public Object checkCache(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        CacheId cacheId = new CacheId(proceedingJoinPoint);
        Map<CacheKey, Object> cache = null;
        if (caches.containsKey(cacheId)) {
            cache = caches.get(cacheId);
        }

        if (cache == null) {
            cache = createCache(proceedingJoinPoint);
            caches.put(cacheId, cache);
        }

        CacheKey cacheKey = new CacheKey(proceedingJoinPoint);
        if (cache.containsKey(cacheKey)) {
            return cache.get(cacheKey);
        } else {
            Object newObject = proceedingJoinPoint.proceed();
            cache.put(cacheKey, newObject);

            return newObject;
        }
    }


    private Map<CacheKey, Object> createCache(ProceedingJoinPoint proceedingJoinPoint) {

        Method method = getMethod(proceedingJoinPoint);
        Cacheable methodAnnotation = method.getAnnotation(Cacheable.class);

        int maxSize = methodAnnotation.maxSize();
        int maxHours = methodAnnotation.maxHours();

        return new LinkedHashMap<>(maxSize, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<CacheKey, Object> eldest) {
                long currentTime = System.currentTimeMillis();
                long entryTime = eldest.getKey().timestamp;
                long ageInMillis = currentTime - entryTime;
                long ageInHours = ageInMillis / (1000 * 60 * 60);

                return size() > maxSize || ageInHours > maxHours;
            }
        };
    }

    public static class CacheKey {

        private final Object target;
        private final Class<?> returnType;
        private final long timestamp;


        public CacheKey(ProceedingJoinPoint proceedingJoinPoint) {
            target = proceedingJoinPoint.getTarget();
            returnType = ((org.aspectj.lang.reflect.MethodSignature) proceedingJoinPoint.getSignature()).getMethod().getReturnType();
            timestamp = System.currentTimeMillis();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null) return false;
            if (o instanceof CacheKey cacheKey) {
                return Objects.equals(target, cacheKey.target) && Objects.equals(returnType, cacheKey.returnType);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(target, returnType);
        }
    }

    public static class CacheId {
        private final Class<?> returnType;
        private final int maxDays;
        private final int maxSize;

        public CacheId(ProceedingJoinPoint proceedingJoinPoint) {
            Method method = ((org.aspectj.lang.reflect.MethodSignature) proceedingJoinPoint.getSignature()).getMethod();
            Cacheable methodAnnotation = method.getAnnotation(Cacheable.class);
            returnType = method.getReturnType();
            maxDays = methodAnnotation.maxHours();
            maxSize = methodAnnotation.maxSize();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null) return false;
            if (o instanceof CacheId cacheId) {
                return maxDays == cacheId.maxDays && maxSize == cacheId.maxSize && returnType == cacheId.returnType;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(returnType, maxDays, maxSize);
        }
    }

    private Method getMethod(JoinPoint joinPoint) {
        return ((org.aspectj.lang.reflect.MethodSignature) joinPoint.getSignature()).getMethod();
    }
}
