package sg.edu.smu.cs203.pandanews.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StatisticNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public StatisticNotFoundException() {
    }

    public StatisticNotFoundException(String message) {
        super("News Not Found");
    }
}
