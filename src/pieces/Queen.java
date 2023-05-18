package pieces;
import game.Board;
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
    public void updateValidMoves(Board board, ArrayList<Board.Move> moves)
    {
        int queenX = super.getCol();
        int queenY = super.getRow();
        String queenColor = super.getColor();
        new Rook(queenColor, queenX, queenY).updateValidMoves(board, moves);
        new Bishop(queenColor, queenX, queenY).updateValidMoves(board, moves);
    }
}
