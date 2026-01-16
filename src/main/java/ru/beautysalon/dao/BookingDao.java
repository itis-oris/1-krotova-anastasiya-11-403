package ru.beautysalon.dao;

import ru.beautysalon.model.*;
import ru.beautysalon.util.DBConnection;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookingDao implements Dao<Booking> {

    @Override
    public Booking save(Booking booking) {
        String sql = "INSERT INTO \"booking\" (user_id, master_id, procedure_id, created_at, data, status) " +
                "VALUES (?, ?, ?, ?, ?, ?::bookingstatus)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, booking.getUserId());
            statement.setLong(2, booking.getMasterId());
            statement.setLong(3, booking.getProcedureID());
            statement.setTimestamp(4, java.sql.Timestamp.valueOf(booking.getCreatedAt()));
            statement.setTimestamp(5, java.sql.Timestamp.valueOf(booking.getData()));
            statement.setString(6, booking.getStatus().name());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Не удалось создать запись");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    booking.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Не удалось создать запись, id не получен");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при сохранении записи", e);
        }

        return booking;
    }

    @Override
    public Optional<Booking> findById(Long id) {
        String sql = "SELECT * FROM \"booking\" WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToBooking(resultSet));
                }
            }
        } catch (SQLException e) {
            return Optional.empty();
        }

        return Optional.empty();
    }

    @Override
    public List<Booking> findAll() {
        String sql = "SELECT * FROM \"booking\" ORDER BY data DESC";
        List<Booking> bookings = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                bookings.add(mapResultSetToBooking(resultSet));
            }
        } catch (SQLException e) {
            return new ArrayList<>();
        }

        return bookings;
    }

    public List<Booking> findByUserId(Long userId) {
        String sql = "SELECT b.*, " +
                "u.firstname as user_firstname, u.lastname as user_lastname, u.email as user_email, u.phone as user_phone, " +
                "m.id as master_id, m.user_id as master_user_id, m.salon_id as master_salon_id, " +
                "mu.firstname as master_firstname, mu.lastname as master_lastname, " +
                "s.id as salon_id, s.salon_name, " +
                "p.procedure_name, p.price, p.description " +
                "FROM \"booking\" b " +
                "LEFT JOIN \"user\" u ON b.user_id = u.id " +
                "LEFT JOIN \"master\" m ON b.master_id = m.id " +
                "LEFT JOIN \"user\" mu ON m.user_id = mu.id " +
                "LEFT JOIN \"salon\" s ON m.salon_id = s.id " +
                "LEFT JOIN \"procedure\" p ON b.procedure_id = p.id " +
                "WHERE b.user_id = ? ORDER BY b.data DESC";

        List<Booking> bookings = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Booking booking = mapResultSetToBookingWithDetails(resultSet);
                    bookings.add(booking);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        return bookings;
    }

    public List<Booking> findByMasterId(Long masterId) {
        String sql = "SELECT b.*, u.firstname, u.lastname, u.email, u.phone, " +
                "p.procedure_name, p.price, p.description " +
                "FROM \"booking\" b " +
                "LEFT JOIN \"user\" u ON b.user_id = u.id " +
                "LEFT JOIN \"procedure\" p ON b.procedure_id = p.id " +
                "WHERE b.master_id = ? ORDER BY b.data DESC";

        List<Booking> bookings = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, masterId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Booking booking = mapResultSetToBooking(resultSet);

                    User user = new User();
                    user.setId(resultSet.getLong("user_id"));
                    user.setFirstname(resultSet.getString("firstname"));
                    user.setLastname(resultSet.getString("lastname"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPhone(resultSet.getString("phone"));
                    booking.setUser(user);

                    Procedure procedure = new Procedure();
                    procedure.setId(resultSet.getLong("procedure_id"));
                    procedure.setProcedure_name(resultSet.getString("procedure_name"));
                    procedure.setPrice(resultSet.getBigDecimal("price"));
                    procedure.setDescription(resultSet.getString("description"));
                    booking.setProcedure(procedure);

                    bookings.add(booking);
                }
            }
        } catch (SQLException e) {
            return new ArrayList<>();
        }

        return bookings;
    }

    @Override
    public void update(Booking booking) throws SQLException {
        String sql = "UPDATE \"booking\" SET user_id = ?, master_id = ?, procedure_id = ?, " +
                "data = ?, status = ?::bookingstatus WHERE id = ?";  // ← ДОБАВЬТЕ ПРИВЕДЕНИЕ ТИПА

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, booking.getUserId());
            statement.setLong(2, booking.getMasterId());
            statement.setLong(3, booking.getProcedureID());
            statement.setTimestamp(4, java.sql.Timestamp.valueOf(booking.getData()));
            statement.setString(5, booking.getStatus().name());  // Убедитесь, что статус не null
            statement.setLong(6, booking.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Ошибка при обновлении записи", e);
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM \"booking\" WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            return;
        }
    }

    private Booking mapResultSetToBooking(ResultSet resultSet) throws SQLException {
        Booking booking = new Booking(
                resultSet.getLong("id"),
                resultSet.getLong("user_id"),
                resultSet.getLong("master_id"),
                resultSet.getLong("procedure_id"),
                resultSet.getTimestamp("created_at").toLocalDateTime(),
                resultSet.getTimestamp("data").toLocalDateTime(),
                null, // user
                null  // procedure
        );

        String statusStr = resultSet.getString("status");
        if (statusStr != null) {
            try {
                booking.setStatus(Booking.BookingStatus.valueOf(statusStr));
            } catch (IllegalArgumentException e) {
                booking.setStatus(Booking.BookingStatus.PENDING);
            }
        }

        return booking;
    }

    private Booking mapResultSetToBookingWithDetails(ResultSet resultSet) throws SQLException {
        Booking booking = mapResultSetToBooking(resultSet);

        User user = new User();
        user.setId(resultSet.getLong("user_id"));
        user.setFirstname(resultSet.getString("user_firstname"));
        user.setLastname(resultSet.getString("user_lastname"));
        user.setEmail(resultSet.getString("user_email"));
        user.setPhone(resultSet.getString("user_phone"));
        booking.setUser(user);

        if (resultSet.getLong("procedure_id") != 0) {
            Procedure procedure = new Procedure();
            procedure.setId(resultSet.getLong("procedure_id"));
            procedure.setProcedure_name(resultSet.getString("procedure_name"));
            procedure.setPrice(resultSet.getBigDecimal("price"));
            procedure.setDescription(resultSet.getString("description"));
            booking.setProcedure(procedure);
        }

        if (resultSet.getLong("master_id") != 0) {
            Master master = new Master();
            master.setId(resultSet.getLong("master_id"));
            master.setSalon_id(resultSet.getLong("master_salon_id"));

            User masterUser = new User();
            masterUser.setId(resultSet.getLong("master_user_id"));
            masterUser.setFirstname(resultSet.getString("master_firstname"));
            masterUser.setLastname(resultSet.getString("master_lastname"));
            master.setUser(masterUser);

            Salon salon = new Salon();
            salon.setId(resultSet.getLong("salon_id"));
            salon.setSalon_Name(resultSet.getString("salon_name")); // Используем salon_name
            master.setSalon(salon);

            booking.setMaster(master);
        }

        return booking;
    }

    public boolean existsByMasterAndDateTime(Long masterId, LocalDateTime dateTime) throws SQLException {

        String sql = "SELECT COUNT(*) as count FROM \"booking\" WHERE master_id = ? AND data = ? AND status = 'CONFIRMED'::bookingstatus";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, masterId);
            statement.setTimestamp(2, Timestamp.valueOf(dateTime));

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("count") > 0;
            }
        }
        return false;
    }
}