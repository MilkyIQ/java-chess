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
        for (int i = 1; i < numPlayers+1; i++)
        {
            String color = Color.getColorCodeOf(reader.getStringValueOf("color", i));
            String name = reader.getStringValueOf("name", i);
            int[][] pawns = reader.getPlayerPieces("pawn", i);

            players[i-1] = new Player(name, color);
            for (int[] p : pawns) { players[i-1].givePiece(new Pawn(color, p[0], p[1])); }
        }

        // Initialize board variables
        int height = reader.getIntValueOf("boardHeight", 0);
        int length = reader.getIntValueOf("boardLength", 0);
        String evensColor    = Color.getColorCodeOf(reader.getStringValueOf("evenSpacesColor", 0));
        String oddsColor     = Color.getColorCodeOf(reader.getStringValueOf("oddSpacesColor", 0));
        String notationColor = Color.getColorCodeOf(reader.getStringValueOf("notationColor", 0));
        
        // Create and populate board
        Board board = new Board(height, length, players);
        board.setColors(evensColor, oddsColor, notationColor);

        // Primary game loop
        int i = 0;
        while (i < numPlayers*5)
        {
            Player player = players[i];
            board.printBoard();

            // passing through a Scanner object is fucking stupid but my hands are tied
            GamePiece piece = selectPiece(player, board, user);   
            int[] move = selectMove(piece, piece.getAllValidMoves(board), board, user);

            board.move(piece, move[0], move[1]);
            
            i = i < numPlayers ? i+1 : 0;
        }

        user.close();

        /* ------------------------------------------------------------------------------------ */

        System.out.println("\n");
    }

    // Ask user for the piece they'd like to move and return that piece
    public static GamePiece selectPiece(Player player, Board board, Scanner user)
    {
        GamePiece piece = null;
        
        System.out.print("Choose a piece to move (x,y): ");
        int[] point = ArrayUtils.extractPointFromString(user.nextLine());
        if (point == null) { return selectPiece(player, board, user); } // edge case for gibberish input

        int col = point[0];
        int row = point[1];
        String systemResponse;

        switch (board.checkSpaceInt(col, row, player.getColor()))
        {
            case -1: systemResponse = Color.RED + "Out of bounds! Please try again."; break;
            case 0:  systemResponse = Color.RED + "Space is empty. Please try again."; break;
            case 2:  systemResponse = Color.RED + "Cannot move enemy piece. Please try again."; break;
            default:
                piece = player.getPiece(col, row);
                systemResponse = Color.GREEN + "You have chosen " + piece.getName() + "(" + piece.getCol() + "," + piece.getRow() + ")";
                break;
        }

        System.out.println(systemResponse + Color.RESET);
        return piece == null ? selectPiece(player, board, user) : piece;
    }

    // Ask user for the move they want to make and return
    public static int[] selectMove(GamePiece piece, int[][] validMoves, Board board, Scanner user)
    {
        int[] move = null;
        System.out.print("Choose a space to move to (x,y) ");
        int[] point = ArrayUtils.extractPointFromString(user.nextLine());
        
        // edge case for bad input and invalidMovements, i don't like that i'm writing the return statement twice though
        if (point == null || ArrayUtils.simpleIndexOfPointInArray(point, validMoves) < 0)
        {
            return selectMove(piece, validMoves, board, user);
        }
        
        // Error checking
        switch (board.checkSpaceInt(point[0], point[1], piece.getColor()))
        {
            case -1: System.out.print(Color.RED + "Out of bounds! Please try again."); break;
            case 1:  System.out.print(Color.RED + "Cannot attack your own piece. Please try again."); break;
            default:
                move = point;
                System.out.print(Color.GREEN + "Moving " + piece.getName() + "(" + piece.getCol() + "," + piece.getRow() + ")" + " to " + point);
                break;
        }
        
        System.out.print(Color.RESET);
        return move == null ? selectMove(piece, validMoves, board, user) : move;
    }
}