package sg.edu.smu.cs203.pandanews.service.measurement;

import org.springframework.stereotype.Service;
import sg.edu.smu.cs203.pandanews.model.Measurement;

import java.util.List;

@Service
public interface MeasurementService {
    List<Measurement> displayMeasurements();
    Measurement getMeasurement(Long id);
    Measurement addMeasurement(Measurement measurement);
    Measurement updateMeasurement(Long id, Measurement newMeasurement);
    void deleteMeasurement(Long id);
}