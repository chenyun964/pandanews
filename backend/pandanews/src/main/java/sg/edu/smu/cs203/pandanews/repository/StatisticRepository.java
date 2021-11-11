package sg.edu.smu.cs203.pandanews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sg.edu.smu.cs203.pandanews.model.Statistic;

import java.util.List;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, Long> {
    List<Statistic> findAllByOrderByDateDesc();

    @Query(value = "SELECT sum(new_cases) FROM statistic", nativeQuery = true)
    Long getTotalCases();

    @Query(value =  "SELECT sum(new_deaths) FROM statistic", nativeQuery = true)
    Long getTotalDeath();

    @Query(value = "SELECT sum(new_recovery) FROM statistic", nativeQuery = true)
    Long getTotalRecovery();

    @Query(value = "SELECT * FROM statistic Order By updated_at desc limit 1;", nativeQuery = true)
    Statistic getUpdatedAt();
}
