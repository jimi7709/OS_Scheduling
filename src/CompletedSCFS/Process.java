package CompletedSCFS;

class Process {
	public int turnaroundTime;
	public int processID;
	public int arriveTime;
	public int burstTime;
	public int waitingTime;
	public int completionTime;

	public Process(int processID, int arriveTime, int burstTime) {
		this.processID = processID;
		this.arriveTime = arriveTime;
		this.burstTime = burstTime;
	}

	public int getburstTime() {
		return burstTime;
	}

	public int getWaitingTime() {
		return waitingTime;
	}
	public int getArriveTime() {
		return arriveTime;
	}
}
