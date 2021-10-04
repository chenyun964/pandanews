package sg.edu.smu.cs203.pandanews.repository;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.edu.smu.cs203.pandanews.model.WorkGroup;

@Repository
public interface WorkGroupRepository extends JpaRepository <WorkGroup, Long> {
    List<WorkGroup> findByOrganisationId(Long organisationId);
    Optional<WorkGroup> findByIdAndOrganisationId(Long id, Long organisationId);
}
