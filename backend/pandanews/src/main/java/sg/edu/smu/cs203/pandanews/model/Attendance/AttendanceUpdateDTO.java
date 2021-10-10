package sg.edu.smu.cs203.pandanews.model.Attendance;


import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class AttendanceUpdateDTO {
    private LocalDate aDate;
    private LocalTime aTime;
}
