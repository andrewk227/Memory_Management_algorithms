import java.util.Vector;
public class Partitions {
    private String partitionName;
    private int partitionSize;
    private static int ID=-1;
    private Process p;
    public Partitions(){
        ID++;
        this.p=null;
    };
    public Partitions(String partitionName,int partitionSize) {
        ID++;
        this.partitionName = partitionName+String.valueOf(ID);
        this.partitionSize=partitionSize;
        this.p=null;
    }
    String getPartitionName() {return partitionName;}
    int getPartitionSize(){return partitionSize;}
    void setPartitionName(String partitionName){this.partitionName=partitionName;}
    void setPartitionSize(int partitionSize){this.partitionSize=partitionSize;}
    public static int getID() {
        return ID;
    }

    public static void setID(int ID) {
        Partitions.ID = ID;
    }

    public Process getP() {
        return p;
    }

    public void setP(Process p) {
        this.p = p;
    }

    public Partitions Update_Create()
    {
        int remainingSize = partitionSize - p.getProcessSize();
        partitionSize=p.getProcessSize();
        if(remainingSize != 0) {
            Partitions par = new Partitions("p", remainingSize);
            return par;
        }
        else
            return null;
    }

}
