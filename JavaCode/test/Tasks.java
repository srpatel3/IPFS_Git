
public class Tasks implements Runnable{
    private String Name;
    public Tasks(String Name){
        this.Name = Name;
    }

    public void printData(){
        System.out.println(this.Name);
        
    }

    public void run(){
        System.out.println(this.Name);
    }
}