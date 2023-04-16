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

    // TODO: this needs an error message
    public static int simpleIndexOfPointInArray(int[] point, int[][] array)
    {
        int i = 0;
        while (i < array.length)
        {
            if (point[0] == array[i][0] && point[1] == array[i][1]) { return i; }
            else { i++; }
        }
        System.out.println(Color.RED + point + " is not a valid move." + Color.RESET);
        return -1;
    }

    public static int[] extractPointFromString(String input)
    {
        int[] output = new int[2];
        try
        {
            int xStart = input.indexOf("(")+1;
            int xEnd   = input.indexOf(",");
            int yStart = input.indexOf(",")+1;
            int yEnd   = input.indexOf(")");
    
            String xString = input.substring(xStart, xEnd);
            String yString = input.substring(yStart, yEnd);
    
            int x = Integer.parseInt(xString);
            int y = Integer.parseInt(yString);
            output[0] = x-1;
            output[1] = y-1;
        }
        catch (NumberFormatException|StringIndexOutOfBoundsException e)
        {
            System.out.println(Color.RED + "`" + input  + "`" + " is not a valid space, please format your spaces in coordinate-point format (x,y)" + Color.RESET);
            output = null;
        }
        return output;
    }
}
