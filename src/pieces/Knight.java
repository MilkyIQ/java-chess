package pieces;
import game.Board;
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
    public void updateValidMoves(Board board, ArrayList<Board.Move> moves)
    {
        final int COL = super.getCol();
        final int ROW = super.getRow();
        int[] shortEdge = {1, -1};
        int[] longEdge = {2, -2};

        for (int shortEdgeInc : shortEdge)
        {
            for (int longEdgeInc : longEdge)
            {
                int[] lShapeXPos = {COL+longEdgeInc, ROW+shortEdgeInc};
                int[] lShapeYPos = {COL+shortEdgeInc, ROW+longEdgeInc};
                boolean lShapeXInBounds = !board.coordinateOutOfBounds(lShapeXPos[0], lShapeXPos[1]);
                boolean lShapeYInBounds = !board.coordinateOutOfBounds(lShapeYPos[0], lShapeYPos[1]);
                if (lShapeXInBounds) { moves.add(board.new Move(this, lShapeXPos[0], lShapeXPos[1])); }
                if (lShapeYInBounds) { moves.add(board.new Move(this, lShapeYPos[0], lShapeYPos[1])); }
            }
        }
    }
}
