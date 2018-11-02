import java.util.concurrent.*;
import java.io.*;
// import org.json.*;

public class Driver{
    static final int numberOfThreads = 3;

    public static void main(String[] args){
        System.out.println("Hello Word...!!");
       
       Runnable T1 = new Tasks("QmbTkA46kSWU7rRrGxQKDH7s5xTJMCEFiPjphoAHd1Wwa7");
       Runnable T2 = new Tasks("QmTR1paJcxRAweWvWEGZNQcmdc9oqVMGTXQqV1EHAchtFy");
       Runnable T3 = new Tasks("QmUhnR1zZ6XMrtUW19EtrwJ4hX9mcAoDjJ4DmJaQJ5kUuY");
    //    Runnable T4 = new Tasks("Stupid");
    //    Runnable T5 = new Tasks("Lol");
    //    T1.printData();
    //    T1.run();
        ExecutorService pool = Executors.newFixedThreadPool(numberOfThreads);
        pool.execute(T1);
        pool.execute(T2);
        pool.execute(T3);
        // pool.execute(T4);
        // pool.execute(T5);
        pool.shutdown();

    }
}