/*
 * Copyright 2024 Hochschule Luzern - Informatik.
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

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Disabled;

/**
 * Tests für das nach oben begrenzte Semaphor.
 */
public class SemaphoreTest {

    public SemaphoreTest() {
    }

    @Test
    @Disabled("bis Semaphore implementiert wird...")
    public void testSemaphoreLowLimit() throws Exception {
        int permits = 4;
        int limit = 3;
        String message = "limit < permits";
        assertThatThrownBy(() -> {
            Semaphore sema = new Semaphore(permits, limit);
        }).isInstanceOf(IllegalArgumentException.class).hasMessageStartingWith(message);
    }

    @Test
    @Disabled("bis Semaphore implementiert wird...")
    public void testReleaseOverflowLimit() throws Exception {
        int permits = 3;
        int limit = 3;
        String message = "overflow limit";
        assertThatThrownBy(() -> {
            Semaphore sema = new Semaphore(permits, limit);
            sema.release();
        }).isInstanceOf(ArithmeticException.class).hasMessageStartingWith(message);
    }

    @Test
    @Disabled("bis Semaphore implementiert wird...")
    public void testReleaseIntOverflowLimit() throws Exception {
        int permits = 0;
        int limit = 3;
        int releaseInt = 4;
        String message = "overflow limit";
        assertThatThrownBy(() -> {
            Semaphore sema = new Semaphore(permits, limit);
            sema.release(releaseInt);
        }).isInstanceOf(ArithmeticException.class).hasMessageStartingWith(message);
    }

    @Test
    @Disabled("bis Semaphore implementiert wird...")
    public void testAcquireIntOverflowPermits() throws Exception {
        int permits = 3;
        int limit = 3;
        int acquireInt = 4;
        String message = "overflow permits";
        assertThatThrownBy(() -> {
            Semaphore sema = new Semaphore(permits, limit);
            sema.acquire(acquireInt);
        }).isInstanceOf(ArithmeticException.class).hasMessageStartingWith(message);
    }

    @Test
    @Disabled("bis Semaphore implementiert wird...")
    public void testAcquireIntNegPermits() throws Exception {
        int permits = 3;
        int limit = 3;
        int acquireInt = -1;
        String message = "permits < 0";
        assertThatThrownBy(() -> {
            Semaphore sema = new Semaphore(permits, limit);
            sema.acquire(acquireInt);
        }).isInstanceOf(IllegalArgumentException.class).hasMessageStartingWith(message);
    }

    @Test
    @Disabled("bis Semaphore implementiert wird...")
    public void testReleaseIntNegPermits() throws Exception {
        int permits = 3;
        int limit = 3;
        int releaseInt = -1;
        String message = "permits < 0";
        assertThatThrownBy(() -> {
            Semaphore sema = new Semaphore(permits, limit);
            sema.release(releaseInt);
        }).isInstanceOf(IllegalArgumentException.class).hasMessageStartingWith(message);
    }
}
