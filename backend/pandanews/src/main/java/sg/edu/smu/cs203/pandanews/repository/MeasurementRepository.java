package sg.edu.smu.cs203.pandanews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.edu.smu.cs203.pandanews.model.Measurement;


@Repository
public interface MeasurementRepository extends JpaRepository <Measurement, Long>{
    
}