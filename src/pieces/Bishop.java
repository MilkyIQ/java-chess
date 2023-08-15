package pieces;
import game.Board;
import game.Move;
import player.Player;
import java.util.ArrayList;

public class Bishop extends GamePiece
{
    public Bishop(String color)
    {
        super("Bishop", "\u265D", color);
    }

    @Override
    public ArrayList<Move> getValidMoves(Board board)
    {
        ArrayList<Move> moves = new ArrayList<Move>();
        final int[] POS = super.searchPos(board);
        final int COL = POS[0];
        final int ROW = POS[1];
        final String COLOR = super.getColor();
        int[][] directions = {{-1, -1}, {1, 1}, {-1, 1}, {1, -1}}; // Left-Down, Right-Up, Left-Up, Right-Down
    
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
                Move newMove = new Move(board, this, x, y);
                if (!newMove.resultsInCheck(board)) {
                    moves.add(new Move(board, this, x, y));
                }
                if (spaceStatus == 2) { break; } // Captures an opponent's piece
    
                x += dx;
                y += dy;
            }
        }

        return moves;
    }
}
