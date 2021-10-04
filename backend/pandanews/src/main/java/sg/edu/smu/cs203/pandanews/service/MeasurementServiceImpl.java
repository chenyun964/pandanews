package sg.edu.smu.cs203.pandanews.service;

import org.springframework.stereotype.Service;

import java.util.List;
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
    public void deleteMeasurement(Long id) {
        measurementRepo.deleteById(id);
    }

    @Override
    public Measurement updateMeasurement(Long id, Measurement newMeasurement){
        return measurementRepo.findById(id).map
        (measurements -> {
            measurements.setTitle(newMeasurement.getTitle());
            measurements.setImageUrl(newMeasurement.getImageUrl());
            measurements.setContent(newMeasurement.getContent());
            return measurementRepo.save(newMeasurement);
        }).orElse(null);
    }

    @Override
    public List<Measurement> displayMeasurements(){
        return measurementRepo.findAll();

    }
}