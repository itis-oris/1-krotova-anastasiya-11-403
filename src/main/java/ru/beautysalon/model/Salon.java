package ru.beautysalon.model;

import java.time.LocalDateTime;

public class Salon {
    private Long id;
    private String salon_name;
    private String address;
    private String description;
    private String avatarURL;
    private String map;
    private LocalDateTime created_at;

    public Salon(String salon_name, String address) {
        this.salon_name = salon_name;
        this.address = address;
    }

    public Salon(Long id, String salon_name, String address, String avatarURL, String description, String map, LocalDateTime created_at) {
        this.id = id;
        this.salon_name = salon_name;
        this.address = address;
        this.avatarURL = avatarURL;
        this.description = description;
        this.map = map;
        this.created_at = created_at;
    }

    public Salon() {}

    public String getSalon_name() {
        return salon_name;
    }

    public void setSalon_Name(String salon_name) {
        this.salon_name = salon_name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public LocalDateTime getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(LocalDateTime created_At) { this.created_at = this.created_at; }
}
