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
package ch.hslu.ad.n21.buffer.guardedblocks;

import java.util.Random;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * Demonstration der BoundedBuffer mit n Producer und m Consumer.
 */
public final class DemoBoundedBuffer {

    private static final Logger LOG = LoggerFactory.getLogger(DemoBoundedBuffer.class);

    /**
     * Privater Konstruktor.
     */
    private DemoBoundedBuffer() {
    }

    /**
     * Main-Demo.
     *
     * @param args not used.
     * @throws InterruptedException wenn Thread unterbrochen wird.
     */
    public static void main(final String args[]) throws InterruptedException {
        final Random random = new Random();
        final BoundedBuffer<Integer> queue = new BoundedBuffer<>(50);
        final Producer p1 = new Producer(queue, random.nextInt(10000));
        final Producer p2 = new Producer(queue, random.nextInt(10000));
        final Consumer c1 = new Consumer(queue);
        final Consumer c2 = new Consumer(queue);
        final Consumer c3 = new Consumer(queue);
        final Thread tp1 = new Thread(p1, "Prod A"); tp1.start();
        final Thread tp2 = new Thread(p2, "Prod B"); tp2.start();
        final Thread tc1 = new Thread(c1, "Cons A"); tc1.start();
        final Thread tc2 = new Thread(c2, "Cons B"); tc2.start();
        final Thread tc3 = new Thread(c3, "Cons C"); tc3.start();
        try {
            tp1.join();
            tp2.join();
        } catch (InterruptedException ex) {
            LOG.debug(ex.getMessage());
        }
        Thread.sleep(500);
        tc1.interrupt();
        tc2.interrupt();
        tc3.interrupt();
        LOG.info("Prod A = {}", p1.getSum());
        LOG.info("Prod B = {}", p2.getSum());
        LOG.info("Cons A = {}", c1.getSum());
        LOG.info("Cons B = {}", c2.getSum());
        LOG.info("Cons C = {}", c3.getSum());
        long expResult = p1.getSum() + p2.getSum();
        long result = c1.getSum() + c2.getSum() + c3.getSum();
        LOG.info("{} = {}", expResult, result);
    }
}
