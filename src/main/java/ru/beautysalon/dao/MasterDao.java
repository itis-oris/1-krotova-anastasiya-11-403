package ru.beautysalon.dao;

import ru.beautysalon.model.*;
import ru.beautysalon.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MasterDao implements Dao<Master> {

    @Override
    public Optional<Master> findById(Long id) {
        String sql = "SELECT m.*, u.firstname, u.lastname, u.email, u.phone, " +
                "s.salon_name, s.address, s.avatarurl as salon_avatar " +
                "FROM \"master\" m " +
                "JOIN \"user\" u ON m.user_id = u.id " +
                "JOIN \"salon\" s ON m.salon_id = s.id " +
                "WHERE m.id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(mapResultSetToMaster(resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Не удалось найти мастера по id: " + id, e);
        }

        return Optional.empty();
    }

    @Override
    public List<Master> findAll() {
        List<Master> masters = new ArrayList<>();
        String sql = "SELECT m.*, u.firstname, u.lastname, u.email, u.phone, " +
                "s.salon_name, s.address, s.avatarurl as salon_avatar " +
                "FROM \"master\" m " +
                "JOIN \"user\" u ON m.user_id = u.id " +
                "JOIN \"salon\" s ON m.salon_id = s.id " +
                "ORDER BY m.id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                masters.add(mapResultSetToMaster(resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Произошла ошибка при поиске всех мастеров", e);
        }
        return masters;
    }

    @Override
    public Master save(Master master) throws SQLException {
        String sql = "INSERT INTO \"master\" (user_id, salon_id, avatarurl, stag, description, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, master.getUserID());
            statement.setLong(2, master.getSalon_id());
            statement.setString(3, master.getAvatarURL());
            statement.setString(4, master.getStag());
            statement.setString(5, master.getDescription());
            statement.setTimestamp(6, Timestamp.valueOf(master.getCreated_at()));

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                return null;
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    master.setId(generatedKeys.getLong(1));
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            return null;
        }

        return master;
    }

    @Override
    public void update(Master master) throws SQLException {
        String sql = "UPDATE \"master\" SET salon_id = ?, avatarurl = ?, stag = ?, description = ? WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, master.getSalon_id());
            statement.setString(2, master.getAvatarURL());
            statement.setString(3, master.getStag());
            statement.setString(4, master.getDescription());
            statement.setLong(5, master.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException("Ошибка при обновлении мастера: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM \"master\" WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException("Ошибка при удалении мастера: " + e.getMessage(), e);
        }
    }

    private Master mapResultSetToMaster(ResultSet resultSet) throws SQLException {
        User user = new User(
                resultSet.getLong("user_id"),
                resultSet.getString("firstname"),
                resultSet.getString("lastname")
        );
        user.setEmail(resultSet.getString("email"));
        user.setPhone(resultSet.getString("phone"));

        Salon salon = new Salon(
                resultSet.getLong("salon_id"),
                resultSet.getString("salon_name"),
                resultSet.getString("address"),
                resultSet.getString("salon_avatar"),
                null,
                null,
                null
        );

        Master master = new Master(
                resultSet.getLong("id"),
                resultSet.getLong("user_id"),
                resultSet.getLong("salon_id"),
                resultSet.getString("avatarurl"),
                resultSet.getString("stag"),
                resultSet.getString("description"),
                resultSet.getTimestamp("created_at").toLocalDateTime()
        );

        master.setUser(user);
        master.setSalon(salon);

        return master;
    }

    public List<Master> findBySalonId(Long salonId) {
        List<Master> masters = new ArrayList<>();
        String sql = "SELECT m.*, u.firstname, u.lastname, u.email, u.phone, " +
                "s.salon_name, s.address, s.avatarurl as salon_avatar " +
                "FROM \"master\" m " +
                "JOIN \"user\" u ON m.user_id = u.id " +
                "JOIN \"salon\" s ON m.salon_id = s.id " +
                "WHERE m.salon_id = ? " +
                "ORDER BY u.firstname, u.lastname";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, salonId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                masters.add(mapResultSetToMaster(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске мастеров по salonId: " + salonId, e);
        }
        return masters;
    }

    public Optional<Master> findByUserId(Long userId) {
        String sql = "SELECT m.*, u.firstname, u.lastname, u.email, u.phone, " +
                "s.salon_name, s.address, s.avatarurl as salon_avatar " +
                "FROM \"master\" m " +
                "JOIN \"user\" u ON m.user_id = u.id " +
                "JOIN \"salon\" s ON m.salon_id = s.id " +
                "WHERE m.user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(mapResultSetToMaster(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске мастера по userId: " + userId, e);
        }
        return Optional.empty();
    }

    public List<Master> getMastersBySalonAndCategory(Long salonId, Long categoryId) {
        List<Master> masters = new ArrayList<>();
        String sql = "SELECT DISTINCT m.*, u.firstname, u.lastname, u.email, u.phone, " +
                "s.salon_name, s.address, s.avatarurl as salon_avatar " +
                "FROM \"master\" m " +
                "JOIN \"user\" u ON m.user_id = u.id " +
                "JOIN \"salon\" s ON m.salon_id = s.id " +
                "JOIN \"procedure\" p ON m.id = p.master_id " +
                "WHERE m.salon_id = ? AND p.category_id = ? " +
                "ORDER BY u.firstname, u.lastname";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, salonId);
            statement.setLong(2, categoryId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                masters.add(mapResultSetToMaster(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске мастеров по salonId и categoryId", e);
        }
        return masters;
    }

    public Optional<Master> getMasterByUserId(Long userId) {
        String sql = "SELECT m.*, u.firstname, u.lastname, u.email, u.phone, u.role, u.created_at as user_created_at " +
                "FROM master m " +
                "JOIN \"user\" u ON m.user_id = u.id " +
                "WHERE m.user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, userId);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Master master = new Master(
                            rs.getLong("id"),
                            rs.getLong("user_id"),
                            rs.getLong("salon_id"),
                            rs.getString("avatarURL"),
                            rs.getString("stag"),
                            rs.getString("description"),
                            rs.getTimestamp("created_at") != null ?
                                    rs.getTimestamp("created_at").toLocalDateTime() : null
                    );

                    User user = new User(
                            rs.getLong("user_id"),
                            rs.getString("email"),
                            null, // пароль не нужен здесь
                            rs.getString("firstname"),
                            rs.getString("lastname"),
                            rs.getString("phone"),
                            User.UserRole.valueOf(rs.getString("role")),
                            rs.getTimestamp("user_created_at") != null ?
                                    rs.getTimestamp("user_created_at").toLocalDateTime() : null
                    );

                    master.setUser(user);

                    return Optional.of(master);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске мастера по userId: " + userId, e);
        }

        return Optional.empty();
    }
}