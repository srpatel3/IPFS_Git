import java.io.IOException;
public class Task implements Runnable{
  private String hash;
  private IPFSConnector ipfs_con;

  public Task(String hash){
    this.hash = hash;
    this.ipfs_con = new IPFSConnector();
  }

  public void run(){
    try{
        byte[] data = ipfs_con.getFile(this.hash);
        String[] strArray = (new String(data)).split("\n");
        int sum = 0;
  		  for (String item_String : strArray){
          char[] chrArray = (item_String.replaceAll(" ","")).toCharArray();
          for(char item_char : chrArray){
            int tempSum = Character.getNumericValue(item_char);
            sum += tempSum;
          }
  		   }
      Driver.resultTable.put(this.hash, sum);
    }
    catch(IOException e){
      e.printStackTrace();
    }
  }

  public String getHash(){
    return this.hash;
  }

}
