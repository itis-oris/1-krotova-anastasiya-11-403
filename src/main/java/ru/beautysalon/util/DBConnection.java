package ru.beautysalon.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {

    private static volatile DataSource dataSource;
    private static final Object lock = new Object();

    private static void init() {

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/pikmi_salon");
        config.setUsername("postgres");
        config.setPassword("uecnheca225");
        config.setDriverClassName("org.postgresql.Driver");
        config.setMaximumPoolSize(5);
        config.setConnectionTimeout(30000);

        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            synchronized (lock) {
                if (dataSource == null) {
                    init();
                }
            }
        }
        return dataSource.getConnection();
    }

    public static void destroy() {
        if (dataSource != null) {
            ((HikariDataSource) dataSource).close();
            dataSource = null;
        }
    }
}