package hr.card.management;

import hr.card.management.api.model.CardRequestDto;
import hr.card.management.api.service.CardRequestApiServiceImpl;
import hr.card.management.infrastructure.model.CardRequest;
import hr.card.management.infrastructure.repository.CardRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = ApiApplication.class)
public class CacheableAspectTest {

    @Autowired
    private CardRequestApiServiceImpl cardRequestApiService;

    @MockBean
    private CardRequestRepository cardRequestRepository;

    @BeforeEach
    public void setUp() {
        Mockito.reset(cardRequestRepository);
    }

    @Test
    public void testFindCardRequestById_Caching() {
        Long testId = 1L;
        CardRequest cardRequest = new CardRequest();
        cardRequest.setId(testId);
        CardRequestDto cardRequestDto = new CardRequestDto();
        cardRequestDto.setId(testId);

        when(cardRequestRepository.findById(testId)).thenReturn(Optional.of(cardRequest));

        // First call should cache the result
        CardRequestDto result1 = cardRequestApiService.findCardRequestById(testId);
        assertNotNull(result1);
        assertEquals(result1.getId(), testId);

        // Second call should hit the cache
        CardRequestDto result2 = cardRequestApiService.findCardRequestById(testId);
        assertNotNull(result2);
        assertEquals(result2.getId(), testId);

        // Verify that repository was called only once
        verify(cardRequestRepository, times(1)).findById(testId);
    }

    @Test
    public void testFindCardRequestById_NullValueNotCached() {
        Long testId = 2L;

        when(cardRequestRepository.findById(testId)).thenReturn(Optional.empty());

        // First call should return null and not cache the result
        CardRequestDto result1 = cardRequestApiService.findCardRequestById(testId);
        assertNull(result1);

        // Second call should again return null and not cache the result
        CardRequestDto result2 = cardRequestApiService.findCardRequestById(testId);
        assertNull(result2);

        // Verify that repository was called twice
        verify(cardRequestRepository, times(2)).findById(testId);
    }
}

