import game.*;
import pieces.*;
import tools.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception
    {
        System.out.println("\n");

        /* ------------------------------------------------------------------------------------ */

        // Initialize head variables
        Scanner user = new Scanner(System.in);
        SettingsReader reader = new SettingsReader("src/settings.json");
        int numPlayers = reader.getNumPlayers();
        Player[] players = new Player[numPlayers];

        // Create all players and their pieces
        for (int i = 0; i < numPlayers; i++)
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

        int i = 0;
        while (i < numPlayers*5)
        {
            Player player = players[i];
            board.printBoard();

            // i fucking hate that the user needs to be passed through as an argument but Scanner exceptions have forced my hand
            GamePiece piece = selectPiece(player, board, user);   

            int[] movePoint = selectMove(piece, board, user);
            
            i = i < numPlayers ? i+1 : 0;
        }

        user.close();

        /* ------------------------------------------------------------------------------------ */

        System.out.println("\n");
    }

    public static GamePiece selectPiece(Player player, Board board, Scanner user)
    {
        GamePiece piece = null;
        
        System.out.print("Choose a piece to move (x,y): ");
        int[] point = ArrayUtils.extractPointFromString(user.nextLine());
        if (point == null) { return selectPiece(player, board, user); } // edge case for gibberish input

        int col = point[0];
        int row = point[1];
        String systemResponse;

        // this could be converted into a switch case if board.checkSpace() was function that returns an int and handles out of bounds inputs
        // e.g. (-1 = out of bounds, 0 = empty space, 1 = space occupied by color, 2 = space occupied by enemy color)
        if (col > board.getLength() || row > board.getHeight() || !board.checkSpace(col, row, player.getColor()))
        {
            systemResponse = Color.RED + point + " is not a valid piece. Please try again.";
        }
        
        {
            piece = player.getPiece(col, row);
            systemResponse = Color.GREEN + "You have chosen " + piece.getName() + "(" + piece.getCol() + "," + piece.getRow() + ")";
        }

        System.out.println(systemResponse + Color.RESET);
        return piece == null ? selectPiece(player, board, user) : piece;
    }

    public static int[] selectMove(GamePiece piece, Board board, Scanner user)
    {
        int[] move = null;

        System.out.print("Choose a space to move to (x,y) ");
        int[] point = ArrayUtils.extractPointFromString(user.nextLine());
        if (point == null) { return selectMove(piece, board, user); } // edge case for gibberish input

        int col = point[0];
        int row = point[1];
        String systemResponse;

        if (col > board.getLength() || row > board.getHeight() || !board.checkSpace(col, row, piece.getColor()))
        {
            systemResponse = Color.RED + point + " is not a valid piece. Please try again.";
        }
        else
        {
            systemResponse = Color.GREEN + "Moving " + piece.getName() + " to " + point + "...";
            
        }
        
        System.out.println(systemResponse + Color.RESET);
        return move == null ? selectMove(piece, board, user) : move;
    }
}
