package game;
import pieces.*;
import game.Color;

public class Board
{
    private GamePiece[][] board;
    private final int LENGTH;
    private final int HEIGHT;

    // Game Board Constructor
    public Board(int rows, int columns, Player[] players)
    {
        this.LENGTH = Math.min(50, Math.abs(columns));
        this.HEIGHT = Math.min(50, Math.abs(rows));
        this.board = new GamePiece[HEIGHT][LENGTH];

        // Create empty board
        for (int row = 0; row < LENGTH; row++)
        {
            for (int col = 0; col < HEIGHT; col++)
            {
                board[row][col] = null;
            }
        }

        // Populate board
        for (Player player : players)
        {
            for (GamePiece piece : player.getPieces())
            {
                this.place(piece);
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

    public int checkSpaceInt(int x, int y, String color)
    {
        if      (x >= LENGTH || x < 0 ||
                 y >= HEIGHT || y < 0 )                 { return -1; } // space is out of bounds
        else if (board[y][x] == null)                   { return 0;  } // space is empty
        else if (board[y][x].getColor().equals(color))  { return 1;  } // space is occupied by color
        else if (!board[y][x].getColor().equals(color)) { return 2;  } // space is occupied NOT by color
        else
        {
            throw new IllegalArgumentException(Color.RED + "Board.checkSpaceInt() was given bad data." + Color.RESET); // just in case
        }
    }

    // Move given piece from one space to another, update spaces and piece data
    public void move(GamePiece piece, int x, int y)
    {
        // This feels really janky because I have to update the position in two different objects... TOO BAD!
        board[y][x] = piece;
        board[piece.getRow()][piece.getCol()] = null;
        piece.setPos(x, y);
    }

    // Place given piece at specified space, replacing any object previously there
    public void place(GamePiece piece)
    {
        int row = piece.getRow();
        int col = piece.getCol();
        board[row][col] = piece;
        piece.setPos(col, row);
    }

    // Print out board to console
    public void printBoard()
    {
        int i = 0;
        for (GamePiece[] row : board)
        {
            i++;
            System.out.println();
            for (GamePiece piece : row)
            {
                String bgColor = i%2 == 0 ? Color.BLACK_BACKGROUND : Color.WHITE_BACKGROUND;
                if (piece == null) { System.out.print(bgColor + "   " + Color.RESET); }
                else { System.out.print(piece.getColor() + bgColor +  " " + piece + " " + Color.RESET); }
                i++;
            }
        }
        System.out.println();
    }
}