// Estimate the value of Pi using Monte-Carlo Method, using parallel program
package assignments;
public class Assignment102 {
	public static void main(String[] args) {
        // A1 : lire nThrows et nbThreads depuis la ligne de commande
        int nThrows = Integer.parseInt(args[0]);
        int nbThreads = Integer.parseInt(args[1]);

        // A2 : imposer au ForkJoinPool d'utiliser nbThreads
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", String.valueOf(nbThreads));

        // (A3) construire l'objet Monte-Carlo avec nThrows
        PiMonteCarlo PiVal = new PiMonteCarlo(nThrows);

        long startTime = System.currentTimeMillis();
		double value = PiVal.getPi();
		long stopTime = System.currentTimeMillis();

        /**
		System.out.println("Approx value:" + value);
		System.out.println("Difference to exact value of pi: " + (value - Math.PI));
		System.out.println("Error: " + (value - Math.PI) / Math.PI * 100 + " %");
		System.out.println("Available processors: " + Runtime.getRuntime().availableProcessors());
		System.out.println("Time Duration: " + (stopTime - startTime) + "ms");
         */
        // B : output minimal pour l'automatisation
        System.out.println(stopTime - startTime);

    }
}