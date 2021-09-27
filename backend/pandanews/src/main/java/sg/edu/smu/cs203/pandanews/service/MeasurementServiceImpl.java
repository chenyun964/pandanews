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
    public Measurement updateMeasurement(Long id, Measurement Newmeasurement){
        return measurementRepo.findById(id).map
        (measurementRepo -> {
            measurementRepo.setTitle(Newmeasurement.getTitle());
            measurementRepo.setImage(Newmeasurement.getImage());
            measurementRepo.setContent(Newmeasurement.getDescr());
            measurementRepo.setDate(Newmeasurement.getDate());
            return measurementRepo.save(measurementRepo);
        }).orElse(null);
    }
}