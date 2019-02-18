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

    public boolean isInRange(int min, int max){
      if ((this.min <= min) && (this.max >= max)){
        return true;
      }
      else{
        return false;
      }
    }

    public String getHash(){
      return this.hash;
    }

    public String toString(){
      return "Min : "+this.min+"\nMax : "+this.max+"\nHash : "+this.hash;
    }
}
