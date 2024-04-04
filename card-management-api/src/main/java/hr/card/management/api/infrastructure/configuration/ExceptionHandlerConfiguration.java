package hr.card.management.api.infrastructure.configuration;

import hr.card.management.api.domain.exceptions.KafkaSenderIllegalArgumentException;
import hr.card.management.api.domain.exceptions.ValidationViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerConfiguration {

    @ExceptionHandler({ValidationViolationException.class, KafkaSenderIllegalArgumentException.class})
    @ResponseBody
    public ResponseEntity<String> handleSpecificExceptions(RuntimeException e) {
        log.warn("Exception handler -> {}", e.getMessage());
        log.error("Stack trace: {}", Arrays.stream(e.getStackTrace()).toArray());
        log.error(e.getLocalizedMessage());
        String errorMessage = "Handling exception: " + e.getMessage();
        log.warn("Exception handler - generate error message  -> {}", errorMessage);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<String> handleAllExceptions(Exception e) {
        log.error("Unhandled exception occurred", e);
        String errorMessage = "An unexpected error occurred: " + e.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
