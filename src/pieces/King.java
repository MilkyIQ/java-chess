package pieces;
import game.Board;
import game.Move;
import player.Player;
import java.util.ArrayList;

public class King extends GamePiece
{
    public King(Player owner, int col, int row)
    {
        super("King", "\u265A", owner, col, row);
    }

    @Override
    public ArrayList<Move> getValidMoves(Board board)
    {
        ArrayList<Move> moves = new ArrayList<Move>();
        for (int dx = -1; dx <= 1; dx++)
        {
            for (int dy = -1; dy <= 1; dy++)
            {
                int x = super.getCol() + dx;
                int y = super.getRow() + dy;
                boolean spaceIsAdjacent  = dx != 0 || dy != 0;
                boolean spaceIsFriendly  = board.checkSpace(x, y, super.getColor()) == 1;
                boolean spaceOutOfBounds = board.coordinateOutOfBounds(x, y);
                if (spaceIsAdjacent && !spaceIsFriendly && !spaceOutOfBounds) { moves.add(new Move(board, this, x, y)); }
            }
        }

        return moves;
    }
}
