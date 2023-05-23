package player;
import pieces.GamePiece;
import tools.Color;
import java.util.Scanner;

import game.Board;
import game.Move;

public class HumanPlayer extends Player 
{
    private Scanner scanner;

    public HumanPlayer(String name, String color, Scanner scanner)
    {
        super(name, color);
        this.scanner = scanner;
    }

    @Override
    public Move selectMove(Board board)
    {
        GamePiece selectedPiece = requestPieceFromUser(board);
        int[]     selectedDest  = requestDestFromUser(board, selectedPiece);
        if (selectedDest == null)
        {
            System.out.println(Color.PURPLE + "Undoing selection..." + Color.RESET); 
            return selectMove(board);
        }

        return new Move(board, selectedPiece, selectedDest[0], selectedDest[1]);
    }

    // Ask user for the piece they'd like to move and return that piece
    private GamePiece requestPieceFromUser(Board board)
    {
        System.out.print(this.getColorCode() + "[" + this.getName() + "] " + Color.RESET);
        System.out.print("Choose a piece to move (x,y): ");

        GamePiece piece = null;
        String systemResponse = Color.RED;
        int[] point = extractPointFromString(scanner.nextLine());
        
        // Error checking
        int returnCode = point != null ? board.checkSpace(point[0], point[1], this.getColor()) : -2;
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
        return piece == null ? requestPieceFromUser(board) : piece;
    }

    // Ask user for the move they want to make and return
    private int[] requestDestFromUser(Board board, GamePiece piece)
    {
        System.out.print("Choose a space to move to (x,y): ");

        int[] move = null;
        String systemResponse = Color.RED;
        String userInput = scanner.nextLine().toLowerCase();
        int[] point = extractPointFromString(userInput);

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
        return move == null ? requestDestFromUser(board, piece) : move;
    }

    // Convert string coordinate to int[], includes error handling
    private int[] extractPointFromString(String input)
    {
        int[] output = new int[2];
        try
        {
            int xEnd   = input.indexOf(",");
            int yStart = input.indexOf(",")+1;
    
            String xString = input.substring(0, xEnd);
            String yString = input.substring(yStart);
    
            int x = Integer.parseInt(xString);
            int y = Integer.parseInt(yString);
            output[0] = x;
            output[1] = y;
        }
        catch (NumberFormatException|StringIndexOutOfBoundsException e)
        {
            System.out.println(Color.RED + "`" + input  + "`" + " is not a coordinate (x,y)" + Color.RESET);
            output = null;
        }
        return output;
    }
}
