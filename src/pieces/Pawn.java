package pieces;
import java.util.ArrayList;
import game.Board;

public class Pawn extends GamePiece
{
    private boolean firstMoveMade;
    private final int DIR;

    public Pawn(String color, int row, int col)
    {
        super("Pawn", color, row, col);
        this.firstMoveMade = false;
        // TODO: this is a TEMPORARY SOLUTION before I implement JSON ruleset
        // i want to be able to grab the board length and height and calculate without passing through a Board object
        this.DIR = (8/2) < col ? 1 : -1;
    }

    @Override
    public int[][] getAllValidMoves(Board board)
    {
        ArrayList<ArrayList<Integer>> moves = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> point = new ArrayList<Integer>(2);

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
            int curX = movement[0];
            int curY = movement[1];
            if (movement != null && !board.checkSpace(curX, curY, super.getColor()))
            {
                // TODO: maybe this can be moved to a utils class and made into a static function?
                point.set(0, curX);
                point.set(0, curY);
                moves.add(point);
            }
        }

        // idk where i can put this into a function that won't look atrocious in organizing so it's staying like this for now
        return moves.stream().map(u->u.stream().mapToInt(i->i).toArray()).toArray(int[][]::new);
    }
}
