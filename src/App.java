import game.Board;
import game.Color;
import pieces.*;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        Board board = new Board(8, 8);

        GamePiece a = new Pawn(Color.GREEN, 0, 0);
        GamePiece b = new Pawn(Color.GREEN, 0, 1);
        GamePiece c = new Pawn(Color.GREEN, 0, 2);
        GamePiece d = new Pawn(Color.RED, 3, 3);

        board.place(a);
        board.place(b);
        board.place(c);
        board.place(d);

        board.printBoard();

        System.out.println("\n");
        
    }
}
