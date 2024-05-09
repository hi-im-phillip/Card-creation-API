package hr.card.management.domain.exceptions;

public class ValidationViolationException extends RuntimeException {

    public ValidationViolationException() {
        super("Validation found!");
    }

    public ValidationViolationException(String message) {
        super(message);
    }

    public ValidationViolationException(String message, Throwable cause) {
        super(message, cause);
    }
}

