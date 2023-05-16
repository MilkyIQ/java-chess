package game;
import pieces.*;
import tools.Color;

import java.util.ArrayList;

public class Board
{
    private GamePiece[][] board;
    private final int LENGTH;
    private final int HEIGHT;
    public String[] colors = new String[3]; 
    
    // Game Board Constructor
    public Board(int columns, int rows)
    {
        this.LENGTH = Math.min(50, Math.abs(columns));
        this.HEIGHT = Math.min(50, Math.abs(rows));
        this.board = new GamePiece[HEIGHT][LENGTH];

        // Create empty board
        for (int row = 0; row < HEIGHT; row++)
        {
            for (int col = 0; col < LENGTH; col++)
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

    public GamePiece getSpace(int x, int y)
    {
        return board[y][x];
    }

    public boolean coordinateOutOfBounds(int x, int y)
    {
        return x >= LENGTH || y >= HEIGHT || x < 0 || y < 0;
    }

    // Returns representative int value -1 to 2 to show whether or not a space is occupied by the given color
    public int checkSpace(int x, int y, String color)
    {
        if      (coordinateOutOfBounds(x, y))           { return -1; } // space is out of bounds
        else if (board[y][x] == null)                   { return 0;  } // space is empty
        else if (board[y][x].getColor().equals(color))  { return 1;  } // space is occupied by color
        else if (!board[y][x].getColor().equals(color)) { return 2;  } // space is occupied NOT by color
        else
        {
            throw new IllegalArgumentException(Color.RED + "Board.checkSpace() was given bad data." + Color.RESET); // just in case
        }
    }

    public void setColors(String evens, String odds, String notation)
    {
        colors[0] = evens;
        colors[1] = odds;
        colors[2] = notation;
    }

    public void populateBoard(ArrayList<Player> players)
    {
        for (Player player : players)
        {
            for (ArrayList<GamePiece> points : player.getHand().values())
            {
                for (GamePiece piece : points) { this.place(piece); } // this is technically only 4 indents so i count a win.
            }
        }
    }

    // Move given piece from one space to another, update spaces and piece data
    public void move(GamePiece piece, int x, int y)
    {
        board[y][x] = piece;
        board[piece.getRow()][piece.getCol()] = null;
        piece.setPos(x, y);
        piece.incMoveCount();
    }

    // Undo the last move (for use with player state checks)
    public void undoMove(GamePiece movedPiece, int oldX, int oldY, GamePiece attackedSpace)
    {
        int attackedX = movedPiece.getCol();
        int attackedY = movedPiece.getRow();
        
        board[oldY][oldX] = movedPiece;
        board[attackedY][attackedX] = attackedSpace;
        movedPiece.setPos(oldX, oldY);
        movedPiece.decMoveCount();
        // no need to update attackedSpace position because it's position doesnt change after attack

    }

    // Place given piece at its already specified position, replacing any object previously there
    public void place(GamePiece piece)
    {
        try
        {
            int row = piece.getRow();
            int col = piece.getCol();
            board[row][col] = piece;
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.print(Color.YELLOW + "[WARNING]: Skipping placement of ");
            System.out.print(piece.getColor() + piece.toFormattedPositon());
            System.out.println(Color.YELLOW + ", piece out of bounds." + Color.RESET);
        }
    }

    // Print out board to console with fancy graphics
    public void printBoard()
    {
        // column numbers (top)
        System.out.print("  ");
        for (int col = 0; col < LENGTH; col++)
        {
            System.out.print(colors[2] + " " + (col+"").substring(0,1) + " " + Color.RESET);
        }
        
        // row in board
        for (int row = HEIGHT-1; row >= 0; row--)
        {
            System.out.println();
            System.out.print(colors[2] + (row+"").substring(0,1) + " " + Color.RESET); // row number (left)
            
            // column in row
            for (int col = 0; col < LENGTH; col++)
            {
                GamePiece piece = board[row][col];
                String bgColor  = (row+col) % 2 == 0    ? colors[1]  : colors[0];
                String fgColor  = piece         == null ? Color.CYAN : piece.getColor();
                String output   = piece         == null ? " "        : piece.toString();
                
                System.out.print(fgColor + bgColor + " " + output + " " + Color.RESET);
            }

            System.out.print(" " + colors[2] + (row+"").substring(0,1) + " " + Color.RESET); // row number (right)
        }

        // column numbers (bottom)
        System.out.print("\n  ");
        for (int col = 0; col < LENGTH; col++)
        {
            System.out.print(colors[2] + " " + (col+"").substring(0,1) + " " + Color.RESET);
        }
        System.out.println();
    }

}