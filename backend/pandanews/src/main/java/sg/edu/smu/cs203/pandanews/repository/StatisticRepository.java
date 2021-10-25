package sg.edu.smu.cs203.pandanews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.smu.cs203.pandanews.model.CovidStats.Statistic;

public interface StatisticRepository extends JpaRepository<Statistic, Long> {
    List<Statistic> findById(Long id);
}
