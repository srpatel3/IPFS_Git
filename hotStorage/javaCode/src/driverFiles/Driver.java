import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import java.util.Iterator;
import java.io.BufferedReader;
import java.io.FileReader;


public class Driver{

	// This arrayList could probably be replaced by another (faster) data structure such as
	// the span-space algorithm (http://web.cse.ohio-state.edu/~shen.94/papers/Shen1996.pdf)

  // To screen result
  public  static ArrayList<ScreeningNode> screeningArray = new ArrayList<>();
  // To Maintain hash to dag relationship
  public static HashMap<String, DataSlice> lookupTable = new HashMap<String, DataSlice>();
  // To store calculated result
  public static HashMap<String, Float> resultTable = new HashMap<String, Float>();

  public static void main(String[] Args) throws IOException{
    // System.out.println("In Main method");
    // System.out.println("Now Starting Thread");
    genDag();
    // Make sure to uncomment this lines as this will get us the list of dag
    // RemoteConnector RMC = new RemoteConnector();
    // Thread connector = new Thread(RMC);
    // connector.start();
    Scanner inFromKey = new Scanner(System.in);
    int input = 0;
    // genDag();
    while (input !=5){

      System.out.println("Enter Min : ");
      int fInput = inFromKey.nextInt();
      System.out.println("Enter Max : ");
      int sInput = inFromKey.nextInt();
      ISBound Bounds = new ISBound(fInput, sInput);
      Getter getterObject = new Getter();
      getterObject.getSubBlock(Bounds, new ISBound(1000,1000));
      System.out.println("What do you want to do?");
      input = inFromKey.nextInt();

    }
    System.exit(0);

  }



/** Retrieve a geometric subset of a data slice. Currently, we restrict the
 *  implementation to handle only bounds that align exactly with block boundaries.
	@param b the ISBounds specifying the subregion to retrieve data for.
	@returns the DataBlock containing the requested data.
*/

  public static void updateInformation(String dag, String hash) throws IOException{
    // System.out.println(dag);
    // We can now start processing dag here
    try{
      JSONObject rawDag = (JSONObject) new JSONParser().parse(dag);
      int smin = Integer.parseInt((String)rawDag.get("min"));
      int smax = Integer.parseInt((String)rawDag.get("max"));
      int snum = Integer.parseInt((String)rawDag.get("number"));
      // int smin = Integer.parseInt(rawDag.get("min"));
      DataSlice tempDataSliceObj = new DataSlice(smin, smax, snum, hash);
      updateDataSliceObject(rawDag, tempDataSliceObj);
      screeningArray.add(new ScreeningNode(smin, smax, hash));
      lookupTable.put(hash, tempDataSliceObj);
      // System.out.println(lookupTable.get(hash).toString());

    } catch (ParseException pe){
      pe.printStackTrace();
    }
  }

  public static void updateDataSliceObject(JSONObject rawDag, DataSlice tempDataSliceObj) throws IOException{
          JSONArray dataSectionArray = (JSONArray) rawDag.get("dataSectionList");
          Iterator sectionListItr = dataSectionArray.iterator();
          while(sectionListItr.hasNext()){
            JSONObject jo = (JSONObject) sectionListItr.next();
            int sectionMin = Integer.parseInt((String)jo.get("min"));
            int sectionMax = Integer.parseInt((String)jo.get("max"));
            int sectionNumber = Integer.parseInt((String)jo.get("number"));
            String sectionPath = tempDataSliceObj.getHash() +"/" + Integer.toString(sectionNumber);
            DataSection tempDataSectionObj = new DataSection(sectionMin, sectionMax, sectionNumber, sectionPath);
            JSONArray dataBlockList = (JSONArray) jo.get("dataBlockList");
            Iterator dataBlockItr = dataBlockList.iterator();
            while(dataBlockItr.hasNext()){
              JSONObject blockInfo = (JSONObject) dataBlockItr.next();
              int blockMin = Integer.parseInt((String)blockInfo.get("min"));
              int blockMax = Integer.parseInt((String)blockInfo.get("max"));
              int blockNumber = Integer.parseInt((String)blockInfo.get("number"));
              // System.out.println(" BlockInfo " + blockNumber);
              String blockPath = (String) blockInfo.get("hash");
              DataBlock tempDataBlockObj = new DataBlock(blockMin, blockMax, blockNumber, blockPath);
              tempDataSectionObj.addDataBlock(blockNumber, tempDataBlockObj);
            }
            tempDataSliceObj.addDataSection(sectionNumber, tempDataSectionObj);
          }
  }


  public static void genDag() throws IOException{
    // String fileName = "/home/sbot/dataDir/DAG/dag_0.json";
        // File file = new File(fileName);
        // FileInputStream fin = new FileInputStream(file);
        // byte[] res = new byte[(int) file.length()];
        // try{
        //     fin.read(res);
        // }
        // catch(IOException e){
        //   System.out.println(e);
        // }

    String fileName = "/home/sbot/dataDir/dagHashes/dagList";
    // String fileName = "/home/sbot/sScripts/dagList";
    IPFSConnector ipfsCon = new IPFSConnector();
    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
          String line;
    	    while ((line = br.readLine()) != null) {
            // System.out.println("Getting DAG : "+line);
            updateInformation(new String(ipfsCon.getDag(line)).trim(), line);
	        }

          // for(ScreeningNode sc_node : screeningArray){
          //   System.out.println(sc_node.toString());
          // }
    }
    catch(FileNotFoundException e){
      e.printStackTrace();
    }
  }

  public static void printList(ArrayList<String> inRangeSlices){
    // for(ScreeningNode item : screeningArray){
    for (String hash : inRangeSlices){
      // System.out.println(item.toString());
      // String hash = item.getHash();
      System.out.println(lookupTable.get(hash).toString());
    }
  }
}
