import game.*;
import pieces.*;
import tools.*;

public class App {
    public static void main(String[] args) throws Exception
    {
        System.out.println("\n");

        /* ------------------------------------------------------------------------------------ */

        // Initialize head variables
        SettingsReader reader = new SettingsReader("src/settings.json");
        Player[] players = new Player[reader.getNumPlayers()];

        // Create all players and their pieces
        for (int i = 0; i < reader.getNumPlayers(); i++)
        {
            String  color = reader.getPlayerValueOf("color", i);
            String  name  = reader.getPlayerValueOf("name", i);
            int[][] pawns = reader.getPlayerPieces("pawn", i);

            players[i] = new Player(name, color);
            for (int[] p : pawns) { players[i].givePiece(new Pawn(color, p[0], p[1])); }
        }

        // Create board and populate board
        int height = (int) reader.getIntValueOf("boardHeight");
        int length = (int) reader.getIntValueOf("boardLength");
        Board board = new Board(height, length, players);
    

        board.printBoard();

        /* ------------------------------------------------------------------------------------ */


        System.out.println("\n");
        
    }
}
