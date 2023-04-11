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

            int[] piecePoint = requestPointFromUser("Choose a piece to move (x,y): ");
            GamePiece piece = player.getPiece(piecePoint[0], piecePoint[1]);
            int[][] validMoves = piece.getAllValidMoves(board);
            System.out.println("You have chosen " + piece.getName() + "(" + piece.getCol() + "," + piece.getRow() + ")");

            int[] movePoint = requestPointFromUser("Choose a space to move to (x,y) ");
            if (ArrayUtils.simpleIndexOfPointInArray(movePoint, validMoves) > -1)
            {
                
            }
            
            i = i < numPlayers ? i+1 : 0;
        }

        /* ------------------------------------------------------------------------------------ */


        System.out.println("\n");
    }

    // Repeatedly ask for a coordinate point from user until they input a valid coordinate 
    public static int[] requestPointFromUser(String prompt)
    {
        Scanner user = new Scanner(System.in);
        int[] point = null;

        while (point == null)
        {
            System.out.print(prompt);
            String input = user.nextLine();
            
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
                point = new int[2];
                point[0] = x;
                point[1] = y;
            }
            catch (NumberFormatException|StringIndexOutOfBoundsException e) // TODO: this needs to handle spaces that are empty or are occupied by an enemy piece
            {
                System.out.println(Color.RED + "`" + input  + "`" + " is not a valid space, please format your spaces in coordinate-point format (x,y)" + Color.RESET);
                point = null;
            }
        }

        return point;
    }

    public static GamePiece selectPiece(Player player, Board board)
    {
        Scanner user = new Scanner(System.in);
        GamePiece piece = null;

        while (piece == null)
        {
            System.out.print("Choose a piece to move (x,y): ");
            int[] point = ArrayUtils.extractPointFromString(user.nextLine());

            String systemResponse;
            boolean spaceIsEmpty;
            boolean spaceIsFriendly;

            try
            {
                spaceIsEmpty = board.checkSpace(point[0], point[1]);
                spaceIsFriendly = board.checkSpace(point[0], point[1], player.getColor());
            }
            catch (Exception ArrayIndexOutOfBoundsException)
            {
                System.out.println(point + "is not a point on this board! Please choose a valid piece.");
                piece = null;
                continue;
            }
            
            if (spaceIsFriendly)
            {
                piece = player.getPiece(point[0], point[1]);
            }
            else if (spaceIsEmpty)
            {
                systemResponse = "Is not a valid piece Please choose a valid piece.";
                piece = null;
            }
        }

        return null;
    }
}
