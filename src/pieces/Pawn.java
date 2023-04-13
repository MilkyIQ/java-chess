package pieces;
import java.util.ArrayList;
import tools.ArrayUtils;
import game.Board;

public class Pawn extends GamePiece
{
    private boolean firstMoveMade;
    private final int DIR;

    public Pawn(String color, int col, int row)
    {
        super("Pawn", color, col, row);
        this.firstMoveMade = false;
        // TODO: this is a TEMPORARY SOLUTION before I implement JSON ruleset
        // i want to be able to grab the board length and height and calculate without passing through a Board object
        this.DIR = (8/2) > row+1 ? 1 : -1;
    }

    // Return a list of all possible points on board that this piece can move at this turn
    @Override
    public int[][] getAllValidMoves(Board board)
    {
        ArrayList<ArrayList<Integer>> moves = new ArrayList<ArrayList<Integer>>();

        // All possible pawn movements
        int x = super.getCol();
        int y = super.getRow();
        int[][] baseMovements = {
            {x  , y+1*DIR}, // up 1 
            {x  , y+2*DIR}, // up 2
            {x+1, y+1*DIR}, // R diag
            {x-1, y+1*DIR}  // L diag
        };
        if (firstMoveMade) { baseMovements[1] = null; }

        // Check conditions and add to validMoves
        for (int[] movement : baseMovements)
        {
            if (movement == null) { continue; }

            int curX = movement[0];
            int curY = movement[1];
            int boardSpaceStatus = board.checkSpaceInt(curX, curY, super.getColor());
            boolean validAttack = (curX != x) && (boardSpaceStatus == 2);
            boolean validForward = (curX == x) && (boardSpaceStatus == 0);
            
            if (validAttack || validForward)
            {
                moves.add(ArrayUtils.createPoint(curX, curY));
            }
        }

        return ArrayUtils.convert2DArrayList(moves);
    }
}
