package ru.beautysalon.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.beautysalon.model.Feedback;
import ru.beautysalon.model.Master;
import ru.beautysalon.model.Procedure;
import ru.beautysalon.service.BookingService;
import ru.beautysalon.service.FeedbackService;
import ru.beautysalon.service.MasterService;
import ru.beautysalon.service.ServiceLocator;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

///master-profile?masterId=123
@WebServlet("/master-profile")
public class MasterProfileServlet extends HttpServlet {
    private MasterService masterService;
    private FeedbackService feedbackService;
    private BookingService bookingService;

    public void init() throws ServletException {
        masterService = ServiceLocator.getInstance().getMasterService();
        feedbackService = ServiceLocator.getInstance().getFeedbackService();
        bookingService = ServiceLocator.getInstance().getBookingService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        try {
            Long masterId = Long.parseLong(req.getParameter("masterId"));
            Optional<Master> master = masterService.getMasterById(masterId);

            if (!master.isPresent()) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            List<Procedure> procedures = masterService.getMasterProcedures(masterId);
            List<Feedback> feedbacks = masterService.getMasterFeedbacks(masterId);
            Double rating = masterService.getMasterRating(masterId);

            req.setAttribute("basePath", req.getContextPath());
            req.setAttribute("master", master.get());
            req.setAttribute("procedures", procedures);
            req.setAttribute("feedbacks", feedbacks);
            req.setAttribute("rating", rating != null ? rating : 0.0);
            req.getRequestDispatcher("/master/profile.ftl").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}