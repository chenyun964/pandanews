package sg.edu.smu.cs203.pandanews.service.measurement;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import sg.edu.smu.cs203.pandanews.model.Measurement;
import sg.edu.smu.cs203.pandanews.repository.MeasurementRepository;

@Service
public class MeasurementServiceImpl implements MeasurementService {
@Autowired

    private MeasurementRepository measurementRepo;

    @Override
    public Measurement addMeasurement(Measurement measurement) {
        List<Measurement> sameTitles = measurementRepo.findByTitle(measurement.getTitle());
        if(sameTitles.size() == 0)
            return measurementRepo.save(measurement);
        else
            return null;
    }

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
        (measurement -> {
            measurement.setTitle(newMeasurement.getTitle());
            measurement.setImageUrl(newMeasurement.getImageUrl());
            measurement.setContent(newMeasurement.getContent());
            return measurementRepo.save(measurement);
        }).orElse(null);
    }

    @Override
    public List<Measurement> displayMeasurements(){
        return measurementRepo.findAll();

    }
}