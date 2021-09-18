package sg.edu.smu.cs203.pandanews.model.news;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    @Lob
    private String description;

    private String content;

    private String coverImage;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;

    private long viewCount = 0;

    public News(String title, String description, String content, String coverImage, Date date) {
        this.title = title;
        this.description = description;
        this.content = content;
        this.coverImage = coverImage;
        this.date = date;
    }

    public News() {
    }

    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean pinned;

    @JsonFormat(pattern = "yyyy-MM-dd HH:MM:ss", timezone = "GMT+8")
    private Date created_at;

    @JsonFormat(pattern = "yyyy-MM-dd HH:MM:ss", timezone = "GMT+8")
    private Date updated_at;


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

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getViewCount() {
        return viewCount;
    }

    public void setViewCount(long viewCount) {
        this.viewCount = viewCount;
    }

    public void incrementViewCount() {
        this.viewCount++;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
