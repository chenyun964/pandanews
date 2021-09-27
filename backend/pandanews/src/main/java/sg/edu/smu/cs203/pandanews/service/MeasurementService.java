package sg.edu.smu.cs203.pandanews.service;

import org.springframework.stereotype.Service;
import sg.edu.smu.cs203.pandanews.model.Measurement;

@Service
public interface MeasurementService {
    Measurement getMeasurement(Long id);
    Measurement updateMeasurement(Measurement measurement);
    Measurement deleteMeasurement(Long id);
}