package pieces;
import game.*;

public class Queen extends GamePiece
{
    public Queen(String color, int col, int row)
    {
        super("\u265B", color, col, row);
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
}
