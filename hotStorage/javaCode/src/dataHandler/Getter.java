import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.Iterator;

public class Getter{

  private ArrayList<Runnable> jobsToRun;

  public Getter(){
    this.jobsToRun = new ArrayList<>();
  }

  public void getInRange(ISBound b){
    long endTime = 0;
    long startTime = System.currentTimeMillis();
  	int min = b.get_sBound();
  	int max = b.get_eBound();
    // System.out.println("Here");
    ArrayList<String> sliceHashes = this.getInRangeSlices(min, max);
    ArrayList<String> hashesToGet = new ArrayList<>();
    for(String hash : sliceHashes){
        // System.out.println(hash);
        hashesToGet.addAll(Driver.lookupTable.get(hash).getDatums(min, max));
    }
    int count = 0;
    for(String hash : hashesToGet){
      System.out.println(hash);
      Runnable job = new Task(hash);
      this.jobsToRun.add(job);
      count += 1;
      // break;
    }
    ExecutorService pool = Executors.newFixedThreadPool(4);
    for(Runnable job : this.jobsToRun){
      pool.execute(job);
      // System.out.println(job.getHash());
    }
    pool.shutdown();
    // System.out.println("Threads job done");
    try {
      pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    // for(Runnable job : this.jobsToRun){
    //   job.join();
    // }
    endTime = System.currentTimeMillis();
		long timeneeded =  ((endTime - startTime) /1000);
    System.out.println("\n\nTime taken : "+timeneeded);
    Iterator<String> itr = Driver.resultTable.keySet().iterator();
    System.out.println("RESULT");
		while (itr.hasNext()) {
      String hash = itr.next();
			System.out.println(hash +" "+Driver.resultTable.get(hash));
		}
    System.out.println("Total : "+count+" blocks processed");
    // return a DataBlock []
    // return hashesToGet;
  }

  public ArrayList<String> getInRangeSlices(int min, int max){
    // System.out.println("Here");
    ArrayList<String> tempList = new ArrayList<>();
    for(ScreeningNode tempNode : Driver.screeningArray){
      if (tempNode.isInRange(min, max)){
        tempList.add(tempNode.getHash());
      }
    }
    return tempList;
  }

}
