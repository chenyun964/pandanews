package sg.edu.smu.cs203.pandanews.service.statistic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sg.edu.smu.cs203.pandanews.model.StatSummary;
import sg.edu.smu.cs203.pandanews.model.Statistic;
import sg.edu.smu.cs203.pandanews.repository.StatisticRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StatisticServiceTests {
    @InjectMocks
    private StatisticServiceImpl statisticService;
    @Mock
    private StatisticRepository statisticRepo;

    @Test
    void addStatistic_Success() {
        Statistic s = new Statistic();
        when(statisticRepo.save(any(Statistic.class))).thenReturn(s);
        Statistic result = statisticService.addStatistic(s);

        assertNotNull(result);
        verify(statisticRepo).save(s);
    }

    @Test
    void addStatistic_Failure() {
        Statistic s = new Statistic();
        when(statisticRepo.save(any(Statistic.class))).thenReturn(null);
        Statistic result = statisticService.addStatistic(s);

        assertNull(result);
        verify(statisticRepo).save(s);
    }

    @Test
    void getStatistic_Success() {
        Statistic s = new Statistic();
        when(statisticRepo.findById(any(Long.class))).thenReturn(Optional.of(s));

        Statistic result = statisticService.getStatistic(10L);
        assertNotNull(result);
        verify(statisticRepo).findById(10L);
    }

    @Test
    void getStatistic_Failure() {
        when(statisticRepo.findById(any(Long.class))).thenReturn(Optional.empty());

        Statistic result = statisticService.getStatistic(10L);
        assertNull(result);
        verify(statisticRepo).findById(10L);
    }

    @Test
    void deleteStatistic_Success() {
        StatisticServiceImpl mock = mock(StatisticServiceImpl.class);
        doNothing().when(mock).deleteStatistic(isA(Long.class));
        mock.deleteStatistic(10L);
        verify(mock, times(1)).deleteStatistic(10L);
    }

    @Test
    void updateStatistics_ReturnUpdatedResult() {
        Statistic stats = new Statistic();
        Statistic updateStats = new Statistic();

        updateStats.setNewCases(1);
        updateStats.setNewDeaths(1);
        updateStats.setNewRecovery(1);

        when(statisticRepo.findById(any(Long.class))).thenReturn(Optional.of(stats));
        when(statisticRepo.save(any(Statistic.class))).thenReturn(updateStats);

        Statistic result = statisticService.updateStatistic(10L, updateStats);

        assertNotNull(result);
        assertEquals(1, result.getNewCases());
        assertEquals(1, result.getNewDeaths());
        assertEquals(1, result.getNewRecovery());
        verify(statisticRepo).findById(10L);
        verify(statisticRepo).save(stats);
    }

    @Test
    void updateStatistics_ReturnNull() {
        when(statisticRepo.findById(any(Long.class))).thenReturn(Optional.empty());
        Statistic result = statisticService.updateStatistic(10L, new Statistic());

        assertNull(result);
        verify(statisticRepo).findById(10L);
    }

    @Test
    void displayStatistics_Success() {
        List<Statistic> statisticList = new ArrayList<>();
        Statistic s = new Statistic();
        statisticList.add(s);
        when(statisticRepo.findAll()).thenReturn(statisticList);

        List<Statistic> result = statisticService.displayStatistics();

        assertNotNull(result);
        verify(statisticRepo).findAll();
    }

    @Test
    void getSummary_Success() {
        when(statisticRepo.getTotalDeath()).thenReturn(1L);
        when(statisticRepo.getTotalCases()).thenReturn(2L);
        when(statisticRepo.getTotalRecovery()).thenReturn(3L);
        when(statisticRepo.getUpdatedAt()).thenReturn(new Statistic());
        StatSummary result = statisticService.getSummary();
        assertNotNull(result);
        assertEquals(1L, result.getTotalDeath());
        assertEquals(2L, result.getTotalCases());
        assertEquals(3L, result.getTotalRecovery());
        verify(statisticRepo).getUpdatedAt();
        verify(statisticRepo).getTotalRecovery();
        verify(statisticRepo).getTotalCases();
        verify(statisticRepo).getTotalDeath();
    }

}
