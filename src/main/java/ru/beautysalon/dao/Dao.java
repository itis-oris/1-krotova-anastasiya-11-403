package ru.beautysalon.dao;

import java.util.List;
import java.util.Optional;
import java.sql.SQLException;

public interface Dao<T> {
    Optional<T> findById(Long id);
    List<T> findAll();
    T save(T entity) throws SQLException;
    void update(T entity) throws SQLException;
    void delete(Long id) throws SQLException;
}