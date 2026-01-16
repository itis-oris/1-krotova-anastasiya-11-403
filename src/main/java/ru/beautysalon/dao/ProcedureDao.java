package ru.beautysalon.dao;

import ru.beautysalon.model.Procedure;
import ru.beautysalon.model.Master;
import ru.beautysalon.model.Category;
import ru.beautysalon.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProcedureDao implements Dao<Procedure> {

    @Override
    public Optional<Procedure> findById(Long id) {
        String sql = "SELECT p.*, c.category_name " +
                "FROM \"procedure\" p " +
                "JOIN \"category\" c ON p.category_id = c.id " +
                "WHERE p.id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(mapResultSetToProcedure(resultSet));
            }
        } catch (SQLException e) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    public List<Procedure> findByMasterId(Long masterId) {
        List<Procedure> procedures = new ArrayList<>();
        String sql = "SELECT p.*, c.category_name " +
                "FROM \"procedure\" p " +
                "JOIN \"category\" c ON p.category_id = c.id " +
                "WHERE p.master_id = ? " +
                "ORDER BY p.procedure_name";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, masterId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                procedures.add(mapResultSetToProcedure(resultSet));
            }
        } catch (SQLException e) {
            return new ArrayList<>();
        }
        return procedures;
    }

    @Override
    public List<Procedure> findAll() {
        List<Procedure> procedures = new ArrayList<>();
        String sql = "SELECT p.*, c.category_name " +
                "FROM \"procedure\" p " +
                "JOIN \"category\" c ON p.category_id = c.id " +
                "ORDER BY p.procedure_name";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                procedures.add(mapResultSetToProcedure(resultSet));
            }
        } catch (SQLException e) {
            return new ArrayList<>();
        }
        return procedures;
    }

    @Override
    public Procedure save(Procedure procedure) throws SQLException {
        String sql = "INSERT INTO \"procedure\" (procedure_name, master_id, category_id, description, price, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, procedure.getProcedure_name());
            statement.setLong(2, procedure.getMaster_Id());
            statement.setLong(3, procedure.getCategory_Id());
            statement.setString(4, procedure.getDescription());
            statement.setBigDecimal(5, procedure.getPrice());
            statement.setTimestamp(6, Timestamp.valueOf(procedure.getCreatedAt()));

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                return null;
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    procedure.setId(generatedKeys.getLong(1));
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            return null;
        }
        return procedure;
    }

    @Override
    public void update(Procedure procedure) throws SQLException {
        String sql = "UPDATE \"procedure\" SET procedure_name = ?, category_id = ?, description = ?, price = ? WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, procedure.getProcedure_name());
            statement.setLong(2, procedure.getCategory_Id());
            statement.setString(3, procedure.getDescription());
            statement.setBigDecimal(4, procedure.getPrice());
            statement.setLong(5, procedure.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            return;
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM \"procedure\" WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            return;
        }
    }

    private Procedure mapResultSetToProcedure(ResultSet resultSet) throws SQLException {
        Procedure procedure = new Procedure(
                resultSet.getLong("id"),
                resultSet.getLong("master_id"),
                resultSet.getString("procedure_name"),
                resultSet.getLong("category_id"),
                resultSet.getString("description"),
                resultSet.getBigDecimal("price"),
                resultSet.getTimestamp("created_at").toLocalDateTime()
        );

        try {
            String categoryName = resultSet.getString("category_name");
            if (categoryName != null) {
                Category category = new Category(
                        resultSet.getLong("category_id"),
                        categoryName
                );
                procedure.setCategory(category);
            }
        } catch (SQLException e) {
            return null;
        }

        return procedure;
    }

    public List<Procedure> findByCategoryId(Long categoryId) {
        List<Procedure> procedures = new ArrayList<>();
        String sql = "SELECT p.*, c.category_name " +
                "FROM \"procedure\" p " +
                "JOIN \"category\" c ON p.category_id = c.id " +
                "WHERE p.category_id = ? " +
                "ORDER BY p.price";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, categoryId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                procedures.add(mapResultSetToProcedure(resultSet));
            }
        } catch (SQLException e) {
            return new ArrayList<>();
        }
        return procedures;
    }
}