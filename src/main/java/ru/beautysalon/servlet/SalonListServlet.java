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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/salon/list")
public class SalonListServlet extends HttpServlet {
    private SalonService salonService;

    public void init() throws ServletException {
        salonService = ServiceLocator.getInstance().getSalonService();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        List<Salon> salons = salonService.getAllSalons();
        if (salons == null) {
            salons = new ArrayList<>();
        }
        req.setAttribute("basePath", req.getContextPath());
        req.setAttribute("salons", salons);
        req.getRequestDispatcher("/salon/list.ftl").forward(req, resp);
    }
}
