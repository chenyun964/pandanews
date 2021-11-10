package sg.edu.smu.cs203.pandanews.service.statistic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sg.edu.smu.cs203.pandanews.model.Statistic;
import sg.edu.smu.cs203.pandanews.repository.AttendanceRepository;
import sg.edu.smu.cs203.pandanews.repository.StatisticRepository;
import sg.edu.smu.cs203.pandanews.repository.UserRepository;
import sg.edu.smu.cs203.pandanews.service.attendance.AttendanceServiceImpl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StatisticServiceTests {
    @InjectMocks
    private StatisticServiceImpl statisticService;
    @Mock
    private StatisticRepository statisticRepository;

    @Test
    void addStatistic_Success() {
        Statistic s = new Statistic();
        when(statisticRepository.save(any(Statistic.class))).thenReturn(s);
        Statistic result = statisticService.addStatistic(s);

        assertNotNull(result);
        verify(statisticRepository).save(s);
    }

    @Test
    void addStatistic_Failure() {
        Statistic s = new Statistic();
        when(statisticRepository.save(any(Statistic.class))).thenReturn(null);
        Statistic result = statisticService.addStatistic(s);

        assertNull(result);
        verify(statisticRepository).save(s);
    }
}
