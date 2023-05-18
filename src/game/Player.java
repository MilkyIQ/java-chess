package game;
import pieces.*;
import tools.Color;
import tools.ArrayUtils;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    public static Scanner scanner = new Scanner(System.in);

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

    /* ------------------------------------------------------------------------------------ */
    // GETTER METHODS
    /* ------------------------------------------------------------------------------------ */

    public String getColor()
    {
        return COLOR;
    }

    public String getColorCode()
    {
        int underscoreIndex = COLOR.indexOf("_");
        String baseColor = underscoreIndex > -1 ? COLOR.substring(0,underscoreIndex) : COLOR;
        return Color.getColorCodeOf(baseColor + "_bold");
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

    /* ------------------------------------------------------------------------------------ */
    // SETTER METHODS
    /* ------------------------------------------------------------------------------------ */

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

    // Ask user for the piece they'd like to move and return that piece
    public GamePiece selectPiece(Board board)
    {
        System.out.print(this.getColorCode() + "[" + this.getName() + "] " + Color.RESET);
        System.out.print("Choose a piece to move (x,y): ");
        
        // Get piece from user input
        GamePiece piece = null;
        String systemResponse = Color.RED;
        int[] point = ArrayUtils.extractPointFromString(scanner.nextLine()); // TODO: figure out a way to not pass scanner through
        
        // Error checking
        int returnCode = point != null ? board.checkSpace(point[0], point[1], this.getColor()) : -2;
        switch (returnCode)
        {
            default: systemResponse += "Something went wrong."; break;
            case -2: systemResponse += "Invalid input. Please try again."; break;
            case -1: systemResponse += "Out of bounds! Please try again."; break;
            case 0:  systemResponse += "Space is empty. Please try again."; break;
            case 2:  systemResponse += "Cannot move enemy piece. Please try again."; break;
            case 1:
                piece = board.getSpace(point[0], point[1]);
                systemResponse = Color.PURPLE + "You have chosen " + piece.toFormattedPositon();
                break;
        }
        
        System.out.println(systemResponse + Color.RESET);
        return piece == null ? this.selectPiece(board) : piece;
    }

    // Ask user for the move they want to make and return
    public int[] selectMove(Board board, GamePiece piece)
    {
        System.out.print("Choose a space to move to (x,y): ");
        String systemResponse = Color.RED;
        int[] move = null;
        
        // Get move from user input
        String userInput = scanner.nextLine().toLowerCase();
        int[] point = ArrayUtils.extractPointFromString(userInput);

        // Undo case
        if (userInput.equals("back") || userInput.equals("undo")) { return null; }
        
        // Error checking
        if (point == null)
        {
            systemResponse += "Invalid input. Please try again.";
        }
        else if (piece.checkMove(point[0], point[1], board))
        {
            move = point;
            systemResponse = Color.PURPLE;
            systemResponse += "Moving " + piece.toFormattedPositon() + " to " + "(" + point[0] + "," + point[1] + ")\n";
        }
        else
        {
            switch (board.checkSpace(point[0], point[1], piece.getColor()))
            {
                case -1: systemResponse += "Out of bounds! Please try again."; break;
                case 0:  systemResponse += "Invalid move. Please try again."; break;
                case 1:  systemResponse += "Cannot attack your own piece. Please try again."; break;
                case 2:  systemResponse += "Invalid attack. Please try again."; break;
            }
        }
        
        System.out.println(systemResponse + Color.RESET);
        return move == null ? this.selectMove(board, piece) : move;
    }

    // Analyzes board and determines whether the player is in check, checkmate, stalemate, or safe
    public void updateState(Board board)
    {
        // Breaks function if king is safe
        if (!this.isInCheck(board))
        {
            this.setState("safe");
            return;
        }

        // Loop through all pieces
        for (GamePiece piece : this.getAllPieces())
        {
            int startX = piece.getCol();
            int startY = piece.getRow();
            ArrayList<GamePiece> currentPieceMoves = new ArrayList<GamePiece>();
            piece.updateValidMoves(board, currentPieceMoves);

            // Check every valid move of current piece until move saves king or list exhausted
            for (GamePiece move : currentPieceMoves)
            {
                int moveX = move.getCol();
                int moveY = move.getRow();
                GamePiece space = board.getSpace(moveX, moveY);

                // Simulate move & calculate player state
                board.move(piece, move.getCol(), move.getRow());
                boolean resultsInCheck = this.isInCheck(board);
                board.undoMove(piece, startX, startY, space);

                // if king is not safe, continue, else, set state and end function
                if (resultsInCheck) { continue; }
                this.setState("check");
                return;
            }
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
                Pawn dummyPawn = new Pawn("",0,0,"up");
                if (space == null || space.getColor().equals(COLOR) || space.getClass() == dummyPawn.getClass()) { continue; }
                space.updateValidMoves(board, moves);
            }
        }

        // Place points on ghostBoard
        for (GamePiece piece : moves) { ghostBoard.place(piece); }
        
        // Place king on board and return status
        return ghostBoard.getSpace(KINGX, KINGY) != null;
    }

    /* ------------------------------------------------------------------------------------ */
    // STATIC FUNCTIONS
    /* ------------------------------------------------------------------------------------ */

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
