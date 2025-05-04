package ch.hslu.ad.exercise.n4.findfile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.concurrent.CountedCompleter;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicInteger;

public final class FindFileTask extends CountedCompleter<String> {

    private static final Logger LOG = LoggerFactory.getLogger(FindFileTask.class);

    private final String regex;
    private final File dir;
    private final AtomicReference<String> result;
    private final AtomicBoolean found;
    private final AtomicInteger counter;

    public FindFileTask(String regex, File dir, AtomicBoolean found, AtomicInteger counter) {
        this(null, regex, dir, new AtomicReference<>(null), found, counter);
    }

    private FindFileTask(CountedCompleter<?> parent, String regex, File dir,
                         AtomicReference<String> result,
                         AtomicBoolean found,
                         AtomicInteger counter) {
        super(parent);
        this.regex = regex;
        this.dir = dir;
        this.result = result;
        this.found = found;
        this.counter = counter;
    }

    @Override
    public void compute() {
        if (found.get()) {
            quietlyCompleteRoot();
            return;
        }

        counter.incrementAndGet();
        LOG.debug("Durchsuche: {}", dir.getAbsolutePath());

        File[] files = dir.listFiles();
        if (files == null) {
            LOG.debug("Kein Zugriff auf: {}", dir.getAbsolutePath());
            tryComplete();
            return;
        }

        for (File file : files) {
            if (found.get()) {
                quietlyCompleteRoot();
                return;
            }

            if (file.isDirectory()) {
                addToPendingCount(1);
                new FindFileTask(this, regex, file, result, found, counter).fork();
            } else if (file.getName().equalsIgnoreCase(regex)) {
                if (result.compareAndSet(null, file.getParent())) {
                    found.set(true);
                    LOG.info("Datei gefunden in: {}", file.getParent());
                }
                quietlyCompleteRoot();
                return;
            }
        }

        tryComplete();
    }

    @Override
    public String getRawResult() {
        return result.get();
    }
}
