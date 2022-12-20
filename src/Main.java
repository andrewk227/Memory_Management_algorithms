import java.util.Scanner;
import java.util.Vector;
import java.math.*;
public class Main {
    public static Vector<Partitions> partitionsVector=new Vector<>();
    public static Vector<Process> processVector=new Vector<>();
    public static Vector<Process> UnAllocatedProcesses=new Vector<>();
    public static void BestFit(Vector <Process> proVec )
    {
        for (int i=0;i<proVec.size();i++)
        {
            int min=1000000,temp,idx=-1;
            for (int j=0;j<partitionsVector.size();j++)
            {
                if (partitionsVector.get(j).getPartitionSize()>=proVec.get(i).getProcessSize())
                {
                    temp=partitionsVector.get(j).getPartitionSize()-proVec.get(i).getProcessSize();
                    if (temp<=min)
                    {
                        min=temp;
                        idx=j;
                    }
                }
            }
            if (idx>=0)
            {
                partitionsVector.get(idx).setP(proVec.get(i));
                Partitions split = partitionsVector.get(idx).Update_Create();
                if(split != null)
                    partitionsVector.add(split);
            }
            else
            {
                UnAllocatedProcesses.add(proVec.get(i));
                proVec.remove(i);
                System.out.println(proVec.get(i).getProcessName() + "can not be allocated");
            }
        }
    }

    public static void Compaction()
    {
        int size=0;
        for (int i=0;i<partitionsVector.size();i++)
        {
            if (partitionsVector.get(i).getP()==null)
            {
                size+=partitionsVector.get(i).getPartitionSize();
                partitionsVector.remove(i);
            }
        }
        Partitions newPar = new Partitions("p",size);
        partitionsVector.add(newPar);
        BestFit(UnAllocatedProcesses);
    }


    public static void main(String[] args) {

        Scanner input=  new Scanner(System.in);
        int  noOfPartitions,partitionSize,noOfProcesses,processSize;
        String partitionName,processName;
        System.out.println("Enter number of partitions ");
        noOfPartitions = input.nextInt();

        System.out.println("enter partition name & size respectively ");
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
        System.out.println("Enter number of Processes ");
        noOfProcesses=input.nextInt();

        System.out.println("enter processes name & size respectively ");
        for(int i=0;i<noOfProcesses;i++){
            processName= input.next();
            processSize=input.nextInt();
            Process process=new Process(processName,processSize);
            processVector.add(process);
        }
        BestFit(processVector);
        for (int i=0;i<partitionsVector.size();i++)
        {
            System.out.println(partitionsVector.get(i).getPartitionName()+" "+partitionsVector.get(i).getPartitionSize()+" ");
            if (partitionsVector.get(i).getP()!=null)
                System.out.println(partitionsVector.get(i).getP().getProcessName());
        }
        System.out.println(partitionsVector.get(6).getP()==null);
        System.out.println("Do you want to compact ? 1.yes 2.no ");
        int choice;
        choice = input.nextInt();
        if (choice==1)
            Compaction();


        for(int i=0;i<partitionsVector.size();i++)
        {
            System.out.println(partitionsVector.get(i).getPartitionName()+" "+partitionsVector.get(i).getPartitionSize());
        }
    }
}
//6
//p 90
//p 20
//p 5
//p 30 p 120 p 80
//4
//pro1 15
//pro2 90
//pro3 30
// pro4 100