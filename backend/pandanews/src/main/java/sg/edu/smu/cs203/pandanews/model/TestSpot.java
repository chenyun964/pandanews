package sg.edu.smu.cs203.pandanews.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.*;

@Entity
@Getter
@ToString
@EqualsAndHashCode
<<<<<<< HEAD:backend/pandanews/src/main/java/sg/edu/smu/cs203/pandanews/model/Organisation.java
public class Organisation {
=======
public class TestSpot {
>>>>>>> p15:backend/pandanews/src/main/java/sg/edu/smu/cs203/pandanews/model/TestSpot.java
    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
<<<<<<< HEAD:backend/pandanews/src/main/java/sg/edu/smu/cs203/pandanews/model/Organisation.java
    private String title;
=======
    private String type;

    @Setter
    private String name;
>>>>>>> p15:backend/pandanews/src/main/java/sg/edu/smu/cs203/pandanews/model/TestSpot.java

    @Setter
    private String address;

    @Setter
<<<<<<< HEAD:backend/pandanews/src/main/java/sg/edu/smu/cs203/pandanews/model/Organisation.java
    private String contact;

    @Setter
    private String code;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
=======
    private Double latitude;

    @Setter
    private Double longitude;

    @Setter
    @Column(name = "operating_hours")
    private String opHours;

    @Setter
    private String contact;
>>>>>>> p15:backend/pandanews/src/main/java/sg/edu/smu/cs203/pandanews/model/TestSpot.java

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
}