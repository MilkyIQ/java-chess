package game;
import pieces.*;
import player.Player;
import tools.Color;
import java.util.ArrayList;

public class Board implements java.io.Serializable
{
    private GamePiece[][] board;
    private final int LENGTH;
    private final int HEIGHT;
    public String[] colors = new String[3];
    
    public Board(int columns, int rows)
    {
        this.LENGTH = Math.min(50, Math.abs(columns));
        this.HEIGHT = Math.min(50, Math.abs(rows));
        this.board = new GamePiece[HEIGHT][LENGTH];
    }

    // GETTERS

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
        return coordinateOutOfBounds(x,y) ? null : board[y][x];
    }

    public ArrayList<GamePiece> findPieces(String color, String type) {
        ArrayList<GamePiece> piecesOnBoard = new ArrayList<GamePiece>();
        for (int col = 0; col < LENGTH; col++) {
            for (int row = 0; row < HEIGHT; row++) {
                GamePiece space = board[row][col];
                if (space != null && space.getColor().equals(color) && space.getTitle().equals(type)) {
                    piecesOnBoard.add(space);
                }
            }
        }
        return piecesOnBoard;
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
    
    public boolean coordinateOutOfBounds(int x, int y)
    {
        return x >= LENGTH || y >= HEIGHT || x < 0 || y < 0;
    }

    // SETTERS

    public void setColors(String evens, String odds, String notation)
    {
        colors[0] = Color.getColorCodeOf(evens);
        colors[1] = Color.getColorCodeOf(odds);
        colors[2] = Color.getColorCodeOf(notation);
    }

    public void place(GamePiece piece, int x, int y)
    {
        try
        {
            board[y][x] = piece;
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.print(Color.YELLOW + "[WARNING]: Skipping placement of ");
            System.out.print(piece.getColorCode() + piece.toFormattedPosition(this));
            System.out.println(Color.YELLOW + ", piece out of bounds." + Color.RESET);
        }
    }

    // EVENTS 

    // Move given piece from one space to another, update spaces and piece data
    public void move(Move move)
    {
        int newPosX = move.getDestX();
        int newPosY = move.getDestY();
        GamePiece piece = move.getOriginPiece();
        GamePiece space = move.getDestPiece();
        // if (space != null) { space.getOwner().remove(space); }

        board[newPosY][newPosX] = piece;
        board[move.getOriginY()][move.getOriginX()] = null;
        piece.incMoveCount();

    }

    // Undo the last move (for use with player state checks)
    public void undoMove(Move move)
    {
        GamePiece attackedSpace = move.getDestPiece();
        GamePiece movedPiece    = move.getOriginPiece();
        int oldX                = move.getOriginX();
        int oldY                = move.getOriginY();
        int attackedX           = move.getDestX();
        int attackedY           = move.getDestY();
        // if (attackedSpace != null) { attackedSpace.getOwner().give(attackedSpace); }

        board[oldY][oldX] = movedPiece;
        board[attackedY][attackedX] = attackedSpace;
        movedPiece.decMoveCount();
        // no need to update attackedSpace position because it's position doesnt change after attack
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
                String fgColor  = piece         == null ? Color.CYAN : piece.getColorCode();
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