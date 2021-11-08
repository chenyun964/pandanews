package sg.edu.smu.cs203.pandanews.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import sg.edu.smu.cs203.pandanews.model.Statistic;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, Long> {
    @Query(value = "SELECT sum(new_cases) FROM Statistic", nativeQuery = true)
    Long getTotalCases();

    @Query(value =  "SELECT sum(new_deaths) FROM Statistic", nativeQuery = true)
    Long getTotalDeath();

    @Query(value = "SELECT sum(new_recovery) FROM Statistic", nativeQuery = true)
    Long getTotalRecovery();

    @Query(value = "SELECT * FROM Statistic Order By updated_at desc limit 1;", nativeQuery = true)
    Statistic getUpdatedAt();
}
