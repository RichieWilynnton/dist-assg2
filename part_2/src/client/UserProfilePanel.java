package client;

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
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.LINE_START;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        add(new JLabel(ClientSession.instance.username), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Number of Wins:"), gbc);

        gbc.gridx = 1;
        add(new JLabel(String.valueOf(ClientSession.instance.numWins)), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Number of Games:"), gbc);

        gbc.gridx = 1;
        add(new JLabel(String.valueOf(ClientSession.instance.numGames)), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Average Time to Win:"), gbc);

        gbc.gridx = 1;
        add(new JLabel(String.valueOf(ClientSession.instance.avgTimeToWin)), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Rank:"), gbc);

        gbc.gridx = 1;
        add(new JLabel(String.valueOf(ClientSession.instance.rank)), gbc);
    }
}