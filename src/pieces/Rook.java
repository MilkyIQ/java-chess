package pieces;
import game.Board;

public class Rook extends GamePiece
{
    public Rook(String color, int col, int row)
    {
        super("\u265C", color, col, row);
    }

    @Override
    public boolean checkMove(int x, int y, Board board)
    {
        int[] piecePt = {super.getCol(), super.getRow()};
        int[] movePt = {x, y};
        int[] delta = {movePt[0] - piecePt[0], movePt[1] - piecePt[1]};

        if ( !(delta[0] == 0 || delta[1] == 0) ) { return false; }

        int axis = (Math.abs(delta[0]) > Math.abs(delta[1])) ? 0 : 1;
        int dir = Math.abs(delta[axis]) / delta[axis];

        piecePt[axis] += dir;
        while (piecePt[axis] != movePt[axis])
        {
            if (board.checkSpace(piecePt[0], piecePt[1], super.getColor()) > 0)
            {
                return false;
            }
            piecePt[axis] += dir;
        }

        return true;
    }
}
