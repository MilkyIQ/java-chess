package game;
import pieces.GamePiece;

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

    public GamePiece getPiece()
    {
        return piece;
    }

    public GamePiece getDestPiece()
    {
        return space;
    }
}