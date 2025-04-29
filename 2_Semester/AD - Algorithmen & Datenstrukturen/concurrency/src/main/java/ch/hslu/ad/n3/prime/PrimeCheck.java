package ch.hslu.ad.n3.prime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public final class PrimeCheck {

    private static final Logger LOG = LoggerFactory.getLogger(PrimeCheck.class);
    private static final int PRIME_COUNT = 100;

    private PrimeCheck() {
    }

    public static void main(String[] args) throws InterruptedException {
        final int threads = Math.max(1, Runtime.getRuntime().availableProcessors() - 2);
        final ExecutorService executor = Executors.newFixedThreadPool(threads);

        final BlockingQueue<BigInteger> foundPrimes = new LinkedBlockingQueue<>();
        final AtomicInteger primeCounter = new AtomicInteger(0);
        final Random random = new Random();

        LOG.info("Suche nach {} Primzahlen mit {} Threads gestartet.", PRIME_COUNT, threads);

        Runnable primeProducer = () -> {
            while (primeCounter.get() < PRIME_COUNT) {
                BigInteger bi = new BigInteger(1024, random);
                if (bi.isProbablePrime(Integer.MAX_VALUE)) {
                    int index = primeCounter.incrementAndGet();
                    if (index <= PRIME_COUNT) {
                        foundPrimes.add(bi);
                        LOG.info("{} : {}...", index, bi.toString().substring(0, 20));
                    }
                }
            }
        };

        for (int i = 0; i < threads; i++) {
            executor.submit(primeProducer);
        }

        while (primeCounter.get() < PRIME_COUNT) {
            Thread.sleep(100);
        }

        executor.shutdownNow();
        LOG.info("Suche abgeschlossen. {} Primzahlen gefunden.", foundPrimes.size());
    }
}
