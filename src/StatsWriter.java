import javafx.scene.text.Text;

import java.io.FileWriter;
import java.io.IOException;

public class StatsWriter {

    private int winnerID;
    private Text winnerName;
    private int numberOfPlayers;

    public StatsWriter(int winnerID, int numberOfPlayers) {
        this.winnerID = winnerID;
        this.numberOfPlayers = numberOfPlayers;
        switch (winnerID) {
            case 1:
                winnerName = new Text(Translate.winner() + ": " + PlayerNamesGUI.player1Name.getText());
                break;
            case 2:
                winnerName = new Text(Translate.winner() + ": " + PlayerNamesGUI.player2Name.getText());
                break;
            case 3:
                winnerName = new Text(Translate.winner() + ": " + PlayerNamesGUI.player3Name.getText());
                break;
            case 4:
                winnerName = new Text(Translate.winner() + ": " + PlayerNamesGUI.player4Name.getText());
                break;
        }
    }

    /**
     * Store the winner in a csv file
     */
    public void writeStatsToCSV() throws IOException {
        FileWriter fileWriter = new FileWriter("winners.csv", true);
        if (numberOfPlayers == 2) {
            // Write empty rows for players that did not player
            fileWriter.write(PlayerNamesGUI.player1Name.getText() + ", " + PlayerNamesGUI.player2Name.getText() + ", "
                    + ", " + ", " + winnerName.getText() + "\n");
        } else {
            fileWriter.write(PlayerNamesGUI.player1Name.getText() + ", " + PlayerNamesGUI.player2Name.getText() + ", "
                    + PlayerNamesGUI.player3Name.getText() + ", " + PlayerNamesGUI.player4Name.getText() + ", "
                    + winnerName.getText() + "\n");
        }
        fileWriter.close();
    }

}
