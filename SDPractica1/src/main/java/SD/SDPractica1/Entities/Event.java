package SD.SDPractica1.Entities;

import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

public class Event {
    private Long id; //event identifier
    private String name;
    private String description;
    private String dateTime;
    private String image;
    private LocalDateTime createdAt; //event creation date
    private LocalDateTime updatedAt; //event last updated date

    public Event() { //empty constructor
    }

    public Event(String name, String description, String dateTime, String image) {
        this.name = name;
        this.description = description;
        this.dateTime = dateTime;
        this.image = image;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateTime() {
        return dateTime;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}