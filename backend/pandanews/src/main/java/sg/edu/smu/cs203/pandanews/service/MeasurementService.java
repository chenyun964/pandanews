package sg.edu.smu.cs203.pandanews.service;

import java.util.List;
import org.springframework.stereotype.Service;
import sg.edu.smu.cs203.pandanews.model.Measurement;

@Service
public interface MeasurementService {
    List<Measurement> displayMeasurements();
    Measurement getMeasurement(Long id);
    Measurement addMeasurement(Measurement measurement);
    Measurement updateMeasurement(Long id, Measurement newMeasurement);
    void deleteMeasurement(Long id);
}