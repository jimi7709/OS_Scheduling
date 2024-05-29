package MainPackageSCFS;

class Process {
    public int processID;
    public int arriveTime;
    public int burstTime;

    public Process(int processID, int arriveTime, int burstTime) {
        this.processID = processID;
        this.arriveTime = arriveTime;
        this.burstTime = burstTime;
    }
    
    
    public int getburstTime() {
    	return burstTime;
    }
}