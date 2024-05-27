package IntegratedMainPackageSJFpreemptive;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class ReadyQueueElement {
    public int processID;
    public int burstTime;
    public int waitingTime;

    public ReadyQueueElement(int processID, int burstTime, int waitingTime) {
        this.processID = processID;
        this.burstTime = burstTime;
        this.waitingTime = waitingTime;
    }
}

class Process {
    public int processID;
    public int arriveTime;
    public int burstTime;
    public int remainingTime;

    public Process(int processID, int arriveTime, int burstTime) {
        this.processID = processID;
        this.arriveTime = arriveTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }
}

class Result {
    private int processId;
    private int completionTime;
    private int turnaroundTime;
    private int waitingTime;

    public Result(int processId, int completionTime, int turnaroundTime, int waitingTime) {
        this.processId = processId;
        this.completionTime = completionTime;
        this.turnaroundTime = turnaroundTime;
        this.waitingTime = waitingTime;
    }

    public int getProcessId() {
        return processId;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    public int getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    @Override
    public String toString() {
        return "ProcessID: " + processId + ", CompletionTime: " + completionTime +
               ", TurnaroundTime: " + turnaroundTime + ", WaitingTime: " + waitingTime;
    }
}

class SchedulingAlgorithm {
    public static List<Result> run(List<Process> jobList, List<Result> resultList) {
        int runTime = 0;
        int completedProcesses = 0;
        int n = jobList.size();
        
        Process currentProcess = null;

        while (completedProcesses < n) {
            for (Process process : jobList) {
                if (process.arriveTime == runTime) {
                    if (currentProcess == null || process.remainingTime < currentProcess.remainingTime) {
                        currentProcess = process;
                    }
                }
            }

            if (currentProcess != null) {
                currentProcess.remainingTime--;

                if (currentProcess.remainingTime == 0) {
                    int completionTime = runTime + 1;
                    int turnaroundTime = completionTime - currentProcess.arriveTime;
                    int waitingTime = turnaroundTime - currentProcess.burstTime;
                    
                    resultList.add(new Result(currentProcess.processID, completionTime, turnaroundTime, waitingTime));
                    completedProcesses++;
                    currentProcess = null;
                }
            }

            runTime++;
        }

        return resultList;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Process> jobList = new ArrayList<>();
        List<Result> resultList = new ArrayList<>();

        System.out.println("Enter the number of processes:");
        int numProcesses = scanner.nextInt();

        for (int i = 0; i < numProcesses; i++) {
            System.out.println("Enter Process ID, Arrival Time, Burst Time for process " + (i + 1) + ":");
            int processID = scanner.nextInt();
            int arriveTime = scanner.nextInt();
            int burstTime = scanner.nextInt();
            jobList.add(new Process(processID, arriveTime, burstTime));
        }

        resultList = SchedulingAlgorithm.run(jobList, resultList);

        System.out.println("Scheduling Results:");
        for (Result result : resultList) {
            System.out.println(result);
        }

        scanner.close();
    }
}
