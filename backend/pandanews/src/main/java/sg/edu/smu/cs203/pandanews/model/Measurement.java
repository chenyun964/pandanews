package sg.edu.smu.cs203.pandanews.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Column;

import lombok.*;


@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Measurement {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String imageUrl;

    @Setter
    private String title;

    @Setter
    private String content;

    // did not manage to find the admin table
    // @OneToMany(mappedBy = "xxx", cascade = CascadeType.All, orphanRemoval = true)
    // private List<Admin> xxx ;

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

    // public Measurement(String title, String image_url, String content, DateTime date){
    //     this.title = title;
    //     this.image_url = image_url;
    //     this.content = content;
    // }
    
}