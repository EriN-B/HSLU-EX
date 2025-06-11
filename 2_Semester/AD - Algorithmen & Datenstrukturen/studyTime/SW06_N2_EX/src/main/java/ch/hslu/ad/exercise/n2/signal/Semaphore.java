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
package ch.hslu.ad.exercise.n2.signal;

/**
 * Ein nach oben begrenztes Semaphor. Der Semaphorenzähler kann nicht unendlich
 * wachsen.
 */
public final class Semaphore {

    private int sema; // Semaphorenzähler
    private int count; // Anzahl der wartenden Threads.
    private int limit;

    public Semaphore() {
        this(0, 1);
    }

    public Semaphore(final int permits) throws IllegalArgumentException {
        this(permits, permits+1);
    }

    public Semaphore(final int permits, final int limit) throws IllegalArgumentException {
        if(permits > limit | permits < 0){
            throw new IllegalArgumentException();
        }

        sema = permits;
        count = 0;
        this.limit = limit;
    }

    public synchronized void acquire() throws InterruptedException {
        while (sema == 0) {
            count++;
            this.wait();
            count--;
        }
        sema--;
    }

    public synchronized void acquire(final int permits) throws InterruptedException {

        if(permits <= 0 | permits > limit){
            throw new IllegalArgumentException();
        }

        this.count++;

        while(permits > sema){
            this.wait();
        }

        this.count--;
        sema-=permits;
    }

    public synchronized void release() {
        sema++;
        this.notifyAll();
    }

    public synchronized void release(final int permits) {
        if (sema + permits > limit | permits < 0) {
            throw new IllegalArgumentException();
        }

        sema+=permits;
        notifyAll();
    }

    public synchronized int pending() {
        return count;
    }
}
