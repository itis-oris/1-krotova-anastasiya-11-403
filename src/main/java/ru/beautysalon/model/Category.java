package ru.beautysalon.model;

public class Category {
    private Long id;
    private String category_name;

    public Category(Long id, String category_name) {
        this.id = id;
        this.category_name = category_name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory_Name() {
        return category_name;
    }

    public void setCategory_Name(String category_name) {
        this.category_name = category_name;
    }
}
