package ru.beautysalon.model;

import java.time.LocalDateTime;

public class Booking {
    private Long id;
    private Long user_id;
    private Long master_id;
    private Long procedure_id;
    private LocalDateTime created_at;
    private LocalDateTime data;
    private BookingStatus status;

    public Booking() {}

    public enum BookingStatus {
        PENDING, CONFIRMED, CANCELLED
    }

    private User user;
    private Procedure procedure;
    private Master master;
    private Salon salon;

    public Booking(Long id, Long user_id, Long master_id, Long procedure_id, LocalDateTime created_at, LocalDateTime data, User user, Procedure procedure) {
        this.id = id;
        this.user_id = user_id;
        this.master_id = master_id;
        this.procedure_id = procedure_id;
        this.created_at = created_at;
        this.data = data;
        this.user = user;
        this.procedure = procedure;
    }

    public Salon getSalon() {
        return salon;
    }

    public void setSalon(Salon salon) {
        this.salon = salon;
    }

    public Master getMaster() {
        return master;
    }

    public void setMaster(Master master) {
        this.master = master;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return user_id;
    }

    public void setUserId(Long user_id) {
        this.user_id = user_id;
    }

    public Long getMasterId() {
        return master_id;
    }

    public void setMasterId(Long master_id) {
        this.master_id = master_id;
    }

    public Long getProcedureID() {
        return procedure_id;
    }

    public void setProcedureID(Long procedure_id) {
        this.procedure_id = procedure_id;
    }

    public LocalDateTime getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Procedure getProcedure() {
        return procedure;
    }

    public void setProcedure(Procedure procedure) {
        this.procedure = procedure;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public BookingStatus getStatus() {
        return status;
    }
}
