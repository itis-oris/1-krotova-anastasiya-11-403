package ru.beautysalon.dao;

import ru.beautysalon.model.User;
import ru.beautysalon.util.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao implements Dao<User> {

    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM \"user\" WHERE email = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(mapResultSetToUser(resultSet));
            }
        } catch (SQLException e) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findById (Long id) {
        String sql = "SELECT * FROM \"user\" WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(mapResultSetToUser(resultSet));
            }
        } catch (SQLException e) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM \"user\" ORDER BY id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                users.add(mapResultSetToUser(resultSet));
            }

        } catch (SQLException e) {
            return new ArrayList<>();
        }

        return users;
    }

    @Override
    public User save (User user) throws SQLException {
        String sql = "INSERT INTO \"user\" (email, passwordhash, firstname, lastname, phone, role, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?::userRole, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPasswordHash());
            statement.setString(3, user.getFirstname());
            statement.setString(4, user.getLastname());
            statement.setString(5, user.getPhone());
            statement.setString(6, user.getRole().name());
            statement.setTimestamp(7, Timestamp.valueOf(user.getCreated_at()));

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                } else {
                    return null;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void update (User user) throws SQLException {
        String sql = "UPDATE \"user\" SET email = ?, firstname = ?, lastname = ?, phone = ?, role = ?::userRole WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getEmail());
            statement.setString(2, user.getFirstname());
            statement.setString(3, user.getLastname());
            statement.setString(4, user.getPhone());
            statement.setString(5, user.getRole().name());
            statement.setLong(6, user.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            return;
        }
    }

    @Override
    public void delete (Long id) throws SQLException {
        String sql = "DELETE FROM \"user\" WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            return;
        }
    }

    private User mapResultSetToUser(ResultSet resultSet) throws SQLException {
        try {

            Long id = resultSet.getLong("id");
            String email = resultSet.getString("email");
            String passwordHash = resultSet.getString("passwordhash");
            String firstname = resultSet.getString("firstname");
            String lastname = resultSet.getString("lastname");
            String phone = resultSet.getString("phone");
            String roleStr = resultSet.getString("role");
            LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

            User.UserRole role = User.UserRole.valueOf(roleStr);

            User user = new User(id, email, passwordHash, firstname, lastname, phone, role, createdAt);
            return user;
        } catch (Exception e) {
            System.err.println("Error in mapResultSetToUser: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public List<User> findAllByRole(User.UserRole role) {
        String sql = "SELECT * FROM \"user\" WHERE role = ?::userRole ORDER BY id";
        List<User> users = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, role.name());

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    users.add(mapResultSetToUser(resultSet));
                }
            }
        } catch (SQLException e) {
            return new ArrayList<>();
        }
        return users;
    }
}