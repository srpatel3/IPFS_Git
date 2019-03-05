public class ISBound{
  private int sBound;
  private int eBound;

  // Constructor
  public ISBound(int sBound, int eBound){
    this.sBound = sBound;
    this.eBound = eBound;
  }


  public int get_sBound(){
    return this.sBound;
  }


  public int get_eBound(){
    return this.eBound;
  }


  public int hashCode(){
        // System.out.println("In hashcode");
        int hashcode = 0;
        hashcode = this.sBound * 20;
        hashcode += this.eBound * 20;
        hashcode += this.toString().hashCode();
        return hashcode;
    }


  public boolean equals(Object ob){
    // System.out.println("In Equal");
    ISBound tempObj = (ISBound) ob;
    if (this.hashCode() == tempObj.hashCode())
      return true;
    else
      return false;
  }
  public String toString(){
    return "sBound : "+this.sBound+" eBound : "+this.eBound;
  }
}
