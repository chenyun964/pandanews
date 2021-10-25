package sg.edu.smu.cs203.pandanews.model.CovidStats;

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
public class Statistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int newCases;
    private int totalCases;

    private int newDeaths;
    private int totalDeaths;

    private int newRecovered;
    private int totalRecovered;

    @JsonFormat(pattern = "yyyy-MM-dd HH:MM:ss", timezone = "GMT+8")
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:MM:ss", timezone = "GMT+8")
    @Column(name = "updated_at")
    private Date updatedAt;


    @PrePersist
    public void logTime() {
        Date temp = new Date();
        Object param = new java.sql.Timestamp(temp.getTime());
        created_at = (Date) param;
        updated_at = created_at;
    }

    @PreUpdate
    public void logUpdate() {
        Date temp = new Date();
        Object param = new java.sql.Timestamp(temp.getTime());
        updated_at = (Date) param;
    }

}
