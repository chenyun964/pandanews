package sg.edu.smu.cs203.pandanews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

import sg.edu.smu.cs203.pandanews.model.User;

@Repository
public interface UserRepository extends JpaRepository <User, Long>{
    Optional<User> findByUsername(String username);


    // SELECT * FROM pandanews.user where username = "test" AND authorities="ROLE_ADMIN";
    @Query("SELECT u FROM User u WHERE u.username = :username AND u.authorities = :role")
    Optional<User> findAdminByUsername(String username, String role);
}
