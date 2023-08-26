package pieces;
import game.Board;
import game.Move;
import java.util.ArrayList;

public class Queen extends GamePiece
{
    public Queen(String color)
    {
        super("\u265B", color);
    }

    @Override
    public ArrayList<Move> getValidMoves(Board board, int x, int y)
    {
        ArrayList<Move> moves = new ArrayList<Move>();
        String queenColor = super.getColor();
        ArrayList<Move> validQueenMoves = new ArrayList<Move>();
        new Rook(queenColor).getValidMoves(board, x, y);
        new Bishop(queenColor).getValidMoves(board, x, y);

        // Have to recreate new move objects otherwise queen will typecast to a Rook or Bishop
        for (Move move : validQueenMoves)
        {
            moves.add(new Move(board, this, x, y, move.getDestX(), move.getDestY()));
        }
        
        return moves;
    }
}
