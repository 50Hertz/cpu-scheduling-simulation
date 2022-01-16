package edu.nile.os.services.scheduling;

import edu.nile.os.domain.Process;

import java.util.Arrays;
import java.util.List;

// Java program to implement Round Robin
// Scheduling with different arrival time
public class RoundRobin {
    private final List<Process> processes;
    private final Integer timeQuantum;

    public RoundRobin(List<Process> processes, Integer timeQuantum) {
        this.processes = processes;
        this.timeQuantum = timeQuantum;
    }

    public List<Float> execute () {

        int n = processes.size();

        int i,qt,count=0,temp,sq=0,bt[],wt[],tat[],rem_bt[];
        float awt=0,atat=0;
        bt = new int[n];
        wt = new int[n];
        tat = new int[n];
        rem_bt = new int[n];

        for (i=0; i<n; i++) {
            bt[i] = Math.toIntExact(processes.get(i).getBurstTime());
            rem_bt[i] = bt[i];
        }

        qt = timeQuantum;

        while(true) {
            for (i=0,count=0;i<n;i++)
            {
                temp = qt;
                if(rem_bt[i] == 0) {
                    count++;
                    continue;
                }
                if(rem_bt[i]>qt)
                    rem_bt[i]= rem_bt[i] - qt;
                else
                if(rem_bt[i]>=0)
                {
                    temp = rem_bt[i];
                    rem_bt[i] = 0;
                }
                sq = sq + temp;
                tat[i] = sq;
            }
            if(n == count)
                break;
        }

        System.out.println("Round-Robin Scheduling algorithm: ");
        System.out.println("pid  burst  turn  waiting");
        for(i=0; i<n; i++) {
            wt[i]=tat[i]-bt[i];
            awt=awt+wt[i];
            atat=atat+tat[i];
            System.out.println(i+1 +"\t\t"+  bt[i]+"\t\t"+ tat[i] +"\t\t"+ wt[i]);
        }

        awt=awt/n;
        atat=atat/n;
        System.out.println("\nAverage waiting Time = "+awt);
        System.out.println("Average turnaround time = "+atat);

        return Arrays.asList(awt, atat);
    }
}
