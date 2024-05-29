package MainPackageSCFS;

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
