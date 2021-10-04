package sg.edu.smu.cs203.pandanews.service;

import org.springframework.stereotype.Service;

import sg.edu.smu.cs203.pandanews.model.Measurement;
import sg.edu.smu.cs203.pandanews.repository.MeasurementRepository;

@Service
public class MeasurementServiceImpl implements MeasurementService {

    private MeasurementRepository measurementRepo;

    @Override
    public Measurement getMeasurement(Long id){
        return measurementRepo.findById(id).orElse(null);
    }

    @Override
    public Measurement deleteMeasurement(Long id, Measurement measurement) {
        measurementRepo.deleteById(id);
    }

    @Override
    public Measurement updateMeasurement(Long id, Measurement newMeasurement){
        return measurementRepo.findById(id).map
        (measurementRepo -> {
            measurementRepo.setTitle(newMeasurement.getTitle());
            measurementRepo.setImage(newMeasurement.getImage());
            measurementRepo.setContent(newMeasurement.getDescr());
            measurementRepo.setDate(newMeasurement.getDate());
            return measurementRepo.save(measurementRepo);
        }).orElse(null);
    }

    @Override
    public List<Measurement> displayMeasurements(){
        return new ArrayList(measurementRepo.values());

    }
}