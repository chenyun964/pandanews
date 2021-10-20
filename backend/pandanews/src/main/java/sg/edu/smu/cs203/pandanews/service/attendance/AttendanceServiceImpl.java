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
    public Attendance markAttendance(Long userId) {
        User u = userServiceImpl.getUser(userId);
        if (u == null) {
            return null;
        }
        LocalDate currDate = LocalDate.now();
        if (attendanceRepository.findByADate(u.getId(), currDate) != null){
//            throw RuntimeException("Taken");
            return null;
        }
            LocalTime currTime = LocalTime.now();
        Attendance a = new Attendance(currDate, currTime, false, u);
        return attendanceRepository.save(a);
    }

    @Override
    public Attendance updateAttendance(Long userId, LocalDate date, LocalTime time) {
        User u = userServiceImpl.getUser(userId);
        if (u == null) {
            return null;
        }
        LocalTime currTime = LocalTime.now();
        Attendance a = attendanceRepository.findByADate(userId,date);
        a.setATime(time);
        return attendanceRepository.save(a);
    }


    @Override
    public List<Attendance> findAttendancesByUserid(Long userId) {
        return attendanceRepository.findByUser(userServiceImpl.getUser(userId));
    }

    @Override
    public Attendance findAttendanceByDate(Long userId, LocalDate date) {
        return attendanceRepository.findByADate(userId,date);
    }

    @Override
    public Attendance findAttendanceById(Long id) {
        return attendanceRepository.findById(id).orElse(null);
    }
}
