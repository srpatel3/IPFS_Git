import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.concurrent.*;

public class Getter{

  private ArrayList<Task> jobsToRun;

  public Getter(){
    this.jobsToRun = new ArrayList<>();
  }

  public void getInRange(ISBound b){
  	int min = b.get_sBound();
  	int max = b.get_eBound();
    // System.out.println("Here");
    ArrayList<String> sliceHashes = this.getInRangeSlices(min, max);
    ArrayList<String> hashesToGet = new ArrayList<>();
    for(String hash : sliceHashes){
        // System.out.println(hash);
        hashesToGet.addAll(Driver.lookupTable.get(hash).getDatums(min, max));
    }
    for(String hash : hashesToGet){
      System.out.println(hash);
      Task job = new Task(hash);
      this.jobsToRun.add(job);
    }

    for(Task job : this.jobsToRun){
      System.out.println(job.getHash());
    }
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
