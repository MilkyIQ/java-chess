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
    public ArrayList<Move> getValidMoves(Board board)
    {
        ArrayList<Move> moves = new ArrayList<Move>();
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

        return moves;
    }
    
}
