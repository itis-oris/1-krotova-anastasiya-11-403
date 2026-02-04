package ru.beautysalon.model;

import java.time.LocalDateTime;

public class Master {
    private Long id;
    private Long user_id;
    private Long salon_id;
    private String avatarURL;
    private String stag;
    private String description;
    private LocalDateTime created_at;

    private Double averageRating; // для рейтинга
    private Integer feedbackCount;

    private User user;
    private Salon salon;

    public Master(Long id, Long user_id, String avatarURL) {
        this.id = id;
        this.user_id = user_id;
        this.avatarURL = avatarURL;
    }

    public Master(Long id, Long user_id, Long salon_id, String avatarURL, String stag, String description, LocalDateTime created_at) {
        this.id = id;
        this.user_id = user_id;
        this.salon_id = salon_id;
        this.avatarURL = avatarURL;
        this.stag = stag;
        this.description = description;
        this.created_at = created_at;
    }

    public Master() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserID() {
        return user_id;
    }

    public void setUserID(Long userId) { this.user_id = userId; }

    public Long getSalon_id() {
        return salon_id;
    }

    public void setSalon_id(Long salon_id) {
        this.salon_id = salon_id;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public String getStag() {
        return stag;
    }

    public void setStag(String stag) {
        this.stag = stag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public void setUser(User user) { this.user = user; }

    public void setSalon(Salon salon) { this.salon = salon; }

    public User getUser() { return user; }

    public Salon getSalon() { return salon; }

    public Double getAverageRating() { return averageRating; }

    public void setAverageRating(Double averageRating) { this.averageRating = averageRating; }

    public Integer getFeedbackCount() { return feedbackCount; }

    public void setFeedbackCount(Integer feedbackCount) { this.feedbackCount = feedbackCount; }

    public void setUserId(Long userId) { this.user_id = user_id; }
}
