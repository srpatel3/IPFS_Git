import java.rmi.*;
import java.rmi.server.*;
public class NodeInfo extends UnicastRemoteObject
                         implements Access
{
    // Default constructor to throw RemoteException
    // from its parent constructor
    NodeInfo() throws RemoteException{
        super();
    }

    public String printInfo() throws RemoteException {
        return "Hello From Server";
    }
}
