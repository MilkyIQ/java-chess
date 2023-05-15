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
        boolean longEdgeX = deltaX == 1 && deltaY == 2;
        boolean longEdgeY = deltaX == 2 && deltaY == 1;
        return (longEdgeX || longEdgeY) ? true : false;
    }

    @Override
    public void updateValidMoves(Board board, ArrayList<GamePiece> moves)
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
                boolean lShapeXInBounds = board.coordinateOutOfBounds(lShapeXPos[0], lShapeXPos[1]);
                boolean lShapeYInBounds = board.coordinateOutOfBounds(lShapeYPos[0], lShapeYPos[1]);
                if (lShapeXInBounds) { moves.add(new GamePiece("x", lShapeXPos[0], lShapeXPos[1])); }
                if (lShapeYInBounds) { moves.add(new GamePiece("x", lShapeYPos[0], lShapeYPos[1])); }
            }
        }
    }
}
