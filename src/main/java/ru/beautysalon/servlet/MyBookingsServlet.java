package ru.beautysalon.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ru.beautysalon.model.Booking;
import ru.beautysalon.model.User;
import ru.beautysalon.service.BookingService;
import ru.beautysalon.service.ServiceLocator;

import java.io.IOException;
import java.util.List;

@WebServlet("/my-bookings")
public class MyBookingsServlet extends HttpServlet {
    private BookingService bookingService;

    @Override
    public void init() throws ServletException {
        bookingService = ServiceLocator.getInstance().getBookingService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        Long userId = (Long) session.getAttribute("userId");
        User.UserRole userRole = (User.UserRole) session.getAttribute("userRole");

        try {
            List<Booking> bookings = bookingService.getUserBookings(userId);

            req.setAttribute("basePath", req.getContextPath());
            req.setAttribute("bookings", bookings);

            req.getRequestDispatcher("/user/my-bookings.ftl").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
