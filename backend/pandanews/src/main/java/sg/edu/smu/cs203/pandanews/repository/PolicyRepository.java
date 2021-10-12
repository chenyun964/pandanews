package sg.edu.smu.cs203.pandanews.repository;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.edu.smu.cs203.pandanews.model.Policy;

@Repository
public interface PolicyRepository extends JpaRepository <Policy, Long> {
    List<Policy> findByOrganisationId(Long organisationId);
    Optional<Policy> findByIdAndOrganisationId(Long id, Long organisationId);
}
