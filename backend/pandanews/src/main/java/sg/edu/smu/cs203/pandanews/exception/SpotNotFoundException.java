package sg.edu.smu.cs203.pandanews.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SpotNotFoundException extends RuntimeException {
    public SpotNotFoundException() {}

    public SpotNotFoundException(String message) {
        super(message);
    }
}
