package CompletedSCFS;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Scfs {
	public static void run(List<Process> jobList) {
		int runTime = 0;// 시스템 내의 시계


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
					if(i<jobList.size()-1)//다음 프로세스가 존재한다면
						if(jobList.get(i+1).arriveTime<= runTime)//현재 프로세스가 실행됐고, 다음 프로세스가 미리 와서 기다리고 있다면
							jobList.get(i+1).waitingTime += (runTime - jobList.get(i+1).arriveTime);
				}
				else {//arrriveTime> runTime 아직 프로세스가 도착하지 않아서 실행불가능하면
					runTime += jobList.get(i).arriveTime; //프로세스 도착시간만큼의 시간이 흐르고
					runTime += jobList.get(i).burstTime;// burstTime 만큼 runtime 증시킨다.(프로세스 실행됨.)
					if(i<jobList.size()-1)//다음 프로세스가 존재한다면
						if(jobList.get(i+1).arriveTime<= runTime)//현재 프로세스가 실행됐고, 다음 프로세스가 미리 와서 기다리고 있다면
							jobList.get(i+1).waitingTime += (runTime - jobList.get(i+1).arriveTime);
				}
				jobList.get(i).completionTime = runTime;
				jobList.get(i).turnaroundTime = runTime - jobList.get(i).arriveTime;
			}

		
		}
	}
