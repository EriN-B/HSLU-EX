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
package ch.hslu.ad.exercise.n2.latch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * Ein Rennpferd, das durch ein Startsignal losläuft. Nach einer zufälligen Zeit
 * kommt es im Ziel an.
 */
public final class RaceHorse implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(RaceHorse.class);
    private final CountDownLatch startSignal;
    private final String name;
    private final Random random;

    /**
     * Erzeugt ein Rennpferd, das in die Starterbox eintritt.
     *
     * @param startSignal Starterbox.
     * @param name Name des Pferdes.
     */
    public RaceHorse(final CountDownLatch startSignal, final String name) {
        this.startSignal = startSignal;
        this.name = name;
        this.random = new Random();
    }

    @Override
    public void run() {
        LOG.info("Rennpferd {} geht in die Box.", name);
        try {
            startSignal.await();
        } catch (InterruptedException ex) {
            LOG.debug("Rennpferd {} wartete vergeblich in der BOX", name);
            return;
        }

        try {
            LOG.info("Rennpferd {} laeuft los...", name);
            Thread.sleep(random.nextInt(3000));
            LOG.info("Rennpferd {} im ZIEL", name);
        }catch (InterruptedException e){
            LOG.info("Rennpferd {} auf dem Weg aufgehalten", name);
        }
    }
}
