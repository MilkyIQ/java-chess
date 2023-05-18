import game.*;
import pieces.*;
import tools.*;
import java.util.ArrayList;

public class App
{
    public static void main(String[] args) throws Exception
    {
        System.out.println("\n");

        /* ------------------------------------------------------------------------------------ */

        // Initialize game variables
        Board board;
        SettingsReader reader     = new SettingsReader("src/game/settings/settings.json");
        ArrayList<Player> players = new ArrayList<Player>();

        // Initialize board variables
        int height           = reader.getIntValueOf("boardHeight", 0);
        int length           = reader.getIntValueOf("boardLength", 0);
        String evensColor    = reader.getStringValueOf("evenSpacesColor", 0);
        String oddsColor     = reader.getStringValueOf("oddSpacesColor", 0);
        String notationColor = reader.getStringValueOf("notationColor", 0);

        // Create all players and their pieces
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

            players.add(new Player(name, color));
            Player curPlayer = players.get(i-1);
            for (int[] p : pawns   ) { curPlayer.give(new   Pawn(color, p[0], p[1], direction)); }
            for (int[] p : rooks   ) { curPlayer.give(new   Rook(color, p[0], p[1])); }
            for (int[] p : knights ) { curPlayer.give(new Knight(color, p[0], p[1])); }
            for (int[] p : bishops ) { curPlayer.give(new Bishop(color, p[0], p[1])); }
            for (int[] p : queens  ) { curPlayer.give(new  Queen(color, p[0], p[1])); }
            for (int[] p : kings   ) { curPlayer.give(new   King(color, p[0], p[1])); }
        }

        // Create and populate board
        board = new Board(length, height);
        board.populateBoard(players);
        board.setColors(evensColor, oddsColor, notationColor);

        // Primary game loop
        int i = 0;
        int k = 0;
        while (players.size() > 1)
        {
            Player player = players.get(i);
            System.out.println("--------------- Turn #" + (k+1) + "---------------\n");
            board.printBoard();

            // Get move from player
            GamePiece piece = player.selectPiece(board);
            int[] move      = player.selectMove(board, piece);
            int[] from      = {piece.getCol(), piece.getRow()};
            if (move == null) { System.out.println(Color.PURPLE + "Undoing selection..." + Color.RESET); continue; } // undo case
            
            // Update board and player states
            GamePiece space = board.getSpace(move[0], move[1]);
            board.move(piece, move[0], move[1]);
            for (Player p : players) { p.updateState(board); }

            // Verify current move is not check
            String playerState = player.getState();
            if (playerState.equals("check") || playerState.equals("checkmate"))
            {
                System.out.println(Color.RED+ "Illegal Move: Cannot leave King in check!" + Color.RESET);
                board.undoMove(piece, from[0], from[1], space);
                continue;
            }

            // Update player list & player hands
            if (space != null) { players.get(Player.indexOf(space.getColor(), players)).remove(space); } // remove piece from enemy hand if attacking
            Player.removeLosers(players);

            i = i < players.size()-1 ? i+1 : 0;
            k++;
        }
        Player.scanner.close();

        String result = (players.size() == 1) ? (players.get(0).getName() + " Wins!") : ("Draw.");
        System.out.println(result);
    }
}