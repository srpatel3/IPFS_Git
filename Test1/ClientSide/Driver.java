import java.util.*;
import java.io.*;
import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 
import org.json.simple.parser.*;
 
class Driver{
	public static void main(String[] args){
		String dagHash ="zdpuAtyxwhcaRBVqqEKAXzjpXXfvQ4epCRmvRykkdu1TnsLuG";
		System.out.println("Hello World");
		try{
			Process process = Runtime.getRuntime().exec("ipfs dag get "+dagHash);
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String output = "";
			String line;
			while((line = reader.readLine())!=null){
				output += line;
				System.out.println(line);
			}
			JSONParser parser = new JSONParser(); 
			JSONObject json = (JSONObject) parser.parse(output);
			JSONArray array = (JSONArray) json.get("blockList");
			Iterator itr = array.iterator();
			while (itr.hasNext()){
				JSONObject tempJSON = (JSONObject)itr.next();
				System.out.println();
				// System.out.println(tempJSON.get("hash"));
				Map address = (Map)tempJSON.get("1"); 
				System.out.println(address.get("hash"));
        			// iterating address Map 
        			Iterator<Map.Entry> itr1 = address.entrySet().iterator();
				while (itr1.hasNext()) { 
            				Map.Entry pair = itr1.next();
            				System.out.println(pair.getKey() + " : " + pair.getValue()); 
				}
			}
		}
		catch(Exception e){
			System.out.println(e);
		}

	}

}
