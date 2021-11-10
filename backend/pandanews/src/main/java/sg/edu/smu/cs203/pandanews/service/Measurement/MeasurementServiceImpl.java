package sg.edu.smu.cs203.pandanews.service.measurement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.smu.cs203.pandanews.model.Measurement;
import sg.edu.smu.cs203.pandanews.repository.MeasurementRepository;

import java.util.List;

@Service
public class MeasurementServiceImpl implements MeasurementService {
@Autowired

    private MeasurementRepository measurementRepo;

    /**
     * Override the addmeasurement function and reate new measurement
     *
     * @param measurement
     * @return created meansurement
     */

    @Override
    public Measurement addMeasurement(Measurement measurement) {
        List<Measurement> sameTitles = measurementRepo.findByTitle(measurement.getTitle());
        return sameTitles.size() == 0 ? measurementRepo.save(measurement) : null;
    }

    /**
     * Override the getMeasurement function and get a specific measurement
     *
     * @return the specific measurement or null if the measurement is not found
     */

    @Override
    public Measurement getMeasurement(Long id){
        return measurementRepo.findById(id).orElse(null);
    }

    /**
     * Override the deleteMeasurement function and delete a specific measurement id
     *
     * @param id
     */

    @Override
    public void deleteMeasurement(Long id) {
        measurementRepo.deleteById(id);
    }

    /**
     * Override the updateMeasurement function and update a specific measurement details 
     *
     * @param id
     * @param newMeasurement
     * @return the updated measurement
     */

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

    /**
     * Override the displayMeasurements function and list all measurements
     *
     * @return the list of measurements 
     */

    @Override
    public List<Measurement> displayMeasurements(){
        return measurementRepo.findAll();
    }
}