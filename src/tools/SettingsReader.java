package tools;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.*;
import java.io.FileReader;

public class SettingsReader
{
    private final JSONArray FILE;

    // I am aware that throwing exception really bad practice but I cannot give more than 0 shits at this point
    public SettingsReader(String file) throws Exception
    {
        this.FILE = (JSONArray) new JSONParser().parse(new FileReader(file));
    }
    
    // Return total number of players
    public int getNumPlayers()
    {
        return FILE.size()-1;
    }

    // Returns int value of specified variable from given index in settings.json
    public int getIntValueOf(String datapoint)
    {
        JSONObject item = (JSONObject) FILE.get(FILE.size()-1);
        Long value = (Long) item.get(datapoint);
        return (int) value.longValue();
    }

    // Returns String value of specified variable from given index in settings.json (index starts from 1)
    public String getPlayerValueOf(String datapoint, int index)
    {
        JSONObject item = (JSONObject) FILE.get(index);
        return (String) item.get(datapoint);
    }

    // Return 2D array of coordinates of specified piece type from given player in settings.json
    public int[][] getPlayerPieces(String piece, int index)
    {
        JSONObject player      = (JSONObject) FILE.get(index);
        JSONArray  pieces      = (JSONArray) player.get(piece);
        int[][]    piecesArray = new int[pieces.size()][2];

        for (int i = 0; i < piecesArray.length; i++)
        {
            // this is utterly fucking retarded, but java has forced my hand
            JSONArray point = (JSONArray) pieces.get(i);
            Long xObject = (Long) point.toArray()[0];
            Long yObject = (Long) point.toArray()[1];
            piecesArray[i][0] = (int) xObject.longValue();
            piecesArray[i][1] = (int) yObject.longValue();
        }

        return piecesArray;
    }
}
