import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class ThreadHandler extends Thread{
	private int blockNo;
	private String ipfs_Hash;
	public static String IPFS_Path;
	public ThreadHandler(int n) {
		// TODO Auto-generated constructor stub
		this.blockNo = n;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Thread for Block: "+blockNo+" is running");
		Process p = null;
		int exit = 1;
		
		try {
				String path = "ipfs dag get "+IPFS_Path+"/Blocks/"+Integer.toString(this.blockNo);
  	 	 	String[] command = {"bash","-c",path};
				// ,"get",path};
				// System.out.println(Arrays.toString(command));
				// String[] command = {"bash","-cl","ls","-l"};
   	 	 	while(exit != 0) {
	   	 		p = Runtime.getRuntime().exec(command);
	        exit = p.waitFor();
					Thread.sleep(500);
				}
	        // System.out.println("Exit is: "+exit);
	        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
					String line = null;
					while((line = reader.readLine())!= null){
						if(line != null){
							this.ipfs_Hash = line;
						}
						// System.out.println(line);
					}
					// System.out.println(this.ipfs_Hash);
					exit = 1;
					path = "ipfs get "+this.ipfs_Hash;
					String[] command1 = {"bash","-c",path};
					System.out.println("Getting data for block : "+this.blockNo);
	   	 	 	while(exit != 0) {
		   	 		p = Runtime.getRuntime().exec(command1);
		        exit = p.waitFor();
						Thread.sleep(500);
						// System.out.println("For thread" + this.blockNo);
					}
		      // reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
					// line = "";
					// while((line = reader.readLine())!= null){
						// System.out.println(line);
					// }
			this.blockNo++;

		}

		catch (Exception e) {
			e.printStackTrace();

			}
	}

}
