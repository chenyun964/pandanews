package sg.edu.smu.cs203.pandanews.controller;


import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import sg.edu.smu.cs203.pandanews.model.Measurement;
import sg.edu.smu.cs203.pandanews.service.measurement.MeasurementService;

@RestController
@CrossOrigin
public class MeasurementController {
    private MeasurementService measurementService;

    public MeasurementController(MeasurementService ms){
        this.measurementService = ms;
    }

    /**
     * Create new measurement
     *
     * @param measurement
     * @return created meansurement
     */

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/measurements")
    public Measurement addMeasurement(@Valid @RequestBody Measurement measurement){
        Measurement savedMeasurement = measurementService.addMeasurement(measurement);
        // if (savedMeasurement ==  null) throw new BookExistsException(measurement.getTitle());
        return savedMeasurement;
    }

    /**
     * list all the measurements
     *
     * @return the measurement list
     */

    @GetMapping("/measurements")
    public List<Measurement> getMeasurements(){
        return measurementService.displayMeasurements();
    }

    /**
     * find a measurement by an id
     *
     * @param id
     * @return the measurement with a specific id
     */

    @GetMapping("/measurements/{id}")
    public Measurement getMeasurement(@PathVariable Long id){
        Measurement measurement = measurementService.getMeasurement(id);

        if(measurement == null) 
            return null;
        return measurementService.getMeasurement(id);
    }

    /**
     * update a specific measurement details 
     *
     * @param id
     * @param newMeasurement
     * @return the updated measurement
     */

    @PutMapping("/measurements/{id}")
    public Measurement updateMeasurement(@PathVariable Long id, @RequestBody Measurement newMeasurement){
        Measurement measurement = measurementService.updateMeasurement(id, newMeasurement);
        if(measurement == null) return null;
        return measurement;
    }

    /**
     * delete a specific measurement id
     *
     * @param id
     */

    @DeleteMapping("/measurements/{id}")
    public void deleteMeasurement(@PathVariable Long id){
        // try{
        //     measurementService.deleteMeasurement(id);
        //  }catch(EmptyResultDataAccessException e) {
        //     //throw new MeasurementNotFoundException(id);
        //  }
        measurementService.deleteMeasurement(id);
    }

}