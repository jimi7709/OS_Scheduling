package IntegratedPriorityNonemptive;


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
}

class Priority {
	public static void run(List<Process> jobList) {
		int runTime = 0;// 시스템 내의 시계
		
		//우선순위가 높은 프로세스 찾기
		int hiPr= -1; //우선순위가 높은 프로세스의 우선순위 값
		int hiPrIn= 0;//우선순위가 높은 프로세스의 인덱스
		for(int i=1; i<jobList.size(); i++) { 
			if(hiPr ==-1)//비교를 위한 변수 초기화
				hiPr = jobList.get(0).priority;
			if(hiPr < jobList.get(i).priority) {
				hiPr = jobList.get(i).priority;
				hiPrIn = i;
			}
			else if(hiPr == jobList.get(i).priority)//우선순위가 같다면 (FCFS방식으로 동작)
			{
				if(jobList.get(hiPrIn).arriveTime > jobList.get(i).arriveTime)//도착시간이 더 적은 프로세스가 실행되어야 할 프로세스가 된다.
				{
					hiPrIn = i;
				}
			}
			
			
			//우선순위 높은 프로세스 실행
			int lowArri;//hiPrIn 프로세스의 도착시간
			int lowArriIn = hiPrIn; //프로세스가 아직 도착하지 않았을 떄, 도착시간이 더 적은 프로세스 인덱스

			if(jobList.get(hiPrIn).arriveTime<=runTime) {//프로세스가 실행가능하다면
				runTime += jobList.get(hiPrIn).burstTime;
				
				if(i<jobList.size()-1)
					if(jobList.get(i+1).arriveTime<= runTime)//현재 프로세스가 실행됐고, 다음 프로세스가 미리 와서 기다리고 있다면
						jobList.get(i+1).waitingTime += (runTime - jobList.get(i+1).arriveTime);
				
			}
			else {//프로세스가 아직 도착하지 않았다면
				for(int j=0; j<jobList.size(); j++) {//hiPr 프로세스의 도착시간보다 적은 도착시간을 가진 프로세스를 찾기
					lowArri = jobList.get(hiPrIn).arriveTime;
					if(lowArri >= jobList.get(j).arriveTime){//j 프로세스의 도착시간이 더 적다면
						lowArri = jobList.get(j).arriveTime;
						lowArriIn = j;
					}
					else {//hiPrIn 프로세스의 도착시간이 더 적다면
						runTime += jobList.get(hiPrIn).arriveTime;
						runTime += jobList.get(hiPrIn).burstTime;
					}
				}
				
			}
				jobList.get(lowArriIn).completionTime = runTime;
				jobList.get(lowArriIn).turnaroundTime = runTime - jobList.get(lowArriIn).arriveTime;
				
			
			
		}
		
		
		
		
		// 도착시간을 기준으로 프로세스들을 정렬--(이론적으로 scfs는 프로세스 도착시간을 기준으로 스케줄링 하므로)
		Collections.sort(jobList, new Comparator<Process>() {
			@Override
			public int compare(Process p1, Process p2) {
				return Integer.compare(p1.getArriveTime(), p2.getArriveTime());
			}
		});
		
			for (int i = 0; i < jobList.size(); i++) {
				if (jobList.get(i).arriveTime <= runTime) {// 프로세스가 도착해서 실행가능하면
					runTime += jobList.get(i).burstTime;// burstTime 만큼 runtime 증시킨다.(프로세스 실행됨.)
					if(i<jobList.size()-1)
						if(jobList.get(i+1).arriveTime<= runTime)//현재 프로세스가 실행됐고, 다음 프로세스가 미리 와서 기다리고 있다면
							jobList.get(i+1).waitingTime += (runTime - jobList.get(i+1).arriveTime);
				}
				else {//arrriveTime> runTime 아직 프로세스가 도착하지 않아서 실행불가능하면
					runTime += jobList.get(i).arriveTime; //프로세스 도착시간만큼의 시간이 흐르고
					runTime += jobList.get(i).burstTime;// burstTime 만큼 runtime 증시킨다.(프로세스 실행됨.)
					if(i<jobList.size()-1)
						if(jobList.get(i+1).arriveTime<= runTime)//현재 프로세스가 실행됐고, 다음 프로세스가 미리 와서 기다리고 있다면
							jobList.get(i+1).waitingTime += (runTime - jobList.get(i+1).arriveTime);
				}
				jobList.get(i).completionTime = runTime;
				jobList.get(i).turnaroundTime = runTime - jobList.get(i).arriveTime;
			}

		
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
			System.out.println("Enter Process ID, Arrival Time, Burst Time for process " + (i + 1) + ":");
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
