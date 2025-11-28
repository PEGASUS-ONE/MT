package assignments;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Experiments {

    private static BufferedWriter strongWriter;
    private static BufferedWriter weakWriter;

    public static void initStrongCsv() throws IOException {
        strongWriter = new BufferedWriter(new FileWriter("strong_scaling.csv"));
        strongWriter.write("p,nPerThread,repeat,timeNs,speedup\n");
    }

    public static void closeStrongCsv() throws IOException {
        if (strongWriter != null) strongWriter.close();
    }

    public static void initWeakCsv() throws IOException {
        weakWriter = new BufferedWriter(new FileWriter("weak_scaling.csv"));
        weakWriter.write("p,nPerThread,repeat,timeNs,efficiency\n");
    }

    public static void closeWeakCsv() throws IOException {
        if (weakWriter != null) weakWriter.close();
    }

    // =============================================
    //                STRONG SCALING
    // =============================================
    public static void runStrongScaling(int[] pValues, int nPerThread, int repeats) throws Exception {

        System.out.println("\n=== STRONG SCALING | nPerThread = " + nPerThread + " ===");

        double T1 = -1;

        for (int p : pValues) {

            double sum = 0;

            for (int r = 1; r <= repeats; r++) {

                long start = System.nanoTime();
                new Master().doRun(nPerThread, p);
                long end = System.nanoTime();

                long timeNs = end - start;
                sum += timeNs;

                strongWriter.write(
                        p + "," + nPerThread + "," + r + "," + timeNs + ",\n"
                );
            }

            double meanNs = sum / repeats;

            if (p == 1)
                T1 = meanNs;

            double speedup = T1 / meanNs;

            strongWriter.write(
                    p + "," + nPerThread + ",mean," + meanNs + "," + speedup + "\n"
            );
        }
    }

    // =============================================
    //                WEAK SCALING
    // =============================================
    public static void runWeakScaling(int[] pValues, int baseWork, int repeats) throws Exception {

        System.out.println("\n=== WEAK SCALING ===");

        double T1 = -1;

        for (int p : pValues) {

            int nPerThread = baseWork;

            double sum = 0;

            for (int r = 1; r <= repeats; r++) {

                long start = System.nanoTime();
                new Master().doRun(nPerThread, p);
                long end = System.nanoTime();

                long timeNs = end - start;
                sum += timeNs;

                weakWriter.write(
                        p + "," + nPerThread + "," + r + "," + timeNs + ",\n"
                );
            }

            double meanNs = sum / repeats;

            if (p == 1)
                T1 = meanNs;

            double efficiency = T1 / meanNs;

            weakWriter.write(
                    p + "," + nPerThread + ",mean," + meanNs + "," + efficiency + "\n"
            );
        }
    }
}
