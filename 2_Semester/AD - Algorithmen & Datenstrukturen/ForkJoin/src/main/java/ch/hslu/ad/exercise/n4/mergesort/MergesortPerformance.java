package ch.hslu.ad.exercise.n4.mergesort;

import ch.hslu.ad.n41.array.init.RandomInitTask;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Performance Vergleich der Mergesort Implementationen.
 */
public final class MergesortPerformance {

    private static final Logger LOG = LoggerFactory.getLogger(MergesortPerformance.class);

    private MergesortPerformance() {
    }

    public static void main(final String[] args) {
        final int size = 300_000_000;
        final int[] arrayOriginal = new int[size];
        final int[] thresholds = {100, 500, 2000, 5000};

        final String dirPath = "../docs/measurements/N4/A1";
        new File(dirPath).mkdirs();

        String os = System.getProperty("os.name").replaceAll("\\s+", "");
        String arch = System.getProperty("os.arch");
        String cores = String.valueOf(Runtime.getRuntime().availableProcessors());
        String memoryGB = String.valueOf(Runtime.getRuntime().maxMemory() / (1024 * 1024 * 1024));
        String systemTag = os + "_" + arch + "_" + cores + "C_" + memoryGB + "GB";

        String timestamp = String.valueOf(System.currentTimeMillis());
        String fileName = String.format("%s/N4_run_%s_%s.csv", dirPath, systemTag, timestamp);

        try (PrintWriter csvWriter = new PrintWriter(new FileWriter(fileName))) {
            csvWriter.println("threshold,time_concurrent_ms,time_sequential_ms,time_parallel_ms");

            for (int threshold : thresholds) {
                MergesortTask.setThreshold(threshold);
                System.out.printf("==== THRESHOLD: %d ====%n", threshold);
                LOG.info("Initialisiere Daten mit Zufallswerten (Größe: {})...", size);

                try (ForkJoinPool pool = new ForkJoinPool()) {
                    RandomInitTask initTask = new RandomInitTask(arrayOriginal, 100);
                    pool.invoke(initTask);
                    LOG.info("Initialisierung abgeschlossen.");

                    // Concurrent Mergesort
                    int[] array = Arrays.copyOf(arrayOriginal, size);
                    System.out.println("Starte concurrent Mergesort...");
                    long start = System.nanoTime();
                    pool.invoke(new MergesortTask(array));
                    long timeConc = (System.nanoTime() - start) / 1_000_000;

                    // Rekursiver Mergesort
                    array = Arrays.copyOf(arrayOriginal, size);
                    System.out.println("Starte rekursiven (sequenziellen) Mergesort...");
                    start = System.nanoTime();
                    MergesortRecursive.mergeSort(array);
                    long timeSeq = (System.nanoTime() - start) / 1_000_000;

                    // ParallelSort
                    array = Arrays.copyOf(arrayOriginal, size);
                    System.out.println("Starte Arrays.parallelSort...");
                    start = System.nanoTime();
                    Arrays.parallelSort(array);
                    long timePar = (System.nanoTime() - start) / 1_000_000;

                    csvWriter.printf("%d,%d,%d,%d%n", threshold, timeConc, timeSeq, timePar);
                    System.out.printf("Messung für THRESHOLD %d abgeschlossen.\n\n", threshold);
                }
            }

            LOG.info("Messung abgeschlossen. Datei gespeichert unter: {}", fileName);

        } catch (IOException e) {
            LOG.error("Fehler beim Schreiben der CSV-Datei: {}", e.getMessage(), e);
        }
    }
}
