package hr.card.management.web.ui.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.HttpServerErrorException;

import java.io.IOException;
import java.net.ConnectException;

/**
 * Retry interceptor for RestTemplate
 * <p>
 * This interceptor will retry requests to the server if it receives a 5xx error.
 * It will retry up to maxAttempts times with an exponential backoff starting from initialIntervalMillis.
 * </p>
 * <p>
 * Also, ResponseErrorHandler can be used to handle the error response from the server.
 * </p>
 */

@RequiredArgsConstructor
@Slf4j
public class RetryInterceptor implements ClientHttpRequestInterceptor {

    private final int maxAttempts;
    private final long initialIntervalMillis;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        int attempt = 0;
        long intervalMillis = initialIntervalMillis;
        while (true) {
            try {
                log.info("Attempt to execute request to {}", request.getURI());
                return execution.execute(request, body);
            } catch (ConnectException | HttpServerErrorException ex) {
                if (++attempt >= maxAttempts) {
                    log.error("Max attempts reached", ex);
                    throw ex;
                }
                try {
                    Thread.sleep(intervalMillis);
                } catch (InterruptedException e) {
                    log.error("Interrupted while waiting for retry", e);
                    Thread.currentThread().interrupt();
                    throw new IllegalStateException("Interrupted while waiting for retry", e);
                }
                intervalMillis *= 2; // Exponential backoff
            }
        }
    }
}

