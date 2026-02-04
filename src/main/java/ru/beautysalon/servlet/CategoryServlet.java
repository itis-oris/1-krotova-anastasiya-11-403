package ru.beautysalon.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.beautysalon.model.Category;
import ru.beautysalon.service.CategoryService;
import ru.beautysalon.service.ServiceLocator;

import java.io.IOException;
import java.util.List;

@WebServlet("/category")
public class CategoryServlet extends HttpServlet {
    private CategoryService categoryService;

    public void init() throws ServletException {
        categoryService = ServiceLocator.getInstance().getCategoryService();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }

        String salonId = req.getParameter("salonId");
        List<Category> categories = categoryService.getAllCategories();

        req.setAttribute("basePath", req.getContextPath());

        req.setAttribute("categories", categories);
        req.setAttribute("salonId", salonId);
        req.getRequestDispatcher("/category/select.ftl").forward(req, resp);
    }
}