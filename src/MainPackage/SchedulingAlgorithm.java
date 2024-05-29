package MainPackage;

import java.util.ArrayList;
import java.util.List;


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
                if (frontJob.arrivalTime == runTime) {
                    readyQueue.add(new ReadyQueueElement(frontJob.processID, frontJob.burstTime, 0));
                    jobList.remove(0);
                } else {
                    break;
                }
            }

            if (currentProcess == 0) {
                if (readyQueue.size() != 0) {
                    ReadyQueueElement rq = readyQueue.get(0);
                    Result result = new Result(rq.processID, runTime, rq.burstTime,rq.waitingTime);
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

            for (ReadyQueueElement rq : readyQueue) {
                rq.waitingTime++;
            }
        }

        return resultList;
    }
}
