package assignments;

import java.io.FileWriter;
import java.util.Arrays;

public class ExperimentRunner2 {

    static int[] pList = {1, 2, 4, 8, 12, 16};
    static int NUM_RUNS = 5;

    // STRONG scaling
    static int STRONG_TOTAL = 20_000_000;

    // WEAK scaling
    static int WEAK_LOCAL = 5_000_000;

    public static void main(String[] args) throws Exception {

        FileWriter strongCSV = new FileWriter("pi_strong_scaling.csv");
        strongCSV.write("p,nTotal,median_ns\n");

        FileWriter weakCSV = new FileWriter("pi_weak_scaling.csv");
        weakCSV.write("p,nTotal,median_ns\n");


        // ------------------------
        // STRONG SCALING
        // ------------------------
        for (int p : pList) {

            long[] times = new long[NUM_RUNS];

            int workPerWorker = STRONG_TOTAL / p;

            for (int i = 0; i < NUM_RUNS; i++) {

                Master master = new Master();

                long start = System.nanoTime();
                master.doRun(workPerWorker, p);
                long end = System.nanoTime();

                times[i] = end - start;
            }

            Arrays.sort(times);
            long median = times[NUM_RUNS / 2];

            strongCSV.write(p + "," + STRONG_TOTAL + "," + median + "\n");
            strongCSV.flush();
        }


        // ------------------------
        // WEAK SCALING
        // ------------------------
        for (int p : pList) {

            long[] times = new long[NUM_RUNS];

            int workPerWorker = WEAK_LOCAL;

            for (int i = 0; i < NUM_RUNS; i++) {

                Master master = new Master();

                long start = System.nanoTime();
                master.doRun(workPerWorker, p);
                long end = System.nanoTime();

                times[i] = end - start;
            }

            Arrays.sort(times);
            long median = times[NUM_RUNS / 2];

            int nTotal = WEAK_LOCAL * p;
            weakCSV.write(p + "," + nTotal + "," + median + "\n");
            weakCSV.flush();
        }

        strongCSV.close();
        weakCSV.close();

        System.out.println("Expériences terminées. CSV générés !");
    }
}
