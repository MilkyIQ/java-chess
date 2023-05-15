package pieces;
import game.Board;
import java.util.ArrayList;

public class Rook extends GamePiece
{
    public Rook(String COLOR, int col, int row)
    { 
        super("Rook", "\u265C", COLOR, col,row);
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
    public void updateValidMoves(Board board, ArrayList<GamePiece> moves)
    {
        final int COL = super.getCol();
        final int ROW = super.getRow();
        final String COLOR = super.getColor();

        // LEFT
        for (int x = COL-1; x >= 0; x--)
        {
            int spaceStatus = board.checkSpace(x, ROW, COLOR);
            if (spaceStatus == 1) { break; }
            moves.add(new GamePiece("x", x, ROW));
            if (spaceStatus == 2) { break; }
        }

        // RIGHT
        for (int x = COL+1; x < board.getLength(); x++)
        {
            int spaceStatus = board.checkSpace(x, ROW, COLOR);
            if (spaceStatus == 1) { break; }
            moves.add(new GamePiece("x", x, ROW));
            if (spaceStatus == 2) { break; }
        }

        // DOWN
        for (int y = ROW-1; y >= 0; y--)
        {
            int spaceStatus = board.checkSpace(COL, y, COLOR);
            if (spaceStatus == 1) { break; }
            moves.add(new GamePiece("x", COL, y));
            if (spaceStatus == 2) { break; }
        }

        // UP
        for (int y = ROW+1; y < board.getHeight(); y++)
        {
            int spaceStatus = board.checkSpace(COL, y, COLOR);
            if (spaceStatus == 1) { break; }
            moves.add(new GamePiece("x", COL, y));
            if (spaceStatus == 2) { break; }
        }
    }
}
