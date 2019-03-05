public class Query{
  private ISBound params;

  public Query(ISBound params){
    this.params = params;
  }

  public void processQuery(){
    Getter.getInRange(this.params);
  }
}
