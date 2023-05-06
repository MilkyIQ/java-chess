package pieces;
import game.Board;
import java.util.ArrayList;
import tools.ArrayUtils;

public class Bishop extends GamePiece
{
    public Bishop(String color, int col, int row)
    {
        super("\u265D", color, col, row);
    }

    @Override
    public boolean checkMove(int x, int y, Board board)
    {
        String color = super.getColor();
        int COL = super.getCol();
        int pieceY = super.getRow();
        int deltaX = x - COL;
        int deltaY = y - pieceY;

        boolean invalidMove = Math.abs(deltaX) != Math.abs(deltaY);
        boolean spaceFriendly = board.checkSpace(x, y, color) == 1;
        if (invalidMove || spaceFriendly) { return false; }

        int xDir = Math.abs(deltaX) / deltaX;
        int yDir = Math.abs(deltaY) / deltaY;

        COL += xDir;
        pieceY += yDir;
        while (COL != x && pieceY != y)
        {
            if (board.checkSpace(COL, pieceY, super.getColor()) > 0)
            {
                return false;
            }
            COL += xDir;
            pieceY += yDir;
        }

        return true;
    }

    public int[][] getAllValidMoves(Board board)
    {
        ArrayList<ArrayList<Integer>> moves = new ArrayList<ArrayList<Integer>>();
        final String COLOR = super.getColor();
        final int COL = super.getCol();
        final int ROW = super.getRow();
        final int LENGTH = board.getLength();
        final int HEIGHT = board.getHeight();
        int x;
        int y;
        
        // LEFT-DOWN
        x = COL - 1;
        y = ROW - 1;
        while (x > 0 || y > 0)
        {
            int spaceStatus = board.checkSpace(x, y, COLOR);
            if (spaceStatus == 1) { break; }
            moves.add(ArrayUtils.createPoint(x, y));
            if (spaceStatus == 2) { break; }
            x--;
            y--;
        }

        // RIGHT-UP
        x = COL + 1;
        y = ROW + 1;
        while (x < LENGTH || y < HEIGHT)
        {
            int spaceStatus = board.checkSpace(x, y, COLOR);
            if (spaceStatus == 1) { break; }
            moves.add(ArrayUtils.createPoint(x, y));
            if (spaceStatus == 2) { break; }
            x++;
            y++;
        }

        // LEFT-UP
        x = COL - 1;
        y = ROW + 1;
        while (x > 0 || y < HEIGHT)
        {
            int spaceStatus = board.checkSpace(x, y, COLOR);
            if (spaceStatus == 1) { break; }
            moves.add(ArrayUtils.createPoint(x, y));
            if (spaceStatus == 2) { break; }
            x--;
            y++;
        }

        // RIGHT-DOWN
        x = COL + 1;
        y = ROW - 1;
        while (x < LENGTH || y > 0)
        {
            int spaceStatus = board.checkSpace(x, y, COLOR);
            if (spaceStatus == 1) { break; }
            moves.add(ArrayUtils.createPoint(x, y));
            if (spaceStatus == 2) { break; }
            x++;
            y--;
        }

        return ArrayUtils.convert2DArrayList(moves);
    }
}
