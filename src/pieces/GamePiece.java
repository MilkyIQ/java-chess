package pieces;
import game.Board;
import game.Move;
import player.Player;
import tools.Color;
import java.util.ArrayList;

public class GamePiece implements java.io.Serializable
{
    private final char SYMBOL;
    private String color;
    private int moveCount;
    
    public GamePiece(char symbol, String color)
    {
        this.SYMBOL = symbol;
        this.color = color;
        this.moveCount = 0;
    }

    // For use with ghost board point placement only
    public GamePiece()
    {
        this.SYMBOL = 'x';
        this.color = null;
    }

    public String getColor()
    {
        return color;
    }
    
    public Player getOwner(ArrayList<Player> playerList)
    {
        for (Player player : playerList) {
            if (player.getColor().equals(color)) {
                return player;
            }
        }
        throw new IllegalStateException("Piece has no owner");
    }

    public String getColorCode()
    {
        return Color.getColorCodeOf(color);
    }

    public int getMoveCount()
    {
        return moveCount;
    }

    public void incMoveCount()
    {
        moveCount++;
    }

    public void decMoveCount()
    {
        moveCount--;
    }

    public ArrayList<Move> getValidMoves(Board board, int x, int y)
    {
        return null;
    }
    
    public boolean hasLegalMoves(Board board, int x, int y)
    {
        return getValidMoves(board, x, y).size() > 0;
    }

    // Calculates all possible movements from all pieces on board (excluding pawns), and continue 
    public boolean isBeingThreatened(Board board)
    {
        // Initialize main variables
        final int LENGTH = board.getLength();
        final int HEIGHT = board.getLength();
        final int[] PIECE_POSITION = board.findPosition(this);
        final int PIECE_X = PIECE_POSITION[0];
        final int PIECE_Y = PIECE_POSITION[1];

        // Check corners for pawns
        int[][] deltas = { {1,1}, {1,-1}, {-1,1}, {-1,-1} };
        for (int[] increase : deltas)
        {
            int x = PIECE_X + increase[0];
            int y = PIECE_Y + increase[1];
            
            // empty or not a pawn
            GamePiece potentialPawn = board.getSpace(x, y);
            if (potentialPawn == null || potentialPawn.getClass() != Pawn.class)
            {
                continue;
            }

            // verify direction
            Move potentialMove = new Move(board, potentialPawn, x, y, PIECE_X, PIECE_Y);
            for (Move move : potentialPawn.getValidMoves(board, x, y))
            {
                if (move.equals(potentialMove)) { return true; }
            }
        }

        // Iterate through board and populate ghostBoard with validMoves
        Board ghostBoard = new Board(LENGTH, HEIGHT);
        for (int col = 0; col < LENGTH; col++)
        {
            for (int row = 0; row < HEIGHT; row++)
            {
                GamePiece space = board.getSpace(col, row);
                if (space == null || space.getColor().equals(color) || space.getClass() == Pawn.class) { continue; }
                
                // abstracting this to get under 4 indents is a waste of time, but feel free to improve
                for (Move move : space.getValidMoves(board, col, row))
                {
                    ghostBoard.place(new GamePiece(), move.getDestX(), move.getDestY());
                }
            }
        }
        
        // Place king on board and return status
        return ghostBoard.getSpace(PIECE_X, PIECE_Y) != null;
    }

    // Print corresponding symbol of gamepiece
    public String toString()
    {
        return color + "" + SYMBOL;
    }
}
