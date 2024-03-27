package SD.SDPractica1.Entities;

import java.time.LocalDateTime;

public class Location {
    private Long id; //location identifier
    private String name;
    private String address;
    private int capacity;
    private String type;
    private String accessibility;
    private String city;
    private String country;
    private String image;
    private LocalDateTime createdAt; //location creation date
    private LocalDateTime updatedAt; //location last updated date

    public Location() { //empty constructor
    }

    public Location(String name, String address, int capacity, String type, String accessibility, String city, String country, String image) {
        this.name = name;
        this.address = address;
        this.capacity = capacity;
        this.type = type;
        this.accessibility = accessibility;
        this.city = city;
        this.country = country;
        this.image = image;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(String accessibility) {
        this.accessibility = accessibility;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
}