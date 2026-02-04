package ru.beautysalon.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.beautysalon.model.Master;
import ru.beautysalon.service.MasterService;
import ru.beautysalon.service.ServiceLocator;

import java.io.IOException;
import java.util.List;

@WebServlet("/master")
public class MasterListServlet extends HttpServlet {
    private MasterService masterService;

    public void init() throws ServletException {
        masterService = ServiceLocator.getInstance().getMasterService();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        String salonIdParam = req.getParameter("salonId");
        String categoryIdParam = req.getParameter("categoryId");

        List<Master> masters;
        try {
            if (salonIdParam != null && categoryIdParam != null) {
                Long salonId = Long.parseLong(salonIdParam);
                Long categoryId = Long.parseLong(categoryIdParam);
                masters = masterService.getMastersBySalonAndCategory(salonId, categoryId);
            } else if (salonIdParam != null) {
                Long salonId = Long.parseLong(salonIdParam);
                masters = masterService.getMastersBySalon(salonId);
            } else {
                masters = java.util.Collections.emptyList();
            }
        } catch (NumberFormatException e) {
            masters = java.util.Collections.emptyList();
        }

        req.setAttribute("masters", masters);
        req.setAttribute("salonId", salonIdParam);
        req.setAttribute("categoryId", categoryIdParam);

        req.setAttribute("basePath", req.getContextPath());
        req.getRequestDispatcher("/master/list.ftl").forward(req, resp);

    }
}
