package game;
import java.util.ArrayList;
import pieces.*;

public class Player {
    private ArrayList<GamePiece> pieces;
    private final String NAME;
    private final String COLOR;

    public Player(String name, String color)
    {
        this.NAME = name;
        this.COLOR = color;
        this.pieces = new ArrayList<GamePiece>();
    }

    public ArrayList<GamePiece> getPieces()
    {
        return pieces;
    }

    public GamePiece getPiece(int x, int y)
    {
        for (GamePiece piece : pieces)
        {
            if ((piece.getCol() == x) && (piece.getRow() == y)) { return piece; }
        }
        return null;
    }

    public void givePiece(GamePiece piece)
    {
        pieces.add(piece);
    }
}
