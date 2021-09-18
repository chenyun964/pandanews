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

import sg.edu.smu.cs203.pandanews.model.User;

import lombok.*;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Organisation {
    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String title;

    @Setter
    private String address;

    @Setter
    private String contact;

    @Setter
    private String code;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

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