package pieces;
import game.Board;

public class Knight extends GamePiece
{
    public Knight(String color, int col, int row)
    {
        super("\u265D", color, col, row);
    }

    @Override
    public boolean checkMove(int x, int y, Board board)
    {
        return true;
    }
}
