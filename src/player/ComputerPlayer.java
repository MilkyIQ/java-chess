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
        switch (difficultyLevel)
        {
            case 0: 
                return randomSelect(board);
            default:
                return null;
        }
    }

    private Move randomSelect(Board board)
    {
        ArrayList<Move> allMoves = new ArrayList<Move>();
        for (GamePiece piece : super.getAllPieces())
        {
            piece.updateValidMoves(board, allMoves);
        }
        int randomIndex = (int) Math.random() * allMoves.size();
        Move selectedMove = allMoves.get(randomIndex);
        return selectedMove;
    }
}
