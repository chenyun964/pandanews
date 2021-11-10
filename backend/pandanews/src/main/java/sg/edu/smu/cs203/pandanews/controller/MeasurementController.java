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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/measurements")
    public Measurement addMeasurement(@Valid @RequestBody Measurement measurement) {
        return measurementService.addMeasurement(measurement);
    }

    @GetMapping("/measurements")
    public List<Measurement> getMeasurements() {
        return measurementService.displayMeasurements();
    }

    @GetMapping("/measurements/{id}")
    public Measurement getMeasurement(@PathVariable Long id) {
        Measurement measurement = measurementService.getMeasurement(id);

        if (measurement == null)
            throw new MeasurementNotFoundException("Measurement not found");
        return measurementService.getMeasurement(id);
    }

    @PutMapping("/measurements/{id}")
    public Measurement updateMeasurement(@PathVariable Long id, @RequestBody Measurement newMeasurement) {
        Measurement measurement = measurementService.updateMeasurement(id, newMeasurement);
        if (measurement == null) throw new MeasurementNotFoundException("Measurement not found");
        return measurement;
    }

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