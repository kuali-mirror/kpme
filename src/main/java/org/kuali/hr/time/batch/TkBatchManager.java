package org.kuali.hr.time.batch;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.core.config.ConfigContext;
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
public class TkBatchManager implements InitializingBean, DisposableBean {
    public static final String MESSAGE_QUEUE_CHECKER_IP_PARAM = "tk.batch.master.ip";
    ExecutorService pool;
    BatchJobEntryPoller poller;
    private static final Logger LOG = Logger.getLogger(TkBatchManager.class);

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
        int secondsToSleep = 60;
        int daysToPoll = 30;

        // create thread pool
        pool = Executors.newFixedThreadPool(5);
        poller = new BatchJobEntryPoller(this, 30);
        poller.start();
        if (isMasterBatchNode()) {
            BatchJobManagerThread batchJobManagerThread = new BatchJobManagerThread(secondsToSleep, daysToPoll);
            batchJobManagerThread.start();
        }
    }

    /**
     * Helper method to determine whether the current machine is the master node,
     * namely, the node that will create all BatchJobEntries to run on the
     * worker nodes.
     *
     * @return true if this node is the master node.
     */
    public boolean isMasterBatchNode() {
		return StringUtils.equals(ConfigContext.getCurrentContextConfig().getProperty(MESSAGE_QUEUE_CHECKER_IP_PARAM), TKUtils.getIPNumber());
	}
}
