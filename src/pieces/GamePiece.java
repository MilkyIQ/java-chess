package pieces;
import game.Board;
import game.Color;
import java.util.ArrayList;

public class GamePiece
{
    private final String COLOR;
    private final String NAME;
    private int row;
    private int col;
    
    public GamePiece(String name, String color, int row, int col)
    {
        this.NAME = name;
        this.COLOR = color;
        this.row = row;
        this.col = col;
    }

    public String getName()
    {
        return NAME;
    }

    public String getColor()
    {
        return COLOR;
    }

    public int getRow()
    {
        return row;
    }

    public int getCol()
    {
        return col;
    }

    public void setPos(int x, int y)
    {
        row = y;
        col = x;
    }

    // Return an arary of all points on grid that the gamepiece can move
    public int[][] getAllValidMoves(Board board)
    {
        return null;
    }

    // Print corresponding symbol of gamepiece
    public String toString()
    {
        return COLOR + NAME.substring(0,1) + Color.RESET;
    }
}
