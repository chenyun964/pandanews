package sg.edu.smu.cs203.pandanews.service.attendance;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sg.edu.smu.cs203.pandanews.model.attendance.Attendance;
import sg.edu.smu.cs203.pandanews.model.user.User;
import sg.edu.smu.cs203.pandanews.repository.AttendanceRepository;
import sg.edu.smu.cs203.pandanews.repository.CategoryRepository;
import sg.edu.smu.cs203.pandanews.repository.NewsRepository;
import sg.edu.smu.cs203.pandanews.repository.UserRepository;
import sg.edu.smu.cs203.pandanews.service.news.NewsServiceImpl;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AttendanceServiceTests {
    @InjectMocks
    private AttendanceServiceImpl attendanceService;
    @Mock
    private AttendanceRepository attendanceRepository;
    @Mock
    private UserRepository userRepository;

    @Test
    void punchInOrOut_punchInSuccess() {
        LocalDate currDate = LocalDate.now();
        LocalTime currTime = LocalTime.now();
        when(attendanceRepository.findByDate(any(Long.class), any(LocalDate.class))).thenReturn(Optional.empty());

        User u = generateTestUser("name");
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(u));

        Attendance a = new Attendance();
        a.setPunchInDate(currDate);
        a.setPunchInTime(currTime);
        a.setUser(u);
        when(attendanceRepository.save(any(Attendance.class))).thenReturn(a);

        Attendance result = attendanceService.punchInOrOut(10L);
        assertEquals("name", result.getUser().getUsername());
        assertFalse(result.isPunchedOut());
        assertEquals(currDate, result.getPunchInDate());
        assertNotNull(result);

        verify(attendanceRepository).findByDate(10L, currDate);
        verify(userRepository).findById(10L);
        //verify(attendanceRepository).save(a);

    }

    void punchInOrOut_punchOutSuccess() {

    }

    private User generateTestUser(String name) {
        return new User(name, "abc@gmail.com", "abc");
    }

}
