package ru.beautysalon.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.beautysalon.service.ServiceLocator;
import ru.beautysalon.service.UserService;

import java.io.IOException;

@WebServlet("/")
public class RootServlet extends HttpServlet {
    private UserService userService;

    public void init() throws ServletException {
        userService = ServiceLocator.getInstance().getUserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("userId") != null) {
            resp.sendRedirect(req.getContextPath() + "/salon/list");

        } else {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
        }
    }
}
