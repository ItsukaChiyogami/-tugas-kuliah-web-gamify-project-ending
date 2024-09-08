package website.ALP.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Quest_List")
public class Quest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Quest_ID")
    private Long questId;

    @Column(name = "Title")
    private String title;

    @Column(name = "Url")
    private String url;

    @Column(name = "Description")
    private String description;

    @Column(name = "Category")
    private String category;

    @Column(name = "Validate")
    private boolean validate;

    @Column(name = "Deadline")
    private LocalDate deadline;

    // Constructors, getters, and setters
    // Constructor
    public Quest() {
    }

    public Quest(String title, String url, String description, String category, boolean validate, LocalDate deadline) {
        this.title = title;
        this.url = url;
        this.description = description;
        this.category = category;
        this.validate = validate;
        this.deadline = deadline;
    }

    // Getters and setters
    public Long getQuestId() {
        return questId;
    }

    public void setQuestId(Long questId) {
        this.questId = questId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isValidate() {
        return validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }

    public LocalDate  getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate  deadline) {
        this.deadline = deadline;
    }
}
