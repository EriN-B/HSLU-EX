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
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Testfälle für {@link ch.hslu.ad.n21.buffer.guardedblocks}.
 */
public class ProducerTest {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ProducerTest.class);

    public ProducerTest() {
    }

    /**
     * Test of getSum method, of class Producer.
     */
    @Test
    public void testGetSumP() {
        final BoundedBuffer<Integer> queue = new BoundedBuffer<>(50);
        final Producer instance = new Producer(queue, 42);
        final Thread thread = new Thread(instance, "Prod");
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException ex) {
            LOG.debug(ex.getMessage());
        }
        long expResult = 861L;
        long result = instance.getSum();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSum method, of class Producer and Consumer.
     * @throws InterruptedException
     */
    @Test
    public void testGetSumP3aC1() throws InterruptedException {
        final BoundedBuffer<Integer> queue = new BoundedBuffer<>(50);
        final Random random = new Random();
        final Producer p1 = new Producer(queue, random.nextInt(10000));
        final Producer p2 = new Producer(queue, random.nextInt(10000));
        final Producer p3 = new Producer(queue, random.nextInt(10000));
        final Consumer c1 = new Consumer(queue);
        final Thread tp1 = new Thread(p1, "Prod A"); tp1.start();
        final Thread tp2 = new Thread(p2, "Prod B"); tp2.start();
        final Thread tp3 = new Thread(p3, "Prod C"); tp3.start();
        final Thread tc1 = new Thread(c1, "Cons A"); tc1.start();
        try {
            tp1.join();
            tp2.join();
            tp3.join();
        } catch (InterruptedException ex) {
            LOG.debug(ex.getMessage());
        }
        Thread.sleep(500);
        tc1.interrupt();
        LOG.info("Prod A = {}", p1.getSum());
        LOG.info("Prod B = {}", p2.getSum());
        LOG.info("Prod C = {}", p3.getSum());
        LOG.info("Cons A = {}", c1.getSum());
        long expResult = p1.getSum() + p2.getSum() + p3.getSum();
        long result = c1.getSum();
        LOG.info("testGetSumP3aC1(): {} = {}", expResult, result);
        assertEquals(expResult, result);
    }

    /**
     * Test of getSum method, of class Producer and Consumer.
     * @throws InterruptedException
     */
    @Test
    public void testGetSumP2aC3() throws InterruptedException {
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
        LOG.info("testGetSumP3aC1(): {} = {}", expResult, result);
        assertEquals(expResult, result);
    }

    /**
     * Test of getSum method, of class Producer with timeout and Consumer.
     * @throws InterruptedException
     */
    @Test
    public void testGetSumPWT3aC1() throws InterruptedException {
        final Random random = new Random();
        final BoundedBuffer<Integer> queue = new BoundedBuffer<>(50);
        final ProducerWithTimeout p1 = new ProducerWithTimeout(queue, random.nextInt(10000));
        final ProducerWithTimeout p2 = new ProducerWithTimeout(queue, random.nextInt(10000));
        final ProducerWithTimeout p3 = new ProducerWithTimeout(queue, random.nextInt(10000));
        final Consumer c1 = new Consumer(queue);
        final Thread tp1 = new Thread(p1, "Prod A"); tp1.start();
        final Thread tp2 = new Thread(p2, "Prod B"); tp2.start();
        final Thread tp3 = new Thread(p3, "Prod C"); tp3.start();
        final Thread tc1 = new Thread(c1, "Cons A"); tc1.start();
        try {
            tp1.join();
            tp2.join();
            tp3.join();
        } catch (InterruptedException ex) {
            LOG.debug(ex.getMessage());
        }
        Thread.sleep(500);
        tc1.interrupt();
        LOG.info("Prod A = {}", p1.getSum());
        LOG.info("Prod B = {}", p2.getSum());
        LOG.info("Prod C = {}", p3.getSum());
        LOG.info("Cons A = {}", c1.getSum());
        long expResult = p1.getSum() + p2.getSum() + p3.getSum();
        long result = c1.getSum();
        LOG.info("testGetSumPWT3aC1(): {} = {}", expResult, result);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetSumPWT4aC1() throws InterruptedException {
        final Random random = new Random();
        final BoundedBuffer<Integer> queue = new BoundedBuffer<>(50);
        final ProducerWithTimeout p1 = new ProducerWithTimeout(queue, random.nextInt(10000));
        final ProducerWithTimeout p2 = new ProducerWithTimeout(queue, random.nextInt(10000));
        final ProducerWithTimeout p3 = new ProducerWithTimeout(queue, random.nextInt(10000));
        final ProducerWithTimeout p4 = new ProducerWithTimeout(queue, random.nextInt(10000));
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
        LOG.info("testGetSumPWT4aC1(): {} = {}", expResult, result);
        assertEquals(expResult, result);
    }
}
