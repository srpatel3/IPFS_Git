import java.util.ArrayList;

public class DataBlock{
  private int min;
  private int max;
  private int dataBlockNumber;
  
  // private ISBounds bounds;  // records both shape and position
  
  private String path; // path starts with hash

  // Constructor
  public DataBlock(int min, int max, int dataBlockNumber, String path){
    this.min  = min;
    this.max = max;
    this.dataBlockNumber = dataBlockNumber;
    this.path = path;
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
    return this.dataBlockNumber;
  }
  
  // public float (or int for now)  getDatum(int row, int column);

  public String getHash(){
    return this.path;
  }

  public String toString() {
    String tempString = "\t\t\t number : "+this.dataBlockNumber+"\n\t\t\t min : "+this.min+"\n\t\t\t max : "+this.max+"\n\t\t\t path : "+this.path + "\n";
    return tempString+"\n";
  }
}
