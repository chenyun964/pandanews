package sg.edu.smu.cs203.pandanews.model.user;

import java.util.List;
import java.util.Date;
import java.util.Arrays;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotNull;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.*;
import sg.edu.smu.cs203.pandanews.model.attendance.Attendance;
import sg.edu.smu.cs203.pandanews.model.Organisation;
import sg.edu.smu.cs203.pandanews.model.VacciSpot;
import sg.edu.smu.cs203.pandanews.model.WorkGroup;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String username;

    @Setter
    private String email;

    @Setter
    @JsonIgnore
    private String password;

    @Setter
    private String name;

    @Setter
    private String contact;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Attendance> attendances;

    @Setter
    @Column(columnDefinition = "bit(1) default 0")
    private Boolean vaccinated;

    @Setter
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Organisation> organisations;

    @Setter
    @OneToMany(mappedBy = "admin")
    @JsonIgnore
    private List<VacciSpot> vacciSpots;

    @Setter
    @OneToMany(mappedBy = "admin")
    @JsonIgnore
    private List<VacciSpot> testSpots;

    @Setter
    @ManyToOne
    @JoinColumn(name = "organisation_id")
    @JsonIgnore
    private Organisation organisation;

    @Setter
    @ManyToOne
    @JoinColumn(name = "workgroup_id")
    private WorkGroup workGroup;

    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    // We define three roles
    /*
     * ROLE_USER ROLE_MANAGER ROLE_OWNER
     * 
     * ROLE_ADMIN
     */
    @Setter
    @NotNull
    private String authorities = "ROLE_USER";

    public User(String role) {
        this.authorities = role;
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

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

    /*
     * Return a collection of authorities granted to the user.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(authorities));
    }

    /*
     * The various is___Expired() methods return a boolean to indicate whether or
     * not the user’s account is enabled or expired.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}