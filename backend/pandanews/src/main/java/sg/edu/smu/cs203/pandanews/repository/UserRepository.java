package sg.edu.smu.cs203.pandanews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sg.edu.smu.cs203.pandanews.model.user.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User, Long>{
    @Query("SELECT u FROM User u WHERE u.username = :username AND u.authorities != 'ROLE_ADMIN'")
    Optional<User> findByUsername(String username);

    List<User> findByOrganisationId(Long organisationId);

    // SELECT * FROM pandanews.user where username = "test" AND authorities="ROLE_ADMIN";
    @Query("SELECT u FROM User u WHERE u.username = :username AND u.authorities = :role")
    Optional<User> findAdminByUsername(String username, String role);


}
