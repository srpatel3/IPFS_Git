import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.Iterator;
import java.util.HashMap;

public class Getter{

  private ArrayList<Runnable> jobsToRun;

  public Getter(){
    this.jobsToRun = new ArrayList<>();
  }


  public void getSubBlock(ISBound sub_block_space, ISBound block_dimension){
    long endTime = 0;
    long startTime = System.currentTimeMillis();
    for(ScreeningNode temp_screening_node : Driver.screeningArray){
       this.jobsToRun.add(new ProcessingThread(sub_block_space, temp_screening_node.getHash(), block_dimension.get_sBound(), block_dimension.get_eBound())); 
    }

    this.jobScheduler(4);

    System.out.println("\n\n");
    Iterator<String> itr = Driver.resultTable.keySet().iterator();
    while (itr.hasNext()) {
      String hash = itr.next();
      System.out.println("Total For : " + hash + " is : " + Driver.resultTable.get(hash));
    }

    endTime = System.currentTimeMillis();
    long timeneeded =  ((endTime - startTime) /1000);
    System.out.println("\n\nTime taken : "+timeneeded);


  }


  public void printArray(float[] floats, int block_dim_row){
    int count = 1;
    for(float num : floats){
      System.out.print(num+"\t");
      if (count % block_dim_row == 0){
        System.out.println("\n\n");
      }
      count++;
    }
    System.out.println();
  }

  public void jobScheduler(int number_of_threads){
    ExecutorService pool = Executors.newFixedThreadPool(number_of_threads);
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
    int number_of_threads = 4;
    for(String hash : hashesToGet){
      Runnable job = new Task(hash);
      this.jobsToRun.add(job);
    }
    this.jobScheduler(number_of_threads);
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
    // System.out.println("Total : "+count+" blocks processed");
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
