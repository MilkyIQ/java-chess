import game.Game;
import java.util.Scanner;

public class App
{
    public static void main(String[] args) throws Exception
    {
        Scanner scanner = new Scanner(System.in);
        Game game = new Game("src/game/settings/settings.json", scanner);

        game.begin();
        game.printResults();
        game.close();
    }
}