package edu.nile.os.services.scheduling;

import edu.nile.os.domain.Process;

import java.util.*;

public class SRTF {
    private final List<Process> processes;

    public SRTF(List<Process> processes) {
        this.processes = processes;
    }

    public List<Float> execute ()
    {
        int n= processes.size();
        int[] pid = new int[n]; // it takes pid of process
        int[] at = new int[n]; // at means arrival time
        int[] bt = new int[n]; // bt means burst time
        int[] ct = new int[n]; // ct means complete time
        int[] ta = new int[n];// ta means turn around time
        int[] wt = new int[n];  // wt means waiting time
        int[] f = new int[n];  // f means it is flag it checks process is completed or not
        int[] k = new int[n];   // it is also stores brust time
        int i;
        int st=0;
        int tot=0;
        float avgwt=0;
        float avgta=0;


        for (i = 0; i < processes.size(); i++) {
            pid[i] = processes.get(i).getPid();
            at[i] = Math.toIntExact(processes.get(i).getArrivalTime());
            bt[i] = Math.toIntExact(processes.get(i).getBurstTime());
            k[i]= bt[i];
            f[i]= 0;
        }

        while(true){
            int min=Integer.MAX_VALUE;
            int c=n;

            if (tot==n)
                break;

            for ( i=0;i<n;i++)
            {
                if ((at[i]<=st) && (f[i]==0) && (bt[i]<min))
                {
                    min=bt[i];
                    c=i;
                }
            }

            if (c==n)
                st++;
            else
            {
                bt[c]--;
                st++;
                if (bt[c]==0)
                {
                    ct[c]= st;
                    f[c]=1;
                    tot++;
                }
            }
        }

        for(i=0;i<n;i++)
        {
            ta[i] = ct[i] - at[i];
            wt[i] = ta[i] - k[i];
            avgwt+= wt[i];
            avgta+= ta[i];
        }

        System.out.println("Shortest Remaining Job First (Preemptive) Scheduling algorithm: ");
        System.out.println("pid  arrival  burst  complete turn waiting");
        for(i=0;i<n;i++)
        {
            System.out.println(pid[i] +"\t\t"+  at[i]+"\t\t"+ k[i] +"\t\t"+ ct[i] +"\t\t"+ ta[i] +"\t\t"+ wt[i]);
        }

        avgta = avgta/n;
        avgwt = avgwt/n;

        System.out.println("\naverage tat is "+ (avgta));
        System.out.println("average wt is "+ (avgwt));

        return Arrays.asList(avgwt, avgta);
    }
}
