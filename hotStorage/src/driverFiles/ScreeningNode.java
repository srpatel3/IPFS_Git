package driverFiles;

public class ScreeningNode{
    private int min;
    private int max;
    private String hash;

    // Constructor
    public ScreeningNode(int min, int max, String hash){
      this.min = min;
      this.max = max;
      this.hash = hash;
    }

    public String getHash(){
      return this.hash;
    }

    public int getMin(){
      return this.min;
    }

    public int getMax(){
      return this.max;
    }

    public boolean isInRange(int min, int max){
      if (min >= this.getMin() && max <= this.getMax()){
        return true;
      } else{
        return false;
      }
    }

    public String toString(){
      return "Min : "+this.min+"\nMax : "+this.max+"\nHash : "+this.hash;
    }
}
