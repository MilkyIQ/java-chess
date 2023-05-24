package pieces;
import game.Board;
import game.Move;
import java.util.ArrayList;

public class Queen extends GamePiece
{
    public Queen(String color, int col, int row)
    {
        super("Queen", "\u265B", color, col, row);
    }

    @Override
    public boolean checkMove(int x, int y, Board board)
    {
        int queenX = super.getCol();
        int queenY = super.getRow();
        String queenColor = super.getColor();
        GamePiece ghostRook = new Rook(queenColor, queenX, queenY);
        GamePiece ghostBishop = new Bishop(queenColor, queenX, queenY);

        return ghostRook.checkMove(x, y, board) || ghostBishop.checkMove(x, y, board);
    }

    @Override
    public void updateValidMoves(Board board, ArrayList<Move> moves)
    {
        int queenX = super.getCol();
        int queenY = super.getRow();
        String queenColor = super.getColor();
        ArrayList<Move> validQueenMoves = new ArrayList<Move>();
        new Rook(queenColor, queenX, queenY).updateValidMoves(board, validQueenMoves);
        new Bishop(queenColor, queenX, queenY).updateValidMoves(board, validQueenMoves);

        // Have to recreate new move objects otherwise queen will typecast to a Rook or Bishop
        for (Move move : validQueenMoves)
        {
            moves.add(new Move(board, this, move.getDestX(), move.getDestY()));
        }
    }
}
