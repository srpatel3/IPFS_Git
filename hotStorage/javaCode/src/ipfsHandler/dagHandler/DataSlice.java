import java.util.ArrayList;
public class DataSlice{
  private int min;
  private int max;
  private int dataSliceNumber;
  private String path; // path starts with hash
  private DataSection[] dataSectionList;


  // Constructor
  public DataSlice(int min, int max, int dataSliceNumber, String path){
    this.min  = min;
    this.max = max;
    this.dataSliceNumber = dataSliceNumber;
    this.path = path;
    this.dataSectionList = new DataSection[4];
  }

  // Add data section to the list [For now]
  public void addDataSection(int dataSectionIndex, DataSection obj){
      this.dataSectionList[dataSectionIndex] = obj;
	}

  // add DataBlock to the list [for now]
  public void addDataBlock(int dataSectionIndex, int dataBlockIndex, DataBlock obj){
    this.dataSectionList[dataSectionIndex].addDataBlock(dataBlockIndex, obj);
  }

  // public ArrayList<String> getDataSections(int min, int max){
  //   ArrayList<String> tempList = new ArrayList<>();
  //   for(int i = 0; i < 4 && this.dataSectionList[i] != null; i++){
  //
  //   }
  // }

  public boolean isInRange(int min, int max){
    if (min >= this.getMin() && max <= this.getMax()){
      return true;
    } else{
      return false;
    }
  }


  public String getHash(){
	return this.path;
  }

  public ArrayList<String> getDatums(int min, int max){
    ArrayList<String> tempList = new ArrayList<>();
    for(int i =0; i<4 && this.dataSectionList[i] != null; i++){
      tempList.addAll(this.dataSectionList[i].getDatums(min, max));
    }
    return tempList;
  }


  public int getMin(){
    return this.min;
  }

  public int getMax(){
    return this.max;
  }

  public int getNum(){
    return this.dataSliceNumber;
  }


  public String toString(){
    String tempString = " min : "+this.min+"\n max : "+this.max+"\n number : "+this.dataSliceNumber+"\n";
    // have to check for error because intermediate list can be empty may be not even need this array could use arrayList instead
    for(int i =0; i<4 && this.dataSectionList[i] != null; i++){
      tempString += this.dataSectionList[i].toString();
    }
    tempString += "\n";
    return tempString;
  }
}
