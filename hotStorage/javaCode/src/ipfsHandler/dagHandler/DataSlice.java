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

  public String getHash(){
	return this.path;
  }
  public String toString(){
    String tempString = " min : "+this.min+"\n max : "+this.max+"\n number : "+this.dataSliceNumber+"\n";
    for(int i =0; i<4 && this.dataSectionList[i] != null; i++){
      tempString += this.dataSectionList[i].toString();
    }
    tempString += "\n";
    return tempString;
  }
}
