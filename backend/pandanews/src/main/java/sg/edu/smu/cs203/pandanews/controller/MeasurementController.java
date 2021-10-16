package sg.edu.smu.cs203.pandanews.controller;

//import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import sg.edu.smu.cs203.pandanews.model.Measurement;
import sg.edu.smu.cs203.pandanews.service.Measurement.MeasurementService;

@RestController
public class MeasurementController {
    private MeasurementService measurementService;

    public MeasurementController(MeasurementService ms){
        this.measurementService = ms;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/measurements/admin")
    public Measurement addMeasurement(@Valid @RequestBody Measurement measurement){
        Measurement savedMeasurement = measurementService.addMeasurement(measurement);
        // if (savedMeasurement ==  null) throw new BookExistsException(measurement.getTitle());
        return savedMeasurement;
    }

    @GetMapping("/measurements")
    public List<Measurement> getMeasurements(){
        return measurementService.displayMeasurements();
    }

    @GetMapping("/measurements/{id}")
    public Measurement getMeasurement(@PathVariable Long id){
        Measurement measurement = measurementService.getMeasurement(id);

        if(measurement == null) 
            return null;
        return measurementService.getMeasurement(id);
    }

    @PutMapping("/measurements/{id}")
    public Measurement updateMeasurement(@PathVariable Long id, @RequestBody Measurement newMeasurement){
        Measurement measurement = measurementService.updateMeasurement(id, newMeasurement);
        if(measurement == null) return null;
        return measurement;
    }

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