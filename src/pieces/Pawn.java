package pieces;
import game.Board;
import game.Move;
import java.util.ArrayList;

public class Pawn extends GamePiece
{
    private final String DIR;

    public Pawn(String color, String direction)
    {
        super('\u265F', color);
        this.DIR = direction;

        if (!(DIR.equals("up") || DIR.equals("down") || DIR.equals("left") || DIR.equals("right")))
        {
            throw new IllegalArgumentException("`" + DIR + "`" + " is not a valid direction.");
        }
    }

    @Override
    public ArrayList<Move> getValidMoves(Board board, int x, int y)
    {
        ArrayList<Move> moves = new ArrayList<Move>();
        final String COLOR = super.getColor();
        final int[] PIECE_POSITION = {x, y};

        // Initialize all possible pawn movements
        int[][] baseMoves = new int[4][2];
        for (int i = 0; i < baseMoves.length; i++)
        {
            baseMoves[i][0] = PIECE_POSITION[0];
            baseMoves[i][1] = PIECE_POSITION[1];
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
            boolean validAttack = (move[0] != PIECE_POSITION[0]) && (spaceStatus == 2);
            boolean validForward = (move[axis] != PIECE_POSITION[axis] && move[anti] == PIECE_POSITION[anti]) && (spaceStatus == 0);

            if ( (validAttack || validForward))
            {
                moves.add(new Move(board, this, x, y, move[0], move[1]));
            }
        }

        return moves;
    }
}
