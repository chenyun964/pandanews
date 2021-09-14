package sg.edu.smu.cs203.pandanews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.edu.smu.cs203.pandanews.model.Example;

@Repository
public interface ExampleRepository extends JpaRepository <Example, Long> {

}
