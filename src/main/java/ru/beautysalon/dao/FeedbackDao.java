package ru.beautysalon.dao;

import ru.beautysalon.model.Feedback;
import ru.beautysalon.model.Master;
import ru.beautysalon.model.User;
import ru.beautysalon.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FeedbackDao implements Dao<Feedback> {

    @Override
    public Optional<Feedback> findById(Long id) {
        String sql = "SELECT f.*, " +
                "u.firstname, u.lastname, u.email as user_email, " +
                "m.id as master_id, mu.firstname as master_firstname, mu.lastname as master_lastname " +
                "FROM \"feedback\" f " +
                "LEFT JOIN \"user\" u ON f.user_id = u.id " +
                "LEFT JOIN \"master\" m ON f.master_id = m.id " +
                "LEFT JOIN \"user\" mu ON m.user_id = mu.id " +
                "WHERE f.id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(mapResultSetToFeedback(resultSet));
            }
        } catch (SQLException e) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public List<Feedback> findAll() {
        List<Feedback> feedbacks = new ArrayList<>();
        String sql = "SELECT f.*, " +
                "u.firstname, u.lastname, u.email as user_email, " +
                "m.id as master_id, mu.firstname as master_firstname, mu.lastname as master_lastname " +
                "FROM \"feedback\" f " +
                "LEFT JOIN \"user\" u ON f.user_id = u.id " +
                "LEFT JOIN \"master\" m ON f.master_id = m.id " +
                "LEFT JOIN \"user\" mu ON m.user_id = mu.id " +
                "ORDER BY f.created_at DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                feedbacks.add(mapResultSetToFeedback(resultSet));
            }
        } catch (SQLException e) {
            return new ArrayList<>();
        }
        return feedbacks;
    }

    public List<Feedback> findByMasterId(Long masterId) {
        List<Feedback> feedbacks = new ArrayList<>();
        String sql = "SELECT f.*, " +
                "u.firstname, u.lastname, u.email as user_email " +
                "FROM feedback f " +
                "JOIN \"user\" u ON f.user_id = u.id " +
                "WHERE f.master_id = ? " +
                "ORDER BY f.created_at DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, masterId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                feedbacks.add(mapResultSetToFeedback(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return feedbacks;
    }

    public List<Feedback> findByUserId(Long userId) {
        List<Feedback> feedbacks = new ArrayList<>();
        String sql = "SELECT f.*, " +
                "m.id as master_id, " +
                "mu.firstname as master_firstname, mu.lastname as master_lastname, " +
                "mu.avatarurl as master_avatar " +
                "FROM \"feedback\" f " +
                "JOIN \"master\" m ON f.master_id = m.id " +
                "JOIN \"user\" mu ON m.user_id = mu.id " +
                "WHERE f.user_id = ? " +
                "ORDER BY f.created_at DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                feedbacks.add(mapResultSetToFeedback(resultSet));
            }
        } catch (SQLException e) {
            return new ArrayList<>();
        }
        return feedbacks;
    }

    public Double getAverageRatingByMaster(Long masterId) {
        String sql = "SELECT COALESCE(AVG(rating), 0) as average_rating " +
                "FROM \"feedback\" " +
                "WHERE master_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, masterId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble("average_rating");
            }
        } catch (SQLException e) {
            return 0.0;
        }
        return 0.0;
    }

    public int getFeedbackCountByMaster(Long masterId) {
        String sql = "SELECT COUNT(*) as feedback_count FROM \"feedback\" WHERE master_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, masterId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("feedback_count");
            }
        } catch (SQLException e) {
            return 0;
        }
        return 0;
    }

    @Override
    public Feedback save(Feedback feedback) throws SQLException {
        String sql = "INSERT INTO \"feedback\" (user_id, master_id, rating, text, created_at) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, feedback.getUserID());
            statement.setLong(2, feedback.getMasterId());
            statement.setInt(3, feedback.getRating().intValue()); // Исправлено на int
            statement.setString(4, feedback.getText());
            statement.setTimestamp(5, Timestamp.valueOf(feedback.getCreated_at()));

            System.out.println("=== FeedbackDao.save DEBUG ===");
            System.out.println("UserID: " + feedback.getUserID());
            System.out.println("MasterID: " + feedback.getMasterId());
            System.out.println("Rating: " + feedback.getRating());
            System.out.println("Text: " + feedback.getText());
            System.out.println("CreatedAt: " + feedback.getCreated_at());

            int affectedRows = statement.executeUpdate();
            System.out.println("Affected rows: " + affectedRows);

            if (affectedRows == 0) {
                throw new SQLException("Не удалось создать отзыв");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    feedback.setId(generatedKeys.getLong(1));
                    System.out.println("Feedback saved with ID: " + feedback.getId());
                } else {
                    throw new SQLException("Не удалось создать отзыв, id не получен");
                }
            }
            return feedback;

        } catch (SQLException e) {
            System.err.println("Error saving feedback: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void update(Feedback feedback) throws SQLException {
        String sql = "UPDATE \"feedback\" SET rating = ?, text = ? WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDouble(1, feedback.getRating());
            statement.setString(2, feedback.getText());
            statement.setLong(3, feedback.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            return;
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM \"feedback\" WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            return;
        }
    }

    private Feedback mapResultSetToFeedback(ResultSet resultSet) throws SQLException {
        Feedback feedback = new Feedback(
                resultSet.getLong("id"),
                resultSet.getLong("user_id"),
                resultSet.getLong("master_id"),
                resultSet.getDouble("rating"),
                resultSet.getString("text"),
                resultSet.getTimestamp("created_at").toLocalDateTime(),
                null, // user
                null  // master
        );

        try {
            String userName = resultSet.getString("firstname");
            if (userName != null) {
                User user = new User(
                        resultSet.getLong("user_id"),
                        userName,
                        resultSet.getString("lastname")
                );
                user.setEmail(resultSet.getString("user_email"));
                feedback.setUser(user);

                String masterFirstName = resultSet.getString("firstname");
                if (masterFirstName != null) {
                    Master master = new Master(
                            resultSet.getLong("master_id"),
                            user.getId(), // user_id
                            null  // avatarURL
                    );
                    feedback.setMaster(master);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return feedback;
    }

    public boolean hasUserReviewedMaster(Long userId, Long masterId) {
        String sql = "SELECT COUNT(*) as count FROM \"feedback\" WHERE user_id = ? AND master_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, userId);
            statement.setLong(2, masterId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("count") > 0;
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }
}