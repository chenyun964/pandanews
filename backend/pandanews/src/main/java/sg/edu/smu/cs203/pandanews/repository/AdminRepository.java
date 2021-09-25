package sg.edu.smu.cs203.pandanews.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.edu.smu.cs203.pandanews.model.Admin;

@Repository
public interface AdminRepository extends JpaRepository <Admin, Long>{
    Optional<Admin> findByUsername(String username);
}
