package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseService {
    private static final String DB_HOST = "localhost";
    private static final String DB_USER = "c3358";
    private static final String DB_PASS = "c3358PASS";
    private static final String DB_NAME = "c3358";

    private final Connection conn;

    public DatabaseService() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        conn = DriverManager.getConnection(
                "jdbc:mysql://" + DB_HOST + "/" + DB_NAME
                        + "?user=" + DB_USER
                        + "&password=" + DB_PASS);
    }

    public void insertUser(String username, String password) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO users (username, password) VALUES (?, ?)");
        stmt.setString(1, username);
        stmt.setString(2, password);
        stmt.execute();
    }

    public UserInfoEntry readUser(String username) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
                "SELECT username, password, num_games, num_wins, avg_time_to_win, rank" +
                " FROM users WHERE username = ?");
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new UserInfoEntry(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getInt("num_games"),
                    rs.getInt("num_wins"),
                    rs.getFloat("avg_time_to_win"),
                    rs.getInt("rank"));
        }
        return null;
    }

    public void updateUserPassword(String username, String newPassword) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
                "UPDATE users SET password = ? WHERE username = ?");
        stmt.setString(1, newPassword);
        stmt.setString(2, username);
        stmt.executeUpdate();
    }

    public void updateUserStats(String username, int numGames, int numWins, float avgTimeToWin, int rank) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
                "UPDATE users SET num_games = ?, num_wins = ?, avg_time_to_win = ?, rank = ?" +
                " WHERE username = ?");
        stmt.setInt(1, numGames);
        stmt.setInt(2, numWins);
        stmt.setFloat(3, avgTimeToWin);
        stmt.setInt(4, rank);
        stmt.setString(5, username);
        stmt.executeUpdate();
    }

    public void deleteUser(String username) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
                "DELETE FROM users WHERE username = ?");
        stmt.setString(1, username);
        stmt.executeUpdate();
    }

    public void insertOnlineUser(String username) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO online_users (username) VALUES (?)");
        stmt.setString(1, username);
        stmt.execute();
    }

    public boolean readOnlineUser(String username) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
                "SELECT username FROM online_users WHERE username = ?");
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        return rs.next();
    }

    public void updateOnlineUser(String oldUsername, String newUsername) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
                "UPDATE online_users SET username = ? WHERE username = ?");
        stmt.setString(1, newUsername);
        stmt.setString(2, oldUsername);
        stmt.executeUpdate();
    }

    public void deleteOnlineUser(String username) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
                "DELETE FROM online_users WHERE username = ?");
        stmt.setString(1, username);
        stmt.executeUpdate();
    }
}
