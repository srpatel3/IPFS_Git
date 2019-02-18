import java.util.ArrayList;

public class DataSection{
  private int min;
  private int max;
  private int dataSectionNumber;
  private String path; // path starts with hash
  private DataBlock[] dataBlockList;
  // Constructor
  public DataSection(int min, int max, int dataSectionNumber, String path){
    this.min  = min;
    this.max = max;
    this.dataSectionNumber = dataSectionNumber;
    this.path = path;
    this.dataBlockList = new DataBlock[4];
  }
  // Adding dataBlock to the list
  public void addDataBlock(int dataBlockIndex, DataBlock obj){
    this.dataBlockList[dataBlockIndex] = obj;
  }
  public String toString(){
    String tempString = "\t\t min : "+this.min+"\n\t\t max : "+this.max+"\n\t\t number : "+this.dataSectionNumber+"\n";
    for(int i =0; i<4 && this.dataBlockList[i] != null; i++){
      tempString += this.dataBlockList[i].toString();
    }
    tempString += "\n";
    return tempString;
  }
}
