package sg.edu.smu.cs203.pandanews.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("Error 404: User not found.");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}