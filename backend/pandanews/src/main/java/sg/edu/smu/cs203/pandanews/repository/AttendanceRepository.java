package sg.edu.smu.cs203.pandanews.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sg.edu.smu.cs203.pandanews.model.attendance.Attendance;
import sg.edu.smu.cs203.pandanews.model.user.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    @Query("SELECT a FROM Attendance a WHERE a.punchInDate = :date AND a.user.id = :userId")
    Optional<Attendance> findByDate(Long userId, LocalDate date);

    List<Attendance> findByUser(User u);
}
