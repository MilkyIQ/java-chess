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
    public boolean checkMove(int x, int y, Board board)
    {
        int queenX = super.getCol();
        int queenY = super.getRow();
        Player queenOwner = super.getOwner();
        Rook ghostRook = new Rook(queenOwner, queenX, queenY);
        Bishop ghostBishop = new Bishop(queenOwner, queenX, queenY);

        return ghostRook.checkMove(x, y, board) || ghostBishop.checkMove(x, y, board);
    }

    @Override
    public void updateValidMoves(Board board, ArrayList<Move> moves)
    {
        int queenX = super.getCol();
        int queenY = super.getRow();
        Player queenOwner = super.getOwner();
        ArrayList<Move> validQueenMoves = new ArrayList<Move>();
        new Rook(queenOwner, queenX, queenY).updateValidMoves(board, validQueenMoves);
        new Bishop(queenOwner, queenX, queenY).updateValidMoves(board, validQueenMoves);

        // Have to recreate new move objects otherwise queen will typecast to a Rook or Bishop
        for (Move move : validQueenMoves)
        {
            moves.add(new Move(board, this, move.getDestX(), move.getDestY()));
        }
    }
}
