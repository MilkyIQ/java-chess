package pieces;
import game.Board;

public class GamePiece
{
    private final String NAME;
    private final String COLOR;
    private int col;
    private int row;
    private int moveCount;
    
    public GamePiece(String name, String color, int col, int row)
    {
        this.NAME = name;
        this.COLOR = color;
        this.col = col;
        this.row = row;
        this.moveCount = 0;
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

    public int getMoveCount()
    {
        return moveCount;
    }

    public void setPos(int x, int y)
    {
        col = x;
        row = y;
    }

    public void updateMoveCount()
    {
        moveCount++;
    }

    // Return an arary of all points on grid that the gamepiece can move
    public int[][] getAllValidMoves(Board board)
    {
        return null;
    }

    public boolean checkMove(int x, int y, Board board)
    {
        return false;
    }

    public String toFormattedPositon()
    {
        return NAME + "(" + (col+1) + "," + (row+1) + ")";
    }

    public boolean equals(GamePiece piece)
    {
        return (piece.getCol() == col) && (piece.getRow() == row);
    }

    // Print corresponding symbol of gamepiece
    public String toString()
    {
        return NAME.substring(0,1);
    }
}
