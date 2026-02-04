package ru.beautysalon.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.beautysalon.model.Master;
import ru.beautysalon.service.FeedbackService;
import ru.beautysalon.service.MasterService;
import ru.beautysalon.service.ServiceLocator;

import java.io.IOException;

@WebServlet("/feedback")
public class FeedbackServlet extends HttpServlet {
    private FeedbackService feedbackService;
    private MasterService masterService;

    public void init() throws ServletException {
        feedbackService = ServiceLocator.getInstance().getFeedbackService();
        masterService = ServiceLocator.getInstance().getMasterService();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        req.setAttribute("basePath", req.getContextPath());

        String masterId = req.getParameter("masterId");
        if (masterId != null) {
            try {
                Long mId = Long.parseLong(masterId);
                java.util.Optional<Master> master = masterService.getMasterById(mId);
                if (master.isPresent()) {
                    req.setAttribute("master", master.get());
                }
            } catch (NumberFormatException e) {}
        }
        req.getRequestDispatcher("/feedback/create.ftl").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            Long userId = (Long) session.getAttribute("userId");
            Long masterId = Long.parseLong(req.getParameter("masterId"));
            Integer rating = Integer.parseInt(req.getParameter("rating"));
            String comment = req.getParameter("comment");

            if (feedbackService.createFeedback(userId, masterId, rating, comment)) {
                System.out.println("Redirecting to master page with success");
                resp.sendRedirect(req.getContextPath() + "/category?salonId=" +
                        masterService.getMasterById(masterId).get().getSalon_id() + "&ok=1");
            } else {
                System.out.println("Redirecting with error");
                resp.sendRedirect(req.getContextPath() + "/feedback?error=1&masterId=" + masterId);
            }
        } catch (Exception e) {
            System.err.println("Error in FeedbackServlet: " + e.getMessage());
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
