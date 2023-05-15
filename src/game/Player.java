package game;
import java.util.ArrayList;
import java.util.HashMap;
import pieces.*;

public class Player {
    private HashMap<String,ArrayList<GamePiece>> hand;
    private final String NAME;
    private final String COLOR;
    private String state;

    public Player(String name, String color)
    {
        this.NAME = name;
        this.COLOR = color;
        this.hand = new HashMap<String,ArrayList<GamePiece>>();
        this.state = "open";

        String[] titles = {"King", "Queen", "Rook", "Bishop", "Knight", "Pawn"};
        for (String t : titles) { hand.put(t, new ArrayList<GamePiece>()); };
    }

    public String getColor()
    {
        return COLOR;
    }

    public String getName()
    {
        return NAME;
    }

    public String getState()
    {
        return state;
    }

    public HashMap<String,ArrayList<GamePiece>> getHand()
    {
        return hand;
    }

    public ArrayList<GamePiece> getAllPiecesOfType(String title)
    {
        return hand.get(title);
    }

    public ArrayList<GamePiece> getAllPieces()
    {
        ArrayList<GamePiece> pieces = new ArrayList<GamePiece>();
        for (ArrayList<GamePiece> type : hand.values())
        {
            for (GamePiece piece : type)
            {
                pieces.add(piece);
            }
        }
        
        return pieces;
    }

    public void setState(String newState)
    {
        state = newState;
    }

    public void give(GamePiece piece)
    {
        hand.get(piece.getTitle()).add(piece);
    }

    public void remove(GamePiece piece)
    {
        hand.get(piece.getTitle()).remove(piece);
    }

    // Analyzes board and determines whether the player is in check, checkmate, stalemate, or safe
    public void updateState(Board board)
    {
        /*
         * STEPS:
         * 1. [x] Iterate through the gameboard and create a list of all moves that every enemy piece can make
         * 2. [x] Create a ghost board from the validMoves
         * 3. [x] Check king's position on ghost board
         * 4. [x] If king not touching any points, return safe, else, continue.
         * 5. [x] Iterate through friendly pieces
         * * -> [x] Create a list of all moves that the current piece can make
         * * -> [x] Iterate through those moves and check if each move keeps the king in check.
         * * -> [ ] If a move leaves the king safe, break and return check, else continue until moves exhausted
         * 6. If reach end of function (no moves leave king safe), return checkmate.
         */

        // Breaks function if king is safe
        if (!this.isInCheck(board))
        {
            this.setState("safe");
            return;
        }

        // Loop through all friendly pieces
        for (GamePiece piece : this.getAllPieces())
        {
            int startX = piece.getCol();
            int startY = piece.getRow();
            ArrayList<GamePiece> currentPieceMoves = new ArrayList<GamePiece>();
            piece.updateValidMoves(board, currentPieceMoves);

            // Check every move current piece until move saves king or list exhausted
            for (GamePiece move : currentPieceMoves)
            {
                int moveX = move.getCol();
                int moveY = move.getRow();
                GamePiece space = board.getSpace(moveX, moveY);
                board.move(piece, move.getCol(), move.getRow());
                if (this.isInCheck(board)) { continue; }

                // if king-saving move found, set state to check and end function
                this.setState("check");
                board.undoMove(piece, startX, startY, space);
                return;
            }

            /*
             * TODO (bug fix):
             * * For some reason, the board is not undoing the simulated moves of the knight correctly
             * * and i do not know why. It may have something to do with the fact that the knight moves very irregularly
             * * or the fact that we're not undoing the move after every single iteration of the moveset.
             * * (That's done that way for indent reasons but that may have to be ignored)
             * 
             * USE THE FOLLOWING MOVEMENTS FOR TESTING:
             * * board.move(board.getSpace(4,0), 4,3);
             * * board.move(board.getSpace(3,6), 3,5);
             */
            
            // Undo move so next piece can be tested
            System.out.println(piece.getTitle());
            board.undoMove(piece, startX, startY, board.getSpace(piece.getCol(), piece.getRow()));
        }

        // If all move lists exhausted, players state is checkmate
        this.setState("checkmate");
    }

    // Calcualtes all possible movements from all pieces on board (excluding pawns), and continue 
    public boolean isInCheck(Board board)
    {
        // Initialize main variables
        ArrayList<GamePiece> moves = new ArrayList<GamePiece>();
        final int LENGTH = board.getLength();
        final int HEIGHT = board.getLength();
        Board ghostBoard = new Board(LENGTH, HEIGHT);
        GamePiece king   = hand.get("King").get(0);
        final int KINGX  = king.getCol();
        final int KINGY  = king.getRow();
        
        // Check corners for pawns
        int[] xInc = {1, -1}, yInc = {1, -1};
        for (int x : xInc)
        {
            for (int y : yInc)
            {
                if (board.coordinateOutOfBounds(KINGX+x, KINGY+y)) { continue; };
                GamePiece potentialPawn = board.getSpace(KINGX+x, KINGY+y);
                Boolean pawnIsAttacking = potentialPawn != null && potentialPawn.getTitle().equals("Pawn") && potentialPawn.checkMove(KINGX, KINGY, board);
                if (pawnIsAttacking) { return true; }
            }
        }
        
        // Iterate through board and populate ghostBoard with validMoves
        for (int col = 0; col < LENGTH; col++)
        {
            for (int row = 0; row < HEIGHT; row++)
            {
                GamePiece space = board.getSpace(col, row);
                if (space == null || space.getColor().equals(COLOR)) { continue; }
                space.updateValidMoves(board, moves);
            }
        }

        // Place points on ghostBoard
        for (GamePiece piece : moves)
        {
            if (!board.coordinateOutOfBounds(piece.getCol(), piece.getRow()))
            {
                ghostBoard.place(piece);
            }
            
        }
        
        // Print ghostBoard for testing (REMOVE BEFORE MERGE)
        ghostBoard.setColors(Color.BLACK, Color.BLACK, Color.BLACK);
        System.out.println(NAME + "'s Board:");
        ghostBoard.printBoard();
        
        // Place king on board and return status
        return ghostBoard.getSpace(KINGX, KINGY) != null;
    }

    // Loop through a list of players and return the index of the specified color
    public static int indexOf(String color, ArrayList<Player> players)
    {
        for (int i = 0; i < players.size(); i++)
        {
            if (players.get(i).getColor().equals(color))
            {
                return i;
            }
        }
        return -1;
    }

    // Loop through all players' game states and removes those who are in checkmate or stalemate
    public static void removeLosers(ArrayList<Player> players)
    {
        for (int i = 0; i < players.size(); i++)
        {
            String state = players.get(i).getState();
            if (state.equals("checkmate") || state.equals("stalemate"))
            {
                players.remove(i);
                i--;
            }
        }
    }
}
