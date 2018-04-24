//import org.json.*;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Map;

//import org.json.JSONArray;
//import org.json.JSONObject;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Driver 
{
	public static void main(String[] args) throws Exception 
	{
		// parsing file "JSONExample.json"
		Object obj = new JSONParser().parse(new FileReader("/home/shirish/Data_Files/IPFS_Data/DB.json"));
		
		// typecasting obj to JSONObject
		JSONObject jo = (JSONObject) obj;
		System.out.println(jo.toString());
		JSONObject dimention = (JSONObject) jo.get("Dimention");
		JSONObject Blocks = (JSONObject) jo.get("Blocks");
		System.out.println(Blocks.toJSONString());
		System.out.println(dimention.toJSONString());
		
//		System.out.println(jo.get("Dimention"));

	}
}
