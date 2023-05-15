import game.*;
import pieces.*;
import tools.*;
import java.util.Scanner;
import java.util.ArrayList;

public class App
{
    public static Scanner user = new Scanner(System.in);
    public static Board board;

    public static void main(String[] args) throws Exception
    {
        System.out.println("\n");

        /* ------------------------------------------------------------------------------------ */

        // Initialize game variables
        SettingsReader reader     = new SettingsReader("src/game/settings/settings.json");
        ArrayList<Player> players = new ArrayList<Player>();

        // Initialize board variables
        int height           = reader.getIntValueOf("boardHeight", 0);
        int length           = reader.getIntValueOf("boardLength", 0);
        String evensColor    = Color.getColorCodeOf(reader.getStringValueOf("evenSpacesColor", 0));
        String oddsColor     = Color.getColorCodeOf(reader.getStringValueOf("oddSpacesColor", 0));
        String notationColor = Color.getColorCodeOf(reader.getStringValueOf("notationColor", 0));

        // Create all players and their pieces
        for (int i = 1; i <= reader.getNumPlayers(); i++)
        {
            String color     = Color.getColorCodeOf(reader.getStringValueOf("color", i));
            String name      = reader.getStringValueOf("name", i);
            String direction = reader.getStringValueOf("pawnDirection", i);
            int[][] pawns    = reader.getPlayerPieces("pawn", i);
            int[][] rooks    = reader.getPlayerPieces("rook", i);
            int[][] knights  = reader.getPlayerPieces("knight", i);
            int[][] bishops  = reader.getPlayerPieces("bishop", i);
            int[][] queens   = reader.getPlayerPieces("queen", i);
            int[][] kings    = reader.getPlayerPieces("king", i);

            players.add(new Player(name, color));
            Player curPlayer = players.get(i-1);
            for (int[] p : pawns   ) { curPlayer.give(new   Pawn(color, p[0], p[1], direction)); }
            for (int[] p : rooks   ) { curPlayer.give(new   Rook(color, p[0], p[1])); }
            for (int[] p : knights ) { curPlayer.give(new Knight(color, p[0], p[1])); }
            for (int[] p : bishops ) { curPlayer.give(new Bishop(color, p[0], p[1])); }
            for (int[] p : queens  ) { curPlayer.give(new  Queen(color, p[0], p[1])); }
            for (int[] p : kings   ) { curPlayer.give(new   King(color, p[0], p[1])); }
        }

        // Create and populate board
        board = new Board(length, height);
        board.populateBoard(players);
        board.setColors(evensColor, oddsColor, notationColor);

        // Primary game loop
        int i = 0;
        int k = 0;
        while (players.size() > 1)
        {
            Player player = players.get(i);
            System.out.println("--------------- Turn #" + (k+1) + "---------------\n");
            board.printBoard();

            // Get move from player
            GamePiece piece = selectPiece(player);
            int[] move      = selectMove(piece);
            int[] from      = {piece.getCol(), piece.getRow()};
            if (move == null) { System.out.println(Color.PURPLE + "Undoing selection..." + Color.RESET); continue; } // undo case
            
            // Update board and player states
            GamePiece space = board.getSpace(move[0], move[1]);
            board.move(piece, move[0], move[1]);
            for (Player p : players) { p.updateState(board); }

            // Verify current move is not check
            String playerState = player.getState();
            if (playerState.equals("check") || playerState.equals("checkmate"))
            {
                System.out.println(Color.RED+ "Illegal Move: Cannot leave King in check!" + Color.RESET);
                board.undoMove(piece, from[0], from[1], space);
                continue;
            }

            // Update player list & player hands
            if (space != null) { players.get(Player.indexOf(space.getColor(), players)).remove(space); } // remove piece from enemy hand if attacking
            Player.removeLosers(players);

            i = i < players.size()-1 ? i+1 : 0;
            k++;
        }
        user.close();

        String result = (players.size() == 1) ? (players.get(0).getName() + " Wins!") : ("Draw.");
        System.out.println(result);
    }




    /* ------------------------------------------------------------------------------------ */




    // Ask user for the piece they'd like to move and return that piece
    public static GamePiece selectPiece(Player player)
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
                piece = board.getSpace(point[0], point[1]);
                systemResponse = Color.PURPLE + "You have chosen " + piece.toFormattedPositon();
                break;
        }

        System.out.println(systemResponse + Color.RESET);
        return piece == null ? selectPiece(player) : piece;
    }

    



    /* ------------------------------------------------------------------------------------ */




    // Ask user for the move they want to make and return
    public static int[] selectMove(GamePiece piece)
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
        return move == null ? selectMove(piece) : move;
    }
}