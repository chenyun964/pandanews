package sg.edu.smu.cs203.pandanews.service.statistic;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import sg.edu.smu.cs203.pandanews.model.Statistic;
import sg.edu.smu.cs203.pandanews.repository.StatisticRepository;

import sg.edu.smu.cs203.pandanews.model.StatSummary;

@Service
public class StatisticServiceImpl implements StatisticService {
    @Autowired
    private StatisticRepository statisticRepo;

    @Override
    public Statistic addStatistic(Statistic statistic) {
        return statisticRepo.save(statistic);
    }

    @Override
    public Statistic getStatistic(Long id) {
        return statisticRepo.findById(id).orElse(null);
    }

    @Override
    public void deleteStatistic(Long id) {
        statisticRepo.deleteById(id);
    }

    @Override
    public Statistic updateStatistic(Long id, Statistic newStatistic) {
        return statisticRepo.findById(id).map
                (Statistic -> {
                    Statistic.setNewCases(newStatistic.getNewCases());
                    Statistic.setNewDeaths(newStatistic.getNewDeaths());
                    Statistic.setNewRecovery(newStatistic.getNewRecovery());
                    return statisticRepo.save(Statistic);
                }).orElse(null);
    }

    @Override
    public List<Statistic> displayStatistics() {
        return statisticRepo.findAll();
    }

    @Override
    public StatSummary getSummary() {
        StatSummary summary = new StatSummary();
        summary.setTotalCases(statisticRepo.getTotalCases());
        summary.setTotalDeath(statisticRepo.getTotalDeath());
        summary.setTotalRecovery(statisticRepo.getTotalRecovery());
        summary.setLastedRecord(statisticRepo.getUpdatedAt());
        return summary;
    }
}