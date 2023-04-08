package game;
import pieces.*;
import game.Color;

public class Board
{
    private int turn;
    private GamePiece[][] board;
    private final int LENGTH;
    private final int HEIGHT;

    // Game Board Constructor
    public Board(int rows, int columns)
    {
        this.LENGTH = Math.min(50, Math.abs(columns));
        this.HEIGHT = Math.min(50, Math.abs(rows));
        this.board = new GamePiece[HEIGHT][LENGTH];
        this.turn = 0;

        for (int row = 0; row < LENGTH; row++)
        {
            for (int col = 0; col < HEIGHT; col++)
            {
                board[row][col] = null;
            }
        }
    }

    public int getLength()
    {
        return LENGTH;
    }

    public int getHeight()
    {
        return HEIGHT;
    }
    
    // Return true if space IS occupied
    public boolean checkSpace(int x, int y)
    {
        return board[y][x] != null;
    }

    // Return true if space IS occupied AND by the specified color
    public boolean checkSpace(int x, int y, String color)
    {
        return board[y][x] == null ? false : board[y][x].getColor().equals(color);
    }

    public void move(GamePiece piece, int x, int y)
    {
        // This feels really janky because I have to update the position in two different objects
        board[y][x] = piece;
        board[piece.getRow()][piece.getCol()] = null;
        piece.setPos(x, y);
    }

    public void place(GamePiece piece)
    {
        int row = piece.getRow();
        int col = piece.getCol();
        board[row][col] = piece;
        piece.setPos(col, row);
    }

    public void printBoard()
    {
        for (GamePiece[] row : board)
        {
            System.out.println();
            for (GamePiece piece : row)
            {
                if (piece == null) { System.out.print("- "); }
                else { System.out.print(piece + " "); }
            }
        }
        System.out.println();
    }
}

//TODO: maybe generate infinite checkers starting board with recursion?