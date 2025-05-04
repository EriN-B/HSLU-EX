package ch.hslu.ad.exercise.n4.quicksort;

import java.util.concurrent.RecursiveAction;

/**
 * Parallelisierter Quicksort mit Fork-Join.
 */
@SuppressWarnings("serial")
public final class QuicksortTask extends RecursiveAction {

    private static int threshold = 5000; // Standardwert

    public static void setThreshold(int value) {
        threshold = value;
    }

    private final int[] array;
    private final int min;
    private final int max;

    public QuicksortTask(int[] array) {
        this(array, 0, array.length - 1);
    }

    private QuicksortTask(final int[] array, final int min, final int max) {
        this.array = array;
        this.min = min;
        this.max = max;
    }

    @Override
    protected void compute() {
        if (min < max) {
            if (max - min < threshold) {
                QuicksortRecursive.quicksort(array, min, max);
            } else {
                int pivotIndex = QuicksortRecursive.partition(array, min, max);
                invokeAll(
                        new QuicksortTask(array, min, pivotIndex - 1),
                        new QuicksortTask(array, pivotIndex + 1, max)
                );
            }
        }
    }
}
