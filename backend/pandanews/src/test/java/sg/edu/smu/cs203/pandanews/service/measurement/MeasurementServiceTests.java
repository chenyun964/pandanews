package sg.edu.smu.cs203.pandanews.service.measurement;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sg.edu.smu.cs203.pandanews.model.Measurement;
import sg.edu.smu.cs203.pandanews.repository.MeasurementRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MeasurementServiceTests {
    @InjectMocks
    private MeasurementServiceImpl measurementService;
    @Mock
    private MeasurementRepository measurementRepo;

    @Test
    void addMeasurement_Success() {
        Measurement p = new Measurement();
        p.setTitle("Test");
        when(measurementRepo.findByTitle(any(String.class))).thenReturn(new ArrayList<Measurement>());
        when(measurementRepo.save(any(Measurement.class))).thenReturn(p);
        Measurement result = measurementService.addMeasurement(p);

        assertNotNull(result);
        verify(measurementRepo).save(p);
        verify(measurementRepo).findByTitle("Test");
    }

    @Test
    void addMeasurement_Failure() {
        Measurement p = new Measurement();
        p.setTitle("Test");
        Measurement p1 = new Measurement();
        p1.setTitle("Test");
        List<Measurement> measurements = new ArrayList<>();
        measurements.add(p1);
        when(measurementRepo.findByTitle(any(String.class))).thenReturn(measurements);
        Measurement result = measurementService.addMeasurement(p);

        assertNull(result);
        verify(measurementRepo).findByTitle("Test");
    }

    @Test
    void getMeasurement_Success() {
        Measurement measurement = new Measurement();
        when(measurementRepo.findById(any(Long.class))).thenReturn(Optional.of(measurement));

        Measurement result = measurementService.getMeasurement(10L);
        assertNotNull(result);
        verify(measurementRepo).findById(10L);
    }

    @Test
    void getMeasurement_Failure() {
        when(measurementRepo.findById(any(Long.class))).thenReturn(Optional.empty());
        Measurement result = measurementService.getMeasurement(10L);
        assertNull(result);
        verify(measurementRepo).findById(10L);
    }

    @Test
    void deleteMeasurement_Success() {
        MeasurementServiceImpl mock = mock(MeasurementServiceImpl.class);
        doNothing().when(mock).deleteMeasurement(isA(Long.class));
        mock.deleteMeasurement(10L);
        verify(mock, times(1)).deleteMeasurement(10L);
    }

    @Test
    void updateMeasurement_ReturnUpdatedResult() {
        Measurement measurement = new Measurement();
        Measurement updatedMeasurement = new Measurement();

        updatedMeasurement.setTitle("updated");
        updatedMeasurement.setContent("updated");

        when(measurementRepo.findById(any(Long.class))).thenReturn(Optional.of(measurement));
        when(measurementRepo.save(any(Measurement.class))).thenReturn(updatedMeasurement);

        Measurement result = measurementService.updateMeasurement(10L, updatedMeasurement);

        assertNotNull(result);
        assertEquals("updated", result.getTitle());
        assertEquals("updated", result.getContent());
        verify(measurementRepo).findById(10L);
        verify(measurementRepo).save(measurement);
    }

    @Test
    void updateMeasurement_ReturnNull() {
        when(measurementRepo.findById(any(Long.class))).thenReturn(Optional.empty());
        Measurement result = measurementService.updateMeasurement(10L, new Measurement());

        assertNull(result);
        verify(measurementRepo).findById(10L);
    }

    @Test
    void displayMeasurements_Success() {
        List<Measurement> measurementArrayList = new ArrayList<>();
        Measurement s = new Measurement();
        measurementArrayList.add(s);

        when(measurementRepo.findAll()).thenReturn(measurementArrayList);

        List<Measurement> result = measurementService.displayMeasurements();

        assertNotNull(result);
        verify(measurementRepo).findAll();
    }

}
