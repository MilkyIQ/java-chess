package player;
import pieces.GamePiece;
import java.util.ArrayList;
import game.Move;
import game.Board;

public class ComputerPlayer extends Player
{
    private int difficultyLevel;

    public ComputerPlayer(String name, String color, int difficultyLevel)
    {
        super(name, color);
        this.difficultyLevel = difficultyLevel;
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
        }

        System.out.println("Move chosen!");
        return selectedMove;
    }

    private Move randomSelect(Board board)
    {
        ArrayList<Move> allMoves = getAllPossibleMoves(board);
        int randomIndex = (int) Math.random() * allMoves.size();
        Move selectedMove = allMoves.get(randomIndex);
        return selectedMove;
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
