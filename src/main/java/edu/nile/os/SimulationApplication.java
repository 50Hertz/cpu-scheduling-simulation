package edu.nile.os;

import edu.nile.os.services.SchedulingProcess;
import edu.nile.os.services.WriteDataToExcel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class SimulationApplication {

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter time quantum for round robin (q): ");
        int q = Integer.parseInt(reader.readLine());
        System.out.println();

        List<Integer> numbers = Arrays.asList(5, 10, 25, 50, 75, 100, 150, 200, 250, 300, 350, 400, 450, 500);

        Map<String, Object[]> wtMap = new TreeMap<>();
        Map<String, Object[]> tatMap = new TreeMap<>();

        for (Integer n : numbers) {
            SchedulingProcess s = new SchedulingProcess(Long.valueOf(n), q);
            s.runScheduling();

            List<Float> wtOutput = s.getWtOutput();
            List<Float> tatOutput = s.getTatOutput();

            wtMap.put(n.toString(), new Object[]{wtOutput.get(0).toString(), wtOutput.get(1).toString(), wtOutput.get(2).toString()});
            tatMap.put(n.toString(), new Object[]{tatOutput.get(0).toString(), tatOutput.get(1).toString(), tatOutput.get(2).toString()});
        }

        WriteDataToExcel wtExcel = new WriteDataToExcel(wtMap, "WT");
        wtExcel.generate();

        WriteDataToExcel tatExcel = new WriteDataToExcel(tatMap, "TAT");
        tatExcel.generate();

        reader.close();
    }
}
