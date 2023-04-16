package pieces;
import java.util.ArrayList;
import tools.ArrayUtils;
import game.Board;

public class Pawn extends GamePiece
{
    private boolean firstMoveMade;
    private final String DIR;

    public Pawn(String color, int col, int row, String direction)
    {
        super("\u265F", color, col, row);
        this.firstMoveMade = false;
        this.DIR = direction;
        if (!(DIR.equals("up") || DIR.equals("down") || DIR.equals("left") || DIR.equals("right")))
        {
            throw new IllegalArgumentException("`" + DIR + "`" + " is not a valid direction.");
        }
    }

    // Return a list of all possible points on board that this piece can move at this turn
    @Override
    public int[][] getAllValidMoves(Board board)
    {
        ArrayList<ArrayList<Integer>> moves = new ArrayList<ArrayList<Integer>>();
        int x = super.getCol();
        int y = super.getRow();

        // Initialize all possible pawn movements
        int[][] baseMovements = new int[4][2];
        for (int i = 0; i < 4; i++)
        {
            baseMovements[i][0] = x;
            baseMovements[i][1] = y;
        }

        // Adjust values accorsding to direction
        int axis    = DIR.equals("up") || DIR.equals("down")  ? 1 : 0;
        int dirMult = DIR.equals("up") || DIR.equals("right") ? 1 : -1;
        baseMovements[0][axis] += 1*dirMult;
        baseMovements[1][axis] += 2*dirMult;
        baseMovements[2][axis] += 1*dirMult;
        baseMovements[3][axis] += 1*dirMult;
        baseMovements[2][Math.abs(axis-1)] += 1;
        baseMovements[3][Math.abs(axis-1)] -= 1;
        if (firstMoveMade) { baseMovements[1] = null; } // edge case

        // Check conditions and add to validMoves
        for (int[] movement : baseMovements)
        {
            if (movement == null) { continue; }

            int curX = movement[0];
            int curY = movement[1];
            int boardSpaceStatus = board.checkSpaceInt(curX, curY, super.getColor());
            boolean validAttack = (curX != x) && (boardSpaceStatus == 2);
            boolean validForward = (curX == x || curY == y) && (boardSpaceStatus == 0);
            
            if (validAttack || validForward)
            {
                moves.add(ArrayUtils.createPoint(curX, curY));
            }
        }

        return ArrayUtils.convert2DArrayList(moves);
    }
}
