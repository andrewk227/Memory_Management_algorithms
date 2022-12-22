import java.util.Scanner;
import java.util.Vector;

public class Main {
    public static Vector<Partitions> partitionsVector=new Vector<>();
    public static Vector<Process> processVector=new Vector<>();
    public static Vector<Process> UnAllocatedProcesses=new Vector<>();
    static int externalFragmentsize;
    static Partitions partition;
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
                    partitionsVector.insertElementAt(split,idx+1);
            }
            else
            {
                UnAllocatedProcesses.add(proVec.get(i));
                proVec.remove(i);
                System.out.println(proVec.get(i).getProcessName() + "can not be allocated");
            }
        }
    }
    public static void worstFit(int maxPartitionSize,Vector <Process> proVec)
    {
       // externalFragmentName = partitionsVector.lastElement().getPartitionName();

        for (int i=0;i< proVec.size();i++)
        {
            for(int j=0;j< partitionsVector.size();j++)
            {
                if (proVec.get(i).getProcessSize() <= maxPartitionSize && partitionsVector.get(j).getPartitionSize()==maxPartitionSize)
                {
                    externalFragmentsize = partitionsVector.get(j).getPartitionSize() - proVec.get(i).getProcessSize();
                    partitionsVector.get(j).setPartitionSize(proVec.get(i).getProcessSize());
                    partitionsVector.get(j).setP(proVec.get(i));
                    partition = new Partitions("p", externalFragmentsize);
                    partitionsVector.add(j + 1, partition);
                    proVec.get(i).flag=true;
                    maxPartitionSize=0;
                    for (int k=0;k< partitionsVector.size();k++)
                    {
                        if(partitionsVector.get(k).getPartitionSize()>maxPartitionSize &&partitionsVector.get(k).getP()==null )
                        {
                            maxPartitionSize=partitionsVector.get(k).getPartitionSize();
                        }
                    }
                    break;
                }
            }
        }
        for(int i=0;i<proVec.size();i++)
        {
            if(proVec.get(i).flag==false)
            {
                UnAllocatedProcesses.add(proVec.get(i));
                System.out.println("process "+proVec.get(i).getProcessName()+" not allocated");
            }
        }
    }


    public static void FirstFit(Vector<Process> Pvector)
    {
       // externalFragmentName = partitionsVector.lastElement().getPartitionID();
        int processVectorSize=Pvector.size();
        for (int i = 0; i < processVectorSize; i++){
            for (int j = 0; j < partitionsVector.size(); j++) {
                if (Pvector.get(i).getProcessSize() <= partitionsVector.get(j).getPartitionSize()) {
                    externalFragmentsize = partitionsVector.get(j).getPartitionSize() - Pvector.get(i).getProcessSize();
                    partitionsVector.get(j).setPartitionSize(Pvector.get(i).getProcessSize());
                    partitionsVector.get(j).setP(Pvector.get(i));
                    partition = new Partitions("p", externalFragmentsize);
                    partitionsVector.add(j + 1, partition);
                    Pvector.get(i).flag=true;
                    break;
                }
            }
        }
        for(int i=0;i<Pvector.size();i++)
        {
            if(Pvector.get(i).flag==false)
            {
                UnAllocatedProcesses.add(Pvector.get(i));
                System.out.println("process "+Pvector.get(i).getProcessName()+" not allocated");
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
                i--;
            }
        }
        Partitions newPar = new Partitions("p",size);
        partitionsVector.add(newPar);
    }


    public static void main(String[] args) {

        Scanner input=  new Scanner(System.in);
        int  noOfPartitions,partitionSize,noOfProcesses,processSize,maxPartitionSize=0;
        String partitionName,processName;

        //Entering data of partitions
        System.out.println("Enter number of partitions ");
        noOfPartitions = input.nextInt();

        System.out.println("enter partition name & size respectively ");
        for(int i=0;i<noOfPartitions;i++)
        {
            partitionName=input.next();
            partitionSize=input.nextInt();
            if(partitionSize>maxPartitionSize)
            {maxPartitionSize=partitionSize;}
            Partitions p=new Partitions(partitionName,partitionSize);
            partitionsVector.add(p);
        }


        //Entering data of processes
        System.out.println("Enter number of Processes ");
        noOfProcesses=input.nextInt();

        System.out.println("enter processes name & size respectively ");
        for(int i=0;i<noOfProcesses;i++){
            processName= input.next();
            processSize=input.nextInt();
            Process process=new Process(processName,processSize);
            processVector.add(process);
        }


        //select which policy we will process
        System.out.println("Select the policy you want to apply:");
        System.out.println("1.First Fit");
        System.out.println("2.Best Fit");
        System.out.println("3.Worst Fit");

        int selector;
        selector=input.nextInt();
        if (selector == 1)
        {
            FirstFit(processVector);
        }
        else if(selector==2)
        {
            BestFit(processVector);
        }
        else if(selector==3)
        {
            worstFit(maxPartitionSize,processVector);
        }
        else
            System.out.println("Selection out of bound");



        //Print all details about memory management
        //and how the process allocated to partitions due to which algorithm we choose
        for (int i=0;i<partitionsVector.size();i++)
        {
            System.out.print(partitionsVector.get(i).getPartitionName()+"  ("+partitionsVector.get(i).getPartitionSize()+"KB)  "+"=>  ");
            if (partitionsVector.get(i).getP()!=null)
                System.out.println(partitionsVector.get(i).getP().getProcessName());
            else
                System.out.println("external fragment");
        }


        //Asking for compaction "as option for the user"
        System.out.println("Do you want to compact ? 1.yes 2.no ");
        int choice;
        choice = input.nextInt();
        if (choice==1)
        {
            Compaction();
            if (selector == 1)
            {
                FirstFit(UnAllocatedProcesses);
            }
            else if(selector==2)
            {
                BestFit(UnAllocatedProcesses);
            }
            else if(selector==3)
            {
                worstFit(maxPartitionSize,UnAllocatedProcesses);
            }
            else
                System.out.println("Selection out of bound");

        }

        for (int j = 0; j < partitionsVector.size(); j++) {
            if (partitionsVector.get(j).getP() != null)
                System.out.print(partitionsVector.get(j).getPartitionName() + "  (" + partitionsVector.get(j).getPartitionSize() + "KB)   "+"=>  " + partitionsVector.get(j).getP().getProcessName() + "\n");
            else {
                System.out.print(partitionsVector.get(j).getPartitionName() + "   (" + partitionsVector.get(j).getPartitionSize() + "KB)  " + "=>  " + "external fragment" + "\n");
            }
        }

    }
}
//6
////p 90
////p 20
////p 5
////p 30 p 120 p 80
////4
////pro1 15
////pro2 90
////pro3 30
//// pro4 100