package sg.edu.smu.cs203.pandanews.service.attendance;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.smu.cs203.pandanews.model.attendance.Attendance;
import sg.edu.smu.cs203.pandanews.model.user.User;
import sg.edu.smu.cs203.pandanews.repository.AttendanceRepository;
import sg.edu.smu.cs203.pandanews.service.user.UserServiceImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    AttendanceRepository attendanceRepository;
    @Autowired
    UserServiceImpl userServiceImpl;

    @Override
    public Attendance punchInOrOut(Long userId) {
        User u = userServiceImpl.getUser(userId);
        if (u == null) {
            return null;
        }
        Attendance a = null;
        LocalDate currDate = LocalDate.now();
        List<Attendance> temp = attendanceRepository.findByADate(u.getId(), currDate);
        LocalTime currTime = LocalTime.now();

        if (temp == null || temp.size() == 0) {
            a = new Attendance(currDate, currTime, false, true, u);
        } else if (temp.size() == 1 && temp.get(0).isPunchedIn() == true) {
            a = new Attendance(currDate, currTime, false, false, u);
        } else {
            return null;
        }
        return attendanceRepository.save(a);
    }

    @Override
    public Attendance updateAttendance(Long id, LocalTime time) {
        Attendance a = attendanceRepository.findById(id).orElse(null);
        a.setATime(time);
        return attendanceRepository.save(a);
    }

    @Override
    public List<Attendance> findAttendancesByUserid(Long userId) {
        return attendanceRepository.findByUser(userServiceImpl.getUser(userId));
    }

    @Override
    public List<Attendance> findAttendanceByDate(Long userId, LocalDate date) {
        return attendanceRepository.findByADate(userId, date);
    }

    @Override
    public Attendance findAttendanceById(Long id) {
        return attendanceRepository.findById(id).orElse(null);
    }
}
