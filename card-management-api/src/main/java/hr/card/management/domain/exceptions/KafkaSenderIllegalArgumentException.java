package hr.card.management.domain.exceptions;

public class KafkaSenderIllegalArgumentException extends RuntimeException {

    public KafkaSenderIllegalArgumentException() {
        super("Illegal argument!");
    }

    public KafkaSenderIllegalArgumentException(String message) {
        super(message);
    }

    public KafkaSenderIllegalArgumentException(String message, Throwable cause) {
        super(message, cause);
    }
}

