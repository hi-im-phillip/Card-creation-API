package hr.card.management;

import hr.card.management.api.model.CardRequestDto;
import hr.card.management.api.service.CardRequestApiServiceImpl;
import hr.card.management.domain.annotations.Cacheable;
import hr.card.management.domain.aspect.CacheableAspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CacheableAspectTest {

    @Mock
    private ProceedingJoinPoint proceedingJoinPoint;
    @Mock
    private MethodSignature methodSignature;
    @Mock
    private CacheableAspect cacheableAspect;


    @BeforeEach
    public void setUp() throws Throwable {

        // Create a CacheableAspect instance
        cacheableAspect = new CacheableAspect();

        // Create a mock ProceedingJoinPoint
        proceedingJoinPoint = Mockito.mock(ProceedingJoinPoint.class);

        methodSignature = Mockito.mock(MethodSignature.class);
        when(proceedingJoinPoint.getSignature()).thenReturn(methodSignature);

        // Define the method signature for the mocked MethodSignature
        when(methodSignature.toShortString()).thenReturn("findCardRequestById(Long)");
        when(methodSignature.toLongString()).thenReturn("CardRequestDto hr.card.management.api.controller.service.CardRequestApiServiceImpl.findCardRequestById(Long)");
        when(methodSignature.getMethod()).thenReturn(CardRequestApiServiceImpl.class.getMethod("findCardRequestById", Long.class));

        when(proceedingJoinPoint.proceed()).thenReturn(CardRequestDto.builder().id(1337L));
    }

    @Test
    public void testCacheHit_2() throws Throwable {

        when(proceedingJoinPoint.proceed()).thenReturn(new CardRequestDto());

        // Invoke the method under test twice
        Object result1 = cacheableAspect.checkCache(proceedingJoinPoint);
        Object result2 = cacheableAspect.checkCache(proceedingJoinPoint);

        // Verify that the same result object is returned from cache
        assertEquals(result1, result2);

        // Verify that the method is only invoked once
        verify(proceedingJoinPoint, times(1)).proceed();
    }


    @Test
    public void testCacheMiss() throws Throwable {

        // Create a mock ProceedingJoinPoint for the second method call
        ProceedingJoinPoint differentProceedingJoinPoint = Mockito.mock(ProceedingJoinPoint.class);
        MethodSignature differentMethodSignature = Mockito.mock(MethodSignature.class);

        when(differentProceedingJoinPoint.getSignature()).thenReturn(differentMethodSignature);
        when(differentMethodSignature.toShortString()).thenReturn("findCustomerById(Long)");
        when(differentMethodSignature.toLongString()).thenReturn("String hr.card.management.api.CustomerApiServiceImpl.findCustomerById(Long)");
        when(differentMethodSignature.getMethod()).thenReturn(MockCustomerServiceImpl.class.getMethod("findCustomerById", Long.class));

        when(differentProceedingJoinPoint.proceed()).thenReturn(CardRequestDto.builder().id(420L));

        // Invoke the first method under test (should result in cache miss)
        Object result1 = cacheableAspect.checkCache(proceedingJoinPoint);

        // Verify that the method is invoked once for the first call
        verify(proceedingJoinPoint, times(1)).proceed();

        // Invoke the second method under test (should result in cache miss)
        Object result2 = cacheableAspect.checkCache(differentProceedingJoinPoint);

        // Verify that the method is invoked once for the second call
        verify(differentProceedingJoinPoint, times(1)).proceed();

        // Assert that the results of the two calls are not equal
        assertNotEquals(result1, result2);
    }


    private static class MockCustomerServiceImpl {

        @Cacheable
        public String findCustomerById(Long id) {
            // Mocked implementation for findCustomerById
            return "Mocked Customer with ID: " + id;
        }
    }
}

