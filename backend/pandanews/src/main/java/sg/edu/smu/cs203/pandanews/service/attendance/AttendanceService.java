package sg.edu.smu.cs203.pandanews.service.attendance;



import sg.edu.smu.cs203.pandanews.model.attendance.Attendance;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AttendanceService {

    Attendance markAttendance(Long userId);

    Attendance updateAttendance(Long userId, LocalDate date, LocalTime time);

    List<Attendance> findAttendancesByUserid(Long userId);

    Attendance findAttendanceByDate(Long userId, LocalDate date);

    Attendance findAttendanceById (Long id);
}
