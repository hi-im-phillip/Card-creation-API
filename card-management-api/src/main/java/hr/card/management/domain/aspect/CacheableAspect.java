package hr.card.management.domain.aspect;

import hr.card.management.domain.annotations.Cacheable;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
@Slf4j
public class CacheableAspect {

    public static final int DEFAULT_MAX_CACHE_SIZE = 100;
    public static final int DEFAULT_MAX_HOURS_IN_CACHE = 5;

    private final Map<CacheId, Map<CacheKey, CachedObject>> caches = new ConcurrentHashMap<>();

    @Pointcut("@annotation(hr.card.management.domain.annotations.Cacheable)")
    public void cacheablePointcut() {
    }

    @Around(value = "cacheablePointcut()")
    public Object checkCache(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        CacheId cacheId = new CacheId(proceedingJoinPoint);
        Map<CacheKey, CachedObject> cache = caches.computeIfAbsent(cacheId, k -> createCache(proceedingJoinPoint));

        CacheKey cacheKey = new CacheKey(proceedingJoinPoint);
        CachedObject cachedObject = cache.get(cacheKey);

        if (cachedObject != null && !cachedObject.isExpired()) {
            log.debug("Cache hit for key: {}", cacheKey);
            return cachedObject.getValue();
        }

        log.debug("Cache miss for key: {}", cacheKey);
        Object newObject = proceedingJoinPoint.proceed();

        if (newObject == null || (newObject instanceof Optional && ((Optional<?>) newObject).isEmpty())) {
            log.debug("Result is null or empty Optional, not caching");
            return newObject;
        }

        long ttlMillis = cacheId.maxHours * 60L * 60L * 1000L;
        cache.put(cacheKey, new CachedObject(newObject, System.currentTimeMillis(), ttlMillis));

        return newObject;
    }

    private Map<CacheKey, CachedObject> createCache(ProceedingJoinPoint proceedingJoinPoint) {
        Method method = getMethod(proceedingJoinPoint);
        Cacheable methodAnnotation = method.getAnnotation(Cacheable.class);

        int maxSize = methodAnnotation.maxSize();

        return new LinkedHashMap<>(maxSize, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<CacheKey, CachedObject> eldest) {
                return size() > maxSize || eldest.getValue().isExpired();
            }
        };
    }

    public static class CacheKey {
        private final Object target;
        private final Class<?> returnType;
        private final List<Object> parameters;
        private final int hashCode;

        public CacheKey(ProceedingJoinPoint proceedingJoinPoint) {
            target = proceedingJoinPoint.getTarget();
            returnType = ((org.aspectj.lang.reflect.MethodSignature) proceedingJoinPoint.getSignature()).getMethod().getReturnType();
            parameters = Arrays.asList(proceedingJoinPoint.getArgs());
            hashCode = Objects.hash(target, returnType, parameters);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CacheKey cacheKey = (CacheKey) o;
            return Objects.equals(target, cacheKey.target) &&
                    Objects.equals(returnType, cacheKey.returnType) &&
                    Objects.equals(parameters, cacheKey.parameters);
        }

        @Override
        public int hashCode() {
            return hashCode;
        }

        @Override
        public String toString() {
            return "CacheKey{" +
                    "target=" + target +
                    ", returnType=" + returnType +
                    ", parameters=" + parameters +
                    '}';
        }
    }

    public static class CacheId {
        private final Class<?> returnType;
        private final int maxHours;
        private final int maxSize;
        private final int hashCode;

        public CacheId(ProceedingJoinPoint proceedingJoinPoint) {
            Method method = ((org.aspectj.lang.reflect.MethodSignature) proceedingJoinPoint.getSignature()).getMethod();
            Cacheable methodAnnotation = method.getAnnotation(Cacheable.class);
            returnType = method.getReturnType();
            maxHours = methodAnnotation.maxHours();
            maxSize = methodAnnotation.maxSize();
            hashCode = Objects.hash(returnType, maxHours, maxSize);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CacheId cacheId = (CacheId) o;
            return maxHours == cacheId.maxHours &&
                    maxSize == cacheId.maxSize &&
                    Objects.equals(returnType, cacheId.returnType);
        }

        @Override
        public int hashCode() {
            return hashCode;
        }

        @Override
        public String toString() {
            return "CacheId{" +
                    "returnType=" + returnType +
                    ", maxHours=" + maxHours +
                    ", maxSize=" + maxSize +
                    '}';
        }
    }

    private Method getMethod(JoinPoint joinPoint) {
        return ((org.aspectj.lang.reflect.MethodSignature) joinPoint.getSignature()).getMethod();
    }

    private static class CachedObject {
        private final Object value;
        private final long timestamp;
        private final long ttlMillis;

        public CachedObject(Object value, long timestamp, long ttlMillis) {
            this.value = value;
            this.timestamp = timestamp;
            this.ttlMillis = ttlMillis;
        }

        public Object getValue() {
            return value;
        }

        public boolean isExpired() {
            return System.currentTimeMillis() - timestamp > ttlMillis;
        }
    }
}
