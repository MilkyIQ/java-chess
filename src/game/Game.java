package game;
import java.util.ArrayList;
import java.util.Scanner;
import tools.SettingsReader;
import pieces.*;
import player.*;

public class Game
{
    private final int NO_CAPTURE_LIMIT = 75;
    private int turn;
    private String gameCondition;
    private Board board;
    private Scanner scanner;
    private ArrayList<Player> alivePlayers, stalePlayers;
    private ArrayList<Move> eventHistory;

    public Game(String settingsFile, Scanner heapScanner) throws Exception
    {
        // "src/game/settings/settings.json"
        SettingsReader reader = new SettingsReader(settingsFile);

        int height           = reader.getIntValueOf("boardHeight", 0);
        int length           = reader.getIntValueOf("boardLength", 0);
        String evensColor    = reader.getStringValueOf("evenSpacesColor", 0);
        String oddsColor     = reader.getStringValueOf("oddSpacesColor", 0);
        String notationColor = reader.getStringValueOf("notationColor", 0);

        this.turn = 0;
        this.gameCondition = "in_progress";
        this.scanner = heapScanner;
        this.board = new Board(length, height);
        this.alivePlayers = new ArrayList<Player>();
        this.stalePlayers = new ArrayList<Player>();
        this.eventHistory = new ArrayList<Move>();

        for (int i = 1; i <= reader.getNumPlayers(); i++) // Index starts at 1 because settings file is a JSON list that contains objects, first object is for game settings
        {
            String color     = reader.getStringValueOf("color", i);
            String name      = reader.getStringValueOf("name", i);
            String direction = reader.getStringValueOf("pawnDirection", i);
            int[][] pawns    = reader.getPlayerPieces("pawn", i);
            int[][] rooks    = reader.getPlayerPieces("rook", i);
            int[][] knights  = reader.getPlayerPieces("knight", i);
            int[][] bishops  = reader.getPlayerPieces("bishop", i);
            int[][] queens   = reader.getPlayerPieces("queen", i);
            int[][] kings    = reader.getPlayerPieces("king", i);

            // Should we necessarily be relying on a try-catch statement for default values? This seems inefficient
            try 
            {
                int difficulty = reader.getIntValueOf("difficultyLevel", i);
                alivePlayers.add(new ComputerPlayer(name, color, difficulty));
            }
            catch (NullPointerException e)
            {
                alivePlayers.add(new HumanPlayer(name, color, scanner));
            }

            Player cur = alivePlayers.get(i-1);
            for (int[] p : pawns   ) { cur.give(new   Pawn(cur, p[0], p[1], direction)); }
            for (int[] p : rooks   ) { cur.give(new   Rook(cur, p[0], p[1])); }
            for (int[] p : knights ) { cur.give(new Knight(cur, p[0], p[1])); }
            for (int[] p : bishops ) { cur.give(new Bishop(cur, p[0], p[1])); }
            for (int[] p : queens  ) { cur.give(new  Queen(cur, p[0], p[1])); }
            for (int[] p : kings   ) { cur.give(new   King(cur, p[0], p[1])); }
        }
        
        board.populate(alivePlayers);
        board.setColors(evensColor, oddsColor, notationColor);
    }

    public void begin()
    {
        int i = 0;
        while (gameCondition.equals("in_progress"))
        {
            Player player = alivePlayers.get(i);
            System.out.println("--------------- Turn #" + (turn+1) + "---------------\n");
            board.printBoard();
            
            // Get move from player & update
            Move move = player.selectMove(board);
            board.move(move);
            for (Player p : alivePlayers)
            {
                String state = p.calculateState(board);
                if (state.equals("check")) { System.out.println(p.getName() + " is in check!"); }
                p.updateState(state);
            }

            if (player.getState().equals("check"))
            {
                throw new IllegalStateException("Player has been left in check at end of turn");
            }

            eventHistory.add(move);
            updateGameCondition();
            i = i < alivePlayers.size()-1 ? i+1 : 0;
            turn++;
        }
    }

    public void close()
    { 
        scanner.close();
        if (gameCondition != null) { return; } // skip calculations if everything has already been figured out
    }

    public void printResults()
    {
        String result = "";
        System.out.print("GAME END: ");
        switch (gameCondition)
        {
            case "zero_sum_win": result += alivePlayers.get(0).getName() + " Wins!"; break;
            case "stalemate":    result += "Stalemate."; break;
            case "no_winners":   result += "Draw by impossibility of checkmate"; break; // TODO: needs implementation
            case "75_rule":      result += "Draw by Seventy-Five-Move-Rule"; break;
            case "50_rule":      result += "Draw by Fifty-Move-Rule"; break; // TODO: needs implementation
            case "3_repeat":     result += "Draw by Threefold-Repetition"; break; // TODO: needs implementation
            case "5_repeat":     result += "Draw by Fivefold-Repetition"; break; // TODO: needs implementation
            case "mutual_draw":  result += "Draw by mutual agreement"; break; // TODO: needs implementation
        }
        System.out.println(result);
    }

    // Loop through all players' game states and removes those who are in checkmate or stalemate
    private void updateGameCondition()
    {
        // Remove losers
        for (int i = 0; i < alivePlayers.size(); i++)
        {
            String state = alivePlayers.get(i).getState();
            if (state.equals("checkmate"))
            {
                alivePlayers.remove(i);
                i--;
            }
            else if (state.equals("stalemate"))
            {
                stalePlayers.add(alivePlayers.get(i)); 
                alivePlayers.remove(i);
                i--;
            }
        }

        // Determine whether the game is a draw
        int numAlive = alivePlayers.size();
        int numStale = stalePlayers.size();
        if (numAlive == 1 && numStale == 0)
        {
            gameCondition = "zero_sum_win";
        }
        else if (numAlive == 1 && numStale > 0)
        {
            gameCondition = "stalemate";
        }
        else
        {
            gameCondition = "draw";
        }

        // If game is a draw, determine which type
        if (!gameCondition.equals("draw")) { return; }
        if (historyIsRepetitive())
        {
            gameCondition = "75_rule";
        }
        else // if all draw sequences exhausted, then game is still in progress
        {
            gameCondition = "in_progress";
        }
    }

    // Check the past 75 moves, and if no pieces have been claimed in that time, then return true
    private boolean historyIsRepetitive()
    {
        if (turn < NO_CAPTURE_LIMIT) { return false; }
        for (int i = turn; i >= turn-NO_CAPTURE_LIMIT; i--)
        {
            Move event = eventHistory.get(i);
            if (event.getDestPiece() != null)
            {
               return false;
            }
        }
        return true;
    }
}
