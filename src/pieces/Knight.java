package pieces;
import game.Board;

public class Knight extends GamePiece
{
    public Knight(String color, int col, int row)
    {
        super("Knight", "\u265E", color, col, row);
    }

    @Override
    public boolean checkMove(int x, int y, Board board)
    {
        int deltaX = Math.abs(x - super.getCol());
        int deltaY = Math.abs(y - super.getRow());
        boolean longEdgeX = deltaX == 1 && deltaY == 2;
        boolean longEdgeY = deltaX == 2 && deltaY == 1;
        return (longEdgeX || longEdgeY) ? true : false;
    }
}
