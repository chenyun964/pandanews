package sg.edu.smu.cs203.pandanews.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.edu.smu.cs203.pandanews.model.TestSpot;

@Repository
public interface TestSpotRepository extends JpaRepository <TestSpot, Long> {
    public Optional<TestSpot> findByName(String name);
    public List<TestSpot> findByType(String type);
}
