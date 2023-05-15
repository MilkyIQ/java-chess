package tools;
import java.util.ArrayList;
import game.Color;

public class ArrayUtils
{
    // Convert given 2D ArrayList to 2D Array
    public static int[][] convert2DArrayList(ArrayList<ArrayList<Integer>> list)
    {
        return list.stream().map(u->u.stream().mapToInt(i->i).toArray()).toArray(int[][]::new);
    }

    // Return an arraylist with two numbers x and y (used for creating points on board)
    public static ArrayList<Integer> createPoint(int x, int y)
    {
        ArrayList<Integer> point = new ArrayList<Integer>();
        point.add(x);
        point.add(y);
        return point;
    }

    public static int[] extractPointFromString(String input)
    {
        int[] output = new int[2];
        try
        {
            int xEnd   = input.indexOf(",");
            int yStart = input.indexOf(",")+1;
    
            String xString = input.substring(0, xEnd);
            String yString = input.substring(yStart);
    
            int x = Integer.parseInt(xString);
            int y = Integer.parseInt(yString);
            output[0] = x;
            output[1] = y;
        }
        catch (NumberFormatException|StringIndexOutOfBoundsException e)
        {
            System.out.println(Color.RED + "`" + input  + "`" + " is not a coordinate (x,y)" + Color.RESET);
            output = null;
        }
        return output;
    }
}
