package pieces;
import game.Board;

public class King extends GamePiece
{
    public King(String color, int col, int row)
    {
        super("\u265A", color, col, row);
    }

    @Override
    public boolean checkMove(int x, int y, Board board)
    {
        return true;
    }
}
