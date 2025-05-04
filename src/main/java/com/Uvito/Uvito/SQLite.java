import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLite {
    private static final String DB_URL = "jdbc:sqlite:example.db";

    public static void main(String[] args) {
        // Создаем соединение с базой данных
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            if (connection != null) {
                System.out.println("Соединение установлено!");
                // Создаем таблицу
                createTable(connection);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createTable(Connection connection) {
        String sql = """
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    email TEXT NOT NULL UNIQUE,
                    created_at TEXT DEFAULT CURRENT_TIMESTAMP
                );
                """;

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Таблица создана!");
        } catch (SQLException e) {
            System.out.println("Ошибка при создании таблицы: " + e.getMessage());
        }
    }
}
