package pieces;
import game.Board;
import tools.Color;

import java.util.ArrayList;

public class GamePiece
{
    private final String TITLE;
    private final String SYMBOL;
    private final String COLOR;
    private int col;
    private int row;
    private int moveCount;
    
    public GamePiece(String title, String symbol, String color, int col, int row)
    {
        this.TITLE = title;
        this.SYMBOL = symbol;
        this.COLOR = color;
        this.col = col;
        this.row = row;
        this.moveCount = 0;
    }

    // For use with ghost board point placement only
    public GamePiece(String symbol, int col, int row)
    {
        this.SYMBOL = symbol;
        this.col = col;
        this.row = row;
        this.TITLE = "GenericGamePiece";
        this.COLOR = "purple_underlined";
    }

    public String getTitle()
    {
        return TITLE;
    }

    public String getSymbol()
    {
        return SYMBOL;
    }

    public String getColor()
    {
        return COLOR;
    }

    public String getColorCode()
    {
        return Color.getColorCodeOf(COLOR);
    }

    public int getRow()
    {
        return row;
    }

    public int getCol()
    {
        return col;
    }

    public int getMoveCount()
    {
        return moveCount;
    }

    public void setPos(int x, int y)
    {
        col = x;
        row = y;
    }

    public void incMoveCount()
    {
        moveCount++;
    }

    public void decMoveCount()
    {
        moveCount--;
    }

    public boolean checkMove(int x, int y, Board board)
    {
        return false;
    }

    public void updateValidMoves(Board board, ArrayList<Board.Move> moves)
    {
        return;
    }

    public String toFormattedPositon()
    {
        return SYMBOL + "(" + (col) + "," + (row) + ")";
    }

    // Print corresponding symbol of gamepiece
    public String toString()
    {
        return SYMBOL.substring(0,1);
    }
}
