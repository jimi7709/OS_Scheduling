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
    
    
    public int getburstTime() {
    	return burstTime;
    }
}

class Result {
    int processId;
    int completionTime;//프로세스가 끝난 시간
    private int turnaroundTime;//프로세스가 도착한 시점부터 끝날 때까지 걸린 시간
    int waitingTime;// 레디 큐에서 대기한 총 시간

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
        return "ProcessID: " + processId + ", CompletionTime(프로세스 별 실행 시간): " + completionTime +
               ", TurnaroundTime: " + turnaroundTime + ", WaitingTime: " + waitingTime;
    }
}

class SchedulingAlgorithm {
    public static List<Result> run(List<Process> jobList, List<Result> resultList) {
        int currentProcess = 0;//현재 프로세스
        int cpuTime = 0;//현재 프로세스가 cpu에서 실행된 시간
        int cpuDone = 0;//cpu에서  실행되어야할 총 시간
        int runTime = 0;//시스템 내의 시계

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

            if (currentProcess == 0) {//현재 실행중인 프로세스가 없고
                if (readyQueue.size() != 0) {//레디큐에서 기다리고 있는 프로세스가 있다면
                    ReadyQueueElement rq = readyQueue.get(0);//레디큐에 있는 프로세스를 가져온다.

                    Result result = new Result(rq.processID, runTime, rq.burstTime, rq.waitingTime);

                    resultList.add(result);//결과값을 리스트에 추가한다.

                    cpuDone = rq.burstTime;//실행된 시간을 계산한다.
                    cpuTime = 0;
                    currentProcess = rq.processID;
                    readyQueue.remove(0);
                }
            } else {//현재 실행중인 프로세스가 있을 때
                if (cpuTime == cpuDone) {//현재 프로세스가 실행된 시간과 현재 프로세스가 실행되어야할 총 시간이 같다면
                	for (Result result : resultList) {//결과를 저장하고 있는 리스트에서 
                        if (result.processId == currentProcess) {//현재 프로세스ID와 같은 프로세스가 결과리스트에 있을 때
                            result.completionTime = runTime;//완료시간은 현재 프로세스가 실행된 시간이된다.
                            break;
                        }
                    }
                    currentProcess = 0;//현재 실행중인 프로세스가 없음으로 된다.
                    continue;
                }
            }

            cpuTime++;//프로세스가 실행된 시간 증가
            runTime++;//cpu 총 실행 시간 증가

            for (ReadyQueueElement rqElement : readyQueue) {//레디큐에 있는 프로세스의 waiting time 증가.
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
        int exeTime = 0;
        int avgWait = 0;
        System.out.println("Enter the number of processes:");
        int numProcesses = scanner.nextInt();

        for (int i = 0; i < numProcesses; i++) {
            System.out.println("Enter Process ID, Arrival Time, Burst Time for process " + (i + 1) + ":");
            int processID = scanner.nextInt();
            int arriveTime = scanner.nextInt();
            int burstTime = scanner.nextInt();
            exeTime += burstTime;
            jobList.add(new Process(processID, arriveTime, burstTime));
        }
        
        resultList = SchedulingAlgorithm.run(jobList, resultList);
        
        System.out.println("Scheduling Results:");
        System.out.println("전체 실행시간:"+ exeTime);
        for (Result result : resultList) {
            System.out.println(result);
            avgWait += result.getWaitingTime();
        }
        System.out.println("평균 대기시간:"+avgWait/numProcesses);

        scanner.close();
    }
}
