package game;
import java.util.ArrayList;
import java.util.HashMap;
import pieces.*;

public class Player {
    private HashMap<String,ArrayList<GamePiece>> hand;
    private final String NAME;
    private final String COLOR;
    private String state;

    public Player(String name, String color)
    {
        this.NAME = name;
        this.COLOR = color;
        this.hand = new HashMap<String,ArrayList<GamePiece>>();
        this.state = "open";

        String[] titles = {"King", "Queen", "Rook", "Bishop", "Knight", "Pawn"};
        for (String t : titles) { hand.put(t, new ArrayList<GamePiece>()); };
    }

    public String getColor()
    {
        return COLOR;
    }

    public String getName()
    {
        return NAME;
    }

    public String getState()
    {
        return state;
    }

    public HashMap<String,ArrayList<GamePiece>> getHand()
    {
        return hand;
    }

    public ArrayList<GamePiece> getPieces(String title)
    {
        return hand.get(title);
    }

    public void setState(String newState)
    {
        state = newState;
    }

    public void give(GamePiece piece)
    {
        hand.get(piece.getTitle()).add(piece);
    }

    public void remove(GamePiece piece)
    {
        hand.get(piece.getTitle()).remove(piece);
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

    public static void updatePlayerStates(Player[] players, Board board)
    {
        // Dummy code
        for (Player player : players) { player.setState("open"); }
    }
}
