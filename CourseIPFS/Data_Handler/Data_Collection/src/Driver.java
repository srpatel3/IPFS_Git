//import org.json.*;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

//import org.json.JSONArray;
//import org.json.JSONObject;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Driver 
{	
	
	public static void main(String[] args) throws Exception {
		// parsing file "JSONExample.json"
//		Object obj = new JSONParser().parse(new FileReader("/home/shirish/Data_Files/IPFS_Data/DB.json"));
		Object obj = new JSONParser().parse(new FileReader("C:\\Users\\shiri\\git\\IPFS_Git\\CourseIPFS\\DB.json"));
		
		// typecasting obj to JSONObject
		JSONObject jo = (JSONObject) obj;
//		System.out.println(jo.toString());
		JSONObject dimention = (JSONObject) jo.get("dimention");
		JSONObject Blocks = (JSONObject) jo.get("Blocks");
//		int Rows = Integer.parseInt(dimention.get("No_Of_Rows").toString());
//		int Cols = Integer.parseInt(dimention.get("No_Of_Columns").toString());
		int Rows = 4;
		int Cols = 4;
		int total_Blocks = Rows * Cols;
		int block_To_Get = 13;
		getNeighbours(Rows, Cols, block_To_Get);
		
//		System.out.println(Rows);
//		System.out.println(Blocks.toJSONString());
//		System.out.println(dimention.toJSONString());
		
//		System.out.println(jo.get("Dimention"));

	}
	
//	Returns the neighbour blocks for data retrieval
	public static void getNeighbours(int Rows, int Cols, int block_To_Get) {
//		List<Integer> list_Blocks = new ArrayList<Integer>();
		Set< Integer> list_Blocks = new HashSet<Integer>();
		int Count = 0;
		for(int i = 0; i< Cols; i++) {
			for (int j = 0; j< Rows; j++) {
				System.out.print(Count+"		");
				Count++;
			}
			System.out.println();
		}
		
		System.out.println();
		
//		Checks for the first and last column of Data Blocks
		if(isOnTheFrontEdge(Rows, Cols, block_To_Get)) {
//			System.out.println("Block is at the front edge");
			list_Blocks.add(block_To_Get+1);
			int next_Block_1 = block_To_Get + Cols + 1;
			int next_Block_2 = block_To_Get - Cols + 1;
		}
		else if(isOnTheEndEdge(Rows, Cols, block_To_Get)) {
//			System.out.println("Block is at the back edge");
			list_Blocks.add(block_To_Get-1);
		}
		else {
//			System.out.println("Block is Somewhere in middle");
			list_Blocks.add(block_To_Get+1);
			list_Blocks.add(block_To_Get-1);
		}
		
//		Checks if block is in First or last row
		if(isOnTheFirstRow(Rows, Cols, block_To_Get)) {
//			System.out.println("Block is on the First row");
			list_Blocks.add(block_To_Get+Cols);
		}
		else if(isOnTheLastRow(Rows, Cols, block_To_Get)) {
//			System.out.println("Block is on the last row");
			list_Blocks.add(block_To_Get-Cols);
		}
		
		System.out.println("Neighbour for block " + block_To_Get+" is:");
		for (int i : list_Blocks) {
			System.out.print(i+",");
		}
		
	}
	
	public static Boolean isOnTheFrontEdge(int Rows, int Cols, int block_To_Get) {
		if (block_To_Get%(Cols) == 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static Boolean isOnTheEndEdge(int Rows, int Cols, int block_To_Get) {
		if((block_To_Get+1)%(Cols) == 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static Boolean isOnTheFirstRow(int Rows, int Cols, int block_To_Get) {
		if((block_To_Get - Cols) < 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static Boolean isOnTheLastRow(int Rows, int Cols, int block_To_Get) {
		if((block_To_Get + Cols) > Rows*Cols-1) {
			return true;
		}
		else {
			return false;
		}
	}

}

