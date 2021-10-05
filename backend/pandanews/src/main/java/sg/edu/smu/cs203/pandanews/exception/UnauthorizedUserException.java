package sg.edu.smu.cs203.pandanews.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UnauthorizedUserException extends RuntimeException {
    public UnauthorizedUserException() {
        super("Error 403: FORBIDDEN. User is not authorized to access.");
    }

    public UnauthorizedUserException(String message) {
        super(message);
    }
}