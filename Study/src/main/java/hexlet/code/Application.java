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

            String sql2 = "INSERT INTO users (username, phone) VALUES ('tommy', '123456789')";
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(sql2);
            }

            String sql4 = "INSERT INTO users (username, phone) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql4)) {
                stmt.setString(1, "Alex");
                stmt.setString(2, "228");
                stmt.executeUpdate();

                stmt.setString(1, "Bob");
                stmt.setString(2, "123123");
                stmt.executeUpdate();
            }
            String sql5 = "DELETE FROM users WHERE USERNAME = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql5)) {
                stmt.setString(1, "tommy");
                stmt.execute();
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