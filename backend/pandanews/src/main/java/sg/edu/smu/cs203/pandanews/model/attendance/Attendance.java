package sg.edu.smu.cs203.pandanews.model.attendance;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.Type;
import sg.edu.smu.cs203.pandanews.model.user.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Entity
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @JsonFormat(timezone = "GMT+8")
    private LocalDate punchInDate;

    @Setter
    @JsonFormat(timezone = "GMT+8")
    private LocalTime punchInTime;

    @Setter
    @JsonFormat(timezone = "GMT+8")
    private LocalDate punchOutDate;

    @Setter
    @JsonFormat(timezone = "GMT+8")
    private LocalTime punchOutTime;

    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isInOffice;

    @Setter
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isPunchedOut;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @Setter
    private User user;

    @JsonFormat(pattern = "yyyy-MM-dd HH:MM:ss", timezone = "GMT+8")
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:MM:ss", timezone = "GMT+8")
    @Column(name = "updated_at")
    private Date updatedAt;

    @PrePersist
    public void logCreation() {
        Date temp = new Date();
        Object param = new java.sql.Timestamp(temp.getTime());
        createdAt = (Date) param;
        updatedAt = (Date) param;
    }

    @PreUpdate
    public void logUpdate() {
        Date temp = new Date();
        Object param = new java.sql.Timestamp(temp.getTime());
        updatedAt = (Date) param;
    }

    public void setOnline(boolean online) {
        isInOffice = online;
    }
}
