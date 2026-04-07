package server;

import dto.GetProfileRequest;
import dto.GetProfileResponse;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

public class UserServiceImpl extends UnicastRemoteObject implements UserService {
    private final DatabaseService db;

    public UserServiceImpl(DatabaseService db) throws RemoteException {
        this.db = db;
    }

    private boolean isAuthenticated(String username) throws SQLException {
        return db.readOnlineUser(username);
    }

    @Override
    public GetProfileResponse getProfile(GetProfileRequest request) throws RemoteException {
        try {
            if (!isAuthenticated(request.username)) {
                return new GetProfileResponse(false, "Not authenticated");
            }
            UserInfoEntry entry = db.readUser(request.username);
            if (entry == null) {
                return new GetProfileResponse(false, "User not found");
            }
            return new GetProfileResponse(true, "OK",
                    entry.username, entry.numGames, entry.numWins,
                    entry.avgTimeToWin, entry.rank);
        } catch (SQLException e) {
            System.err.println("Database error in getProfile: " + e.getMessage());
            return new GetProfileResponse(false, "Internal server error");
        }
    }
}
