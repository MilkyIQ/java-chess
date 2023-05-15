package pieces;
import game.Board;
import java.util.ArrayList;

public class King extends GamePiece
{
    public King(String color, int col, int row)
    {
        super("King", "\u265A", color, col, row);
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

    @Override
    public void updateValidMoves(Board board, ArrayList<GamePiece> moves)
    {
        for (int dx = -1; dx <= 1; dx++)
        {
            for (int dy = -1; dy <= 1; dy++)
            {
                int x = super.getCol() + dx;
                int y = super.getRow() + dy;
                boolean spaceIsAdjacent = dx != 0 || dy != 0;
                boolean spaceIsFriendly = board.checkSpace(x, y, super.getColor()) == 1;
                if (spaceIsAdjacent && !spaceIsFriendly) { moves.add(new GamePiece("x", x, y)); }
            }
        }
    }

    /*
     * PF3
         PE6
       PG4
         QH4
     */
}
