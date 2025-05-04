// Erweiterung um Quick-Insertion-Sort nach Aufgabe 3.3

package ch.hslu.ad.a1;

import ch.hslu.ad.a1.animation.SortingAnimation;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;

public class Sorter {
    private static int quickSortComparisons;

    private static final String LOG_DIRECTORY = "../docs";
    private static final int QUICK_INSERTION_THRESHOLD = 32; // M - optimierbar

    static {
        try {
            Files.createDirectories(Paths.get(LOG_DIRECTORY));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void logResult(String algorithm, int size, String inputType, long timeMillis, int comparisons) {
        String filename = String.format("%s/%s.csv", LOG_DIRECTORY, algorithm.toLowerCase());
        String line = String.format("%d,%s,%d,%d\n", size, inputType, timeMillis, comparisons);

        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Quick-Insertion-Sort (hybrider Algorithmus) */
    public static int quickInsertionSort(int[] array, boolean withAnimation, String inputType) {
        quickSortComparisons = 0;
        long start = System.nanoTime();
        quickInsertionSort(array, 0, array.length - 1, withAnimation);
        long duration = System.nanoTime() - start;
        logResult("quickinsertionsort", array.length, inputType, duration / 1_000_000, quickSortComparisons);
        return quickSortComparisons;
    }

    private static void quickInsertionSort(int[] array, int low, int high, boolean withAnimation) {
        if (high - low + 1 < QUICK_INSERTION_THRESHOLD) {
            insertionSortRange(array, low, high, withAnimation);
        } else {
            int pivotIndex = partition(array, low, high, withAnimation);
            quickInsertionSort(array, low, pivotIndex - 1, withAnimation);
            quickInsertionSort(array, pivotIndex + 1, high, withAnimation);
        }
    }

    private static void insertionSortRange(int[] array, int low, int high, boolean withAnimation) {
        for (int i = low + 1; i <= high; i++) {
            int key = array[i];
            int j = i - 1;

            while (j >= low && array[j] > key) {
                quickSortComparisons++;
                array[j + 1] = array[j];
                j--;
                if (withAnimation) SortingAnimation.instance().showArray(array, 30, i, j);
            }
            if (j >= low) quickSortComparisons++;
            array[j + 1] = key;
        }
    }

    /** Quicksort (klassisch) */
    public static int quickSort(int[] array, boolean withAnimation, String inputType) {
        quickSortComparisons = 0;
        long start = System.nanoTime();
        quickSort(array, 0, array.length - 1, withAnimation);
        long duration = System.nanoTime() - start;
        logResult("quicksort", array.length, inputType, duration / 1_000_000, quickSortComparisons);
        return quickSortComparisons;
    }

    public static int quickSort(int[] array) {
        return quickSort(array, false, "default");
    }

    private static void quickSort(int[] array, int low, int high, boolean withAnimation) {
        if (low < high) {
            int pivotIndex = partition(array, low, high, withAnimation);
            quickSort(array, low, pivotIndex - 1, withAnimation);
            quickSort(array, pivotIndex + 1, high, withAnimation);
        }
    }

    private static int partition(int[] array, int low, int high, boolean withAnimation) {
        int pivot = array[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            quickSortComparisons++;
            if (array[j] <= pivot) {
                i++;
                swap(array, i, j);
            }
            if (withAnimation) SortingAnimation.instance().showArray(array, 30, i, j, high);
        }
        swap(array, i + 1, high);
        if (withAnimation) SortingAnimation.instance().showArray(array, 30, i + 1, high);
        return i + 1;
    }

    /** Quicksort (randomisiert) */
    public static int quickSortRandom(int[] array, boolean withAnimation, String inputType) {
        quickSortComparisons = 0;
        long start = System.nanoTime();
        quickSortRandom(array, 0, array.length - 1, withAnimation);
        long duration = System.nanoTime() - start;
        logResult("quicksortrandom", array.length, inputType, duration / 1_000_000, quickSortComparisons);
        return quickSortComparisons;
    }

    private static void quickSortRandom(int[] array, int low, int high, boolean withAnimation) {
        if (low < high) {
            int pivotIndex = partitionRandom(array, low, high, withAnimation);
            quickSortRandom(array, low, pivotIndex - 1, withAnimation);
            quickSortRandom(array, pivotIndex + 1, high, withAnimation);
        }
    }

    private static int partitionRandom(int[] array, int low, int high, boolean withAnimation) {
        int pivotIndex = low + (int) (Math.random() * (high - low + 1));
        swap(array, pivotIndex, high);
        return partition(array, low, high, withAnimation);
    }

    private static void swap(int[] array, int i, int j) {
        if (i != j) {
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }
}