package MainPackagePriority;

class ReadyQueueElement {
    public int processID;
    public int burstTime;
    public int waitingTime;
    public int priority;//우선순위를 위해 추가

    public ReadyQueueElement(int processID, int burstTime, int waitingTime,int priority) {
        this.processID = processID;
        this.burstTime = burstTime;
        this.waitingTime = waitingTime;
        this.priority = priority;
        
    }
}
