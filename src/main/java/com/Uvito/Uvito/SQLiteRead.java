import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteRead {
    public static void readUsers(Connection connection) {
        String sql = "SELECT * FROM users";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                        ", Name: " + rs.getString("name") +
                        ", Email: " + rs.getString("email") +
                        ", Created At: " + rs.getString("created_at"));
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при чтении пользователей: " + e.getMessage());
        }
    }
}
