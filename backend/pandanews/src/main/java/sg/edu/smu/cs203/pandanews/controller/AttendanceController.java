package sg.edu.smu.cs203.pandanews.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.smu.cs203.pandanews.model.attendance.AttendanceUpdateDTO;
import sg.edu.smu.cs203.pandanews.service.attendance.AttendanceServiceImpl;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
public class AttendanceController {

    @Autowired
    private AttendanceServiceImpl attendanceService;

    //This method will be removed
    @PostMapping(path = "attendance/mark/{id}")
    public ResponseEntity<?> markAttendance(@PathVariable Long id) {
        return ResponseEntity.ok(attendanceService.punchInOrOut(id));
    }

    @PostMapping(path = "attendance/update/{id}")
    public ResponseEntity<?> updateAttendance(@PathVariable Long id, @RequestBody LocalTime time) {
        return ResponseEntity.ok(attendanceService.updateAttendance(id, time));
    }

    @GetMapping(path = "attendance/get/user/{userId}")
    public ResponseEntity<?> getUserAttendances(@PathVariable Long userId) {
        return ResponseEntity.ok(attendanceService.findAttendancesByUserid(userId));
    }

    @GetMapping(path = "attendance/get/{id}")
    public ResponseEntity<?> getAttendance(@PathVariable Long id) {
        return ResponseEntity.ok(attendanceService.findAttendanceById(id));
    }

    @GetMapping(path = "attendance/get/date/{id}")
    public ResponseEntity<?> getUserAttendancesByDate(@PathVariable Long userId, @RequestParam LocalDate date) {
        return ResponseEntity.ok(attendanceService.findAttendanceByDate(userId, date));
    }

}
