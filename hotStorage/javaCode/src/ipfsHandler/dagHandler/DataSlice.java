import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

  public boolean isInRange(int min, int max){
    if (min >= this.getMin() && max <= this.getMax()){
      return true;
    } else{
      return false;
    }
  }


  // get list of blocks within range
  public ArrayList<String> getDatums(int min, int max){
    ArrayList<String> tempList = new ArrayList<>();
    for(int i =0; i<4 && this.dataSectionList[i] != null; i++){
      tempList.addAll(this.dataSectionList[i].getDatums(min, max));
    }
    return tempList;
  }

  public int getDataSectionIndex(int i, int j){
      return i*2+j;
  }
  public HashMap<String, ISBound> getSubBlock(ISBound b){
    int sIndex = b.get_sBound();
    int eIndex = b.get_eBound();
    int dimension = 4;
    int sRow = sIndex/dimension;
    int sCol = sIndex%dimension;
    int eRow = eIndex/dimension;
    int eCol = eIndex%dimension;
    int starting_row = Math.min(sRow,eRow);
    int ending_row = Math.max(sRow, eRow);
    int starting_col = Math.min(sCol, eCol);
    int ending_col = Math.max(sCol, eCol);
    ArrayList<String> hashesToGet = new ArrayList<>();
    HashMap<String, ISBound> hash_to_ISB = new HashMap<>();
    for(int i = starting_row ; i <= ending_row ; i++){
      for(int j = starting_col; j<= ending_col; j++){
          int datasection_index = getDataSectionIndex(i/2,j/2);
          // System.out.println(datasection_index);
          System.out.println("Cordinates are : "+i+","+j +" from " + datasection_index);
          if(this.dataSectionList[datasection_index] != null){
            // hashesToGet.add(this.dataSectionList[datasection_index].getBlocks(i,j));
              hash_to_ISB.put(this.dataSectionList[datasection_index].getBlocks(i,j),new ISBound(i,j));
          }
      }
    }
    hash_to_ISB.put("starting_bound",new ISBound(starting_row, starting_col));
    hash_to_ISB.put("ending_bound", new ISBound(ending_row, ending_col));

    return hash_to_ISB;
  }

  public String getHash(){
	return this.path;
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
