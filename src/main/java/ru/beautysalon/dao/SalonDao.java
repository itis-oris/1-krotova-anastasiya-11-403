package ru.beautysalon.dao;

import ru.beautysalon.model.Salon;
import ru.beautysalon.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SalonDao implements Dao<Salon> {

    @Override
    public Optional<Salon> findById (Long id) {
        String sql = "SELECT * FROM " + " \"salon\" WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(mapResultSetToSalon(resultSet));
            }
        } catch (SQLException e) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public List<Salon> findAll() {
        List<Salon> salons = new ArrayList<>();
        String sql = "SELECT * FROM \"salon\" ORDER BY id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                salons.add(mapResultSetToSalon(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salons;
    }

    @Override
    public Salon save (Salon salon) throws SQLException {
        String sql = "INSERT INTO \"salon\" (salon_name, address, avatarurl, description, map, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, salon.getSalon_name());
            statement.setString(2, salon.getAddress());
            statement.setString(3, salon.getAvatarURL());
            statement.setString(4, salon.getDescription());
            statement.setString(5, salon.getMap());
            statement.setTimestamp(6, Timestamp.valueOf(salon.getCreatedAt()));

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                return null;
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    salon.setId(generatedKeys.getLong(1));
                } else {
                    return null;
                }
            }

        } catch (SQLException e) {
            return null;
        }
        return salon;
    }

    @Override
    public void update (Salon salon) throws SQLException {
        String sql = "UPDATE \"salon\" SET salon_name = ?, address = ?, avatarurl = ?, description = ?, map = ? WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, salon.getSalon_name());
            statement.setString(2, salon.getAddress());
            statement.setString(3, salon.getAvatarURL());
            statement.setString(4, salon.getDescription());
            statement.setString(5, salon.getMap());
            statement.setLong(6, salon.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            return;
        }
    }

    @Override
    public void delete (Long id) throws SQLException {
        String sql = "DELETE FROM \"salon\" WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            return;
        }
    }

    public Salon mapResultSetToSalon(ResultSet resultSet) throws SQLException {
        return new Salon(
                resultSet.getLong("id"),
                resultSet.getString("salon_name"),
                resultSet.getString("address"),
                resultSet.getString("avatarurl"),
                resultSet.getString("description"),
                resultSet.getString("map"),
                resultSet.getTimestamp("created_at").toLocalDateTime()
        );
    }
}