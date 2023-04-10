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

            // TODO: combine this to return GamePiece object
            int[] piecePoint = selectPiece();

            GamePiece piece = player.getPiece(piecePoint[0], piecePoint[1]);
            int[][] validMoves = piece.getAllValidMoves(board);
            System.out.println("You have chosen " + piece.getName() + "(" + piece.getCol() + "," + piece.getRow() + ")");

            // TODO: combine this to return validMoves index
            int[] movePoint = selectMovement();

            if (ArrayUtils.simpleIndexOfPointInArray(movePoint, validMoves) > -1)
            {
                
            }
            
            i = i < numPlayers ? i+1 : 0;
        }

        /* ------------------------------------------------------------------------------------ */


        System.out.println("\n");
    }

    public static int[] selectPiece()
    {
        System.out.print("Choose a piece to move (x,y): ");
        return null;
    }

    public static int[] selectMovement()
    {
        System.out.print("Choose a space to move to (x,y) ");
        return null;
    }

    public static int[] extractCoordinates(String input)
    {
        int[] output = new int[2];
        try
        {
            int xStart = input.indexOf("(")+1;
            int xEnd = input.indexOf(",");
            int yStart = input.indexOf(",")+1;
            int yEnd = input.indexOf(")");
    
            String xString = input.substring(xStart, xEnd);
            String yString = input.substring(yStart, yEnd);
    
            int x = Integer.parseInt(xString);
            int y = Integer.parseInt(yString);
            output = new int[2];
            output[0] = x;
            output[1] = y;
        }
        catch (NumberFormatException|StringIndexOutOfBoundsException e)
        {
            System.out.println("`" + input  + "`" + " is not a valid space, please format your spaces in coordinate-point format (x,y)");
            output = null;
        }
        return output;
    }
}
