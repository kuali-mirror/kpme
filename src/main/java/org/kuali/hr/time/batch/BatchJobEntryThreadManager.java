package org.kuali.hr.time.batch;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Spring Bean to fire upon initialization.
 *
 * Runs on each worker node, manages runables with the ExecutorService.
 */
public class BatchJobEntryThreadManager implements InitializingBean, DisposableBean {

    ExecutorService pool;
    BatchJobEntryPoller poller;
    private static final Logger LOG = Logger.getLogger(BatchJobEntryThreadManager.class);

    @Override
    public void destroy() throws Exception {
        pool.shutdown();
        try {
            if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                pool.shutdownNow(); // Cancel currently executing tasks
            }
        } catch (InterruptedException ie) {
            pool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // create thread pool
        pool = Executors.newFixedThreadPool(5);
        poller = new BatchJobEntryPoller(this, 30);
        poller.start();
    }
}
