package tools;
import java.util.ArrayList;

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
        ArrayList<Integer> point = new ArrayList<Integer>(2);
        point.set(0, x);
        point.set(1, y);
        return point;
    }

    public static int simpleIndexOfPointInArray(int[] point, int[][] array)
    {
        int index = 0;
        while (index < array.length)
        {
            int[] arrayPoint = array[index];
            
            if (array.equals(arrayPoint)) { return index; }
            else { index++; }
        }
        return -1;
    }
}
