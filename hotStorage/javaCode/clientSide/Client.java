import java.rmi.*;
public class Client
{
    public static void main(String args[])
    {
        String answer,value="Reflection in Java";
        try
        {
            // lookup method to find reference of remote object
            Access access =
                (Access)Naming.lookup("rmi://localhost:1900"+
                                      "/access");
            answer = access.printInfo();
            System.out.println("Article on " +
                            " " + answer+" at GeeksforGeeks");
        }
        catch(Exception ae)
        {
            System.out.println(ae);
        }
    }
}
