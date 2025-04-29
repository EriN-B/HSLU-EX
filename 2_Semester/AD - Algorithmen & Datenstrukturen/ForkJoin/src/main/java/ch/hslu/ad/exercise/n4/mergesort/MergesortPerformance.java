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
package ch.hslu.ad.exercise.n4.mergesort;

import ch.hslu.ad.n41.array.init.RandomInitTask;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * Performance Vergleich der Mergesort Implementationen.
 */
public final class MergesortPerformance {

    private static final Logger LOG = LoggerFactory.getLogger(MergesortPerformance.class);

    /**
     * Privater Konstruktor.
     */
    private MergesortPerformance() {
    }

    /**
     * Main-Demo.
     *
     * @param args not used.
     */
    public static void main(final String[] args) {
        final int size = 300_000;
        final int[] arrayOriginal = new int[size];
        try (final ForkJoinPool pool = new ForkJoinPool()) {
            RandomInitTask initTask = new RandomInitTask(arrayOriginal, 100);
            pool.invoke(initTask);
            int[] array = Arrays.copyOf(arrayOriginal, size);
            final MergesortTask sortTask = new MergesortTask(array);
            pool.invoke(sortTask);
            LOG.info("Conc. Mergesort : {} msec.", '?');
            array = Arrays.copyOf(arrayOriginal, size);
            MergesortRecursive.mergeSort(array);
            LOG.info("MergesortRec.   : {} msec.", '?');
            array = Arrays.copyOf(arrayOriginal, size);
            Arrays.parallelSort(array);
            LOG.info("ParallelSort    : {} msec.", '?');
        } finally {
            // Executor shutdown
        }
    }
}
