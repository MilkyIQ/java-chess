package pieces;
import game.Board;

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
    public int[][] getAllValidMoves(Board board)
    {
        int i = 0;
        int queenX = super.getCol();
        int queenY = super.getRow();
        String queenColor = super.getColor();
        int[][] rookMoves   = new Rook(queenColor, queenX, queenY).getAllValidMoves(board);
        int[][] bishopMoves = new Bishop(queenColor, queenX, queenY).getAllValidMoves(board);
        int[][] allMoves    = new int[rookMoves.length + bishopMoves.length][2];
        for (int[] move : rookMoves)   { allMoves[i] = move; i++; }
        for (int[] move : bishopMoves) { allMoves[i] = move; i++; }
        return allMoves;
    }
}
