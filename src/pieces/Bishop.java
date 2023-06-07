package pieces;
import game.Board;
import game.Move;
import player.Player;
import java.util.ArrayList;

public class Bishop extends GamePiece
{
    public Bishop(Player owner, int col, int row)
    {
        super("Bishop", "\u265D", owner, col, row);
    }

    @Override
    public boolean checkMove(int x, int y, Board board)
    {
        final String COLOR = super.getColor();
        int pieceX = super.getCol();
        int pieceY = super.getRow();
        int deltaX = x - pieceX;
        int deltaY = y - pieceY;

        boolean invalidMove = Math.abs(deltaX) != Math.abs(deltaY);
        boolean spaceFriendly = board.checkSpace(x, y, COLOR) == 1;
        if (invalidMove || spaceFriendly) { return false; }

        int xDir = Math.abs(deltaX) / deltaX;
        int yDir = Math.abs(deltaY) / deltaY;

        pieceX += xDir;
        pieceY += yDir;
        while (pieceX != x && pieceY != y)
        {
            if (board.checkSpace(pieceX, pieceY, COLOR) > 0)
            {
                return false;
            }
            pieceX += xDir;
            pieceY += yDir;
        }

        return true;
    }

    @Override
    public ArrayList<Move> getValidMoves(Board board)
    {
        ArrayList<Move> moves = new ArrayList<Move>();
        final int COL = super.getCol();
        final int ROW = super.getRow();
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
                moves.add(new Move(board, this, x, y));
                if (spaceStatus == 2) { break; } // Captures an opponent's piece
    
                x += dx;
                y += dy;
            }
        }

        return moves;
    }
}
