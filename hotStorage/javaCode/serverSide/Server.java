import java.rmi.*;
import java.rmi.registry.*;
public class Server
{
    public static void main(String args[])
    {
        try{
          Access obj = new NodeInfo();
          LocateRegistry.createRegistry(1900);
          System.setProperty("java.rmi.server.hostname","130.74.96.25");
          Naming.rebind("rmi://130.74.96.25:1900/access",obj);
          System.out.println("Server Started");
        }
        catch(Exception ae)
        {
            System.out.println(ae);
        }
    }
}
