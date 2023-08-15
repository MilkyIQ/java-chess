package pieces;
import game.Board;
import game.Move;
import java.util.ArrayList;

public class Bishop extends GamePiece
{
    public Bishop(String color)
    {
        super("Bishop", "\u265D", color);
    }

    @Override
    public ArrayList<Move> getValidMoves(Board board, int x, int y)
    {
        ArrayList<Move> moves = new ArrayList<Move>();
        final String COLOR = super.getColor();
        int[][] directions = {{-1, -1}, {1, 1}, {-1, 1}, {1, -1}}; // Left-Down, Right-Up, Left-Up, Right-Down
    
        for (int[] direction : directions)
        {
            int dx = direction[0];
            int dy = direction[1];
            int crawlX = x + dx;
            int crawlY = y + dy;
    
            while (crawlX >= 0 && crawlX < board.getLength() && crawlY >= 0 && crawlY < board.getHeight())
            {
                int spaceStatus = board.checkSpace(crawlX, crawlY, COLOR);
                if (spaceStatus == 1) { break; } // Blocked by a piece of the same color
                moves.add(new Move(board, this, x, y, crawlX, crawlY));
                if (spaceStatus == 2) { break; } // Captures an opponent's piece
    
                crawlX += dx;
                crawlY += dy;
            }
        }

        return moves;
    }
}
