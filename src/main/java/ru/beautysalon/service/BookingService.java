package ru.beautysalon.service;

import ru.beautysalon.dao.*;
import ru.beautysalon.model.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class BookingService {
    private BookingDao bookingDao;

    public BookingService(BookingDao bookingDao) {
        this.bookingDao = bookingDao;
    }

    public boolean createBooking(long userId, Long masterId, Long procedureId, LocalDate date, LocalTime time) throws SQLException {

        LocalDateTime dateTime = LocalDateTime.of(date, time);

        if (bookingDao.existsByMasterAndDateTime(masterId, dateTime)) {
            return false;
        }

        Booking booking = new Booking();
        booking.setUserId(userId);
        booking.setMasterId(masterId);
        booking.setProcedureID(procedureId);
        booking.setData(dateTime);
        booking.setStatus(Booking.BookingStatus.PENDING);
        booking.setCreatedAt(LocalDateTime.now());

        Booking savedBooking = bookingDao.save(booking);
        return savedBooking != null && savedBooking.getId() != null;
    }


    public List<Booking> getMasterBookings(long masterId) {
        return bookingDao.findByMasterId(masterId);
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingDao.findById(id);
    }

    public boolean updateBooking(Long bookingId, LocalDateTime newDateTime, Booking.BookingStatus newStatus) {
        try {
            Optional<Booking> bookingOpt = bookingDao.findById(bookingId);
            if (!bookingOpt.isPresent()) {
                return false;
            }

            Booking booking = bookingOpt.get();
            booking.setData(newDateTime);
            booking.setStatus(newStatus);

            bookingDao.update(booking);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBooking(Long bookingId) {
        try {
            bookingDao.delete(bookingId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Booking> getUserBookings(Long userId) {
        return bookingDao.findByUserId(userId);
    }
}
