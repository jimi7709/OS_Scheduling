package MainPackage;

public class Process {
    int processID;
    int arrivalTime;
    int burstTime;
    private int priority;

    public int getProcessId() {
        return processID;
    }

    public void setProcessId(int processId) {
        this.processID = processId;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
