package IntegratedPriorityPreEmptive;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

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

class Priority {
	public static void run(List<Process> jobList) {
		// 우선순위 순서대로 정렬
		Collections.sort(jobList, new Comparator<Process>() {
			@Override
			public int compare(Process p1, Process p2) {
				return Integer.compare(p2.getPriority(), p1.getPriority());// 내림차순 고려해서 반대로 반환.
			}
		});

		int runTime = 0;// 시스템 내의 시계

		// 우선순위가 높은 프로세스 찾기
		for (int i = 0; i < jobList.size(); i++) {
			if (i+1 <= jobList.size() - 1) {
				if (jobList.get(i).priority == jobList.get(i + 1).priority)// 우선순위가 같다면  
				{
					if (jobList.get(i).arriveTime <= jobList.get(i + 1).arriveTime)// i의 도착시간이 더 적다면
					{
						if (jobList.get(i).arriveTime <= runTime) {// 프로세스 도착 O
							runTime += jobList.get(i).burstTime;// 프로세스 실행
							if (i+1 <= jobList.size() - 1)
								if (jobList.get(i + 1).arriveTime <= runTime)// 현재 프로세스가 실행됐고, 다음 프로세스가 미리 와서 기다리고 있다면
									jobList.get(i + 1).waitingTime += (runTime - jobList.get(i + 1).arriveTime);
						} else {// 프로세스 도착 X
							runTime += jobList.get(i).arriveTime;// 도착시간까지 시스템이 흘러간다.
							runTime += jobList.get(i).burstTime;// 도착시간에 도달했으므로 프로세스를 실행.
							if (i+1 <= jobList.size() - 1)
								if (jobList.get(i + 1).arriveTime <= runTime)// 현재 프로세스가 실행됐고, 다음 프로세스가 미리 와서 기다리고 있다면
									jobList.get(i + 1).waitingTime += (runTime - jobList.get(i + 1).arriveTime);
						}
						jobList.get(i).completionTime = runTime;
						jobList.get(i).turnaroundTime = runTime - jobList.get(i).arriveTime;
					} 
					else {// i+1의 도착시간이 더 적다면
						int index1 = i;
						int index2 = i + 1;
						swap(jobList, index1, index2);
				//---------------------
						if (jobList.get(i).arriveTime <= runTime) {// 프로세스 도착 O
							runTime += jobList.get(i).burstTime;// 프로세스 실행
							if (i+1 <= jobList.size() - 1)
								if (jobList.get(i + 1).arriveTime <= runTime)// 현재 프로세스가 실행됐고, 다음 프로세스가 미리 와서 기다리고 있다면
									jobList.get(i + 1).waitingTime += (runTime - jobList.get(i + 1).arriveTime);
						} else {// 프로세스 도착 X
							runTime += jobList.get(i).arriveTime;// 도착시간까지 시스템이 흘러간다.
							runTime += jobList.get(i).burstTime;// 도착시간에 도달했으므로 프로세스를 실행.
							if (i+1 <= jobList.size() - 1)
								if (jobList.get(i + 1).arriveTime <= runTime)// 현재 프로세스가 실행됐고, 다음 프로세스가 미리 와서 기다리고 있다면
									jobList.get(i + 1).waitingTime += (runTime - jobList.get(i + 1).arriveTime);
						}
						jobList.get(i).completionTime = runTime;
						jobList.get(i).turnaroundTime = runTime - jobList.get(i).arriveTime;	
				
				//-----------------------
					
					}// i+1의 도착시간이 더 적다면
				}//우선순위가 같다면
				else {//우선순위가 다르다면
					if(jobList.get(i).arriveTime<=runTime) {//프로세스가 도착 O
						runTime += jobList.get(i).burstTime;
						if(i+1 <= jobList.size()-1) {
							jobList.get(i+1).waitingTime += (runTime - jobList.get(i + 1).arriveTime);
						}
					}
					else {//프로세스가 도착X
						runTime += jobList.get(i).arriveTime;
						runTime += jobList.get(i).burstTime;
						if(i+1 <= jobList.size()-1) {
							jobList.get(i+1).waitingTime += (runTime - jobList.get(i + 1).arriveTime);
						}
					}
					jobList.get(i).completionTime = runTime;
					jobList.get(i).turnaroundTime = runTime - jobList.get(i).arriveTime;
				}
				
			}//다음 프로세스가 존재한다면
			else {//다음 프로세스가 없다면(i가 최대인덱스와 같다면)
				if(i==jobList.size()-1)
				{
					if(jobList.get(i).arriveTime<=runTime) {//프로세스가 도착 O
						runTime += jobList.get(i).burstTime;
					}
					else {//프로세스가 도착X
						runTime += jobList.get(i).arriveTime;
						runTime += jobList.get(i).burstTime;
					}
					jobList.get(i).completionTime = runTime;
					jobList.get(i).turnaroundTime = runTime - jobList.get(i).arriveTime;
				}
			}
		}//모든 프로세스들에 대햐여서
	}

	public static <T> void swap(List<Process> temp1, int index1, int index2) {// 두 요소 교환
		Process val = temp1.get(index1);
		temp1.set(index1, temp1.get(index2));
		temp1.set(index2, val);
	}
}

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		List<Process> jobList = new ArrayList<>();
		int exeTime = 0;
		int totalWait = 0;
		System.out.println("Enter the number of processes:");
		int numProcesses = scanner.nextInt();

		for (int i = 0; i < numProcesses; i++) {
			System.out.println("Enter Process ID, Arrival Time, Burst Time,Priority for process " + (i + 1) + ":");
			int processID = scanner.nextInt();
			int arriveTime = scanner.nextInt();
			int burstTime = scanner.nextInt();
			int priority = scanner.nextInt();
			exeTime += burstTime;
			jobList.add(new Process(processID, arriveTime, burstTime, priority));
		}

		Priority.run(jobList);

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
