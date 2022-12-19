import java.util.Scanner;
import java.util.Vector;

public class Main {

    public static void main(String[] args) {
        Vector<Partitions> partitionsVector=new Vector<>();
        Vector<Process> processVector=new Vector<>();
        Scanner input=  new Scanner(System.in);
        int  noOfPartitions,partitionSize,noOfProcesses,processSize;
        String partitionName,processName;

        noOfPartitions = input.nextInt();

        for(int i=0;i<noOfPartitions;i++)
        {

            partitionName=input.next();
            partitionSize=input.nextInt();
            Partitions p=new Partitions(partitionName,partitionSize);
            partitionsVector.add(p);
        }
       /* for(int i=0;i<noOfPartitions;i++)
        {
            System.out.print(partitionsVector.get(i).getPartitionName()+""+partitionsVector.get(i).getPartitionSize());
        }*/
        noOfProcesses=input.nextInt();
        for(int i=0;i<noOfProcesses;i++){
            processName= input.next();
            processSize=input.nextInt();
            Process process=new Process(processName,processSize);
            processVector.add(process);
        }
    }
}