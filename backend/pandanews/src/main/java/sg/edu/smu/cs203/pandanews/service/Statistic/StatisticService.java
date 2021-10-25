package sg.edu.smu.cs203.pandanews.service.Statistic;

import java.util.List;
import org.springframework.stereotype.Service;
import sg.edu.smu.cs203.pandanews.model.CovidStats.Statistic;

@Service
public interface StatisticService {
    List<Statistic> displayStatistics();
    Statistic getStatistic(Long id);
    Statistic addStatistic(Statistic statistic);
    Statistic updateStatistic(Long id, Statistic statistic);
    void deleteStatistic(Long id);
}