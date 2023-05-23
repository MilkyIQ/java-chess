package pieces;
import game.Board;
import game.Move;
import java.util.ArrayList;

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
        boolean isLShapeX = deltaX == 1 && deltaY == 2;
        boolean isLShapeY = deltaX == 2 && deltaY == 1;
        return isLShapeX || isLShapeY;
    }

    @Override
    public void updateValidMoves(Board board, ArrayList<Move> moves)
    {
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
    }
}
