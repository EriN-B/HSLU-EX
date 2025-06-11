/*
 * Copyright 2025 Hochschule Luzern - Informatik.
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
package ch.hslu.ad.n22.executor.plain;

import ch.hslu.ad.n21.buffer.guardedblocks.BoundedBuffer;
import java.util.concurrent.Executor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * Ein einfacher Thread Pool Executor, der Aufgaben an eine bestimmte Anzahl
 * Threads zuteilt. Die Aufgaben werden in einer Task Queue gespeichert.
 */
public final class PlainThreadPool implements Executor {

    private static final Logger LOG = LoggerFactory.getLogger(PlainThreadPool.class);
    private final BoundedBuffer<Runnable> taskQueue;
    private final int nThreads;

    /**
     * Erzeugt einen einfachen Thread Pool Executor.
     *
     * @param capacity Grösse für die Anzahl Aufgaben.
     * @param nThreads Anzahl Threads.
     */
    public PlainThreadPool(final int capacity, final int nThreads) {
        this.taskQueue = new BoundedBuffer<>(capacity);
        this.nThreads = nThreads;
        for (int i = 0; i < this.nThreads; ++i) {
            activate();
        }
    }

    /**
     * @see java.util.concurrent.Executor
     */
    @Override
    public void execute(Runnable command) {
        try {
            taskQueue.add(command);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void activate() {
        final Runnable runLoop = () -> {
            try {
                while (true) {
                    final Runnable r = taskQueue.remove();
                    r.run();
                }
            } catch (InterruptedException e) {
                LOG.debug(e.getMessage(), e);
            }
        };
        final Thread thread = new Thread(runLoop);
        thread.setDaemon(true);
        thread.start();
    }
}
