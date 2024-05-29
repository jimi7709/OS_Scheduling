package MainPackagePriority;

import java.util.ArrayList;
import java.util.List;


class SchedulingAlgorithm {
    public static List<Result> run(List<Process> jobList, List<Result> resultList) {
        int currentProcess = 0;//현재 프로세스
        int cpuTime = 0;//현재 프로세스가 cpu에서 실행된 시간
        int cpuDone = 0;//cpu에서  실행되어야할 총 시간
        int runTime = 0;//시스템 내의 시계

        List<ReadyQueueElement> readyQueue = new ArrayList<>();

        while (jobList.size() != 0 || readyQueue.size() != 0 || currentProcess != 0) {
            //while (jobList.size() != 0) {
            	for(int i=0; i<jobList.size(); i++) {
                Process frontJob = jobList.get(i);
                if (frontJob.arriveTime == runTime) {
                    readyQueue.add(new ReadyQueueElement(frontJob.processID, frontJob.arriveTime, frontJob
                    .burstTime,frontJob.priority));
                    jobList.remove(0);
                } else {
                    break;
                }
           // }

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
            } 
            else {//현재 실행중인 프로세스가 있을 때
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

    }
        return resultList;
    }
}
