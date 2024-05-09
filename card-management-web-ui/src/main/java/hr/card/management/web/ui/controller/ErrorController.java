package hr.card.management.web.ui.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpStatusCodeException;

@ControllerAdvice
@Slf4j
public class ErrorController {

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(final Throwable throwable, final Model model) {
        log.error("Exception during execution of application", throwable);
        String statusCode = null;
        if (throwable instanceof HttpStatusCodeException httpStatusCodeException) {
            statusCode = httpStatusCodeException.getStatusCode().toString();
        }
        String errorMessage = (statusCode != null ? statusCode : "Unknown error");
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }

}
