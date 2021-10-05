package sg.edu.smu.cs203.pandanews.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthenticatedException extends RuntimeException {
    public UnauthenticatedException() {
        super("Error 403: UNAUTHORIZED. User lacks authentication credentials.");
    }

    public UnauthenticatedException(String message) {
        super(message);
    }
}