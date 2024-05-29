package CompletedSCFS;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		List<Process> jobList = new ArrayList<>();
		int exeTime = 0;
		int totalWait = 0;
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

		Scfs.run(jobList);

		System.out.println("Scheduling Results:");
		System.out.println("총 cpu 실행시간(execution time):" + exeTime);
		for (int i = 0; i < numProcesses; i++) {
			totalWait += jobList.get(i).waitingTime;
			System.out.println("ProcessID : " + jobList.get(i).processID + " CompletionTime : "
					+ jobList.get(i).completionTime + " WaitingTime : " + jobList.get(i).waitingTime
					+ " TurnaroundTime : " + jobList.get(i).turnaroundTime);
		}

		System.out.println("평균 대기시간:" + totalWait / numProcesses);

		scanner.close();
	}
}
