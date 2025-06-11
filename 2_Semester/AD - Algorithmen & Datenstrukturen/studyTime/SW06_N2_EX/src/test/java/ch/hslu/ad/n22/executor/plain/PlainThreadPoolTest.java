/*
 * Copyright 2023 Hochschule Luzern - Informatik.
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

import ch.hslu.ad.n21.buffer.sema.BoundedBuffer;
import ch.hslu.ad.n21.buffer.sema.Consumer;
import ch.hslu.ad.n21.buffer.sema.Producer;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.slf4j.LoggerFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Testfälle für {@link ch.hslu.ad.n22.executor.plain}.
 */
public class PlainThreadPoolTest {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(PlainThreadPoolTest.class);

    public PlainThreadPoolTest() {
    }

    /**
     * Test of getSum method, of class Producer run on PlainThreadPool.
     */
    @Test
    public void testGetSumP() {
        final BoundedBuffer<Integer> queue = new BoundedBuffer<>(50);
        final Producer instance = new Producer(queue, 42);
        final PlainThreadPool workerPool = new PlainThreadPool(10, 5);
        workerPool.execute(instance);
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException ex) {
            LOG.debug(ex.getMessage());
        }
        long expResult = 861L;
        long result = instance.getSum();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSum method, of class Producer and Consumer run on
     * PlainThreadPool.
     */
    @Test
    public void testGetSumNPandMC() {
        final Random random = new Random();
        final PlainThreadPool workerPool = new PlainThreadPool(10, 5);
        final int nPros = 3;
        final int mCons = 2;
        final Producer[] producers = new Producer[nPros];
        final Consumer[] consumers = new Consumer[mCons];
        final BoundedBuffer<Integer> queue = new BoundedBuffer<>(50);
        for (int i = 0; i < nPros; i++) {
            producers[i] = new Producer(queue, random.nextInt(10000));
            workerPool.execute(producers[i]);
        }
        for (int i = 0; i < mCons; i++) {
            consumers[i] = new Consumer(queue);
            workerPool.execute(consumers[i]);
        }
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException ex) {
            LOG.debug(ex.getMessage());
        }
        long expResult = 0;
        for (int i = 0; i < nPros; i++) {
            LOG.info("Prod " + (char) (i + 65) + " = " + producers[i].getSum());
            expResult += producers[i].getSum();
        }
        long result = 0;
        for (int i = 0; i < mCons; i++) {
            LOG.info("Cons " + (char) (i + 65) + " = " + consumers[i].getSum());
            result += consumers[i].getSum();
        }
        LOG.info("{} = {}", expResult, result);
        assertEquals(expResult, result);
    }
}
