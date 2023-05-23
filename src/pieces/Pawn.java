package pieces;
import game.Board;
import game.Move;
import java.util.ArrayList;

public class Pawn extends GamePiece
{
    private final String DIR;

    public Pawn(String color, int col, int row, String direction)
    {
        super("Pawn", "\u265F", color, col, row);
        this.DIR = direction;

        if (!(DIR.equals("up") || DIR.equals("down") || DIR.equals("left") || DIR.equals("right")))
        {
            throw new IllegalArgumentException("`" + DIR + "`" + " is not a valid direction.");
        }
    }

    @Override
    public boolean checkMove(int x, int y, Board board)
    {
        final String COLOR = super.getColor();
        final int[] POS = {super.getCol(), super.getRow()};

        // Initialize all possible pawn movements
        int[][] baseMoves = new int[4][2];
        for (int i = 0; i < baseMoves.length; i++)
        {
            baseMoves[i][0] = POS[0];
            baseMoves[i][1] = POS[1];
        }

        // Adjust values according to direction
        int axis = DIR.equals("up") || DIR.equals("down")  ? 1 : 0;
        int dir = DIR.equals("up") || DIR.equals("right") ? 1 : -1;
        int anti = Math.abs(axis-1);
        baseMoves[0][axis] += 1*dir;
        baseMoves[1][axis] += 2*dir;
        baseMoves[2][axis] += 1*dir;
        baseMoves[2][anti] += 1;
        baseMoves[3][axis] += 1*dir;
        baseMoves[3][anti] -= 1;

        // Edge case for starting skip 2
        if (super.getMoveCount() != 0 || board.checkSpace(baseMoves[0][0], baseMoves[0][1], COLOR) != 0)
        { 
            baseMoves[1] = null;
        }

        // Check conditions and continue until match reaced or list exhausted
        for (int[] move : baseMoves)
        {
            if (move == null) { continue; }

            int spaceStatus = board.checkSpace(move[0], move[1], COLOR);
            boolean validAttack = (move[0] != POS[0]) && (spaceStatus == 2);
            boolean validForward = (move[axis] != POS[axis] && move[anti] == POS[anti]) && (spaceStatus == 0);

            if ( (validAttack || validForward) && (move[0] == x && move[1] == y) )
            {
                return true;
            }
        }

        return false;
    }

    @Override
    public void updateValidMoves(Board board, ArrayList<Move> moves)
    {
        final String COLOR = super.getColor();
        final int[] POS = {super.getCol(), super.getRow()};

        // Initialize all possible pawn movements
        int[][] baseMoves = new int[4][2];
        for (int i = 0; i < baseMoves.length; i++)
        {
            baseMoves[i][0] = POS[0];
            baseMoves[i][1] = POS[1];
        }

        // Adjust values according to direction
        int axis = DIR.equals("up") || DIR.equals("down")  ? 1 : 0;
        int dir = DIR.equals("up") || DIR.equals("right") ? 1 : -1;
        int anti = Math.abs(axis-1);
        baseMoves[0][axis] += 1*dir;
        baseMoves[1][axis] += 2*dir;
        baseMoves[2][axis] += 1*dir;
        baseMoves[2][anti] += 1;
        baseMoves[3][axis] += 1*dir;
        baseMoves[3][anti] -= 1;

        // Edge case for starting skip 2
        if (super.getMoveCount() != 0 || board.checkSpace(baseMoves[0][0], baseMoves[0][1], COLOR) != 0)
        { 
            baseMoves[1] = null;
        }

        // Check conditions and continue until match reaced or list exhausted
        for (int[] move : baseMoves)
        {
            if (move == null) { continue; }

            int spaceStatus = board.checkSpace(move[0], move[1], COLOR);
            boolean validAttack = (move[0] != POS[0]) && (spaceStatus == 2);
            boolean validForward = (move[axis] != POS[axis] && move[anti] == POS[anti]) && (spaceStatus == 0);

            if ( (validAttack || validForward))
            {
                moves.add(new Move(board, this, move[0], move[1]));
            }
        }
    }
}
