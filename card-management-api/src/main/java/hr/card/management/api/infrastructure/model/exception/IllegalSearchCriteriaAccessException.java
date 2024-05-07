package hr.card.management.api.infrastructure.model.exception;

public class IllegalSearchCriteriaAccessException extends RuntimeException {
    public IllegalSearchCriteriaAccessException(String message, Throwable cause) {
        super(message, cause);
    }

}
