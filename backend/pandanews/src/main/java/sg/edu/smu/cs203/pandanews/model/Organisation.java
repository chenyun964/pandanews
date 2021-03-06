package sg.edu.smu.cs203.pandanews.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import sg.edu.smu.cs203.pandanews.model.user.User;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Organisation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String title;

    @Setter
    private String address;

    @Setter
    private String contact;

    @Setter
    private String code;

    @Setter
    private byte status;

    @Setter
    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonIgnore
    private User owner;

    @Setter
    @OneToMany(mappedBy = "organisation")
    @JsonIgnore
    private List<User> employee;

    @Setter
    @OneToMany(mappedBy = "organisation", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Policy> policy;

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

    public Organisation(String title) {
        this.title = title;
    }

}