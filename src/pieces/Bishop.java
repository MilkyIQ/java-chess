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
        int pieceX = super.getCol(); // 5,5 - > 8,8
        int pieceY = super.getRow();

        int deltaX = x - pieceX; 
        int deltaY = y - pieceY;

        if ((Math.abs(deltaX) != Math.abs(deltaY)) || (deltaX == 0 || deltaY == 0) ) { return false; }

        System.out.println("Move is diagonal!");

        int xDir = (deltaX > 0) ? 1 : -1;
        int yDir = (deltaY > 0) ? 1 : -1;

        int col = pieceX + xDir;
        int row = pieceY + yDir;
        while (col != x && row != y)
        {
            if (board.checkSpaceInt(col, row, super.getColor()) > 0)
            {
                System.out.println("occupied space.");
                return false;
            }
            else
            {
                System.out.println("space is clear!");
            }
            col += xDir;
            row += yDir;
        }

        return true;
    }
}
