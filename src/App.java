import game.*;
import pieces.*;
import tools.*;
import java.util.Scanner;

public class App
{
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
            int[][] rooks    = reader.getPlayerPieces("rook", i);
            int[][] bishops  = reader.getPlayerPieces("bishop", i);
            int[][] queens   = reader.getPlayerPieces("queen", i);

            players[i-1] = new Player(name, color);
            for (int[] p : pawns  ) { players[i-1].give(new   Pawn(color, p[0], p[1], direction)); }
            for (int[] p : rooks  ) { players[i-1].give(new   Rook(color, p[0], p[1])); }
            for (int[] p : bishops) { players[i-1].give(new Bishop(color, p[0], p[1])); }
            for (int[] p : queens ) { players[i-1].give(new  Queen(color, p[0], p[1])); }
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
            int[] move = selectMove(piece, board, user);
            if (move == null) { System.out.println(Color.PURPLE + "Undoing selection..." + Color.RESET); continue; }

            GamePiece space = board.getSpace(move[0], move[1]);
            if (space != null) { players[Player.indexOf(space.getColor(), players)].remove(space); } // remove piece from enemy hand if attacking

            board.move(piece, move[0], move[1]);
            
            i = i < players.length-1 ? i+1 : 0;
            k++;
        }

        user.close();
    }




    /* ------------------------------------------------------------------------------------ */




    // Ask user for the piece they'd like to move and return that piece
    public static GamePiece selectPiece(Player player, Board board, Scanner user)
    {
        System.out.print(player.getColor() + "[" + player.getName() + "] " + Color.RESET);
        System.out.print("Choose a piece to move (x,y): ");

        GamePiece piece = null;
        String systemResponse = Color.RED;
        int[] point = ArrayUtils.extractPointFromString(user.nextLine());
        
        // Error checking
        int returnCode = point != null ? board.checkSpace(point[0], point[1], player.getColor()) : -2;
        switch (returnCode)
        {
            default: systemResponse += "Something went wrong."; break;
            case -2: systemResponse += "Invalid input. Please try again."; break;
            case -1: systemResponse += "Out of bounds! Please try again."; break;
            case 0:  systemResponse += "Space is empty. Please try again."; break;
            case 2:  systemResponse += "Cannot move enemy piece. Please try again."; break;
            case 1:
                piece = player.getPiece(point[0], point[1]);
                systemResponse = Color.PURPLE + "You have chosen " + piece.toFormattedPositon();
                break;
        }

        System.out.println(systemResponse + Color.RESET);
        return piece == null ? selectPiece(player, board, user) : piece;
    }

    



    /* ------------------------------------------------------------------------------------ */




    // Ask user for the move they want to make and return
    public static int[] selectMove(GamePiece piece, Board board, Scanner user)
    {
        System.out.print("Choose a space to move to (x,y): ");

        int[] move = null;
        String systemResponse = Color.RED;
        String userInput = user.nextLine().toLowerCase();
        int[] point = ArrayUtils.extractPointFromString(userInput);

        if (userInput.equals("back") || userInput.equals("undo")) { return null; }

        // Error checking
        if (point == null)
        {
            systemResponse += "Invalid input. Please try again.";
        }
        else if (piece.checkMove(point[0], point[1], board))
        {
            move = point;
            systemResponse = Color.PURPLE;
            systemResponse += "Moving " + piece.toFormattedPositon() + " to " + "(" + point[0] + "," + point[1] + ")\n";
        }
        else
        {
            switch (board.checkSpace(point[0], point[1], piece.getColor()))
            {
                case -1: systemResponse += "Out of bounds! Please try again."; break;
                case 0:  systemResponse += "Invalid move. Please try again."; break;
                case 1:  systemResponse += "Cannot attack your own piece. Please try again."; break;
                case 2:  systemResponse += "Invalid attack. Please try again."; break;
            }
        }
        
        System.out.println(systemResponse + Color.RESET);
        return move == null ? selectMove(piece, board, user) : move;
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