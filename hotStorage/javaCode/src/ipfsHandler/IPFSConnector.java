import io.ipfs.cid.*;
import io.ipfs.*;
import io.ipfs.multihash.Multihash;
import io.ipfs.multiaddr.MultiAddress;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;

import java.io.IOException;
import java.util.ArrayList;

public class IPFSConnector{

  private IPFS ipfs;

  public IPFSConnector(){
    try{
            // System.out.println("Error");
            this.ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");
    }
    catch(Exception e){
      e.printStackTrace();
      this.ipfs = null;
    }

  }

 public void pinFiles(ArrayList<String> listToPin) throws IOException{
    for(String item : listToPin){
      Cid hash = Cid.decode(item);
      String temp = ipfs.pin.add(hash).toString();
      System.out.println("Pinned : " + temp);
    }
  }

  public void pinFile(String hashToPin) throws IOException{
      Cid hash = Cid.decode(hashToPin);
      String temp = ipfs.pin.add(hash).toString();
      System.out.println("Pinned : " + temp);
  }

  public byte[] getFile(String hash) throws IOException{
    this.pinFile(hash);
    // System.out.println("Trying to get file : "+hash);
    Multihash filePointer = Multihash.fromBase58(hash);
    byte[] fileContents = this.ipfs.cat(filePointer);
    // System.out.println("Done Fetching File");
    return fileContents;
  }

  public String getDag(String hash) throws IOException{
    Cid genHash = Cid.decode(hash);
    // System.out.println(genHash);
    byte[] res = ipfs.dag.get(genHash);
    return new String(res).trim();
  }

}
