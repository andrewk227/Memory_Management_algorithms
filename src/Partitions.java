import java.util.Vector;
public class Partitions {
    private String partitionName;
    private int partitionSize;
    public Partitions(){};
    public Partitions(String partitionName,int partitionSize) {
        this.partitionName = partitionName;
        this.partitionSize=partitionSize;
    }
    String getPartitionName() {return partitionName;}
    int getPartitionSize(){return partitionSize;}
    void setPartitionName(String partitionName){this.partitionName=partitionName;}
    void setPartitionSize(int partitionSize){this.partitionSize=partitionSize;}

}
