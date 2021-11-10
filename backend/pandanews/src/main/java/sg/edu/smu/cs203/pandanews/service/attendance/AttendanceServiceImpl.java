package sg.edu.smu.cs203.pandanews.service.attendance;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.smu.cs203.pandanews.model.attendance.Attendance;
import sg.edu.smu.cs203.pandanews.repository.AttendanceRepository;
import sg.edu.smu.cs203.pandanews.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    AttendanceRepository attendanceRepository;
    @Autowired
    UserRepository userRepository;


    @Override
    public Attendance punchInOrOut(Long userId) {
        Optional<Attendance> temp = attendanceRepository.findByDate(userId, LocalDate.now());
        if (temp.isPresent() && temp.get().isPunchedOut()) {
            return null;
        }

        Attendance attendance = temp.orElse(new Attendance());
        if (temp.isPresent()) {
            attendance.setPunchedOut(true);
            attendance.setPunchOutDate(LocalDate.now());
            attendance.setPunchOutTime(LocalTime.now());
        } else {
            attendance.setPunchInDate(LocalDate.now());
            attendance.setPunchInTime(LocalTime.now());
        }
        attendance.setUser(userRepository.findById(userId).orElse(null));
        return attendanceRepository.save(attendance);
    }

    // @Override
    // public Attendance updateAttendance(Long id, LocalTime time) {
    //     Attendance a = attendanceRepository.findById(id).orElse(null);
    //     if (a == null) return null;
    //     a.setATime(time);
    //     return attendanceRepository.save(a);

    // }

    @Override
    public List<Attendance> findAttendancesByUserid(Long userId) {
        return attendanceRepository.findByUser(userRepository.findById(userId).orElse(null));
    }

    @Override
    public Attendance findAttendanceByDate(Long userId, LocalDate date) {
        return attendanceRepository.findByDate(userId, date).orElse(null);
    }

    @Override
    public Attendance findAttendanceById(Long id) {
        return attendanceRepository.findById(id).orElse(null);
    }
}
