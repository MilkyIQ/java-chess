package pieces;
import game.Board;
import java.util.ArrayList;
import tools.ArrayUtils;

public class Rook extends GamePiece
{
    public Rook(String COLOR, int col, int row)
    {
        super("\u265C", COLOR, col, row);
    }

    @Override
    public boolean checkMove(int x, int y, Board board)
    {
        final String COLOR = super.getColor();
        int[] piecePt = {super.getCol(), super.getRow()};
        int[] movePt = {x, y};
        int[] delta = {movePt[0] - piecePt[0], movePt[1] - piecePt[1]};

        boolean invalidMove = !(delta[0] == 0 || delta[1] == 0);
        boolean spaceFriendly = board.checkSpace(x, y, COLOR) == 1;
        if (invalidMove || spaceFriendly) { return false; }

        // Determine which side of coordinate [x,y] to incremement, and in what direction
        int axis = (Math.abs(delta[0]) > Math.abs(delta[1])) ? 0 : 1;
        int dir = Math.abs(delta[axis]) / delta[axis];

        piecePt[axis] += dir;
        while (piecePt[axis] != movePt[axis])
        {
            if (board.checkSpace(piecePt[0], piecePt[1], COLOR) > 0)
            {
                return false;
            }
            piecePt[axis] += dir;
        }
        
        return true;
    }

    // Returns a 2D array of all points on board that the rook can make
    /*
     * Uses 4 for loops to achieve with almost identical code in each to achieve this by looping through each axis in each direction.
     * I REALLY didnt want this one to work, but it's ~12x faster than anything else I've attempted to come up with.
     * So for now, this is the best unoptimizied method.
     */
    public int[][] getAllValidMoves(Board board)
    {
        ArrayList<ArrayList<Integer>> moves = new ArrayList<ArrayList<Integer>>();
        final int COL = super.getCol();
        final int ROW = super.getRow();
        final String COLOR = super.getColor();

        // LEFT
        for (int x = COL-1; x >= 0; x--)
        {
            int spaceStatus = board.checkSpace(x, ROW, COLOR);
            if (spaceStatus == 1) { break; }
            moves.add(ArrayUtils.createPoint(x, ROW));
            if (spaceStatus == 2) { break; }
        }

        // RIGHT
        for (int x = COL+1; x < board.getLength(); x++)
        {
            int spaceStatus = board.checkSpace(x, ROW, COLOR);
            if (spaceStatus == 1) { break; }
            moves.add(ArrayUtils.createPoint(x, ROW));
            if (spaceStatus == 2) { break; }
        }

        // DOWN
        for (int y = ROW-1; y >= 0; y--)
        {
            int spaceStatus = board.checkSpace(COL, y, COLOR);
            if (spaceStatus == 1) { break; }
            moves.add(ArrayUtils.createPoint(COL, y));
            if (spaceStatus == 2) { break; }
        }

        // UP
        for (int y = ROW+1; y < board.getHeight(); y++)
        {
            int spaceStatus = board.checkSpace(COL, y, COLOR);
            if (spaceStatus == 1) { break; }
            moves.add(ArrayUtils.createPoint(COL, y));
            if (spaceStatus == 2) { break; }
        }

        return ArrayUtils.convert2DArrayList(moves);
    }
}
