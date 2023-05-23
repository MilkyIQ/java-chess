package game;
import java.util.ArrayList;
import java.util.Scanner;
import tools.SettingsReader;
import tools.Color;
import pieces.*;
import player.*;

public class Game
{
    private int turn;
    private Scanner scanner;
    private Board board;
    private ArrayList<Player> alivePlayers;

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
        this.scanner = heapScanner;
        this.board = new Board(length, height);
        this.alivePlayers = new ArrayList<Player>();

        /*
         * Index starts at 1 because settings file is a JSON list that contains objects.
         * The first object in the list is always the overall game/board settings.
         * This will eventually be deprecated in favor of a less eye bleaching structure.
         */
        for (int i = 1; i <= reader.getNumPlayers(); i++)
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
            for (int[] p : pawns   ) { cur.give(new   Pawn(color, p[0], p[1], direction)); }
            for (int[] p : rooks   ) { cur.give(new   Rook(color, p[0], p[1])); }
            for (int[] p : knights ) { cur.give(new Knight(color, p[0], p[1])); }
            for (int[] p : bishops ) { cur.give(new Bishop(color, p[0], p[1])); }
            for (int[] p : queens  ) { cur.give(new  Queen(color, p[0], p[1])); }
            for (int[] p : kings   ) { cur.give(new   King(color, p[0], p[1])); }
        }
        
        board.populate(alivePlayers);
        board.setColors(evensColor, oddsColor, notationColor);
    }

    public void begin()
    {
        int i = 0;
        while (alivePlayers.size() > 1)
        {
            Player player = alivePlayers.get(i);
            System.out.println("--------------- Turn #" + (turn+1) + "---------------\n");
            board.printBoard();

            // Get move from player & update
            Move move = player.selectMove(board);
            board.move(move);
            for (Player p : alivePlayers) { p.updateState(board); }

            // Verify current move is not check
            String playerState = player.getState();
            if (playerState.equals("check") || playerState.equals("checkmate"))
            {
                System.out.println(Color.RED+ "Illegal Move: Cannot leave King in check!" + Color.RESET);
                board.undoMove(move);
                continue;
            }

            // Update player list & player hands
            GamePiece space = move.getDest();
            if (space != null) { alivePlayers.get(Player.indexOf(space.getColor(), alivePlayers)).remove(space); } // remove piece from enemy hand if attacking
            removeLosers();

            i = i < alivePlayers.size()-1 ? i+1 : 0;
            turn++;
        }
    }

    public void close()
    { 
        scanner.close();
    }

    public void printResults()
    {
        String result = (alivePlayers.size() == 1) ? (alivePlayers.get(0).getName() + " Wins!") : ("Draw.");
        System.out.println(result);
    }

    // Loop through all players' game states and removes those who are in checkmate or stalemate
    private void removeLosers()
    {
        for (int i = 0; i < alivePlayers.size(); i++)
        {
            String state = alivePlayers.get(i).getState();
            if (state.equals("checkmate") || state.equals("stalemate"))
            {
                alivePlayers.remove(i);
                i--;
            }
        }
    }
}
