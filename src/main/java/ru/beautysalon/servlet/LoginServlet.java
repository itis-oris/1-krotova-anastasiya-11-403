package ru.beautysalon.servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ru.beautysalon.service.*;
import ru.beautysalon.model.*;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {
    private UserService userService;

    public void init() throws ServletException {
        userService = ServiceLocator.getInstance().getUserService();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("userId") != null) {
            resp.sendRedirect(req.getContextPath() + "/salon/list");
            return;
        }

        req.setAttribute("basePath", req.getContextPath());
        req.getRequestDispatcher("/auth/login.ftl").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        Optional<User> user = userService.authenticate(email, password);

        if (user.isPresent()) {
            HttpSession session = req.getSession();
            User authenticatedUser = user.get();

            session.setAttribute("user", authenticatedUser);
            session.setAttribute("userId", authenticatedUser.getId());
            session.setAttribute("email", authenticatedUser.getEmail());
            session.setAttribute("name", authenticatedUser.getFirstname() + " " + authenticatedUser.getLastname());
            session.setAttribute("userRole", authenticatedUser.getRole());

            resp.sendRedirect(req.getContextPath() + "/salon/list");

        } else {
            req.setAttribute("basePath", req.getContextPath());
            req.setAttribute("error", "Неверные учетные данные");
            req.getRequestDispatcher("/auth/login.ftl").forward(req, resp);
        }
    }
}