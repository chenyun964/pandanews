package sg.edu.smu.cs203.pandanews.exception.news;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NewsDuplicationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public NewsDuplicationException() {
    }

    public NewsDuplicationException(String title) {
        super("News exists: " + title);
    }
}
