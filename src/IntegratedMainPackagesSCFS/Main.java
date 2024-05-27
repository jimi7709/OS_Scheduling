package IntegratedMainPackagesSCFS;

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

    public Process(int processID, int arriveTime, int burstTime) {
        this.processID = processID;
        this.arriveTime = arriveTime;
        this.burstTime = burstTime;
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
        int currentProcess = 0;
        int cpuTime = 0;
        int cpuDone = 0;
        int runTime = 0;

        List<ReadyQueueElement> readyQueue = new ArrayList<>();

        while (jobList.size() != 0 || readyQueue.size() != 0 || currentProcess != 0) {
            while (jobList.size() != 0) {
                Process frontJob = jobList.get(0);
                if (frontJob.arriveTime == runTime) {
                    readyQueue.add(new ReadyQueueElement(frontJob.processID, frontJob.burstTime, 0));
                    jobList.remove(0);
                } else {
                    break;
                }
            }

            if (currentProcess == 0) {
                if (readyQueue.size() != 0) {
                    ReadyQueueElement rq = readyQueue.get(0);

                    Result result = new Result(rq.processID, runTime, rq.burstTime, rq.waitingTime);

                    resultList.add(result);

                    cpuDone = rq.burstTime;
                    cpuTime = 0;
                    currentProcess = rq.processID;
                    readyQueue.remove(0);
                }
            } else {
                if (cpuTime == cpuDone) {
                    currentProcess = 0;
                    continue;
                }
            }

            cpuTime++;
            runTime++;

            for (ReadyQueueElement rqElement : readyQueue) {
                rqElement.waitingTime++;
            }
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
