package pieces;
import game.Board;

public class Bishop extends GamePiece
{
    public Bishop(String color, int col, int row)
    {
        super("\u265D", color, col, row);
    }

    @Override
    public boolean checkMove(int x, int y, Board board)
    {
        String color = super.getColor();
        int pieceX = super.getCol();
        int pieceY = super.getRow();
        int deltaX = x - pieceX;
        int deltaY = y - pieceY;

        boolean invalidMove = Math.abs(deltaX) != Math.abs(deltaY);
        boolean spaceFriendly = board.checkSpace(x, y, color) == 1;
        if (invalidMove || spaceFriendly) { return false; }

        int xDir = Math.abs(deltaX) / deltaX;
        int yDir = Math.abs(deltaY) / deltaY;

        pieceX += xDir;
        pieceY += yDir;
        while (pieceX != x && pieceY != y)
        {
            if (board.checkSpace(pieceX, pieceY, super.getColor()) > 0)
            {
                return false;
            }
            pieceX += xDir;
            pieceY += yDir;
        }

        return true;
    }
}
