package sg.edu.smu.cs203.pandanews.model;

import java.util.List;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User {
    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String username;

    @Setter
    private String email;

    @Setter
    private String password;

    @Setter
    private String name;

    @Setter
    private String contact;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Organisation> organisations;

    @ManyToOne
    @JoinColumn(name = "workgroup_id", nullable = false)
    private WorkGroup workgroup;

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