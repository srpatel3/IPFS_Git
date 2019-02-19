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

public class Driver{
  public  static ArrayList<ScreeningNode> screeningArray = new ArrayList<>();
  public static HashMap<String, DataSlice> lookupTable = new HashMap<String, DataSlice>();
  public static void main(String[] Args){
    System.out.println("In Main method");
    System.out.println("Now Starting Thread");
    // Make sure to uncomment this lines as this will get us the list of dag
    Thread connector = new Thread(new RemoteConnector());
    connector.start();
    Scanner inFromKey = new Scanner(System.in);
    int input = 0;
    // genDag();
    while (input !=5){
      System.out.println("Enter Min : ");
      int min = inFromKey.nextInt();
      System.out.println("Enter Max : ");
      int max = inFromKey.nextInt();
      ArrayList<String> inRangeSlices = getInRangeSlices(min, max);
      System.out.println("What do you want to do?");
      input = inFromKey.nextInt();
      // printList(inRangeSlices);
      getDatums(min, max);
    }

  }


 public static ArrayList<String> getInRangeSlices(int min, int max){
   ArrayList<String> tempList = new ArrayList<>();
   for(ScreeningNode tempNode : screeningArray){
     if (tempNode.isInRange(min, max)){
       tempList.add(tempNode.getHash());
     }
   }
   return tempList;
 }

// public static void getDataSections(int min, int max){
//   ArrayList<String> sliceHashes = getInRangeSlices(min, max);
//   ArrayList<String> hashesToGet = new ArrayList<>();
//   for(String hash : sliceHashes){
//       hashesToGet.addAll(lookupTable.get(hash).getDataSections(min, max));
//   }
//   for(String hash : hashesToGet){
//     System.out.println(hash);
//   }
// }

public static void getDatums(int min, int max){
  ArrayList<String> sliceHashes = getInRangeSlices(min, max);
  ArrayList<String> hashesToGet = new ArrayList<>();
  for(String hash : sliceHashes){
      hashesToGet.addAll(lookupTable.get(hash).getDatums(min, max));
  }
  for(String hash : hashesToGet){
    System.out.println(hash);
  }
  // return hashesToGet;
}


  public static void updateInformation(String dag, String hash){
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

  public static void updateDataSliceObject(JSONObject rawDag, DataSlice tempDataSliceObj){
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


  public static void genDag(){
    String fileName = "/home/sbot/git/IPFS_Git/hotStorage/pyScripts/dag1.json";
    try{
        File file = new File(fileName);
        FileInputStream fin = new FileInputStream(file);
        byte[] res = new byte[(int) file.length()];
        try{
            fin.read(res);
        }
        catch(IOException e){
          System.out.println(e);
        }

        // generateDag(new String(res).trim(), "HASH");
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