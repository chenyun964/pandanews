package sg.edu.smu.cs203.pandanews.exception;

public class MeasurementNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MeasurementNotFoundException() {
    }

    public MeasurementNotFoundException(String title) {
        super("Measurement Not Found");
    }
}
