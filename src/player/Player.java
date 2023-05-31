package player;
import java.util.ArrayList;
import java.util.HashMap;

import game.Board;
import game.Move;
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

    // GETTER METHODS 

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

    // SETTER METHODS

    public void give(GamePiece piece)
    {
        hand.get(piece.getTitle()).add(piece);
    }

    public void remove(GamePiece piece)
    {
        hand.get(piece.getTitle()).remove(piece);
    }

    public void updateState(String value)
    {
        state = value;
    }

    public Move selectMove(Board board)
    {
        throw new IllegalStateException("Illegal creation of generic class object");
    }

    // Analyzes board and determines whether the player is in check, checkmate, stalemate, or safe
    public String calculateState(Board board)
    {
        GamePiece king = hand.get("King").get(0);
        
        // Breaks function if king is safe
        if (!king.isBeingThreatened(board))
        {
            return "safe";
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
                boolean resultsInCheck = king.isBeingThreatened(board);
                board.undoMove(move);

                // if king is not safe, continue, else, set state and end function
                if (resultsInCheck) { continue; }
                return "check";
            }
        }

        // If all move lists exhausted, players state is checkmate
        return "checkmate";
    }
}
