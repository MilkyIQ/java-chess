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
        /*
         * STEPS:
         * 1. [x] Iterate through the gameboard and create a list of all moves that every enemy piece can make
         * 2. [x] Create a ghost board from the validMoves
         * 3. [x] Check king's position on ghost board
         * 4. [x] If king not touching any points, return safe, else, continue.
         * 5. [ ] Iterate through friendly pieces
         * * -> [ ] Create a list of all moves that the current piece can make
         * * -> [ ] Iterate through those moves and check if each move keeps the king in check.
         * * -> [ ] If a move leaves the king safe, break and return check, else continue until moves exhausted
         * 6. If reach end of function (no moves leave king safe), return checkmate.
         */
        String state = "safe";
        boolean kingInCheck = this.isInCheck(board);

        if (kingInCheck)
        {
            // for loop
            // // create list
            // // iterate through list
            // // // does move make king safe?
            // // // -> if YES: state = "check";
            // // set state "checkmate" if loop exhausted
            state = "check";
        }

        this.setState(state);
    }

    public boolean isInCheck(Board board)
    {
        final int LENGTH = board.getLength();
        final int HEIGHT = board.getLength();
        ArrayList<GamePiece> moves = new ArrayList<GamePiece>();
        Board ghostBoard = new Board(LENGTH, HEIGHT);
        
        // Iterate through board and populate ghostBoard with validMoves
        for (int col = 0; col < LENGTH; col++)
        {
            for (int row = 0; row < HEIGHT; row++)
            {
                GamePiece space = board.getSpace(col, row);
                if (space == null || space.getColor().equals(COLOR)) { continue; }
                space.updateValidMoves(board, moves);
            }
        }
        for (GamePiece piece : moves) { ghostBoard.place(piece); }
        
        // Print ghostBoard (for testing)
        ghostBoard.setColors(Color.BLACK, Color.BLACK, Color.BLACK);
        ghostBoard.printBoard();
        
        // Place king on board and return status
        GamePiece king = hand.get("King").get(0);
        return ghostBoard.getSpace(king.getCol(), king.getRow()) != null;
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
