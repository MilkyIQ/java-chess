package pieces;
import game.Board;
import game.Move;
import player.Player;
import tools.Color;
import java.util.ArrayList;

public class GamePiece
{
    private final String TITLE;
    private final String SYMBOL;
    private Player owner;
    private int col;
    private int row;
    private int moveCount;
    
    public GamePiece(String title, String symbol, Player owner, int col, int row)
    {
        this.TITLE = title;
        this.SYMBOL = symbol;
        this.owner = owner;
        this.col = col;
        this.row = row;
        this.moveCount = 0;
    }

    // For use with ghost board point placement only
    public GamePiece(int col, int row)
    {
        this.col = col;
        this.row = row;
        this.SYMBOL = "x";
        this.TITLE = null;
        this.owner = null;
    }

    public String getTitle()
    {
        return TITLE;
    }

    public String getSymbol()
    {
        return SYMBOL;
    }

    public String getColor()
    {
        return owner.getColor();
    }

    public Player getOwner()
    {
        return owner;
    }

    public String getColorCode()
    {
        return Color.getColorCodeOf(owner.getColor());
    }

    public int getRow()
    {
        return row;
    }

    public int getCol()
    {
        return col;
    }

    public int getMoveCount()
    {
        return moveCount;
    }

    public void setPos(int x, int y)
    {
        col = x;
        row = y;
    }

    public void incMoveCount()
    {
        moveCount++;
    }

    public void decMoveCount()
    {
        moveCount--;
    }

    public ArrayList<Move> getValidMoves(Board board)
    {
        return null;
    }
    
    public boolean hasLegalMoves(Board board)
    {
        for (Move move : owner.getAllLegalMoves(board))
        {
            if (move.getOriginPiece() == this)
            {
                return true;
            }
        }
        return false;
    }

    // Calculates all possible movements from all pieces on board (excluding pawns), and continue 
    public boolean isBeingThreatened(Board board)
    {
        // Initialize main variables
        final int LENGTH = board.getLength();
        final int HEIGHT = board.getLength();
        final int PIECE_X = this.getCol();
        final int PIECE_Y = this.getRow();

        // Check corners for pawns
        int[][] deltas = { {1,1}, {1,-1}, {-1,1}, {-1,-1} };
        for (int[] increase : deltas)
        {
            int x = PIECE_X + increase[0];
            int y = PIECE_Y + increase[1];
            
            // empty or not a pawn
            GamePiece potentialPawn = board.getSpace(x, y);
            if (potentialPawn == null || !potentialPawn.getTitle().equals("Pawn"))
            {
                continue;
            }

            // verify direction
            Move potentialMove = new Move(board, potentialPawn, PIECE_X, PIECE_Y);
            for (Move move : potentialPawn.getValidMoves(board))
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
                if (space == null || space.getColor().equals(owner.getColor()) || space.getTitle().equals("Pawn")) { continue; }
                
                // abstracting this to get under 4 indents is a waste of time, but feel free to improve
                for (Move move : space.getValidMoves(board))
                {
                    ghostBoard.place(new GamePiece(move.getDestX(), move.getDestY()));
                }
            }
        }
        
        // Place king on board and return status
        return ghostBoard.getSpace(PIECE_X, PIECE_Y) != null;
    }

    public String toFormattedPositon()
    {
        return SYMBOL + "(" + (col) + "," + (row) + ")";
    }

    // Print corresponding symbol of gamepiece
    public String toString()
    {
        return SYMBOL.substring(0,1);
    }
}
