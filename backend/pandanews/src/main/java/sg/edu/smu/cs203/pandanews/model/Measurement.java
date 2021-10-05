package sg.edu.smu.cs203.pandanews.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Column;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;


@Entity
@Getter
@ToString
@EqualsAndHashCode
public class Measurement {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String imageUrl;

    @Setter
    @Size(min = 5, max = 200, message = "Measurement's title should be at least 5 characters long")
    private String title;

    @Setter
    private String content;

    // did not manage to find the admin table
    // @OneToMany(mappedBy = "xxx", cascade = CascadeType.All, orphanRemoval = true)
    // private List<Admin> xxx ;

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
}