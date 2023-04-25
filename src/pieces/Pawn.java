package pieces;
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

    @Override
    public boolean checkMove(int x, int y, Board board)
    {
        int[] pos = {super.getCol(), super.getRow()};

        // Initialize all possible pawn movements
        int[][] baseMovements = new int[4][2];
        for (int i = 0; i < baseMovements.length; i++)
        {
            baseMovements[i][0] = pos[0];
            baseMovements[i][1] = pos[1];
        }

        // Adjust values accorsding to direction
        int axis = DIR.equals("up") || DIR.equals("down")  ? 1 : 0;
        int dir = DIR.equals("up") || DIR.equals("right") ? 1 : -1;
        int anti = Math.abs(axis-1);
        baseMovements[0][axis] += 1*dir;
        baseMovements[1][axis] += 2*dir;
        baseMovements[2][axis] += 1*dir;
        baseMovements[2][anti] += 1;
        baseMovements[3][axis] += 1*dir;
        baseMovements[3][anti] -= 1;
        if (firstMoveMade) { baseMovements[1] = null; } // edge case

        // Check conditions and continue until match reaced or list exhausted
        for (int[] move : baseMovements)
        {
            if (move == null) { continue; }

            int spaceStatus = board.checkSpace(move[0], move[1], super.getColor());
            boolean validAttack = (move[0] != pos[0]) && (spaceStatus == 2);
            boolean validForward = (move[axis] != pos[axis] && move[anti] == pos[anti]) && (spaceStatus == 0);

            if ( (validAttack || validForward) && (move[0] == x && move[1] == y) )
            {
                return true;
            }
        }

        return false;
    }
}
