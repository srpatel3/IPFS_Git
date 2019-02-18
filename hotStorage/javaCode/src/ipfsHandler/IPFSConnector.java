import io.ipfs.*;
import io.ipfs.cid.*;
import io.ipfs.multihash.Multihash;
import io.ipfs.multiaddr.MultiAddress;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;

import java.io.IOException;

public class IPFSConnector{

  private IPFS ipfs;

  public IPFSConnector(){
    this.ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");
  }

  public String getDag(String hash) throws IOException{
    Cid genHash = Cid.decode(hash);
    System.out.println(genHash);
    byte[] res = ipfs.dag.get(genHash);
    return new String(res).trim();
  }

}
