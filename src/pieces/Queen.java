package pieces;
import game.Board;
import game.Move;
import player.Player;
import java.util.ArrayList;

public class Queen extends GamePiece
{
    public Queen(Player owner, int col, int row)
    {
        super("Queen", "\u265B", owner, col, row);
    }

    @Override
    public ArrayList<Move> getValidMoves(Board board)
    {
        ArrayList<Move> moves = new ArrayList<Move>();
        int queenX = super.getCol();
        int queenY = super.getRow();
        Player queenOwner = super.getOwner();
        ArrayList<Move> validQueenMoves = new ArrayList<Move>();
        new Rook(queenOwner, queenX, queenY).getValidMoves(board);
        new Bishop(queenOwner, queenX, queenY).getValidMoves(board);

        // Have to recreate new move objects otherwise queen will typecast to a Rook or Bishop
        for (Move move : validQueenMoves)
        {
            moves.add(new Move(board, this, move.getDestX(), move.getDestY()));
        }
        
        return moves;
    }
}
