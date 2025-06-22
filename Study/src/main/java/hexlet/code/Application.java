package hexlet.code;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Application {
    public static void main(String[] args) throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:hexlet_test")) {
            String sql =
                "CREATE TABLE users (id BIGINT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(255), phone VARCHAR(255))";
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
            }
            UserDAO ud = new UserDAO(conn);
            ud.save(new User("tommy", "123456789"));
            ud.save(new User("Alex", "228"));
            ud.save(new User("Bob", "123123"));

            String sql2 = "DELETE FROM users WHERE USERNAME = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql2)) {
                stmt.setString(1, "tommy");
                stmt.executeUpdate();
            }
            String sql3 = "SELECT * FROM users";
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(sql3);
                while (rs.next()) {
                    System.out.print(rs.getString("username") + " ");
                    System.out.println(rs.getString("phone"));
                }
            }
        }
    }
}