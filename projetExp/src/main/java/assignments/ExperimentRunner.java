package assignments;

import java.io.FileWriter;
import java.util.Arrays;

public class ExperimentRunner {

    static int[] pList = {1, 2, 4, 8, 12, 16, 20};
    static int strongNThrows = 100000;
    static int weakBase = 500000;

    static int NUM_RUNS = 10;

    public static void main(String[] args) throws Exception {

        FileWriter strongCSV = new FileWriter("strong_scaling.csv");
        strongCSV.write("p,nThrows,medianTp_ms\n");

        FileWriter weakCSV = new FileWriter("weak_scaling.csv");
        weakCSV.write("p,nThrows,medianTp_ms\n");

        for (int p : pList) {

            // ---------------------
            // STRONG SCALING
            // ---------------------
            long[] times = new long[NUM_RUNS];

            for (int i = 0; i < NUM_RUNS; i++) {

                System.setProperty(
                        "java.util.concurrent.ForkJoinPool.common.parallelism",
                        String.valueOf(p)
                );

                PiMonteCarlo mc = new PiMonteCarlo(strongNThrows);
                long start = System.nanoTime();
                mc.getPi(); // exécution directe !
                long end = System.nanoTime();

                times[i] = end - start;
            }

            Arrays.sort(times);
            long median = times[NUM_RUNS / 2];
            strongCSV.write(p + "," + strongNThrows + "," + median + "\n");
            strongCSV.flush();


            // ---------------------
            // WEAK SCALING
            // ---------------------
            int weakN = p * weakBase;
            long[] timesW = new long[NUM_RUNS];

            for (int i = 0; i < NUM_RUNS; i++) {

                System.setProperty(
                        "java.util.concurrent.ForkJoinPool.common.parallelism",
                        String.valueOf(p)
                );

                PiMonteCarlo mc = new PiMonteCarlo(weakN);
                long start = System.nanoTime();
                mc.getPi();
                long end = System.nanoTime();

                timesW[i] = end - start;
            }

            Arrays.sort(timesW);
            long medianW = timesW[NUM_RUNS / 2];
            weakCSV.write(p + "," + weakN + "," + medianW + "\n");
            weakCSV.flush();
        }

        strongCSV.close();
        weakCSV.close();
        System.out.println("Expériences terminées. CSV générés !");
    }
}
