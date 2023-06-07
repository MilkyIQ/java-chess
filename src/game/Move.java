package game;
import pieces.GamePiece;
import java.util.ArrayList;
import tools.Color;

public class Move
{
    private GamePiece piece, space;
    private int fromX, fromY;
    private int toX, toY;

    public Move(Board board, GamePiece piece, int toX, int toY)
    {
        this.piece = piece;
        this.fromX = piece.getCol();
        this.fromY = piece.getRow();
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
        board.move(this);
        boolean state = piece.getOwner().getKing().isBeingThreatened(board);
        board.undoMove(this);
        return state;
    }

    public boolean isValid(Board board)
    {
        ArrayList<Move> moves = piece.getOwner().getAllLegalMoves(board);
        for (Move legalMove : moves)
        {
            if (this.equals(legalMove)) { return true; }
        }
        return false;
    }

    public String toString()
    {
        String fromString = piece.toFormattedPositon();
        String toString = space != null ? space.toFormattedPositon() : toX + "," + toY;
        return piece.getOwner().getColorCode() + "Move chosen!" + fromString + " to " + toString + Color.RESET;
    }

    public boolean equals(Move move)
    {
        return
           move.getOriginX() == this.fromX
        && move.getOriginY() == this.fromY
        && move.getDestX()   == this.toX
        && move.getDestY()   == this.toY;
    }
}