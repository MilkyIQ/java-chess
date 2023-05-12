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

    // Analyzes board and determines whether the player is in check, checkmate, stalemate, or safe
    public void updateState(Board board)
    {
        // Dummy code
        this.setState("safe");
    }

    // Loop through a list of players and return the index of the specified color
    public static int indexOf(String color, ArrayList<Player> players)
    {
        for (int i = 0; i < players.size(); i++)
        {
            if (players.get(i).getColor().equals(color))
            {
                return i;
            }
        }
        return -1;
    }

    // Loop through all players' game states and removes those who are in checkmate or stalemate
    public static void removeLosers(ArrayList<Player> players)
    {
        for (int i = 0; i < players.size(); i++)
        {
            String state = players.get(i).getState();
            if (state.equals("checkmate") || state.equals("stalemate"))
            {
                players.remove(i);
                i--;
            }
        }
    }
}
