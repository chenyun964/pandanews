package sg.edu.smu.cs203.pandanews.service.vaccispot;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sg.edu.smu.cs203.pandanews.model.VacciSpot;
import sg.edu.smu.cs203.pandanews.repository.VacciSpotRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class VacciSpotServiceTests {

    @InjectMocks
    private VacciSpotServiceImpl vacciSpotService;
    @Mock
    private VacciSpotRepository vacciSpotRepo;

    @Test
    void addvacciSpot_Success() {
        VacciSpot t = new VacciSpot();
        when(vacciSpotRepo.save(any(VacciSpot.class))).thenReturn(t);
        VacciSpot result = vacciSpotService.add(t);

        assertNotNull(result);
        verify(vacciSpotRepo).save(t);
    }

    @Test
    void addvacciSpot_Failure() {
        VacciSpot t = new VacciSpot();
        when(vacciSpotRepo.save(any(VacciSpot.class))).thenReturn(null);
        VacciSpot result = vacciSpotService.add(t);

        assertNull(result);
        verify(vacciSpotRepo).save(t);
    }

    @Test
    void getById_Success() {
        VacciSpot vacciSpot = new VacciSpot();
        when(vacciSpotRepo.findById(any(Long.class))).thenReturn(Optional.of(vacciSpot));

        VacciSpot result = vacciSpotService.getById(10L);
        assertNotNull(result);
        verify(vacciSpotRepo).findById(10L);
    }

    @Test
    void getById_Failure() {
        when(vacciSpotRepo.findById(any(Long.class))).thenReturn(Optional.empty());
        VacciSpot result = vacciSpotService.getById(10L);
        assertNull(result);
        verify(vacciSpotRepo).findById(10L);
    }

    @Test
    void getByName_Success() {
        VacciSpot vacciSpot = new VacciSpot();
        when(vacciSpotRepo.findByName(any(String.class))).thenReturn(Optional.of(vacciSpot));
        VacciSpot result = vacciSpotService.getByName("Test");
        assertNotNull(result);
        verify(vacciSpotRepo).findByName("Test");
    }

    @Test
    void getByName_Failure() {
        when(vacciSpotRepo.findByName(any(String.class))).thenReturn(Optional.empty());
        VacciSpot result = vacciSpotService.getByName("Test");
        assertNull(result);
        verify(vacciSpotRepo).findByName("Test");
    }

    @Test
    void deletevacciSpot_Success() {
        VacciSpot vacciSpot = new VacciSpot();
        when(vacciSpotRepo.findById(any(Long.class))).thenReturn(Optional.of(vacciSpot));
        doNothing().when(vacciSpotRepo).delete(any(VacciSpot.class));
        VacciSpot result = vacciSpotService.deleteById(10L);
        assertNotNull(result);
        verify(vacciSpotRepo, times(1)).delete(vacciSpot);

    }

    @Test
    void updatevacciSpot_ReturnUpdatedResult() {
        VacciSpot vacciSpot = new VacciSpot();
        VacciSpot updatedvacciSpot = new VacciSpot();

        updatedvacciSpot.setType("updated");
        updatedvacciSpot.setRegion("region");

        when(vacciSpotRepo.findById(any(Long.class))).thenReturn(Optional.of(vacciSpot));
        when(vacciSpotRepo.save(any(VacciSpot.class))).thenReturn(updatedvacciSpot);

        VacciSpot result = vacciSpotService.update(10L, updatedvacciSpot);

        assertNotNull(result);
        assertEquals("updated", result.getType());
        assertEquals("region", result.getRegion());
        verify(vacciSpotRepo).findById(10L);
        verify(vacciSpotRepo).save(vacciSpot);
    }

    @Test
    void updatevacciSpot_ReturnNull() {
        when(vacciSpotRepo.findById(any(Long.class))).thenReturn(Optional.empty());
        VacciSpot result = vacciSpotService.update(10L, new VacciSpot());

        assertNull(result);
        verify(vacciSpotRepo).findById(10L);
    }

    @Test
    void listAll_Success() {
        List<VacciSpot> vacciSpotArrayList = new ArrayList<>();
        VacciSpot t = new VacciSpot();
        vacciSpotArrayList.add(t);

        when(vacciSpotRepo.findAll()).thenReturn(vacciSpotArrayList);

        List<VacciSpot> result = vacciSpotService.listAll();

        assertNotNull(result);
        verify(vacciSpotRepo).findAll();
    }

    @Test
    void listByType_Success() {
        List<VacciSpot> vacciSpotArrayList = new ArrayList<>();
        VacciSpot t = new VacciSpot();
        t.setType("Test");
        vacciSpotArrayList.add(t);

        when(vacciSpotRepo.findByType(any(String.class))).thenReturn(vacciSpotArrayList);

        List<VacciSpot> result = vacciSpotService.listByType(t.getType());
        assertNotNull(result);
        verify(vacciSpotRepo).findByType("Test");
    }
}
