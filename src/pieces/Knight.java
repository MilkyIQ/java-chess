package pieces;
import game.Board;
import game.Move;
import java.util.ArrayList;

public class Knight extends GamePiece
{
    public Knight(String color)
    {
        super("Knight", "\u265E", color);
    }

    @Override
    public ArrayList<Move> getValidMoves(Board board, int x, int y)
    {
        ArrayList<Move> moves = new ArrayList<Move>();
        int[] shortEdge = {1, -1};
        int[] longEdge = {2, -2};

        for (int shortEdgeInc : shortEdge)
        {
            for (int longEdgeInc : longEdge)
            {
                int[] lShapeX = {x+longEdgeInc, y+shortEdgeInc};
                int[] lShapeY = {x+shortEdgeInc, y+longEdgeInc};
                boolean moveXinBounds = !board.coordinateOutOfBounds(lShapeX[0], lShapeX[1]);
                boolean moveYinBounds = !board.coordinateOutOfBounds(lShapeY[0], lShapeY[1]);
                boolean moveXisFriendly = board.checkSpace(lShapeX[0], lShapeX[1], super.getColor()) != 1;
                boolean moveYisFriendly = board.checkSpace(lShapeY[0], lShapeY[1], super.getColor()) != 1;
                if (moveXinBounds && moveXisFriendly) { moves.add(new Move(board, this, x, y, lShapeX[0], lShapeX[1])); }
                if (moveYinBounds && moveYisFriendly) { moves.add(new Move(board, this, x, y, lShapeY[0], lShapeY[1])); }
            }
        }

        return moves;
    }
}
