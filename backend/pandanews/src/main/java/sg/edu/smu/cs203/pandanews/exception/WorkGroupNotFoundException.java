package sg.edu.smu.cs203.pandanews.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WorkGroupNotFoundException extends RuntimeException {
    public WorkGroupNotFoundException() {
        super("Error 404: Work Group not found.");
    }

    public WorkGroupNotFoundException(String message) {
        super(message);
    }
}