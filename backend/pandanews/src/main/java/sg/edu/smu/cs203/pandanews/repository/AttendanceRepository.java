package sg.edu.smu.cs203.pandanews.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sg.edu.smu.cs203.pandanews.model.Attendance.Attendance;
import sg.edu.smu.cs203.pandanews.model.User;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    @Query("SELECT a FROM Attendance a WHERE a.aDate = :date AND a.user.id = :userId")
    Attendance findByADate(Long userId, LocalDate date);

    List<Attendance> findByUser(User u);
}
