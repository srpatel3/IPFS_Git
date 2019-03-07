import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.*;

public class Task implements Runnable{
  private String hash;
  private IPFSConnector ipfs_con;

  public Task(String hash){
    this.hash = hash;
    this.ipfs_con = new IPFSConnector();
  }

  public void run(){
    try{
        // System.out.println("Getting : "+this.hash);
        byte[] data = ipfs_con.getFile(this.hash);
        ByteBuffer buf = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);
        // System.out.println("Length of Byte Array is : " + data.length);
        FloatBuffer fbuf = buf.asFloatBuffer();
        int numFloats = fbuf.limit();
        float [] floats = new float[numFloats];
        fbuf.get(floats);
        // System.out.println("Length of float Array is : " + numFloats);
        float sum = 0;
        for(int i=0; i<numFloats; i++){
          sum += floats[i];
        }

      Driver.resultTable.put(this.hash, sum);
    }
    catch(IOException e){
      e.printStackTrace();
    }
    // System.out.println("Done with : "+this.hash);
    // System.exit(0);
  }

  public String getHash(){
    return this.hash;
  }

}
