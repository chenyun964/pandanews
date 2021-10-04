package sg.edu.smu.cs203.pandanews.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@EqualsAndHashCode
@NoArgsConstructor
@Getter
@Entity
public class Attendance {
    public Attendance(LocalDate aDate, LocalTime time, Status status, boolean isOnline, User user) {
        this.aDate = aDate;
        this.aTime = time;
        this.status = status;
        this.isOnline = isOnline;
        this.user = user;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate aDate;

    private LocalTime aTime;

    private Status status;

    private boolean isOnline;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created_at", updatable = false)
    private Date createdAt;

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
        setStatus();
    }

    public void setStatus() {
        //TODO: status will change based on the time
        this.status = Status.PRESENT;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }
}
