package player;
import java.util.ArrayList;
import game.Board;
import game.Move;
import pieces.*;
import tools.Color;

public class Player {
    private ArrayList<GamePiece> hand;
    private final String NAME;
    private final String COLOR;
    private String state;

    public Player(String name, String color)
    {
        this.NAME = name;
        this.COLOR = color;
        this.hand = new ArrayList<GamePiece>();
        this.state = "open";
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

    public ArrayList<GamePiece> getAllPieces()
    {
        return hand;
    }

    public ArrayList<Move> getAllLegalMoves(Board board)
    {
        ArrayList<Move> allMoves = new ArrayList<Move>();
        for (GamePiece piece : board.findPieces(COLOR, null))
        {
            int[] piecePosition = piece.searchPos(board);
            for (Move move : piece.getValidMoves(board, piecePosition[0], piecePosition[1]))
            {
                if (!move.resultsInCheck(board))
                {
                    allMoves.add(move);
                }
            }
        }
        return allMoves;
    }

    // SETTER METHODS

    public void give(GamePiece piece)
    {
        hand.add(piece);
    }

    public void remove(GamePiece piece)
    {
        hand.remove(piece);
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
        GamePiece king = board.findPieces(COLOR, King.class).get(0);
        boolean kingIsThreatened = king.isBeingThreatened(board);
        boolean playerHasMoves = getAllLegalMoves(board).size() > 0;
        
        if (!kingIsThreatened && playerHasMoves)
        {
            return "safe";
        }
        else if (!kingIsThreatened && !playerHasMoves)
        {
            return "stalemate";
        }
        else if (kingIsThreatened && playerHasMoves)
        {
            return "check";
        }
        else if (kingIsThreatened && !playerHasMoves)
        {
            return "checkmate";
        }

        return null;
    }
}
