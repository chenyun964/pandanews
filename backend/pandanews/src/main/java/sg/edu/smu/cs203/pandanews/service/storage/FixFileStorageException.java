package sg.edu.smu.cs203.pandanews.service.storage;

public class FixFileStorageException extends RuntimeException {
    public FixFileStorageException(String message) {
        super(message);
    }

    public FixFileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}