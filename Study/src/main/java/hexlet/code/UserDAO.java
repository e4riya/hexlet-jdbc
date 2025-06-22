package hexlet.code;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class UserDAO {
    private Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    public void save(User user) throws SQLException {
        if(user.getId() == null){
            var sql = "INSERT INTO users (username, phone) VALUES (?, ?)";
            try(var preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                preparedStatement.setString(1,user.getName());
                preparedStatement.setString(2, user.getNumber());
                preparedStatement.executeUpdate();

                var generatedKeys = preparedStatement.getGeneratedKeys();
                if(generatedKeys.next()){
                    user.setId(generatedKeys.getLong(1));
                }else{
                    throw new SQLException("DB have not returned an id after saving an entity");
                }
            }
        }else{
            var sql = "UPDATE users SET username=?, phone=? WHERE id=?";
            try(var preparedStatement = conn.prepareStatement(sql)){
                preparedStatement.setString(1,user.getName());
                preparedStatement.setString(2, user.getNumber());
                preparedStatement.setLong(3, user.getId());
                preparedStatement.executeUpdate();
            }
        }
    }

    public Optional<User> find(Long id) throws SQLException {
        var sql = "SELECT * FROM users WHERE id=?";
        try(var preparedStatement = conn.prepareStatement(sql)){
            preparedStatement.setLong(1, id);
            var rs = preparedStatement.executeQuery();
            if(rs.next()){
                var username = rs.getString("username");
                var phone = rs.getString("phone");
                var user = new User(username, phone);
                return Optional.of(user);
            }
            return Optional.empty();
        }
    }
    public void delete(Long id) throws SQLException {
        var sql = "DELETE FROM users WHERE id=?";
        try(var preparedStatement = conn.prepareStatement(sql)){
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
    }
}
