package game;
import pieces.*;

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

    public int[] getFriendlyPoint()
    {
        int[] point = {fromX, fromY};
        return point;
    }

    public int[] getAttackedPoint()
    {
        int[] point = {toX, toY};
        return point;
    }

    public GamePiece getFriendlyPiece()
    {
        return piece;
    }

    public GamePiece getAttackedSpace()
    {
        return space;
    }
}
