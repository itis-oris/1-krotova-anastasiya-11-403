package ru.beautysalon.dao;

import ru.beautysalon.model.Category;
import ru.beautysalon.util.DBConnection;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.*;
import java.sql.Connection;

public class CategoryDao implements Dao<Category> {

    @Override
    public Optional<Category> findById(Long id) {
        String sql = "SELECT * FROM \"category\" WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(mapResultSetToCategory(resultSet));
            }
        } catch (SQLException e) {
            return Optional.empty();
        }

        return Optional.empty();
    }

    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM \"category\" ORDER BY category_name";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                categories.add(mapResultSetToCategory(resultSet));
            }
        } catch (SQLException e) {
            return new ArrayList<>();
        }

        return categories;
    }

    @Override
    public Category save(Category category) throws SQLException {
        String sql = "INSERT INTO \"category\" (category_name) VALUES (?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, category.getCategory_Name());
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Не удалось создать категорию, заполните поля.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    category.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Не удалось создать категорию, id не получен.");
                }
            }
        } catch (SQLException e) {
            return null;
        }

        return category;
    }

    @Override
    public void update(Category category) throws SQLException {

        String sql = "UPDATE \"category\" SET category_name = ? WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, category.getCategory_Name());
            statement.setLong(2, category.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            return;
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM \"category\" WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            return;
        }
    }

    private Category mapResultSetToCategory(ResultSet resultSet) throws SQLException {
        return new Category(
                resultSet.getLong("id"),
                resultSet.getString("category_name")
        );
    }

    public List<Category> findBySalonId(Long salonId) {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT DISTINCT c.* " +
                "FROM \"category\" c " +
                "JOIN \"procedure\" p ON c.id = p.category_id " +
                "JOIN \"master\" m ON p.master_id = m.id " +
                "WHERE m.salon_id = ? " +
                "ORDER BY c.category_name";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, salonId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                categories.add(mapResultSetToCategory(resultSet));
            }
        } catch (SQLException e) {
            return new ArrayList<>();
        }
        return categories;
    }

    public List<Category> findByMasterId(Long masterId) {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT DISTINCT c.* " +
                "FROM \"category\" c " +
                "JOIN \"procedure\" p ON c.id = p.category_id " +
                "WHERE p.master_id = ? " +
                "ORDER BY c.category_name";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, masterId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                categories.add(mapResultSetToCategory(resultSet));
            }
        } catch (SQLException e) {
            return new ArrayList<>();
        }

        return categories;
    }
}