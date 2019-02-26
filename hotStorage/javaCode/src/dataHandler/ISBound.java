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
}
