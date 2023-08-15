package pieces;
import game.Board;
import game.Move;
import player.Player;
import java.util.ArrayList;

public class Queen extends GamePiece
{
    public Queen(String color)
    {
        super("Queen", "\u265B", color);
    }

    @Override
    public ArrayList<Move> getValidMoves(Board board)
    {
        ArrayList<Move> moves = new ArrayList<Move>();
        int[] queenPosition = super.searchPos(board);
        int queenX = queenPosition[0];
        int queenY = queenPosition[1];
        String queenColor = super.getColor();
        ArrayList<Move> validQueenMoves = new ArrayList<Move>();
        /*
         * TODO:
         * This causes an error because its trying to search for the positions of
         * two pieces on a board that does not exist. Need to update getAllValidMoves()
         * methods to take x and y values and just deal with the positions outside of the
         * function call.
         */
        new Rook(queenColor).getValidMoves(board);
        new Bishop(queenColor).getValidMoves(board);

        // Have to recreate new move objects otherwise queen will typecast to a Rook or Bishop
        for (Move move : validQueenMoves)
        {
            moves.add(new Move(board, this, move.getDestX(), move.getDestY()));
        }
        
        return moves;
    }
}
