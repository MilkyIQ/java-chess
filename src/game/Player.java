package game;
import java.util.ArrayList;
import java.util.HashMap;
import tools.ArrayUtils;
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
        ArrayList<ArrayList<Integer>> moves = new ArrayList<ArrayList<Integer>>();
        Board ghostBoard = new Board(board.getLength(), board.getHeight());
        GamePiece king = players[0].getPieces("King").get(0);

        ArrayList<GamePiece> rooks   = players[0].getPieces("Rook");
        ArrayList<GamePiece> bishops = players[0].getPieces("Bishop");
        ArrayList<GamePiece> queens  = players[0].getPieces("Queen");

        // TODO: knight positions
        
        // TODO: pawn positions

        for (int i = 0; i < rooks.size(); i++)
        {
            Rook rook = (Rook) rooks.get(i);
            for (int[] point : rook.getAllValidMoves(board))
            {
                moves.add(ArrayUtils.createPoint(point[0], point[1]));
            }
        }

        for (int i = 0; i < bishops.size(); i++)
        {
            Bishop bishop = (Bishop) bishops.get(i);
            for (int[] point : bishop.getAllValidMoves(board))
            {
                moves.add(ArrayUtils.createPoint(point[0], point[1]));
            }
        }

        for (int i = 0; i < queens.size(); i++)
        {
            Queen queen = (Queen) queens.get(i);
            for (int[] point : queen.getAllValidMoves(board))
            {
                moves.add(ArrayUtils.createPoint(point[0], point[1]));
            }
        }

        for (ArrayList<Integer> point : moves)
        {
            ghostBoard.place(new GamePiece("dummyPoint", point.get(0), point.get(1)));
        }
    }
}
