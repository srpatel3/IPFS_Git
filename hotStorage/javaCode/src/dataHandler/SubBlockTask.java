import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.*;
import java.util.HashMap;

public class SubBlockTask implements Runnable{
  private String hash;
  private IPFSConnector ipfs_con;
  private ISBound bounds;
  
  public SubBlockTask(String hash){
    this.hash = hash;
    this.ipfs_con = new IPFSConnector();
    // this.bounds = bounds;
    this.bounds = null;
  }

  public void run(){
    try{

        byte[] data = ipfs_con.getFile(this.hash);
        // System.out.println(this.hash);
        ByteBuffer buf = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);
        FloatBuffer fbuf = buf.asFloatBuffer();
        int numFloats = fbuf.limit();
        float [] floats = new float[numFloats];
        fbuf.get(floats);
        // System.out.println(this.bounds.toString()+" Added");
        ProcessingThread.arrayMap.put(this.hash, floats);
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
