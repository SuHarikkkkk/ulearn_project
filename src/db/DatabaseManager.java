package db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

//DatabaseManager отвечает за создание структуры базы данных SQLite
public class DatabaseManager {

    public static void createTables(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {

            //создаЄм таблицу businesses - информаци€ о компании
            String businesses = """
                    CREATE TABLE IF NOT EXISTS businesses (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        company_name TEXT NOT NULL,
                        street_name TEXT,
                        business_type TEXT
                    );
            """;

            //создаЄм таблицу fiscal_years Ч справочник лет, каждый год хранитс€ 1 раз
            String fiscalYears = """
                    CREATE TABLE IF NOT EXISTS fiscal_years (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        year INTEGER UNIQUE NOT NULL
                    );
            """;

            //создаЄм таблицу grants Ч храним конкретные гранты, каждый грант св€зан с компанией и годом
            String grants = """
                CREATE TABLE IF NOT EXISTS grants (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    business_id INTEGER NOT NULL,
                    fiscal_year_id INTEGER NOT NULL,
                    amount REAL NOT NULL,
                    jobs_created INTEGER,
                    FOREIGN KEY (business_id) REFERENCES businesses(id),
                    FOREIGN KEY (fiscal_year_id) REFERENCES fiscal_years(id)
                );
            """;

            //создание таблиц
            stmt.execute(businesses);
            stmt.execute(fiscalYears);
            stmt.execute(grants);
        }
    }
}
