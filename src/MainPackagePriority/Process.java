package MainPackagePriority;

class Process {
    public int processID;
    public int arriveTime;
    public int burstTime;
    public int priority;//우선순위를 위해서 추가.
    
    public Process(int processID, int arriveTime, int burstTime,int priority) {
        this.processID = processID;
        this.arriveTime = arriveTime;
        this.burstTime = burstTime;
        this.priority = priority;
    }
    
    
    public int getburstTime() {
    	return burstTime;
    }


	public int getArriveTime() {
		// TODO Auto-generated method stub
		return 0;
	}


	public int getPriority() {
		// TODO Auto-generated method stub
		return 0;
	}


	public int getProcessID() {
		// TODO Auto-generated method stub
		return 0;
	}



}