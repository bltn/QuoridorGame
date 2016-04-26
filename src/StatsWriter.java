import javafx.scene.text.Text;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by junaid on 4/26/16.
 */
public class StatsWriter {

    private int winnerID;
    private Text winnerName;
    private int numberOfPlayers;

    public StatsWriter(int winnerID, int numberOfPlayers) {
        this.winnerID = winnerID;
        this.numberOfPlayers = numberOfPlayers;
        switch (winnerID) {
            case 1:
                winnerName = new Text(Translate.winner() + ": " + MenuGUI.player1Name.getText());
                break;
            case 2:
                winnerName = new Text(Translate.winner() + ": " + MenuGUI.player2Name.getText());
                break;
            case 3:
                winnerName = new Text(Translate.winner() + ": " + MenuGUI.player3Name.getText());
                break;
            case 4:
                winnerName = new Text(Translate.winner() + ": " + MenuGUI.player4Name.getText());
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
            fileWriter.write(MenuGUI.player1Name.getText() + ", " + MenuGUI.player2Name.getText() + ", "
                    + ", " + ", " + winnerName.getText() + "\n");
        } else {
            fileWriter.write(MenuGUI.player1Name.getText() + ", " + MenuGUI.player2Name.getText() + ", "
                    + MenuGUI.player3Name.getText() + ", " + MenuGUI.player4Name.getText() + ", "
                    + winnerName.getText() + "\n");
        }
        fileWriter.close();
    }

}
