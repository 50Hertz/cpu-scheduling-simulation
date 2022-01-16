package edu.nile.os.services.scheduling;

import edu.nile.os.domain.Process;

import java.util.Arrays;
import java.util.List;

public class PriorityNonPreemptive {

    int[] burstTime;
    int[] priority;
    int[] arrivalTime;
    String[] processId;
    int numberOfProcess;

    public PriorityNonPreemptive(List<Process> processes) {
        numberOfProcess = processes.size();
        burstTime = new int[numberOfProcess];
        priority = new int[numberOfProcess];
        arrivalTime = new int[numberOfProcess];
        processId = new String[numberOfProcess];
        String st = "P";

        for (int i = 0; i < numberOfProcess; i++) {
            processId[i] = st.concat(Integer.toString(processes.get(i).getPid()));
            burstTime[i] = Math.toIntExact(processes.get(i).getBurstTime());
            arrivalTime[i] = Math.toIntExact(processes.get(i).getArrivalTime());
            priority[i] = processes.get(i).getPriority();
        }
    }

    void sortAccordingArrivalTimeAndPriority(int[] at, int[] bt, int[] prt, String[] pid) {
        int temp;
        String stemp;
        for (int i = 0; i < numberOfProcess; i++)
        {

            for (int j = 0; j < numberOfProcess - i - 1; j++)
            {
                if (at[j] > at[j + 1])
                {
                    //swapping arrival time
                    temp = at[j];
                    at[j] = at[j + 1];
                    at[j + 1] = temp;

                    //swapping burst time
                    temp = bt[j];
                    bt[j] = bt[j + 1];
                    bt[j + 1] = temp;

                    //swapping priority
                    temp = prt[j];
                    prt[j] = prt[j + 1];
                    prt[j + 1] = temp;

                    //swapping process identity
                    stemp = pid[j];
                    pid[j] = pid[j + 1];
                    pid[j + 1] = stemp;

                }
                //sorting according to priority when arrival timings are same
                if (at[j] == at[j + 1])
                {
                    if (prt[j] > prt[j + 1])
                    {
                        //swapping arrival time
                        temp = at[j];
                        at[j] = at[j + 1];
                        at[j + 1] = temp;

                        //swapping burst time
                        temp = bt[j];
                        bt[j] = bt[j + 1];
                        bt[j + 1] = temp;

                        //swapping priority
                        temp = prt[j];
                        prt[j] = prt[j + 1];
                        prt[j + 1] = temp;

                        //swapping process identity
                        stemp = pid[j];
                        pid[j] = pid[j + 1];
                        pid[j + 1] = stemp;

                    }
                }
            }

        }
    }

    public List<Float> execute() {
        int[] finishTime = new int[numberOfProcess];
        int[] bt = burstTime.clone();
        int[] at = arrivalTime.clone();
        int[] prt = priority.clone();
        String[] pid = processId.clone();
        int[] waitingTime = new int[numberOfProcess];
        int[] turnAroundTime = new int[numberOfProcess];

        sortAccordingArrivalTimeAndPriority(at, bt, prt, pid);

        //calculating waiting & turn-around time for each process
        finishTime[0] = at[0] + bt[0];
        turnAroundTime[0] = finishTime[0] - at[0];
        waitingTime[0] = turnAroundTime[0] - bt[0];

        for (int i = 1; i < numberOfProcess; i++)
        {
            finishTime[i] = bt[i] + finishTime[i - 1];
            turnAroundTime[i] = finishTime[i] - at[i];
            waitingTime[i] = turnAroundTime[i] - bt[i];
        }
        float sum = 0;
        for (int n : waitingTime)
        {
            sum += n;
        }
        float averageWaitingTime = sum / numberOfProcess;

        sum = 0;
        for (int n : turnAroundTime)
        {
            sum += n;
        }
        float averageTurnAroundTime = sum / numberOfProcess;

        //print on console the order of processes along with their finish time & turn around time
        System.out.println("Priority Non-Preemptive Scheduling algorithm: ");
        System.out.format("%s%20s%20s%20s%20s%20s%20s\n", "ProcessId", "BurstTime", "ArrivalTime", "Priority", "FinishTime", "WaitingTime", "TurnAroundTime");
        for (int i = 0; i < numberOfProcess; i++) {
            System.out.format("%s%20d%20d%20d%20d%20d%20d\n", pid[i], bt[i], at[i], prt[i], finishTime[i], waitingTime[i], turnAroundTime[i]);
        }

        System.out.format("%85s%20f%20f\n", "Average", averageWaitingTime, averageTurnAroundTime);

        return Arrays.asList(averageWaitingTime, averageTurnAroundTime);
    }

}
