import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.Scanner;
import java.util.ArrayList;

public class Driver
{
	private static ArrayList<Thread> arrThreads = new ArrayList<Thread>();
	public static void main(String[] args) throws Exception {
		String ipfs_Hash1 = "zdpuAwhSJXdapooNGQLjssq8oprvMgmoovXd8DPPcnZ7EF26j";
		ThreadHandler.IPFS_Path = ipfs_Hash1;
		Scanner sc = new Scanner(System.in);
		int Rows = 0;
		int Cols = 0;
		int total_Blocks = Rows * Cols;
		Cols = Rows;
		Set<Integer> Blocks_Have = new HashSet<Integer>();
		System.out.println();
		int block_To_Get = 0;
		int serial = 7;
		System.out.println("Please enter Total No of Blocks");
		block_To_Get = sc.nextInt();
		System.out.println("Do you want to run serial or parallel?? \n 0:Serial and 1: parallel");
		serial = sc.nextInt();
		if(serial != 0){
			long startTime = System.currentTimeMillis();
			long endTime = 0;
			for (int i = 0; i<block_To_Get;i++){
				Thread th = new Thread(new ThreadHandler(i));
				th.start();
				arrThreads.add(th);
			}
			for (int i = 0; i < arrThreads.size(); i++){
				arrThreads.get(i).join();
			}
			endTime = System.currentTimeMillis();
			long timeneeded =  ((endTime - startTime) /1000);
			System.out.println("\n\n\n\n\nTotal Parallel Running Time : "+timeneeded);
		}
		else{
			long startTime = System.currentTimeMillis();
			long endTime = 0;
			for(int i = 0; i< block_To_Get; i++){
				Process p = null;
				int exit = 1;
				String ipfs_Hash = null;
				try {
						String path = "ipfs dag get "+ipfs_Hash1+"/Blocks/"+Integer.toString(i);
						String[] command = {"bash","-c",path};
						// ,"get",path};
						// System.out.println(Arrays.toString(command));
						// String[] command = {"bash","-cl","ls","-l"};
						while(exit != 0) {
							p = Runtime.getRuntime().exec(command);
							exit = p.waitFor();
							// sleep(500);
						}
							BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
							String line = null;
							while((line = reader.readLine())!= null){
								if(line != null){
									ipfs_Hash = line;
								}
								// System.out.println(line);
							}
							// System.out.println(this.ipfs_Hash);
							exit = 1;
							path = "ipfs get "+ipfs_Hash;
							String[] command1 = {"bash","-c",path};
							System.out.println("Getting data for block : "+i);
							while(exit != 0) {
								p = Runtime.getRuntime().exec(command1);
								exit = p.waitFor();
							}

				}
				catch (Exception e) {
					e.printStackTrace();
					 }
			}
			endTime = System.currentTimeMillis();
			long timeneeded =  ((endTime - startTime) /1000);
			System.out.println("\n\n\n\n\nTotal Parallel Running Time : "+timeneeded);
		}


	}



}
