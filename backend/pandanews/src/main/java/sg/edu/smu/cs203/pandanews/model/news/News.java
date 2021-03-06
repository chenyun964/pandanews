package sg.edu.smu.cs203.pandanews.model.news;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.Type;
import sg.edu.smu.cs203.pandanews.model.category.Category;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;

    @Lob
    private String description;

    private String content;

    @Getter
    @Setter
    private String source;

    private String coverImage;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate date;

    private long viewCount = 0;

    public News(String title, String description, String content, String coverImage, LocalDate date) {
        this.title = title;
        this.description = description;
        this.content = content;
        this.coverImage = coverImage;
        this.date = date;
    }

    @Setter
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean pinned;

    @JsonFormat(pattern = "yyyy-MM-dd HH:MM:ss", timezone = "GMT+8")
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:MM:ss", timezone = "GMT+8")
    private Date updatedAt;


    @PostPersist
    public void logTime() {
        Date temp = new Date();
        Object param = new java.sql.Timestamp(temp.getTime());
        slug = title.replaceAll(" ", "-").replaceAll("_", "-") + "-" + (this.id << LocalDateTime.now().getDayOfMonth());
        createdAt = (Date) param;
        updatedAt = createdAt;
    }

    @Getter
    @Column(unique = true)
    private String slug;

    public void generateSlug() {
        slug = title.replaceAll(" ", "-").replaceAll("_", "-") + "-" + (this.id << LocalDateTime.now().getDayOfMonth());
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    @PreUpdate
    public void logUpdate() {
        Date temp = new Date();
        Object param = new java.sql.Timestamp(temp.getTime());
        updatedAt = (Date) param;
    }

    public Long getId() {
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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

    public News(Long id, String title, String description, String content, String coverImage, LocalDate date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
        this.coverImage = coverImage;
        this.date = date;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
