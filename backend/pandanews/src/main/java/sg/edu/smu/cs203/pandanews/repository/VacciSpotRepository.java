package sg.edu.smu.cs203.pandanews.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.edu.smu.cs203.pandanews.model.VacciSpot;

@Repository
public interface VacciSpotRepository extends JpaRepository <VacciSpot, Long> {
    public Optional<VacciSpot> findByName(String name);
    public List<VacciSpot> findByType(String type);
    public List<VacciSpot> findByRegion(String region);
    public List<VacciSpot> findByVacciType(String vacciType);
}
