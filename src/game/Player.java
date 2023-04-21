package game;
import java.util.ArrayList;
import pieces.*;

public class Player {
    private ArrayList<GamePiece> hand;
    private final String NAME;
    private final String COLOR;

    public Player(String name, String color)
    {
        this.NAME = name;
        this.COLOR = color;
        this.hand = new ArrayList<GamePiece>();
    }

    public String getColor()
    {
        return COLOR;
    }

    public String getName()
    {
        return NAME;
    }

    public ArrayList<GamePiece> getHand()
    {
        return hand;
    }

    public GamePiece getPiece(int x, int y)
    {
        for (GamePiece piece : hand)
        {
            if ((piece.getCol() == x) && (piece.getRow() == y)) { return piece; }
        }
        return null;
    }

    public void give(GamePiece piece)
    {
        hand.add(piece);
    }

    public void remove(GamePiece piece)
    {
        hand.remove(piece);
    }

    // Loop through a list of players and return the index of the specified color,
    // not too sure about where to put this, but a static method here seemed fitting
    public static int indexOf(String color, Player[] players)
    {
        for (int i = 0; i < players.length; i++)
        {
            if (players[i].getColor().equals(color))
            {
                return i;
            }
        }
        return -1;
    }
}
