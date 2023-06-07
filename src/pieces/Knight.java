package pieces;
import game.Board;
import game.Move;
import player.Player;
import java.util.ArrayList;

public class Knight extends GamePiece
{
    public Knight(Player owner, int col, int row)
    {
        super("Knight", "\u265E", owner, col, row);
    }

    @Override
    public ArrayList<Move> getValidMoves(Board board)
    {
        ArrayList<Move> moves = new ArrayList<Move>();
        final int COL = super.getCol();
        final int ROW = super.getRow();
        int[] shortEdge = {1, -1};
        int[] longEdge = {2, -2};

        for (int shortEdgeInc : shortEdge)
        {
            for (int longEdgeInc : longEdge)
            {
                int[] lShapeX = {COL+longEdgeInc, ROW+shortEdgeInc};
                int[] lShapeY = {COL+shortEdgeInc, ROW+longEdgeInc};
                boolean moveXinBounds = !board.coordinateOutOfBounds(lShapeX[0], lShapeX[1]);
                boolean moveYinBounds = !board.coordinateOutOfBounds(lShapeY[0], lShapeY[1]);
                boolean moveXisFriendly = board.checkSpace(lShapeX[0], lShapeX[1], super.getColor()) != 1;
                boolean moveYisFriendly = board.checkSpace(lShapeY[0], lShapeY[1], super.getColor()) != 1;
                if (moveXinBounds && moveXisFriendly) { moves.add(new Move(board, this, lShapeX[0], lShapeX[1])); }
                if (moveYinBounds && moveYisFriendly) { moves.add(new Move(board, this, lShapeY[0], lShapeY[1])); }
            }
        }

        return moves;
    }
}
