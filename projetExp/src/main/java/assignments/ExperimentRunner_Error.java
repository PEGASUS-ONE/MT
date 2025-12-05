package assignments;

import java.io.FileWriter;
import java.util.Random;

public class ExperimentRunner_Error {

    public static void main(String[] args) throws Exception {

        FileWriter csv = new FileWriter("pi_error_curve_dense.csv");
        csv.write("N_total,error,log10_error\n");

        Random rng = new Random();

        // Nombre de points : modifiable (2000 recommandé)
        int NUM_POINTS = 2000;

        // Taille minimale et maximale
        int N_min = 1_000;
        int N_max = 20_000_000;

        for (int i = 0; i < NUM_POINTS; i++) {

            // interpolation linéaire sur N
            int N = N_min + (int)((N_max - N_min) * (i / (double) NUM_POINTS));

            long circleCount = 0;

            // tirages rapides
            for (int j = 0; j < N; j++) {
                double x = rng.nextDouble();
                double y = rng.nextDouble();
                if (x * x + y * y < 1.0) circleCount++;
            }

            // pi estimé
            double pi_hat = 4.0 * circleCount / N;

            // erreur relative
            double err = Math.abs(pi_hat - Math.PI) / Math.PI;

            // log10(erreur)
            double logErr = Math.log10(err);

            csv.write(N + "," + err + "," + logErr + "\n");
        }

        csv.close();
        System.out.println("OK : pi_error_curve_dense.csv généré !");
    }
}
