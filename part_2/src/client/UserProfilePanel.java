package client;

import dto.GetProfileRequest;
import dto.GetProfileResponse;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class UserProfilePanel extends JPanel {
    public UserProfilePanel() {
        super(new GridBagLayout());
        buildUi();
    }

    private void buildUi() {
        String username = ClientSession.username;
        int numWins = 0, numGames = 0, rank = 0;
        float avgTimeToWin = 0.0f;

        boolean fetchFailed = false;

        try {
            GetProfileResponse resp = ClientApp.userService.getProfile(new GetProfileRequest(username));
            if (resp.success) {
                numWins = resp.numWins;
                numGames = resp.numGames;
                avgTimeToWin = resp.avgTimeToWin;
                rank = resp.rank;
            } else {
                fetchFailed = true;
            }
        } catch (Exception e) {
            fetchFailed = true;
        }

        if (fetchFailed) {
            add(new JLabel("Failed to fetch profile."));
            return;
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.LINE_START;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        add(new JLabel(username), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Number of Wins:"), gbc);

        gbc.gridx = 1;
        add(new JLabel(String.valueOf(numWins)), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Number of Games:"), gbc);

        gbc.gridx = 1;
        add(new JLabel(String.valueOf(numGames)), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Average Time to Win:"), gbc);

        gbc.gridx = 1;
        add(new JLabel(String.valueOf(avgTimeToWin)), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Rank:"), gbc);

        gbc.gridx = 1;
        add(new JLabel(String.valueOf(rank)), gbc);
    }
}