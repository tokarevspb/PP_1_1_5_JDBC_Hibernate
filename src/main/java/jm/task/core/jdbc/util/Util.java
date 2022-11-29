package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/new_schema";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "kata";
    public static Connection connection = getConnection();
    public static SessionFactory sessionFactory = getSessionFactory();
    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            if (!connection.isClosed()) {
                System.out.println("Соединение с БД установлено!");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при попытке установления соединения!");
            e.printStackTrace();
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        // Hibernate 5.4 SessionFactory example without XML
        Map<String, String> settings = new HashMap<>();
        settings.put("connection.driver_class", "com.mysql.jdbc.Driver");
        settings.put("dialect", "org.hibernate.dialect.MySQL8Dialect");
        settings.put("hibernate.connection.url",
                "jdbc:mysql://localhost:3306/new_schema");
        settings.put("hibernate.connection.username", "root");
        settings.put("hibernate.connection.password", "kata");
        settings.put("hibernate.current_session_context_class", "thread");
        settings.put("hibernate.show_sql", "true");
        settings.put("hibernate.format_sql", "true");

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(settings).build();

        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        metadataSources.addAnnotatedClass(User.class);
        Metadata metadata = metadataSources.buildMetadata();

        // here we build the SessionFactory (Hibernate 5.4)
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        return sessionFactory;
    }
}
