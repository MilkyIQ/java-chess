package game;
import pieces.GamePiece;
import pieces.King;
import java.util.ArrayList;
import tools.UnoptimizedDeepCopy;

public class Move
{
    private GamePiece piece, space;
    private int fromX, fromY;
    private int toX, toY;

    public Move(Board board, GamePiece piece, int fromX, int fromY, int toX, int toY)
    {
        this.piece = piece;
        this.fromX = fromX;
        this.fromY = fromY;
        this.space = board.getSpace(toX, toY);
        this.toX = toX;
        this.toY = toY;
    }

    public int getOriginX()
    {
        return fromX;
    }

    public int getOriginY()
    {
        return fromY;
    }

    public int getDestX()
    {
        return toX;
    }

    public int getDestY()
    {
        return toY;
    }

    public GamePiece getOriginPiece()
    {
        return piece;
    }

    public GamePiece getDestPiece()
    {
        return space;
    }

    public boolean resultsInCheck(Board board)
    {
        Board copy = (Board) UnoptimizedDeepCopy.copy(board);
        copy.move(this);
        boolean state = copy.findPieces(piece.getColor(), King.class).get(0).isBeingThreatened(copy);
        return state;
    }

    public boolean isValid(Board board)
    {
        ArrayList<Move> moves = piece.getValidMoves(board, fromX, fromY);
        for (Move legalMove : moves)
        {
            if (this.equals(legalMove)) { return true; }
        }
        return false;
    }

    // public String toString()
    // {
    //     String fromString = piece.toFormattedPositionOnBoard(board);
    //     String toString = space != null ? space.toFormattedPositionOnBoard(board) : toX + "," + toY;
    //     return piece.getOwner().getColorCode() + "Move chosen!" + fromString + " to " + toString + Color.RESET;
    // }

    public boolean equals(Move move)
    {
        return
           move.getOriginX() == this.fromX
        && move.getOriginY() == this.fromY
        && move.getDestX()   == this.toX
        && move.getDestY()   == this.toY;
    }
}