package sg.edu.smu.cs203.pandanews.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import sg.edu.smu.cs203.pandanews.model.attendance.Attendance;
import sg.edu.smu.cs203.pandanews.model.user.User;
import sg.edu.smu.cs203.pandanews.service.attendance.AttendanceService;
import sg.edu.smu.cs203.pandanews.service.user.UserService;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/attendance")
public class AttendanceController {

    private AttendanceService attendanceService;

    private UserService userService;

    @Autowired
    public AttendanceController(AttendanceService attendanceService, UserService userService) {
        this.attendanceService = attendanceService;
        this.userService = userService;
    }

    /**
     * mark attendance
     * 
     * @return Marked Attendance
     */
    @PostMapping
    public Attendance markAttendance() {
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        User user = userService.getUserByUsername(userDetails.getUsername());
        if (user == null)
            return null;

        return attendanceService.punchInOrOut(user.getId());
    }

    /**
     * Update existing Attendance with a specific date
     * 
     * @param id
     * @param time
     * @return updated attendance
     */
    // @PutMapping(path = "/{id}")
    // public Attendance updateAttendance(@PathVariable Long id, @RequestBody LocalTime time) {
    //     return attendanceService.updateAttendance(id, time);
    // }

    /**
     * get a list of attendance from a user
     * 
     * @param userId
     * @return a list of attendance
     */
    @GetMapping(path = "/user/{userId}")
    public List<Attendance> getAttendanceByUser(@PathVariable Long userId) {
        return attendanceService.findAttendancesByUserid(userId);
    }

    /**
     * get a particular attendance. Deprecated method
     * 
     * @param id
     * @return get a particular attendance
     */
    @GetMapping(path = "/{id}")
    public Attendance getAttendance(@PathVariable Long id) {
        return attendanceService.findAttendanceById(id);
    }

    /**
     * Get existing Attendance with a specific date
     * 
     * @param date
     * @return an attendance
     */
    @GetMapping(path = "/date")
    public Attendance getUserAttendancesByDate(@RequestParam("date") 
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        final UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        User user = userService.getUserByUsername(userDetails.getUsername());
        if (user == null)
            return null;

        return attendanceService.findAttendanceByDate(user.getId(), date);
    }

}
