package sg.edu.smu.cs203.pandanews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;

import sg.edu.smu.cs203.pandanews.model.Organisation;

@Repository
public interface OrganisationRepository extends JpaRepository <Organisation, Long>{
    Optional<Organisation> findByOwnerId(Long owner_id);
    Optional<Organisation> findByCode(String code);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.organisation = NULL WHERE u.organisation.id = :id")
    void setOrganisationToNull(Long id);
}
