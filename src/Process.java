public class Process {
    private String processName;
    private int processSize;
    public Process(String processName, int processSize)
    {
        this.processName=processName;
        this.processSize=processSize;
    }
    String getProcessName() {return processName;}
    int getProcessSize(){return processSize;}
    void setProcessName(String partitionName){this.processName=processName;}
    void setProcessSize(int processSize){this.processSize=processSize;}
}
