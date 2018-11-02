

import java.util.concurrent.*;
import java.io.*;

public class Tasks implements Runnable{
    private String Name;
    public Tasks(String Name){
        this.Name = Name;
    }

    public void printData(){
        System.out.println(this.Name);
        
    }

    public void run(){
        String ipfsHash = this.Name;
        // Runtime rt = Runtime.getRuntime();
        // System.out.println(this.Name);
        Runtime rt = Runtime.getRuntime();
        String s = null;
        String cmd = "ipfs block get "+ipfsHash;
        System.out.println(cmd);
        try {
            Process p = rt.exec(cmd);
            BufferedReader stdInput = new BufferedReader(new 
                 InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new 
                 InputStreamReader(p.getErrorStream()));

            // read the output from the command
            System.out.println("Here is the standard output of the command:\n");
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }
            
            // read any errors from the attempted command
            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
            
            System.exit(0);
        } catch (Exception e) {
            //TODO: handle exception
        }
    
    }
}