package ru.beautysalon.service;

import ru.beautysalon.dao.*;
import ru.beautysalon.model.*;
import java.util.List;
import java.util.Optional;

public class CategoryService {
    private CategoryDao categoryDao;

    public CategoryService(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public List<Category> getAllCategories() {
        return categoryDao.findAll();
    }

}
