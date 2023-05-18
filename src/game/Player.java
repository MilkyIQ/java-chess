package game;
import java.util.ArrayList;
import java.util.HashMap;
import pieces.*;
import tools.Color;

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

    public String getColorCode()
    {
        int underscoreIndex = COLOR.indexOf("_");
        String baseColor = underscoreIndex > -1 ? COLOR.substring(0,underscoreIndex) : COLOR;
        return Color.getColorCodeOf(baseColor + "_bold");
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

    public ArrayList<GamePiece> getAllPiecesOfType(String title)
    {
        return hand.get(title);
    }

    public ArrayList<GamePiece> getAllPieces()
    {
        ArrayList<GamePiece> pieces = new ArrayList<GamePiece>();
        for (ArrayList<GamePiece> type : hand.values())
        {
            for (GamePiece piece : type)
            {
                pieces.add(piece);
            }
        }
        
        return pieces;
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
        // Breaks function if king is safe
        if (!this.isInCheck(board))
        {
            this.setState("safe");
            return;
        }

        // Loop through all pieces
        for (GamePiece piece : this.getAllPieces())
        {
            ArrayList<Move> currentPieceMoves = new ArrayList<Move>();
            piece.updateValidMoves(board, currentPieceMoves);

            // Check every valid move of current piece until move saves king or list exhausted
            for (Move move : currentPieceMoves)
            {
                // Simulate move & calculate player state
                board.move(move);
                boolean resultsInCheck = this.isInCheck(board);
                board.undoMove(move);

                // if king is not safe, continue, else, set state and end function
                if (resultsInCheck) { continue; }
                this.setState("check");
                return;
            }
        }

        // If all move lists exhausted, players state is checkmate
        this.setState("checkmate");
    }

    // Calcualtes all possible movements from all pieces on board (excluding pawns), and continue 
    public boolean isInCheck(Board board)
    {
        // Initialize main variables
        ArrayList<Move> moves = new ArrayList<Move>();
        final int LENGTH = board.getLength();
        final int HEIGHT = board.getLength();
        Board ghostBoard = new Board(LENGTH, HEIGHT);
        GamePiece king   = hand.get("King").get(0);
        final int KINGX  = king.getCol();
        final int KINGY  = king.getRow();
        
        // Check corners for pawns
        int[] xInc = {1, -1}, yInc = {1, -1};
        for (int x : xInc)
        {
            for (int y : yInc)
            {
                if (board.coordinateOutOfBounds(KINGX+x, KINGY+y)) { continue; };
                GamePiece potentialPawn = board.getSpace(KINGX+x, KINGY+y);
                Boolean pawnIsAttacking = potentialPawn != null && potentialPawn.getTitle().equals("Pawn") && potentialPawn.checkMove(KINGX, KINGY, board);
                if (pawnIsAttacking) { return true; }
            }
        }
        
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

        // Place points on ghostBoard
        for (Move move : moves)
        {
            ghostBoard.place(new GamePiece("x", move.getDestX(), move.getDestY()));
        }
        
        // Place king on board and return status
        return ghostBoard.getSpace(KINGX, KINGY) != null;
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
