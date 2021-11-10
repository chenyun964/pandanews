package sg.edu.smu.cs203.pandanews.service.testspot;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sg.edu.smu.cs203.pandanews.model.TestSpot;
import sg.edu.smu.cs203.pandanews.repository.TestSpotRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TestSpotServiceTests {

    @InjectMocks
    private TestSpotServiceImpl testSpotService;
    @Mock
    private TestSpotRepository testSpotRepo;

    @Test
    void addTestSpot_Success() {
        TestSpot t = new TestSpot();
        when(testSpotRepo.save(any(TestSpot.class))).thenReturn(t);
        TestSpot result = testSpotService.add(t);

        assertNotNull(result);
        verify(testSpotRepo).save(t);
    }

    @Test
    void addTestSpot_Failure() {
        TestSpot t = new TestSpot();
        when(testSpotRepo.save(any(TestSpot.class))).thenReturn(null);
        TestSpot result = testSpotService.add(t);

        assertNull(result);
        verify(testSpotRepo).save(t);
    }

    @Test
    void getById_Success() {
        TestSpot testSpot = new TestSpot();
        when(testSpotRepo.findById(any(Long.class))).thenReturn(Optional.of(testSpot));

        TestSpot result = testSpotService.getById(10L);
        assertNotNull(result);
        verify(testSpotRepo).findById(10L);
    }

    @Test
    void getById_Failure() {
        when(testSpotRepo.findById(any(Long.class))).thenReturn(Optional.empty());
        TestSpot result = testSpotService.getById(10L);
        assertNull(result);
        verify(testSpotRepo).findById(10L);
    }

    @Test
    void getByName_Success() {
        TestSpot testSpot = new TestSpot();
        when(testSpotRepo.findByName(any(String.class))).thenReturn(Optional.of(testSpot));
        TestSpot result = testSpotService.getByName("Test");
        assertNotNull(result);
        verify(testSpotRepo).findByName("Test");
    }

    @Test
    void getByName_Failure() {
        when(testSpotRepo.findByName(any(String.class))).thenReturn(Optional.empty());
        TestSpot result = testSpotService.getByName("Test");
        assertNull(result);
        verify(testSpotRepo).findByName("Test");
    }

    @Test
    void deleteTestSpot_Success() {
        TestSpot testSpot = new TestSpot();
        when(testSpotRepo.findById(any(Long.class))).thenReturn(Optional.of(testSpot));
        doNothing().when(testSpotRepo).delete(any(TestSpot.class));
        TestSpot result = testSpotService.deleteById(10L);
        assertNotNull(result);
        verify(testSpotRepo, times(1)).delete(testSpot);

    }

    @Test
    void updateTestSpot_ReturnUpdatedResult() {
        TestSpot testSpot = new TestSpot();
        TestSpot updatedTestSpot = new TestSpot();

        updatedTestSpot.setType("updated");
        updatedTestSpot.setContact("contact");

        when(testSpotRepo.findById(any(Long.class))).thenReturn(Optional.of(testSpot));
        when(testSpotRepo.save(any(TestSpot.class))).thenReturn(updatedTestSpot);

        TestSpot result = testSpotService.update(10L, updatedTestSpot);

        assertNotNull(result);
        assertEquals("updated", result.getType());
        assertEquals("contact", result.getContact());
        verify(testSpotRepo).findById(10L);
        verify(testSpotRepo).save(testSpot);
    }

    @Test
    void updateTestSpot_ReturnNull() {
        when(testSpotRepo.findById(any(Long.class))).thenReturn(Optional.empty());
        TestSpot result = testSpotService.update(10L, new TestSpot());

        assertNull(result);
        verify(testSpotRepo).findById(10L);
    }

    @Test
    void listAll_Success() {
        List<TestSpot> testSpotArrayList = new ArrayList<>();
        TestSpot t = new TestSpot();
        testSpotArrayList.add(t);

        when(testSpotRepo.findAll()).thenReturn(testSpotArrayList);

        List<TestSpot> result = testSpotService.listAll();

        assertNotNull(result);
        verify(testSpotRepo).findAll();
    }

    @Test
    void listByType_Success() {
        List<TestSpot> testSpotArrayList = new ArrayList<>();
        TestSpot t = new TestSpot();
        t.setType("Test");
        testSpotArrayList.add(t);

        when(testSpotRepo.findByType(any(String.class))).thenReturn(testSpotArrayList);

        List<TestSpot> result = testSpotService.listByType(t.getType());
        assertNotNull(result);
        verify(testSpotRepo).findByType("Test");
    }
}
