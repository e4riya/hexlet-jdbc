package hexlet.code;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Application {
    public static void main(String[] args) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:hexlet_test");
        String sql =
            "CREATE TABLE users (id BIGINT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(255), phone VARCHAR(255))";
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        stmt.close();

        String sql2 = "INSERT INTO users (username, phone) VALUES ('tommy', '123456789')";
        Statement stmt2 = conn.createStatement();
        stmt2.executeUpdate(sql2);
        stmt2.close();

        String sql3 = "SELECT * FROM users";
        Statement stmt3 = conn.createStatement();
        ResultSet rs = stmt3.executeQuery(sql3);
        while (rs.next()) {
            System.out.println(rs.getString("username"));
            System.out.println(rs.getString("phone"));
        }
        stmt3.close();
        conn.close();
    }
}