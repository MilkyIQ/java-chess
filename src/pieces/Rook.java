package pieces;
import game.Board;
import game.Move;
import player.Player;
import java.util.ArrayList;

public class Rook extends GamePiece
{
    public Rook(Player owner, int col, int row)
    { 
        super("Rook", "\u265C", owner, col,row);
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

    @Override
    public void updateValidMoves(Board board, ArrayList<Move> moves)
    {
        final int COL = super.getCol();
        final int ROW = super.getRow();
        final String COLOR = super.getColor();
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Left, Right, Down, Up
    
        for (int[] direction : directions)
        {
            int dx = direction[0];
            int dy = direction[1];
            int x = COL + dx;
            int y = ROW + dy;
    
            while (x >= 0 && x < board.getLength() && y >= 0 && y < board.getHeight())
            {
                int spaceStatus = board.checkSpace(x, y, COLOR);
                if (spaceStatus == 1) { break; } // Blocked by a piece of the same color
                moves.add(new Move(board, this, x, y));
                if (spaceStatus == 2) { break; } // Captures an opponent's piece
    
                x += dx;
                y += dy;
            }
        }
    }
    
}
