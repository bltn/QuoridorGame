import javafx.scene.text.Text;
import java.io.FileWriter;
import java.io.IOException;

/**
 * StatsWriter uses the player names to store the players and the winners
 * of each game into a CSV file. First, the names of each of the players
 * who played in the game is stored and then the name of the winner
 * is written to the CSV file.
 *
 * @author Junaid Rasheed
 */
public class StatsWriter {

    private Text winnerName;
    private int numberOfPlayers;

    public StatsWriter(int winnerID, int numberOfPlayers) {
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
