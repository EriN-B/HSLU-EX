package ch.hslu.E4.Performance;

import java.util.Random;

public class PerformanceUtils {
    public static long measureTime(Runnable test) {
        long startTime = System.nanoTime();
        test.run();
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1_000_000;
    }

    public static String[] generateArray(int size) {
        String[] data = new String[size];
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            data[i] = "Item" + random.nextInt(1000);
        }
        return data;
    }
}
