package hr.card.management.infrastructure.model.exception;

public class IllegalSearchCriteriaAccessException extends RuntimeException {
    public IllegalSearchCriteriaAccessException(String message, Throwable cause) {
        super(message, cause);
    }

}
