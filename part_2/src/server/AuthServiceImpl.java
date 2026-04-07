package server;

import dto.LoginRequest;
import dto.LoginResponse;
import dto.LogoutRequest;
import dto.LogoutResponse;
import dto.RegisterRequest;
import dto.RegisterResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AuthServiceImpl extends UnicastRemoteObject implements AuthService {
    private final String USER_INFO_FILE = "UserInfo.txt";
    private final String ONLINE_USER_FILE = "OnlineUser.txt";

    public AuthServiceImpl() throws RemoteException {
        ensureFilesExist();
    }

    private void ensureFilesExist() {
        try {
            new File(USER_INFO_FILE).createNewFile();
            new File(ONLINE_USER_FILE).createNewFile();
        } catch (IOException e) {
            System.err.println("Error creating data files: " + e.getMessage());
        }
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        UserInfoEntry userInfo = findUserInfo(loginRequest.username);
        if (userInfo == null) {
            return new LoginResponse(false, "User not found");
        }

        if (!userInfo.password.equals(loginRequest.password)) {
            return new LoginResponse(false, "Incorrect password");
        }

        if (isUserOnline(loginRequest.username)) {
            return new LoginResponse(false, "User already logged in");
        }

        addOnlineUser(loginRequest.username);
        System.out.println("User logged in: " + loginRequest.username);
        return new LoginResponse(true, "Login successful");
    }

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        UserInfoEntry userInfo = findUserInfo(registerRequest.username);
        if (userInfo != null) {
            return new RegisterResponse(false, "Username already exists");
        }

        addUserInfo(registerRequest);
        System.out.println("New user registered: " + registerRequest.username);
        return new RegisterResponse(true, "Registration successful");
    }

    @Override
    public LogoutResponse logout(LogoutRequest logoutRequest) {
        removeOnlineUser(logoutRequest.username);
        System.out.println("User logged out: " + logoutRequest.username);
        return new LogoutResponse(true, "Logout successful");
    }

    private UserInfoEntry findUserInfo(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_INFO_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length < 2) {
                    continue;
                }

                String fileUsername = parts[0];
                String filePassword = parts[1];

                if (fileUsername.equals(username)) {
                    return new UserInfoEntry(fileUsername, filePassword);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading " + USER_INFO_FILE + ": " + e.getMessage());
        }
        return null;
    }

    private boolean isUserOnline(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ONLINE_USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading " + ONLINE_USER_FILE + ": " + e.getMessage());
        }
        return false;
    }

    private void addUserInfo(RegisterRequest registerRequest)
    {
        try (FileWriter writer = new FileWriter(USER_INFO_FILE, true)) {
            writer.write(registerRequest.username + "," + registerRequest.password + "\n");
        } catch (IOException e) {
            System.err.println("Error writing to " + USER_INFO_FILE + ": " + e.getMessage());
        }
    }

    private void addOnlineUser(String username) {
        try (FileWriter writer = new FileWriter(ONLINE_USER_FILE, true)) {
            writer.write(username + "\n");
        } catch (IOException e) {
            System.err.println("Error writing to " + ONLINE_USER_FILE + ": " + e.getMessage());
        }
    }

    private void removeOnlineUser(String username) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(ONLINE_USER_FILE));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().equals(username)) {
                    sb.append(line).append("\n");
                }
            }
            reader.close();
            
            FileWriter writer = new FileWriter(ONLINE_USER_FILE);
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            System.err.println("Error updating " + ONLINE_USER_FILE + ": " + e.getMessage());
        }
    }

}
