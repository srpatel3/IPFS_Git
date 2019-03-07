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
    int number_of_threads = 1;
    int total_row = 0;
    int total_col = 0;
    int block_dim_row = block_dimension.get_sBound();
    int block_dim_col = block_dimension.get_eBound();
    float sum = 0;
    long endTime = 0;
    long startTime = System.currentTimeMillis();
    for(ScreeningNode temp_screening_node : Driver.screeningArray){
      HashMap<String, ISBound> blocks_to_get = new HashMap<>();
      blocks_to_get.putAll(Driver.lookupTable.get(temp_screening_node.getHash()).getSubBlock(sub_block_space, new ISBound(4,4)));
      ISBound sBound = blocks_to_get.remove("starting_bound");
      ISBound eBound = blocks_to_get.remove("ending_bound");
      this.subBlockGetter(blocks_to_get,number_of_threads);
      ArrayList<float[]> floats = new ArrayList<>();
      for(int i = sBound.get_sBound(); i <= eBound.get_sBound(); i++){
        for(int j = sBound.get_eBound() ; j <= eBound.get_eBound(); j++){
          ISBound key = new ISBound(i,j);
          if(SubBlockTask.arrayMap.containsKey(key)){
            floats.add(SubBlockTask.arrayMap.get(key));
          }else{
            System.out.println(key.toString()+" does not exists");
          }
        }
      }
      total_col = eBound.get_eBound() - sBound.get_eBound() + 1;
      total_row = eBound.get_sBound() - sBound.get_sBound() + 1;

      // System.out.println("total rows : "+total_row * block_dim_row +" total Cols : "+total_col * block_dim_col);
      RegularCompositeDataBlock rb = new RegularCompositeDataBlock(new ISBound(total_row, total_col), new ISBound(block_dim_row,block_dim_col), floats);
      sum = 0;
      for(int i = 0; i < total_row*block_dim_row; i++){
	      for(int j = 0; j < total_col*block_dim_col; j++){
          // System.out.print(rb.getFloat(i,j)+"\t");
          sum += rb.getFloat(i,j);
        }
        // System.out.println("\n\n");
      }

      Driver.resultTable.put(temp_screening_node.getHash(), sum);
    }

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

  public void subBlockGetter(HashMap<String, ISBound> blocks_to_get,int number_of_threads){
    Iterator<String> itr = blocks_to_get.keySet().iterator();
    ArrayList<Runnable> jobs = new ArrayList<>();
    System.out.println("RESULT");
    while (itr.hasNext()) {
      String hash = itr.next();
      jobs.add(new SubBlockTask(hash, blocks_to_get.get(hash)));
    }

    ExecutorService pool = Executors.newFixedThreadPool(number_of_threads);
    for(Runnable job : jobs){
      pool.execute(job);
    }
    pool.shutdown();
    try {
      pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
      System.out.println("All threads done");
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
  }

  public void jobScheduler(ArrayList<String> hashesToGet, int number_of_threads){
    for(String hash : hashesToGet){
      Runnable job = new Task(hash);
      this.jobsToRun.add(job);
    }
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
    this.jobScheduler(hashesToGet,number_of_threads);
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
