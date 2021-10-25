package sg.edu.smu.cs203.pandanews.model.attendance;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import sg.edu.smu.cs203.pandanews.model.user.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@EqualsAndHashCode
@NoArgsConstructor
@Getter
@Entity
public class Attendance {
    public Attendance(LocalDate aDate, LocalTime time, boolean isInOffice,boolean isPunchedIn, User user) {
        this.aDate = aDate;
        this.aTime = time;
        this.isInOffice = isInOffice;
        this.user = user;
        this.isPunchedIn = isPunchedIn;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private LocalDate aDate;

    private LocalTime aTime;

    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isInOffice;

    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isPunchedIn;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
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

    public void setADate(LocalDate aDate) {
        this.aDate = aDate;
    }

    public void setATime(LocalTime aTime) {
        this.aTime = aTime;
    }

    public void setOnline(boolean online) {
        isInOffice = online;
    }
}
