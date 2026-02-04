package ru.beautysalon.model;

import java.time.LocalDateTime;

public class Feedback {
    private Long id;
    private Long user_id;
    private Long master_id;
    private Double rating;
    private String text;
    private LocalDateTime created_at;

    private User user;
    private Master master;

    public Feedback(Long id, Long user_id, Long master_id, Double rating, String text, LocalDateTime created_at, User user, Master master) {
        this.id = id;
        this.user_id = user_id;
        this.master_id = master_id;
        this.rating = rating;
        this.text = text;
        this.created_at = created_at;
        this.user = user;
        this.master = master;
    }

    public Feedback(Long user_id, Long master_id, Double rating, String text) {
        this.user_id = user_id;
        this.master_id = master_id;
        this.rating = rating;
        this.text = text;
    }
    public Feedback() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserID() {
        return user_id;
    }

    public void setUserID(Long user_id) {
        this.user_id = user_id;
    }

    public Long getMasterId() {
        return master_id;
    }

    public void setMaster_Id(Long master_id) {
        this.master_id = master_id;
    }

    public Double getRating() {
        return rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Master getMaster() {
        return master;
    }

    public void setMaster(Master master) {
        this.master = master;
    }

    public void setRating(Double rating) {
        if (rating != null && (rating < 1.0 || rating > 5.0)) {
            throw new IllegalArgumentException("Рейтинг должен быть от 1.0 до 5.0");
        }
        this.rating = rating;
    }
}