package sg.edu.smu.cs203.pandanews.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.smu.cs203.pandanews.service.attendance.AttendanceServiceImpl;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
public class AttendanceController {

    @Autowired
    private AttendanceServiceImpl attendanceService;

    /**
     * mark attendance
     * @param id
     * @return Marked Attendance
     */
    @PostMapping(path = "attendance/mark/{id}")
    public ResponseEntity<?> markAttendance(@PathVariable Long id) {
        return ResponseEntity.ok(attendanceService.punchInOrOut(id));
    }

    /**
     * Update existing Attendance with a specific date
     * @param id
     * @param time
     * @return updated attendance
     */
    @PostMapping(path = "attendance/update/{id}")
    public ResponseEntity<?> updateAttendance(@PathVariable Long id, @RequestBody LocalTime time) {
        return ResponseEntity.ok(attendanceService.updateAttendance(id, time));
    }

    /**
     * get a list of attendance from a user
     * @param userId
     * @return a list of attendance
     */
    @GetMapping(path = "attendance/get/user/{userId}")
    public ResponseEntity<?> getUserAttendances(@PathVariable Long userId) {
        return ResponseEntity.ok(attendanceService.findAttendancesByUserid(userId));
    }

    /**
     * get a particular attendance. Deprecated method
     * @param id
     * @return get a particular attendance
     */
    @GetMapping(path = "attendance/get/{id}")
    public ResponseEntity<?> getAttendance(@PathVariable Long id) {
        return ResponseEntity.ok(attendanceService.findAttendanceById(id));
    }

    /**
     * Get existing Attendance with a specific date
     * @param userId
     * @param date
     * @return an attendance
     */
    @GetMapping(path = "attendance/get/date/{id}")
    public ResponseEntity<?> getUserAttendancesByDate(@PathVariable Long userId, @RequestParam LocalDate date) {
        return ResponseEntity.ok(attendanceService.findAttendanceByDate(userId, date));
    }

}
