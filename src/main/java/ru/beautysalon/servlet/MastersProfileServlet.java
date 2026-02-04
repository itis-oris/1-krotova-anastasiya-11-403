package ru.beautysalon.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ru.beautysalon.model.*;
import ru.beautysalon.service.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@WebServlet("/mas-profile")
public class MastersProfileServlet extends HttpServlet {
    private MasterService masterService;
    private BookingService bookingService;
    private ProcedureService procedureService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        masterService = ServiceLocator.getInstance().getMasterService();
        bookingService = ServiceLocator.getInstance().getBookingService();
        procedureService = ServiceLocator.getInstance().getProcedureService();
        userService = ServiceLocator.getInstance().getUserService();
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

        try {
            Optional<Master> master = masterService.getMasterByUserId(userId);

            if (!master.isPresent()) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Профиль мастера не найден");
                return;
            }

            List<Booking> bookings = bookingService.getMasterBookings(master.get().getId());

            List<Procedure> procedures = procedureService.getProceduresByMaster(master.get().getId());

            List<User> clients = userService.getAllClients();

            req.setAttribute("basePath", req.getContextPath());
            req.setAttribute("master", master.get());
            req.setAttribute("bookings", bookings);
            req.setAttribute("procedures", procedures);
            req.setAttribute("clients", clients);

            req.getRequestDispatcher("/master/master-profile.ftl").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        Long userId = (Long) session.getAttribute("userId");
        User.UserRole userRole = (User.UserRole) session.getAttribute("userRole");

        if (userRole != User.UserRole.MASTER) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String action = req.getParameter("action");

        try {
            switch (action) {
                case "updateProfile":
                    handleUpdateProfile(req, resp, userId);
                    break;
                case "createBooking":
                    handleCreateBooking(req, resp, userId);
                    break;
                case "updateBooking":
                    handleUpdateBooking(req, resp, userId);
                    break;
                case "deleteBooking":
                    handleDeleteBooking(req, resp, userId);
                    break;
                default:
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void handleUpdateProfile(HttpServletRequest req, HttpServletResponse resp, Long userId)
            throws ServletException, IOException {
        Optional<Master> master = masterService.getMasterByUserId(userId);

        if (!master.isPresent()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Master m = master.get();

        String description = req.getParameter("description");
        String stag = req.getParameter("stag");
        String avatarURL = req.getParameter("avatarURL");

        if (description != null) m.setDescription(description);
        if (stag != null) m.setStag(stag);
        if (avatarURL != null) m.setAvatarURL(avatarURL);

        boolean success = masterService.updateMaster(m.getId(), m);

        if (success) {
            resp.sendRedirect(req.getContextPath() + "/mas-profile?success=profile_updated");
        } else {
            resp.sendRedirect(req.getContextPath() + "/mas-profile?error=update_failed");
        }
    }

    private void handleCreateBooking(HttpServletRequest req, HttpServletResponse resp, Long userId)
            throws ServletException, IOException {
        Optional<Master> master = masterService.getMasterByUserId(userId);

        if (!master.isPresent()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        try {
            String clientIdParam = req.getParameter("clientId");
            String procedureIdParam = req.getParameter("procedureId");
            String dateParam = req.getParameter("date");
            String timeParam = req.getParameter("time");

            if (clientIdParam == null || clientIdParam.isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/mas-profile?error=client_not_selected");
                return;
            }

            Long clientId = Long.parseLong(clientIdParam);
            Long procedureId = Long.parseLong(procedureIdParam);
            LocalDate date = LocalDate.parse(dateParam);
            LocalTime time = LocalTime.parse(timeParam);

            boolean success = bookingService.createBooking(
                    clientId,
                    master.get().getId(),
                    procedureId,
                    date,
                    time
            );

            if (success) {
                resp.sendRedirect(req.getContextPath() + "/mas-profile?success=booking_created");
            } else {
                resp.sendRedirect(req.getContextPath() + "/mas-profile?error=booking_failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/mas-profile?error=invalid_data");
        }
    }

    private void handleUpdateBooking(HttpServletRequest req, HttpServletResponse resp, Long userId)
            throws ServletException, IOException {
        try {
            Long bookingId = Long.parseLong(req.getParameter("bookingId"));
            LocalDate date = LocalDate.parse(req.getParameter("date"));
            LocalTime time = LocalTime.parse(req.getParameter("time"));
            String status = req.getParameter("status");

            boolean success = bookingService.updateBooking(
                    bookingId,
                    LocalDateTime.of(date, time),
                    Booking.BookingStatus.valueOf(status)
            );

            if (success) {
                resp.sendRedirect(req.getContextPath() + "/mas-profile?success=booking_updated");
            } else {
                resp.sendRedirect(req.getContextPath() + "/mas-profile?error=update_failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/mas-profile?error=invalid_data");
        }
    }

    private void handleDeleteBooking(HttpServletRequest req, HttpServletResponse resp, Long userId)
            throws ServletException, IOException {
        try {
            Long bookingId = Long.parseLong(req.getParameter("bookingId"));

            boolean success = bookingService.deleteBooking(bookingId);

            if (success) {
                resp.sendRedirect(req.getContextPath() + "/mas-profile?success=booking_deleted");
            } else {
                resp.sendRedirect(req.getContextPath() + "/mas-profile?error=delete_failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/mas-profile?error=invalid_data");
        }
    }
}