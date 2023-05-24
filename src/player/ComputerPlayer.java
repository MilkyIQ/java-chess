package player;
import pieces.GamePiece;
import java.util.ArrayList;
import java.util.HashMap;
import game.Move;
import game.Board;
import tools.Color;

public class ComputerPlayer extends Player
{
    private int difficultyLevel;
    private HashMap<String,Integer> pieceRankings;

    public ComputerPlayer(String name, String color, int difficultyLevel)
    {
        super(name, color);
        this.difficultyLevel = difficultyLevel;
        this.pieceRankings = new HashMap<String,Integer>();
        pieceRankings.put("King",   100);
        pieceRankings.put("Queen",  95);
        pieceRankings.put("Knight", 84);
        pieceRankings.put("Rook",   65);
        pieceRankings.put("Bishop", 65);
        pieceRankings.put("Pawn",   33);
    }

    @Override
    public Move selectMove(Board board)
    {
        System.out.println("Computer is thinking...");
        System.out.println("Simulating moves...");
        System.out.println("Contacting parallel universes...");

        Move selectedMove = null;
        switch (difficultyLevel)
        {
            case 0: 
                selectedMove = randomSelect(board);
            case 1:
                selectedMove = simpleRankedSelect(board);
        }

        System.out.println(super.getColorCode() + "Move chosen!" + 
        selectedMove.getOwner().toFormattedPositon() + " " + selectedMove.getDestX() + "," + selectedMove.getDestY() 
        + Color.RESET);
        return selectedMove;
    }

    // Picks a random move to make from list of all available moves 1 turn out. Has no real intelligence.
    private Move randomSelect(Board board)
    {
        ArrayList<Move> allMoves = getAllPossibleMoves(board);
        int randomIndex = (int) (Math.random() * allMoves.size());
        Move selectedMove = allMoves.get(randomIndex);
        return selectedMove;
    }

    /*
     * Analyzes all possible moves 1 turn out AI can make and gives each move a higher/lower ranking
     * based on whether or not the move claims a piece and what type of piece that is.
     * Better pieces (queens/knights) have higher ranking values than common pieces (pawns).
     * All moves to an empty space are considered to be equal. 
     * If presented with multiple equally ranked moves, method will choose one of those at random
     */
    private Move simpleRankedSelect(Board board)
    {
        int bestRanking = 0;
        int defaultMovePoints = 0;
        ArrayList<Move> bestMoves = new ArrayList<Move>();

        for (Move curMove : getAllPossibleMoves(board))
        {
            GamePiece dest = curMove.getDest();

            int curMovePoints = dest != null ? pieceRankings.get(dest.getTitle()) : defaultMovePoints;
            int rank = defaultMovePoints + curMovePoints;

            if (rank < bestRanking) { continue; }
            if (rank > bestRanking) { bestMoves.clear(); }
            bestMoves.add(curMove);
            bestRanking = rank;
        }

        int randomIndex = (int) (Math.random() * bestMoves.size());
        return bestMoves.get(randomIndex);
    }

    private ArrayList<Move> getAllPossibleMoves(Board board)
    {
        ArrayList<Move> allMoves = new ArrayList<Move>();
        for (GamePiece piece : super.getAllPieces())
        {
            piece.updateValidMoves(board, allMoves);
        }
        return allMoves;
    }
}
