package sg.edu.smu.cs203.pandanews.controller;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sg.edu.smu.cs203.pandanews.exception.MeasurementNotFoundException;
import sg.edu.smu.cs203.pandanews.model.Measurement;
import sg.edu.smu.cs203.pandanews.service.measurement.MeasurementService;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
public class MeasurementController {
    private MeasurementService measurementService;

    public MeasurementController(MeasurementService ms) {
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
    public Measurement addMeasurement(@Valid @RequestBody Measurement measurement) {
        return measurementService.addMeasurement(measurement);
    }

    /**
     * list all the measurements
     *
     * @return the measurement list
     */

    @GetMapping("/measurements")
    public List<Measurement> getMeasurements() {
        return measurementService.displayMeasurements();
    }

    /**
     * find a measurement by an id
     *
     * @param id
     * @return the measurement with a specific id
     */

    @GetMapping("/measurements/{id}")
    public Measurement getMeasurement(@PathVariable Long id) {
        Measurement measurement = measurementService.getMeasurement(id);

        if (measurement == null)
            throw new MeasurementNotFoundException("Measurement not found");
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
    public Measurement updateMeasurement(@PathVariable Long id, @RequestBody Measurement newMeasurement) {
        Measurement measurement = measurementService.updateMeasurement(id, newMeasurement);
        if (measurement == null) throw new MeasurementNotFoundException("Measurement not found");
        return measurement;
    }

    /**
     * delete a specific measurement id
     *
     * @param id
     */

    @DeleteMapping("/measurements/{id}")
    public void deleteMeasurement(@PathVariable Long id) {
        // try{
        //     measurementService.deleteMeasurement(id);
        //  }catch(EmptyResultDataAccessException e) {
        //     //throw new MeasurementNotFoundException(id);
        //  }
        measurementService.deleteMeasurement(id);
    }

}