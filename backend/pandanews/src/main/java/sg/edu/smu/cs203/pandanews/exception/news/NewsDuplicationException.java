package sg.edu.smu.cs203.pandanews.exception.news;

public class NewsDuplicationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public NewsDuplicationException() {
    }

    public NewsDuplicationException(String title) {
        super("News exists: " + title);
    }
}
