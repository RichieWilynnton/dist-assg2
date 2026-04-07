package client;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class LeaderboardPanel extends JPanel {
    public LeaderboardPanel() {
        super(new BorderLayout());
        add(buildLeaderboardTable(), BorderLayout.CENTER);
    }

    private JScrollPane buildLeaderboardTable() {
        String[] columns = {"Name", "Wins", "Games", "Avg Time to Win", "Rank"};
        Object[][] data = {
            {"Alice", 18, 42, "02:30", "1"},
            {"Ben", 14, 39, "02:57", "2"},
            {"Charlie", 10, 28, "03:20", "3"},
            {"David", 12, 35, "02:45", "4"}
        };

        DefaultTableModel tableModel = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(tableModel);
        return new JScrollPane(table);
    }
}