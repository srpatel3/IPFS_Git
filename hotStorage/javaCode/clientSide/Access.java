// importing RMI package

import java.rmi.*;

public interface Access extends Remote{
	public String printInfo() throws RemoteException;
}
