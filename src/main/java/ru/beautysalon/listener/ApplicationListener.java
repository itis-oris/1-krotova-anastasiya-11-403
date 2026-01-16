package ru.beautysalon.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.beautysalon.service.ServiceLocator;
import ru.beautysalon.util.DBConnection;

@WebListener
public class ApplicationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ServiceLocator.getInstance().init();
            System.out.println("веб листенер начал работу");
        } catch (Exception e) {
            System.err.println("Ошибка при инициализации!");
            e.printStackTrace();
            throw new RuntimeException("Не удалось инициализировать приложение", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("веб листенер закончил работу");
        DBConnection.destroy();
    }
}