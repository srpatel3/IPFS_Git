//Importing packages for implementation
import java.io.BufferedReader;
import java.util.Scanner;


public class Driver{
  public static void main(String[] args){
      //System.out.println("Hii I am inside print");
      String ipfsHash = "SomeString";
      Scanner inFromKey = new Scanner(System.in);
      System.out.println("You want to run serial version or parallel...?");
      String input = null;
      do{
          System.out.println("Enter 1. Serial or 2. Parallel");
          input = inFromKey.next();
        }while(!input.equals(Integer.toString(1)) & !input.equals(Integer.toString(2)));
      System.out.println("You have Chosen to go With "+input);
      if (input.equals(Integer.toString(1))){
        System.out.println("You are in Serial Version....");
        get_Data_in_Serial(ipfsHash);
      }
      else {
        System.out.println("You are in Parallel Version");
      }
  }

  public static void get_Data_in_Serial(String ipfsHash){
    System.out.println("Getting Data in Serial with Hash:" + ipfsHash);
  }
}
