package ru.beautysalon.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.beautysalon.service.ServiceLocator;
import ru.beautysalon.service.UserService;

import java.io.IOException;

@WebServlet("/auth/register")
public class RegisterServlet extends HttpServlet {
    private UserService userService;

    public void init() throws ServletException {
        userService = ServiceLocator.getInstance().getUserService();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("basePath", req.getContextPath());
        req.getRequestDispatcher("/auth/register.ftl").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String firstName = req.getParameter("firstname");
        String lastName = req.getParameter("lastname");
        String phone = req.getParameter("phone");

        if (userService.registerUser(email, password, firstName, lastName, phone)) {
            resp.sendRedirect(req.getContextPath() + "/auth/login?success=true");
        } else {
            req.setAttribute("basePath", req.getContextPath());
            req.setAttribute("error", "Email уже используется");
            req.getRequestDispatcher("/auth/register.ftl").forward(req, resp);
        }
    }
}
