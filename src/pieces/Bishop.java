package pieces;
import game.Board;

public class Bishop extends GamePiece
{
    public Bishop(String color, int col, int row)
    {
        super("B", color, col, row);
    }

    @Override
    public boolean checkMove(int x, int y, Board board)
    {
        int pieceX = super.getCol();
        int pieceY = super.getRow();
        int deltaX = x - pieceX;
        int deltaY = y - pieceY;

        if (Math.abs(deltaX) != Math.abs(deltaY)) { return false; }

        int xDir = Math.abs(deltaX) / deltaX;
        int yDir = Math.abs(deltaY) / deltaY;

        pieceX += xDir;
        pieceY += yDir;
        while (pieceX != x && pieceY != y)
        {
            if (board.checkSpaceInt(pieceX, pieceY, super.getColor()) > 0)
            {
                return false;
            }
            pieceX += xDir;
            pieceY += yDir;
        }

        return true;
    }
}
