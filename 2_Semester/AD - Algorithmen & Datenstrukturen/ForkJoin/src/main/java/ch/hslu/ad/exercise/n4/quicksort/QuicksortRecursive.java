/*
 * Copyright 2025 Hochschule Luzern Informatik.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.hslu.ad.exercise.n4.quicksort;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Sequentieller Quicksort zur Verwendung in parallelen Aufgaben.
 */
public final class QuicksortRecursive {

    private QuicksortRecursive() {
        // Verhindert Instanziierung
    }

    /**
     * Öffentliche Methode zum Sortieren eines gesamten Arrays.
     *
     * @param array das zu sortierende Array.
     */
    public static void quicksort(int[] array) {
        quicksort(array, 0, array.length - 1);
    }

    /**
     * Rekursive QuickSort-Logik für Teilbereiche.
     *
     * @param array zu sortierendes Array
     * @param startIdx unterer Index (inkl.)
     * @param endIdx oberer Index (inkl.)
     */
    public static void quicksort(int[] array, int startIdx, int endIdx) {
        if (startIdx < endIdx) {
            int partitionIdx = partition(array, startIdx, endIdx);
            quicksort(array, startIdx, partitionIdx - 1);
            quicksort(array, partitionIdx + 1, endIdx);
        }
    }

    /**
     * Partitioniert das Array um einen zufälligen Pivot.
     *
     * @param array das zu partitionierende Array
     * @param left linker Rand (inkl.)
     * @param right rechter Rand (inkl.)
     * @return Index des Pivots nach Partitionierung
     */
    public static int partition(int[] array, int left, int right) {
        // Zufälligen Pivot wählen und an das Ende tauschen
        int pivotIndex = ThreadLocalRandom.current().nextInt(left, right + 1);
        exchange(array, pivotIndex, right);

        int pivot = array[right];
        int i = left - 1;

        for (int j = left; j < right; j++) {
            if (array[j] <= pivot) {
                i++;
                exchange(array, i, j);
            }
        }

        exchange(array, i + 1, right);
        return i + 1;
    }

    /**
     * Tauscht zwei Elemente im Array.
     *
     * @param array das Array
     * @param i Index 1
     * @param j Index 2
     */
    private static void exchange(final int[] array, final int i, final int j) {
        if (i != j) {
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }
}
