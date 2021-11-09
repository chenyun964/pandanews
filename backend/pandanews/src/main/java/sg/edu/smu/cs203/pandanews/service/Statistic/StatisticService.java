package sg.edu.smu.cs203.pandanews.service.statistic;

import java.util.List;
import org.springframework.stereotype.Service;
import sg.edu.smu.cs203.pandanews.model.Statistic;
import sg.edu.smu.cs203.pandanews.model.StatSummary;

@Service
public interface StatisticService {
    List<Statistic> displayStatistics();
    Statistic getStatistic(Long id);
    Statistic addStatistic(Statistic statistic);
    Statistic updateStatistic(Long id, Statistic newStatistic);
    void deleteStatistic(Long id);
    StatSummary getSummary();
}