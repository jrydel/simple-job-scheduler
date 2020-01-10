package cz.jr.simplejobscheduler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;
import java.util.concurrent.ThreadFactory;

/**
 * Created by Jiří Rýdel on 1/10/20, 8:57 PM
 */
public class SimpleThreadFactory implements ThreadFactory {

    private static final Logger LOG = LogManager.getLogger(SimpleThreadFactory.class);

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName("Simple-job-scheduler_" + UUID.randomUUID().toString());
        thread.setUncaughtExceptionHandler((t, e) -> LOG.error(String.format("Unexpected error occurred in: %s", t.getName()), e));
        return thread;
    }
}
