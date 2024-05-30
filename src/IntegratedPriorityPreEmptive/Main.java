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
		int runTime = 0;// 시스템 내의 시계

		List<Process> temp = new ArrayList<>();
		temp.addAll(jobList);

		Collections.sort(temp, new Comparator<Process>() {// 전체 스케줄링에는 아무 영향 없지만 초기값 세팅을 위해서 정렬 사용.
			@Override
			public int compare(Process p1, Process p2) {
				return Integer.compare(p1.getArriveTime(), p2.getArriveTime());// 내림차순 고려해서 반대로 반환.
			}
		});

		Process pi;// 초기값
		int piIn;// 초기값
		int k = 0; // 반복문 제어변수
		// while (temp.size() != 0) {// 모든 프로세스가 처리될때까지 반복

		for (int i = 1; i < temp.size(); i++) {// 우선순위가 가장 큰 거 찾기
			pi = temp.get(0);
			piIn = 0;
			if (i <= temp.size() - 1) {// 최대 인덱스 고려
				if (temp.get(i).arriveTime <= runTime) {// 도착O && 우선순위가 더 크다면
					if (pi.priority < temp.get(i).priority) {
						pi = temp.get(i);
						piIn = i;
					}
					else {//다음 프로세스가 도착해있지만, 우선순위가 같거나 작다면
						//jobList.get(i).waitingTime += temp.get(i).
					}
				} // 도착O && 우선순위가 더 크다면)
				//도착 X인 경우는 프로세스가 없으므로 고려하지 않아도 됨.
				else{
					runTime += pi.arriveTime;
				}

			} // 최대 인덱스 고려

			while (pi.burstTime != 0) {// 특정프로세스가 실행 동안 처리
				runTime += 1;
				temp.get(piIn).burstTime = temp.get(piIn).burstTime - 1;
				if (k != piIn) {
					if ((pi.priority < temp.get(k).priority) && (temp.get(k).arriveTime <= runTime)) {
						jobList.get(piIn).waitingTime += (temp.get(k).burstTime - temp.get(piIn).arriveTime);
						pi = temp.get(k);
						piIn = k;
						k++;

					}
					if((pi.priority >= temp.get(k).priority) && (temp.get(k).arriveTime <= runTime)) {
						jobList.get(k).waitingTime += 1;
					}
				}
			} // 특정프로세스가 실행가능할동안 처리
			jobList.get(piIn).completionTime = runTime;
			jobList.get(piIn).turnaroundTime = runTime - temp.get(piIn).arriveTime;
			temp.remove(piIn);
			k=0;
			
		} // 도착시간이 가장 빠르고 우선순위가 가장 큰 프로세스에 대한 반복문
		// } // 모든 프로세스가 처리될때까지 반복


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
}}
