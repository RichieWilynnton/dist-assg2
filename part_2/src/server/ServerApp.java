package server;

public class ServerApp {

    public static void main(String[] args) {
        try {
            AuthService authService = new AuthServiceImpl();
            System.setSecurityManager(new SecurityManager());
            java.rmi.Naming.rebind("AuthService", authService);
            System.out.println("Server is running...");
        } catch (Exception e) {
            System.err.println("Exception thrown: " + e);
        }
    }
}
