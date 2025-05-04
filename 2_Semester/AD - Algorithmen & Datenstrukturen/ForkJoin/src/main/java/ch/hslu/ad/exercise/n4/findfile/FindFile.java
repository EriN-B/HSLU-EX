package ch.hslu.ad.exercise.n4.findfile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public final class FindFile {

    private static final Logger LOG = LoggerFactory.getLogger(FindFile.class);

    private FindFile() {}

    public static void findFile(final String name, final File dir, AtomicBoolean found, AtomicInteger count) {
        if (found.get()) return;

        count.incrementAndGet();
        LOG.debug("Durchsuche: {}", dir.getAbsolutePath());

        final File[] list = dir.listFiles();
        if (list != null) {
            for (File file : list) {
                if (found.get()) return;

                if (file.isDirectory()) {
                    findFile(name, file, found, count);
                } else if (name.equalsIgnoreCase(file.getName())) {
                    found.set(true);
                    LOG.info("Datei gefunden in: {}", file.getParentFile().getAbsolutePath());
                    return;
                }
            }
        } else {
            LOG.debug("Kein Zugriff auf: {}", dir.getAbsolutePath());
        }
    }
}
