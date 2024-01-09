package org.max.seminar;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AbstractTest {

    private static Connection connection;
    //  подключение к бд
    private static SessionFactory ourSessionFactory;
    // определенная сессия
    @BeforeAll
    static void init() {
        try {
            //Регистрация драйвера
            Class.forName("org.sqlite.JDBC");
            //Создание подключения
            connection = DriverManager.getConnection("jdbc:sqlite:seminar.db");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        try { // конфиги hibernate для бд
            Configuration configuration = new Configuration();
            configuration.configure();

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }

        System.out.println("Opened database successfully");
    }


    @AfterAll
    static void close() throws SQLException {
        connection.close();// закрытие подключения
        getSession().close();// закрытие сессии
        System.out.println("Closed database successfully");
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public static Connection getConnection() {
        return connection;
    }
}
