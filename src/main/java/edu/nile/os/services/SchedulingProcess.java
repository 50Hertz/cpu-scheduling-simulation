package edu.nile.os.services;

import edu.nile.os.domain.Process;
import edu.nile.os.services.scheduling.PriorityNonPreemptive;
import edu.nile.os.services.scheduling.RoundRobin;
import edu.nile.os.services.scheduling.SRTF;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

public class SchedulingProcess {
    private final Random rand = SecureRandom.getInstanceStrong();
    private static final NormalDistributionGenerator generator = new NormalDistributionGenerator();
    private final Long numberOfProcesses;
    private final Integer timeQuantum;
    private List<Process> processes;
    private List<Float> wtOutput = new ArrayList<>();
    private List<Float> tatOutput = new ArrayList<>();


    public SchedulingProcess(Long numberOfProcesses, Integer timeQuantum) throws NoSuchAlgorithmException {
        this.numberOfProcesses = numberOfProcesses;
        this.timeQuantum = timeQuantum;

        List<Long> arrivalTimes = new ArrayList<>();
        this.processes = new ArrayList<>();

        for (int i = 0; i < numberOfProcesses; i++) {
            arrivalTimes.add(generator.sample());
        }

        arrivalTimes.sort(Comparator.naturalOrder());
        int count = 1;
        for (Long l : arrivalTimes) {
            Process p = Process.builder()
                    .pid(count)
                    .burstTime(generator.sample())
                    .arrivalTime(l)
                    .priority(getPriority())
                    .build();

            this.processes.add(p);
            count++;
        }
    }

    public void runScheduling() {
        executeSRTF();
        System.out.println();
        executePriority();
        System.out.println();
        executeRoundRobin();
        System.out.println();
    }

    public List<Float> getWtOutput() {
        return wtOutput;
    }

    public List<Float> getTatOutput() {
        return tatOutput;
    }

    private void executeSRTF() {
        SRTF srtf = new SRTF(processes);
        List<Float> floats = srtf.execute();
        wtOutput.add(floats.get(0));
        tatOutput.add(floats.get(1));
    }

    private void executePriority() {
        PriorityNonPreemptive priorityNonPreemptive = new PriorityNonPreemptive(processes);
        List<Float> floats = priorityNonPreemptive.execute();
        wtOutput.add(floats.get(0));
        tatOutput.add(floats.get(1));
    }

    private void executeRoundRobin() {
        RoundRobin roundRobin = new RoundRobin(processes, timeQuantum);
        List<Float> floats = roundRobin.execute();
        wtOutput.add(floats.get(0));
        tatOutput.add(floats.get(1));
    }

    private Integer getPriority() {
        int min = 1;
        int max = Math.toIntExact(numberOfProcesses + 1);

        return rand.nextInt(max - min) + min;
    }
}
