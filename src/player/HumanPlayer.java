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
        /*
         * TODO: HumanPlayer selectMove() overhaul
         * These functions need to entirely overhauled (again).
         * Maybe we use a while loop instead of recursion?
         * Or maybe we put some of the error checking in the constructors
         * for GamePiece and Move.
         */
        GamePiece selectedPiece = requestPieceFromUser(board);
        Move selectedDest = requestMoveFromUser(board, selectedPiece);
        return selectedDest == null ? selectMove(board) : selectedDest;
    }

    // Ask user for the piece they'd like to move and return that piece
    private GamePiece requestPieceFromUser(Board board)
    {
        System.out.print(this.getColorCode() + "[" + this.getName() + "] " + Color.RESET);
        System.out.print("Choose a piece to move (x,y): ");
        
        String userInput = scanner.nextLine().toLowerCase();
        
        // GARBAGE INPUT
        int[] point = extractPointFromString(userInput);
        if (point == null)
        {
            System.out.println(Color.RED + "Invalid input." + Color.RESET);
            return requestPieceFromUser(board);
        }

        // ILLEGAL PIECE
        GamePiece piece = board.getSpace(point[0], point[1]);
        if (piece == null || piece.getColor() != super.getColor() || !piece.hasLegalMoves(board, point[0], point[1])) // a switch statement here is probably redundant
        {
            System.out.print(Color.RED);
            switch (board.checkSpace(point[0], point[1], this.getColor()))
            {
                case -1: System.out.print("Out of bounds."); break;
                case 0:  System.out.print("Space is empty."); break;
                case 1:  System.out.print("Piece has no legal moves."); break;
                case 2:  System.out.print("Cannot move enemy piece."); break;
            }
            System.out.print(Color.RESET + "\n");
            return requestPieceFromUser(board);
        }

        // LEGAL PIECE
        System.out.println(Color.PURPLE + "You have chosen " + piece.toFormattedPosition(board));
        return piece;
    }

    // Ask user for the move they want to make and return
    private Move requestMoveFromUser(Board board, GamePiece piece)
    {
        System.out.print(this.getColorCode() + "[" + this.getName() + "] " + Color.RESET);
        System.out.print("Choose a space to move to (x,y): ");

        // PROPOSE UNDO
        String userInput = scanner.nextLine().toLowerCase();
        if (userInput.equals("back") || userInput.equals("undo"))
        {
            System.out.println(Color.PURPLE + "Undoing selection..." + Color.RESET);
            return null;
        }
        
        // GARBAGE DATA
        int[] point = extractPointFromString(userInput);
        if (point == null)
        {
            System.out.println(Color.RED + "Invalid input. Please try again." + Color.RESET);
            return requestMoveFromUser(board, piece);
        }

        // OUT OF BOUNDS
        if (board.checkSpace(point[0], point[1], piece.getColor()) == -1)
        {
            System.out.println(Color.RED + "Out of bounds! Please try again." + Color.RESET);
            return requestMoveFromUser(board, piece);
        }

        // MOVE NOT VALID
        int[] piecePosition = piece.searchPos(board);
        Move move = new Move(board, piece, piecePosition[0], piecePosition[1], point[0], point[1]); // This is really bad and needs to be fixed
        if (!move.isValid(board))
        {
            System.out.println(Color.RED + "Invalid move. Please try again." + Color.RESET);
            return requestMoveFromUser(board, piece);
        }

        // MOVE IS VALID
        System.out.println(move);
        return move;
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
            output = null;
        }
        return output;
    }
}
