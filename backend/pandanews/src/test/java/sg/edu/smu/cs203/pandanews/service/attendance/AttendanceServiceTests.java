package sg.edu.smu.cs203.pandanews.service.attendance;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sg.edu.smu.cs203.pandanews.model.attendance.Attendance;
import sg.edu.smu.cs203.pandanews.model.user.User;
import sg.edu.smu.cs203.pandanews.repository.AttendanceRepository;
import sg.edu.smu.cs203.pandanews.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AttendanceServiceTests {
    @InjectMocks
    private AttendanceServiceImpl attendanceService;
    @Mock
    private AttendanceRepository attendanceRepo;
    @Mock
    private UserRepository userRepo;

    @Test
    void punchInOrOut_punchInSuccess() {
        LocalDate currDate = LocalDate.now();
        LocalTime currTime = LocalTime.now();
        when(attendanceRepo.findByDate(any(Long.class), any(LocalDate.class))).thenReturn(Optional.empty());

        User u = generateTestUser("name");
        when(userRepo.findById(any(Long.class))).thenReturn(Optional.of(u));

        Attendance a = new Attendance();
        a.setPunchInDate(currDate);
        a.setPunchInTime(currTime);
        a.setUser(u);
        when(attendanceRepo.save(any(Attendance.class))).thenReturn(a);

        Attendance result = attendanceService.punchInOrOut(10L);
        assertEquals("name", result.getUser().getUsername());
        assertFalse(result.isPunchedOut());
        assertEquals(currDate, result.getPunchInDate());
        assertNotNull(result);

        verify(attendanceRepo).findByDate(10L, currDate);
        verify(userRepo).findById(10L);
        //verify(attendanceRepository).save(a);

    }

    @Test
    void punchInOrOut_punchOutSuccess() {
        User u = generateTestUser("name");
        LocalDate currDate = LocalDate.now();
        LocalTime currTime = LocalTime.now();
        Attendance a = new Attendance();
        a.setPunchInDate(currDate);
        a.setPunchInTime(currTime);
        a.setUser(u);

        when(attendanceRepo.findByDate(any(Long.class), any(LocalDate.class))).thenReturn(Optional.of(a));
        when(userRepo.findById(any(Long.class))).thenReturn(Optional.of(u));

        Attendance newA = new Attendance();
        newA.setPunchInDate(currDate);
        newA.setPunchInTime(currTime);
        newA.setPunchOutDate(currDate);
        newA.setPunchOutTime(currTime);
        newA.setUser(u);
        when(attendanceRepo.save(any(Attendance.class))).thenReturn(newA);

        Attendance result = attendanceService.punchInOrOut(10L);
        assertEquals("name", result.getUser().getUsername());
        assertFalse(result.isPunchedOut());
        assertEquals(currDate, result.getPunchInDate());
        assertNotNull(result);
        assertEquals(currDate, result.getPunchOutDate());

        verify(attendanceRepo).findByDate(10L, currDate);
        verify(userRepo).findById(10L);
        //verify(attendanceRepository).save(a);
    }

    @Test
    void punchInOrOut_Failure_DuplicateEntry() {
        LocalDate currDate = LocalDate.now();
        Attendance a = new Attendance();
        a.setPunchedOut(true);
        when(attendanceRepo.findByDate(any(Long.class), any(LocalDate.class))).thenReturn(Optional.of(a));
        Attendance result = attendanceService.punchInOrOut(10L);
        assertNull(result);
        verify(attendanceRepo).findByDate(10L, currDate);
    }

    @Test
    void findAttendancesByUserid_Success() {
        Attendance newA = new Attendance();
        List<Attendance> attendanceList = new ArrayList<>();

        User u = generateTestUser("name");
        newA.setUser(u);
        attendanceList.add(newA);

        when(userRepo.findById(any(Long.class))).thenReturn(Optional.of(u));
        when(attendanceRepo.findByUser(any(User.class))).thenReturn(attendanceList);

        List<Attendance> result = attendanceService.findAttendancesByUserid(10L);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("name", result.get(0).getUser().getUsername());
        verify(userRepo).findById(10L);
        verify(attendanceRepo).findByUser(u);

    }

    @Test
    void findAttendancesByUserid_Failure() {
        when(userRepo.findById(any(Long.class))).thenReturn(Optional.empty());
        when(attendanceRepo.findByUser(null)).thenReturn(null);

        List<Attendance> result = attendanceService.findAttendancesByUserid(10L);
        assertNull(result);
        verify(userRepo).findById(10L);
    }

    @Test
    void findAttendanceByDate_Success() {
        LocalDate currDate = LocalDate.now();
        User u = generateTestUser("name");
        Attendance newA = new Attendance();
        newA.setUser(u);
        newA.setPunchInDate(currDate);
        when(attendanceRepo.findByDate(any(Long.class), any(LocalDate.class))).thenReturn(Optional.of(newA));
        Attendance result = attendanceService.findAttendanceByDate(10L, currDate);

        assertNotNull(result);
        verify(attendanceRepo).findByDate(10L, currDate);
    }

    @Test
    void findAttendanceByDate_Failure() {
        LocalDate currDate = LocalDate.now();
        User u = generateTestUser("name");
        when(attendanceRepo.findByDate(any(Long.class), any(LocalDate.class))).thenReturn(Optional.empty());
        Attendance result = attendanceService.findAttendanceByDate(10L, currDate);

        assertNull(result);
        verify(attendanceRepo).findByDate(10L, currDate);
    }


    private User generateTestUser(String name) {
        return new User(name, "abc@gmail.com", "abc");
    }

}
