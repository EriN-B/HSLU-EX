package ch.hslu.ad.exercise.n4.fibo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ForkJoinPool;

public final class FibonacciPerformance {

    private static final Logger LOG = LoggerFactory.getLogger(FibonacciPerformance.class);

    private FibonacciPerformance() {}

    public static void main(final String[] args) {
        final int n = 42;
        LOG.info("Fibonacci Performance-Messung für n = {}", n);

        final String dirPath = "../docs/measurements/N4/A3";
        new File(dirPath).mkdirs();

        String os = System.getProperty("os.name").replaceAll("\\s+", "");
        String arch = System.getProperty("os.arch");
        String cores = String.valueOf(Runtime.getRuntime().availableProcessors());
        String memoryGB = String.valueOf(Runtime.getRuntime().maxMemory() / (1024 * 1024 * 1024));
        String systemTag = os + "_" + arch + "_" + cores + "C_" + memoryGB + "GB";
        String timestamp = String.valueOf(System.currentTimeMillis());
        String fileName = String.format("%s/N4_fibo_%s_%s.csv", dirPath, systemTag, timestamp);

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("method,threshold,time_ms");

            // Iterativ
            long start = System.nanoTime();
            long result = FibonacciCalc.fiboIterative(n);
            long timeIter = (System.nanoTime() - start) / 1_000_000;
            writer.printf("iterative,-1,%d%n", timeIter);
            LOG.info("iterative: {} ms (fibo({}) = {})", timeIter, n, result);

            // Verschiedene Thresholds
            int[] thresholds = {5, 10, 20, 500, 1000, 2000};

            for (int threshold : thresholds) {
                FibonacciTask.setThreshold(threshold);
                FibonacciTask task = new FibonacciTask(n);
                ForkJoinPool pool = ForkJoinPool.commonPool();
                start = System.nanoTime();
                result = pool.invoke(task);
                long timePar = (System.nanoTime() - start) / 1_000_000;
                writer.printf("parallel_recursive,%d,%d%n", threshold, timePar);
                LOG.info("parallel_recursive (threshold {}): {} ms", threshold, timePar);
            }

            // Rekursiv
            start = System.nanoTime();
            result = FibonacciCalc.fiboRecursive(n);
            long timeRec = (System.nanoTime() - start) / 1_000_000;
            writer.printf("recursive,-1,%d%n", timeRec);
            LOG.info("recursive: {} ms (fibo({}) = {})", timeRec, n, result);

            LOG.info("Messung abgeschlossen: {}", fileName);

        } catch (IOException e) {
            LOG.error("Fehler beim Schreiben der Datei: {}", e.getMessage(), e);
        }
    }
}
