package sg.edu.smu.cs203.pandanews.service.Statistic;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import sg.edu.smu.cs203.pandanews.model.Statistic;
import sg.edu.smu.cs203.pandanews.repository.StatisticRepository;

@Service
public class StatisticServiceImpl implements StatisticService {
@Autowired

    private StatisticRepository StatisticRepo;
    @Override
    public Statistic addStatistic(Statistic Statistic) {
        
            return StatisticRepo.save(Statistic);

    }

    @Override
    public Statistic getStatistic(Long id){
        return StatisticRepo.findById(id).orElse(null);
    }

    @Override
    public void deleteStatistic(Long id) {
        StatisticRepo.deleteById(id);
    }

    @Override
    public Statistic updateStatistic(Long id, Statistic newStatistic){
        return StatisticRepo.findById(id).map
        (Statistic -> {
            Statistic.setNewCases(newStatistic.getNewCases());
            Statistic.setNewDeaths(newStatistic.getNewDeaths());
            Statistic.setNewRecovery(newStatistic.getNewRecovery());
            Statistic.setTotalCases(newStatistic.getTotalCases());
            Statistic.setTotalDeaths(newStatistic.getTotalDeaths());
            Statistic.setTotalRecovery(newStatistic.getTotalRecovery());
            return StatisticRepo.save(Statistic);
        }).orElse(null);
    }

    @Override
    public List<Statistic> displayStatistics(){
        return StatisticRepo.findAll();
    }
}