package ru.beautysalon.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.beautysalon.model.Salon;
import ru.beautysalon.service.SalonService;
import ru.beautysalon.service.ServiceLocator;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/salon/detail/*")
public class SalonDetailServlet extends HttpServlet {
    private SalonService salonService;

    public void init() throws ServletException {
        salonService = ServiceLocator.getInstance().getSalonService();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        try {
            Long salonId = Long.parseLong(pathInfo.substring(1));
            Optional<Salon> salon = salonService.getSalonById(salonId);

            if (!salon.isPresent()) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            req.setAttribute("basePath", req.getContextPath());

            req.setAttribute("salon", salon.get());
            req.setAttribute("masters", salonService.getMastersBySalon(salonId));
            req.getRequestDispatcher("/salon/detail.ftl").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
