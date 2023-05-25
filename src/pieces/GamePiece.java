package pieces;
import game.Board;
import game.Move;
import player.Player;
import tools.Color;

import java.util.ArrayList;

public class GamePiece
{
    private final String TITLE;
    private final String SYMBOL;
    private Player owner;
    private int col;
    private int row;
    private int moveCount;
    
    public GamePiece(String title, String symbol, Player owner, int col, int row)
    {
        this.TITLE = title;
        this.SYMBOL = symbol;
        this.owner = owner;
        this.col = col;
        this.row = row;
        this.moveCount = 0;
    }

    // For use with ghost board point placement only
    public GamePiece(int col, int row)
    {
        this.col = col;
        this.row = row;
        this.SYMBOL = "x";
        this.TITLE = null;
        this.owner = null;
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
        return owner.getColor();
    }

    public Player getOwner()
    {
        return owner;
    }

    public String getColorCode()
    {
        return Color.getColorCodeOf(owner.getColor());
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

    public void updateValidMoves(Board board, ArrayList<Move> moves)
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
