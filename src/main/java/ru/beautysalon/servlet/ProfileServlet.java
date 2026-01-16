package ru.beautysalon.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.beautysalon.model.User;
import ru.beautysalon.service.ServiceLocator;
import ru.beautysalon.service.UserService;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private UserService userService;

    public void init() throws ServletException {
        userService = ServiceLocator.getInstance().getUserService();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        Long userId = (Long) session.getAttribute("userId");
        Optional<User> user = userService.getUserById(userId);

        req.setAttribute("basePath", req.getContextPath());

        if (user.isPresent()) {
            req.setAttribute("user", user.get());
            req.getRequestDispatcher("/user/profile.ftl").forward(req, resp);
        } else {
            session.invalidate();
            resp.sendRedirect(req.getContextPath() + "/auth/login");
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        Long userId = (Long) session.getAttribute("userId");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String phone = req.getParameter("phone");

        if (userService.updateUser(userId, firstName, lastName, phone)) {
            session.setAttribute("name", firstName + " " + lastName);
            resp.sendRedirect(req.getContextPath() + "/profile?ok=1");
        } else {
            doGet(req, resp);
        }
    }
}
