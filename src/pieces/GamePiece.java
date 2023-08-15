package pieces;
import game.Board;
import game.Move;
import player.Player;
import tools.Color;
import java.util.ArrayList;

public class GamePiece implements java.io.Serializable
{
    private final String TITLE;
    private final String SYMBOL;
    private String color;
    private int moveCount;
    
    public GamePiece(String title, String symbol, String color)
    {
        this.TITLE = title;
        this.SYMBOL = symbol;
        this.color = color;
        this.moveCount = 0;
    }

    // For use with ghost board point placement only
    public GamePiece()
    {
        this.SYMBOL = "x";
        this.TITLE = null;
        this.color = null;
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

    /*
     * TODO:
     * it might be worth it to learn how to create iterables so we can create
     * streams and crap so we dont have to make these annoying for loops all
     * the time to traverse the board.
     */
    public int[] searchPos(Board board) {
        int[] position = new int[2];
        for (int col = 0; col < board.getLength(); col++) {
            for (int row = 0; row < board.getHeight(); row++) {
                if (board.getSpace(col, row) == this) {
                    position[0] = col;
                    position[1] = row;
                    return position;
                }
            }
        }
        //return null;
        board.printBoard();
        throw new IllegalStateException(this + "Piece does not exist on board"); // keep for now, may be an issue later with board history
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
        final int[] PIECE_POSITION = this.searchPos(board);
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
            if (potentialPawn == null || !potentialPawn.getTitle().equals("Pawn"))
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
                if (space == null || space.getColor().equals(color) || space.getTitle().equals("Pawn")) { continue; }
                
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

    public String toFormattedPosition(Board board)
    {
        int[] position = searchPos(board);
        return SYMBOL + "(" + (position[0]) + "," + (position[1]) + ")";
    }

    // Print corresponding symbol of gamepiece
    public String toString()
    {
        return SYMBOL.substring(0,1);
    }
}
