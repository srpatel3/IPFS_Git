import java.net.*;
import java.io.*;

public class RemoteConnector implements Runnable{
	private int port;
	private ServerSocket socket;
	private boolean flag;
	private IPFSConnector ipfsCon;
	// Constructor
	public RemoteConnector(){
		this.port = 6789;
		this.flag = true;
		try{
			this.socket = new ServerSocket(this.port);
			this.ipfsCon = new IPFSConnector();
		}
		catch (Exception e){
			System.out.println(e);
		}

	}

	public void updateFlag(){
		this.flag = false;
	}

	public void run(){
		try{
			System.out.println("Inside Thread");
			while(this.flag) {
  			Socket connectionSocket = this.socket.accept();
				// System.out.println("Now Waiting for Client");
        BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
				String hashFromClient;
				while ((hashFromClient = inFromClient.readLine())!= null){
					Driver.updateInformation(ipfsCon.getDag(hashFromClient),hashFromClient);
				}

        }
		}
		catch(Exception e){

		}
			// System.exit(0);
	}
}
