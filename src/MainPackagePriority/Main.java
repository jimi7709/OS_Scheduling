package MainPackagePriority;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


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
            System.out.println("Enter Process ID, Arrival Time, Burst Time, Priority for process " + (i + 1) + ":");
            int processID = scanner.nextInt();
            int arriveTime = scanner.nextInt();
            int burstTime = scanner.nextInt();
            int priority = scanner.nextInt();//추
            exeTime += burstTime;
            jobList.add(new Process(processID, arriveTime, burstTime,priority));
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
