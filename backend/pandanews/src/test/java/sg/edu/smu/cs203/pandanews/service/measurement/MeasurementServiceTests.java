package sg.edu.smu.cs203.pandanews.service.measurement;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sg.edu.smu.cs203.pandanews.model.Measurement;
import sg.edu.smu.cs203.pandanews.model.Policy;
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
//
//    @Test
//    void getPolicy_Success() {
//        Policy policy = new Policy();
//        when(measurementRepo.findById(any(Long.class))).thenReturn(Optional.of(policy));
//
//        Policy result = measurementService.getPolicy(10L);
//        assertNotNull(result);
//        verify(measurementRepo).findById(10L);
//    }
//
//    @Test
//    void getPolicy_Failure() {
//        when(measurementRepo.findById(any(Long.class))).thenReturn(Optional.empty());
//
//        Policy result = measurementService.getPolicy(10L);
//        assertNull(result);
//        verify(measurementRepo).findById(10L);
//    }
//
//    @Test
//    void deletePolicy_Success() {
//        PolicyServiceImpl mock = mock(PolicyServiceImpl.class);
//        doNothing().when(mock).deletePolicy(isA(Long.class));
//        mock.deletePolicy(10L);
//        verify(mock, times(1)).deletePolicy(10L);
//    }
//
//    @Test
//    void updatePolicy_ReturnUpdatedResult() {
//        Policy policy = new Policy();
//        Policy updatePolicy = new Policy();
//
//        updatePolicy.setMessage("updated");
//        updatePolicy.setValidity(true);
//
//        when(measurementRepo.findById(any(Long.class))).thenReturn(Optional.of(policy));
//        when(measurementRepo.save(any(Policy.class))).thenReturn(updatePolicy);
//
//        Policy result = measurementService.updatePolicy(10L, updatePolicy);
//
//        assertNotNull(result);
//        assertEquals("updated", result.getMessage());
//        assertTrue(result.getValidity());
//        verify(measurementRepo).findById(10L);
//        verify(measurementRepo).save(policy);
//    }
//
//    @Test
//    void updatePolicy_ReturnNull() {
//        when(measurementRepo.findById(any(Long.class))).thenReturn(Optional.empty());
//        Policy result = measurementService.updatePolicy(10L, new Policy());
//
//        assertNull(result);
//        verify(measurementRepo).findById(10L);
//    }
//
//    @Test
//    void listPolicies_Success() {
//        List<Policy> policyList = new ArrayList<>();
//        Policy s = new Policy();
//        policyList.add(s);
//
//        when(measurementRepo.findByOrganisationId(any(Long.class))).thenReturn(policyList);
//
//        List<Policy> result = measurementService.listPolicies(10L);
//
//        assertNotNull(result);
//        verify(measurementRepo).findByOrganisationId(10L);
//    }

}
