package pieces;
import game.Board;
import game.Move;
import player.Player;
import java.util.ArrayList;

public class King extends GamePiece
{
    public King(String color)
    {
        super("King", "\u265A", color);
    }

    @Override
    public ArrayList<Move> getValidMoves(Board board)
    {
        int[] position = super.searchPos(board);
        ArrayList<Move> moves = new ArrayList<Move>();
        for (int dx = -1; dx <= 1; dx++)
        {
            for (int dy = -1; dy <= 1; dy++)
            {
                int x = position[0] + dx;
                int y = position[1] + dy;
                boolean spaceIsAdjacent  = dx != 0 || dy != 0;
                boolean spaceIsFriendly  = board.checkSpace(x, y, super.getColor()) == 1;
                boolean spaceOutOfBounds = board.coordinateOutOfBounds(x, y);
                if (spaceIsAdjacent && !spaceIsFriendly && !spaceOutOfBounds) { moves.add(new Move(board, this, x, y)); }
            }
        }

        return moves;
    }
}
