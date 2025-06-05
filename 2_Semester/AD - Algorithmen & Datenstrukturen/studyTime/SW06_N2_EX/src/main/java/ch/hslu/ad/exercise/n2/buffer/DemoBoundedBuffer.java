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
package ch.hslu.ad.exercise.n2.buffer;

import java.util.Random;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * Demonstration des BoundedBuffers mit n Producer und m Consumer.
 */
public final class DemoBoundedBuffer {

    private static final Logger LOG = LoggerFactory.getLogger(DemoBoundedBuffer.class);

    /**
     * Timeout für Producer und Consumer in Millisekunden.
     */
    public static final int TIMEOUT = 2;

    /**
     * Privater Konstruktor.
     */
    private DemoBoundedBuffer() {
    }

    /**
     * Main-Demo.
     *
     * @param args not used.
     * @throws InterruptedException wenn das warten unterbrochen wird.
     */
    public static void main(final String args[]) throws InterruptedException {
        final Random random = new Random();
        final BoundedBuffer<Integer> queue = new BoundedBuffer<>(50);
        final Producer p1 = new Producer(queue, random.nextInt(1_000_000));
        final Producer p2 = new Producer(queue, random.nextInt(1_000_000));
        final Producer p3 = new Producer(queue, random.nextInt(1_000_000));
        final Producer p4 = new Producer(queue, random.nextInt(1_000_000));
        final Consumer c1 = new Consumer(queue);
        final Thread tp1 = new Thread(p1, "Prod A"); tp1.start();
        final Thread tp2 = new Thread(p2, "Prod B"); tp2.start();
        final Thread tp3 = new Thread(p3, "Prod C"); tp3.start();
        final Thread tp4 = new Thread(p4, "Prod D"); tp4.start();
        final Thread tc1 = new Thread(c1, "Cons A"); tc1.start();
        try {
            tp1.join();
            tp2.join();
            tp3.join();
            tp4.join();
        } catch (InterruptedException ex) {
            LOG.debug(ex.getMessage());
        }
        Thread.sleep(500);
        tc1.interrupt();
        LOG.info("Prod A = {}", p1.getSum());
        LOG.info("Prod B = {}", p2.getSum());
        LOG.info("Prod C = {}", p3.getSum());
        LOG.info("Prod D = {}", p4.getSum());
        LOG.info("Cons A = {}", c1.getSum());
        long expResult = p1.getSum() + p2.getSum() + p3.getSum() + p4.getSum();
        long result = c1.getSum();
        LOG.info("{} = {}", expResult, result);
        LOG.info("queue size = {}", queue.size());
        LOG.info("queue empty? {}", queue.empty());
    }
}
