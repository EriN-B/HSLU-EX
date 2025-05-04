package ch.hslu.ad.exercise.n4.quicksort;

import ch.hslu.ad.n41.array.init.RandomInitTask;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

public final class QuicksortPerformance {

    private QuicksortPerformance() {}

    public static void main(String[] args) {
        final int size = 100_000_000; // Zum Testen evtl. kleiner wählen
        final int[] arrayOriginal = new int[size];

        final String dirPath = "../docs/measurements/N4/A2";
        new File(dirPath).mkdirs();

        String os = System.getProperty("os.name").replaceAll("\\s+", "");
        String arch = System.getProperty("os.arch");
        String cores = String.valueOf(Runtime.getRuntime().availableProcessors());
        String memoryGB = String.valueOf(Runtime.getRuntime().maxMemory() / (1024 * 1024 * 1024));
        String systemTag = os + "_" + arch + "_" + cores + "C_" + memoryGB + "GB";
        String timestamp = String.valueOf(System.currentTimeMillis());

        String fileName = String.format("%s/N4_qs_%s_%s.csv", dirPath, systemTag, timestamp);

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("algorithm,threshold,time_ms");

            System.out.println("Initialisiere Zufallsdaten...");
            try (ForkJoinPool pool = new ForkJoinPool()) {
                RandomInitTask initTask = new RandomInitTask(arrayOriginal, 1_000_000);
                pool.invoke(initTask);
            }
            System.out.println("Array initialisiert mit " + size + " Elementen.");

            int[] thresholds = {500, 1000, 5000, 10000, 20000};

            for (int threshold : thresholds) {
                System.out.printf("▶ THRESHOLD = %d%n", threshold);
                QuicksortTask.setThreshold(threshold);

                int[] arrParallel = Arrays.copyOf(arrayOriginal, size);
                long start = System.nanoTime();
                ForkJoinPool.commonPool().invoke(new QuicksortTask(arrParallel));
                long timePar = (System.nanoTime() - start) / 1_000_000;
                writer.printf("parallel_quicksort,%d,%d%n", threshold, timePar);
            }

            int[] arrSeq = Arrays.copyOf(arrayOriginal, size);
            long start = System.nanoTime();
            QuicksortRecursive.quicksort(arrSeq);
            long timeSeq = (System.nanoTime() - start) / 1_000_000;
            writer.printf("recursive_quicksort,-1,%d%n", timeSeq);

            int[] arrQuickIns = Arrays.copyOf(arrayOriginal, size);
            start = System.nanoTime();
            Sorter.quickInsertionSort(arrQuickIns, false, "perf");
            long timeIns = (System.nanoTime() - start) / 1_000_000;
            writer.printf("quick_insertion_sort,-1,%d%n", timeIns);

            int[] arrJava = Arrays.copyOf(arrayOriginal, size);
            start = System.nanoTime();
            Arrays.sort(arrJava);
            long timeJava = (System.nanoTime() - start) / 1_000_000;
            writer.printf("java_dualpivot_sort,-1,%d%n", timeJava);

            System.out.println("✅ Messung abgeschlossen. Datei gespeichert unter:");
            System.out.println(fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
