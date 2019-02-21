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

  public ArrayList<String> getDatums(int min, int max){
    ArrayList<String> tempList = new ArrayList<>();
    for(int i =0; i<4 && this.dataBlockList[i] != null; i++){
      if(this.dataBlockList[i].isInRange(min, max)){
        tempList.add(this.dataBlockList[i].getHash());
      }
    }
    return tempList;
  }

  public boolean isInRange(int min, int max){
    if (min >= this.getMin() && max <= this.getMax()){
      return true;
    } else{
      return false;
    }
  }

  public int getMin(){
    return this.min;
  }

  public int getMax(){
    return this.max;
  }

  public int getNum(){
    return this.dataSectionNumber;
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
