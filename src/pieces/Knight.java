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
        int pieceX = super.getCol();
        int pieceY = super.getRow();
        int[] shortEdge = {1, -1};
        int[] longEdge = {2, -2};

        for (int shortInc : shortEdge)
        {
            for (int longInc : longEdge)
            {
                moves.add(new GamePiece("x", pieceX+shortInc, pieceY+longInc));
                moves.add(new GamePiece("x", pieceX+longInc, pieceY+shortInc));
            }
        }
    }
}
