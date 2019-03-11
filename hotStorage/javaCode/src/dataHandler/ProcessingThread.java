import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.*;
import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.Iterator;
import java.util.HashMap;


public class ProcessingThread implements Runnable{
  private ArrayList<Runnable> jobsToRun;
  // public static HashMap<String, float[]> hash_to_content_mapper = new HashMap<String, float<>(); 
  // private ArrayList<String> hashes_to_get = new ArrayList<>();
  public static  HashMap<String, float[]> arrayMap = new HashMap<String, float[]>();
  public static HashMap<String, ISBound> blocks_to_get = new HashMap<>();
  private String slice_hash;
  private int block_dim_row;
  private int block_dim_col;
  private ISBound sub_block_space;

  public ProcessingThread(ISBound sub_block_space, String hash, int block_dim_row, int block_dim_col){
    this.slice_hash = hash;
    this.block_dim_row = block_dim_row;
    this.block_dim_col = block_dim_col;
    this.sub_block_space = sub_block_space;
    this.jobsToRun = new ArrayList<>();
    // System.out.println("In Processing Thread");
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

  public void run(){
    try{
      int number_of_threads = 4;
      int total_row = 0;
      int total_col = 0;
      float sum = 0;
      // System.out.println("Here");
      ArrayList<String> hashes_to_get = new ArrayList<>();
      String key1 = this.slice_hash+"starting_bound";
      String key2 = this.slice_hash+"ending_bound";
      hashes_to_get.addAll(Driver.lookupTable.get(this.slice_hash).getSubBlock(this.slice_hash, this.sub_block_space, new ISBound(4,4)));
      ISBound sBound = blocks_to_get.remove(key1);
      ISBound eBound = blocks_to_get.remove(key2);
      for(String hash : hashes_to_get){
        // System.out.println(hash);
      // }
      // Iterator<String> itr = blocks_to_get.keySet().iterator();
      // while (itr.hasNext()) {
      //   String hash = itr.next();
        // System.out.println(hash);
        this.jobsToRun.add(new SubBlockTask(hash));
      }

      this.jobScheduler(number_of_threads);
      ArrayList<float[]> floats = new ArrayList<>();
      for(String item : hashes_to_get){
        floats.add(ProcessingThread.arrayMap.remove(item));
      }
      // for(int i = sBound.get_sBound(); i <= eBound.get_sBound(); i++){
      //   for(int j = sBound.get_eBound() ; j <= eBound.get_eBound(); j++){
      //     ISBound key = new ISBound(i,j);
      //     if(ProcessingThread.arrayMap.containsKey(key)){
      //       floats.add(ProcessingThread.arrayMap.remove(key));
      //     }else{
      //       System.out.println(key.toString()+" does not exists");
      //     }
      //   }
      // }
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
      Driver.resultTable.put(this.slice_hash, sum);
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }
}
