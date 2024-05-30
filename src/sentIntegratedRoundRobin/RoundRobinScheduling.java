package sentIntegratedRoundRobin;


import java.util.*;

class Process {
    int processnum, burstTime, arrivetime, waitingtime, turnaroundtime, responsetime, remaineburstTime;
    boolean isFirstResponse;

    public Process(int processnum, int burstTime, int arrivetime) {
        this.processnum = processnum;
        this.burstTime = burstTime;
        this.arrivetime = arrivetime;
        this.waitingtime = 0;
        this.turnaroundtime = 0;
        this.responsetime = -1;  
        this.remaineburstTime = burstTime; 
        this.isFirstResponse = true; 
    }
}

public class RoundRobinScheduling {
    static int n, tq;
    static ArrayList<Process> processes = new ArrayList<>();
    static ArrayList<Integer> ganttChart = new ArrayList<>();
    static int totalExecutionTime = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Process의 개수를 입력하시오: ");
        n = sc.nextInt();

        for (int i = 0; i < n; i++) {
            System.out.print("Process " + (i + 1) + "의 BurstTime, ArriveTime을 입력하시오: ");
            int bt = sc.nextInt();
            int at = sc.nextInt();
            processes.add(new Process(i + 1, bt, at));
        }

        System.out.print("Timeslice를 입력하시오: ");
        tq = sc.nextInt();

        roundRobinScheduling();
        printResults();
        printGanttChart();
    }

    static void roundRobinScheduling() {
        int time = 0;
        Queue<Process> readyQueue = new LinkedList<>();
        ArrayList<Process> completedProcesses = new ArrayList<>();

        while (true) {
            boolean allCompleted = true;
            for (Process p : processes) {
                if (p.arrivetime <= time && !completedProcesses.contains(p) && !readyQueue.contains(p)) {
                    readyQueue.add(p);
                }
                if (p.remaineburstTime > 0) {
                    allCompleted = false;
                }
            }

            if (readyQueue.isEmpty() && allCompleted) {
                break;
            }

            if (!readyQueue.isEmpty()) {
                Process currentProcess = readyQueue.poll();
                if (currentProcess.isFirstResponse) {
                    currentProcess.responsetime = time - currentProcess.arrivetime; 
                    currentProcess.isFirstResponse = false;
                }
                int execTime = Math.min(currentProcess.remaineburstTime, tq);
                for (int i = 0; i < execTime; i++) {
                    ganttChart.add(currentProcess.processnum);
                    time++;
                }
                currentProcess.remaineburstTime -= execTime;

                if (currentProcess.remaineburstTime == 0) {
                    currentProcess.turnaroundtime = time - currentProcess.arrivetime;
                    currentProcess.waitingtime = currentProcess.turnaroundtime - currentProcess.burstTime;
                    completedProcesses.add(currentProcess);
                } else {
                    readyQueue.add(currentProcess);
                }
            } else {
                ganttChart.add(0);
                time++;
            }
        }

        totalExecutionTime = time; 
    }

    static void printResults() {
        int totalwaitingtime = 0, totalturnaroundtime = 0, totalresponsetime = 0;
        System.out.println("\n=== 결과 ===");
        for (Process p : processes) {
            System.out.println("Process " + p.processnum + " turnaroundTime: " + p.turnaroundtime + " waitingTime: " + p.waitingtime + " responseTime: " + p.responsetime);
            totalwaitingtime += p.waitingtime;
            totalturnaroundtime += p.turnaroundtime;
            totalresponsetime += p.responsetime;
        }
        System.out.println("\n전체 실행 시간: " + totalExecutionTime);
        System.out.println("평균 대기 시간: " + (double) totalwaitingtime / n);
        System.out.println("평균 실행 시간: " + (double) totalturnaroundtime / n);
        System.out.println("평균 응답 시간: " + (double) totalresponsetime / n);
        System.out.println("Throughput: " + (double) n / totalExecutionTime);
    }

    static void printGanttChart() {
        System.out.print("간트 차트\n");
        for (int processnum : ganttChart) {
            if (processnum == 0) {
                System.out.print("_ ");
            } else {
                System.out.print(processnum + " ");
            }
        }
        System.out.println();
    }
}
