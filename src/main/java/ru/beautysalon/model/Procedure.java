package ru.beautysalon.model;


import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Procedure {
    private Long id;
    private String procedure_name;
    private Long master_id;
    private Long category_id;
    private String description;
    private BigDecimal price;
    private LocalDateTime created_at;

    private Master master;
    private Category category;

    public Procedure(Long id, Long master_id, String procedure_name, Long category_id, String description, BigDecimal price, LocalDateTime created_at) {
        this.id = id;
        this.master_id = master_id;
        this.procedure_name = procedure_name;
        this.category_id = category_id;
        this.description = description;
        this.price = price;
        this.created_at = created_at;
    }

    public Procedure() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() { return category; }

    public String getProcedure_name() {
        return procedure_name;
    }

    public void setProcedure_name(String procedure_name) {
        this.procedure_name = procedure_name;
    }

    public Long getMaster_Id() {
        return master_id;
    }

    public void setMaster_Id(Long master_id) {
        this.master_id = master_id;
    }

    public Long getCategory_Id() {
        return category_id;
    }

    public void setCategory_Id(Long category_Id) {
        this.category_id = category_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public void setMaster(Master master) {
        this.master = master;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


}
