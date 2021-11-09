package sg.edu.smu.cs203.pandanews.exception;

public class FixFileStorageException extends RuntimeException {
    public FixFileStorageException(String message) {
        super(message);
    }

    public FixFileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}