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
        int deltaX = Math.abs(x - super.getCol());
        int deltaY = Math.abs(y - super.getRow());

        boolean invalidMove = deltaX > 1 || deltaY > 1;
        boolean spaceFriendly = board.checkSpace(x, y, super.getColor()) == 1;
        if (invalidMove || spaceFriendly) { return false; }

        return true; 
    }
}
