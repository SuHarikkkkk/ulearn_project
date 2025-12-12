package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    //метод подключается к SQLite-файлу
    public static Connection connect(String dbPath) {
        Connection conn = null;
        try {
            //путь к файлу
            String url = "jdbc:sqlite:" + dbPath;

            //устанавливаем соединение
            conn = DriverManager.getConnection(url);

            System.out.println("Подключение к SQLite прошло успешно!");

        } catch (SQLException e) {
            System.out.println("Ошибка подключения к базе данных!");
            e.printStackTrace();
        }

        return conn;
    }
}
