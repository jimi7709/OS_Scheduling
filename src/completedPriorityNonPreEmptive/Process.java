package completedPriorityNonPreEmptive;

class Process {
	public int turnaroundTime;
	public int processID;
	public int arriveTime;
	public int burstTime;
	public int waitingTime;
	public int completionTime;
	public int priority;

	public Process(int processID, int arriveTime, int burstTime, int priority) {
		this.processID = processID;
		this.arriveTime = arriveTime;
		this.burstTime = burstTime;
		this.priority = priority;
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

	public int getPriority() {
		return priority;
	}
}