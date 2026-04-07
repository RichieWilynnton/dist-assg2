package client;

public final class ClientSession {
    public static ClientSession instance;

    public String username;
    public int numGames = 2; // Random value
    public int numWins = 1; // Random value
    public float avgTimeToWin = 4.0f; // Random value
    public int rank = 8; // Random value

    private ClientSession() {}

    public static void init()
    {
        if (instance == null) {
            instance = new ClientSession();
        }
    }

    public static void clear() {
        instance = null;
    }
}
