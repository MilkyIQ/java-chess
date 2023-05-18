package game;
import pieces.*;
import java.util.ArrayList;

public class ComputerPlayer extends Player
{
    public ComputerPlayer(String color)
    {
        super("AIPlayer", color);
    }
    
    @Override
    public GamePiece selectPiece(Board board)
    {
        ArrayList<GamePiece> allPieces = super.getAllPieces();
        GamePiece selectedPiece = allPieces.get(pickRandomIndex(allPieces.size()));
        return selectedPiece;
    }

    @Override
    public int[] selectMove(Board board, GamePiece piece)
    {
        ArrayList<GamePiece> moves = new ArrayList<GamePiece>();
        piece.updateValidMoves(board, moves);
        if (moves.size() == 0) { return null; }
        GamePiece randMove = moves.get(pickRandomIndex(moves.size()));
        int[] point = {randMove.getCol(), randMove.getRow()};
        return point;
    }

    private static int pickRandomIndex(int size)
    {
        return (int) (Math.random() * size);
    }
}
