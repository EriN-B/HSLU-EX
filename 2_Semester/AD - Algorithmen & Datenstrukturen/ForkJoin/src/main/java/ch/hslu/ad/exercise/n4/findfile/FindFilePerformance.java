package ch.hslu.ad.exercise.n4.findfile;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public final class FindFilePerformance {

    private FindFilePerformance() {}

    public static void main(String[] args) {
        final String searchFile = "find.me";
        final File rootDir = new File(System.getProperty("user.home"));

        final String outputPath = "../docs/measurements/N4/A4/";
        new File(outputPath).mkdirs();
        final String fileName = outputPath + "N4_findfile_result.csv";

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("mode,time_ms,found,dirs_visited,root");

            // Parallel
            AtomicInteger dirsVisitedParallel = new AtomicInteger(0);
            AtomicBoolean foundParallel = new AtomicBoolean(false);

            long start = System.currentTimeMillis();
            FindFileTask task = new FindFileTask(searchFile, rootDir, foundParallel, dirsVisitedParallel);
            ForkJoinPool.commonPool().invoke(task);
            long timePar = System.currentTimeMillis() - start;

            writer.printf("parallel,%d,%b,%d,%s%n",
                    timePar,
                    foundParallel.get(),
                    dirsVisitedParallel.get(),
                    rootDir.getAbsolutePath());

            // Sequentiell
            AtomicInteger dirsVisitedSeq = new AtomicInteger(0);
            AtomicBoolean foundSeq = new AtomicBoolean(false);

            start = System.currentTimeMillis();
            FindFile.findFile(searchFile, rootDir, foundSeq, dirsVisitedSeq);
            long timeSeq = System.currentTimeMillis() - start;

            writer.printf("sequential,%d,%b,%d,%s%n",
                    timeSeq,
                    foundSeq.get(),
                    dirsVisitedSeq.get(),
                    rootDir.getAbsolutePath());

            System.out.println("Messung abgeschlossen: " + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
