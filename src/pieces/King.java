package pieces;
import game.Board;
import game.Move;
import java.util.ArrayList;

public class King extends GamePiece
{
    public King(String color)
    {
        super("King", "\u265A", color);
    }

    @Override
    public ArrayList<Move> getValidMoves(Board board, int x, int y)
    {
        ArrayList<Move> moves = new ArrayList<Move>();
        for (int dx = -1; dx <= 1; dx++)
        {
            for (int dy = -1; dy <= 1; dy++)
            {
                int crawlX = x + dx;
                int crawlY = y + dy;
                boolean spaceIsAdjacent  = dx != 0 || dy != 0;
                boolean spaceIsFriendly  = board.checkSpace(crawlX, crawlY, super.getColor()) == 1;
                boolean spaceOutOfBounds = board.coordinateOutOfBounds(crawlX, crawlY);
                if (spaceIsAdjacent && !spaceIsFriendly && !spaceOutOfBounds) { moves.add(new Move(board, this, x, y, crawlX, crawlY)); }
            }
        }

        return moves;
    }
}
