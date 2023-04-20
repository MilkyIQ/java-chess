import game.*;
import pieces.*;
import tools.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception
    {
        System.out.println("\n");

        /* ------------------------------------------------------------------------------------ */

        // Initialize game variables
        SettingsReader reader = new SettingsReader("src/settings.json");
        Scanner user          = new Scanner(System.in);
        Player[] players      = new Player[reader.getNumPlayers()];

        // Initialize board variables
        int height           = reader.getIntValueOf("boardHeight", 0);
        int length           = reader.getIntValueOf("boardLength", 0);
        String evensColor    = Color.getColorCodeOf(reader.getStringValueOf("evenSpacesColor", 0));
        String oddsColor     = Color.getColorCodeOf(reader.getStringValueOf("oddSpacesColor", 0));
        String notationColor = Color.getColorCodeOf(reader.getStringValueOf("notationColor", 0));

        // Create all players and their pieces
        for (int i = 1; i < players.length+1; i++)
        {
            String color     = Color.getColorCodeOf(reader.getStringValueOf("color", i));
            String name      = reader.getStringValueOf("name", i);
            String direction = reader.getStringValueOf("pawnDirection", i);
            int[][] pawns    = reader.getPlayerPieces("pawn", i);

            players[i-1] = new Player(name, color);
            for (int[] p : pawns) { players[i-1].givePiece(new Pawn(color, p[0], p[1], direction)); }
        }

        
        // Create and populate board
        Board board = new Board(length, height);
        board.populateBoard(players);
        board.setColors(evensColor, oddsColor, notationColor);

        // Primary game loop
        int i = 0;
        int k = 0;
        while (k < 10)
        {
            Player player = players[i];
            board.printBoard();

            // passing through a Scanner object is fucking stupid but my hands are tied
            GamePiece piece = selectPiece(player, board, user);   
            int[] move = selectMove(piece, piece.getAllValidMoves(board), board, user);

            // TODO: need to figure out a way to update the enemy player's hand when their piece has been claimed
            board.move(piece, move[0], move[1]);
            
            i = i < players.length-1 ? i+1 : 0;
            k++;
        }

        user.close();

        /* ------------------------------------------------------------------------------------ */

        // Board board = new Board(8,8);
        // GamePiece rat = new Rook(Color.GREEN_BOLD, 4, 4);
        // GamePiece enemy = new Pawn(Color.RED_BOLD, 3, 3, "up");
        // board.place(rat);
        // board.place(enemy);
        // board.setColors(Color.BLACK_BACKGROUND, Color.WHITE_BACKGROUND, Color.BLACK);
        
        // board.printBoard();
        // int[] move = {4,5};
        // boolean test = rat.checkMove(move[0], move[1], board);
        // System.out.println(test);

    }




    /* ------------------------------------------------------------------------------------ */




    // Ask user for the piece they'd like to move and return that piece
    public static GamePiece selectPiece(Player player, Board board, Scanner user)
    {
        GamePiece piece = null;
        
        System.out.print(player.getColor() + "[" + player.getName() + "] " + Color.RESET);
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
                systemResponse = Color.PURPLE + "You have chosen " + piece.toFormattedPositon();
                break;
        }

        System.out.println(systemResponse + Color.RESET);
        return piece == null ? selectPiece(player, board, user) : piece;
    }

    



    /* ------------------------------------------------------------------------------------ */




    // Ask user for the move they want to make and return
    public static int[] selectMove(GamePiece piece, int[][] validMoves, Board board, Scanner user)
    {
        int[] move = null;
        System.out.print("Choose a space to move to (x,y): ");
        int[] point = ArrayUtils.extractPointFromString(user.nextLine());
        
        // edge case for bad input and invalid movements
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
                System.out.print(Color.PURPLE + "Moving " + piece.toFormattedPositon() + " to " + point);
                System.out.println("\n");
                break;
        }
        
        System.out.print(Color.RESET);
        return move == null ? selectMove(piece, validMoves, board, user) : move;
    }
}

/*
 * TODO:
 * [ ] "Undo" case for user input
 * [ ] "Draw" case for user input
 * [ ] Insertion sort
 * [ ] Binary search
 * [ ] What happens if pawn is placed outside of possible area?
 */