package org.kuali.hr.time.batch;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Spring Bean to fire upon initialization.
 *
 * Runs on each worker node, manages runables with the ExecutorService.
 */
public class TkBatchManager implements InitializingBean, DisposableBean {
    public static final String MESSAGE_QUEUE_CHECKER_IP_PARAM = "tk.batch.master.ip";
    public static final String WORKER_THREAD_POOL_SIZE_PARAM = "tk.batch.threadpool.size";
    public static final String BATCH_POLLER_SLEEP_PARAM = "tk.batch.poller.seconds.sleep";
    public static final String BATCH_MASTER_POLL_SLEEP_PARAM = "tk.batch.job.poller.seconds.sleep";
    public static final String BATCH_DAY_WINDOW_FOR_POLLING_PARAM = "tk.batch.job.days.polling.window";
    public static final String BATCH_WORK_STARUP_SLEEP = "tk.batch.thread.startup.sleep";

    ExecutorService pool;
    BatchJobEntryPoller poller;
    private static final Logger LOG = Logger.getLogger(TkBatchManager.class);

    // Some defaults for setting up our threads.
    int masterJobPollSleep = 60;
    int workerEntryPollSleep = 60;
    int threadPoolSize = 5;
    int daysToPollWindow = 30;
    int startupSleep = 30;

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
        parseConfiguration();

        // create thread pool
        pool = Executors.newFixedThreadPool(this.threadPoolSize);
        poller = new BatchJobEntryPoller(this, this.workerEntryPollSleep, this.startupSleep);
        poller.start();
        if (isMasterBatchNode()) {
            BatchJobManagerThread batchJobManagerThread = new BatchJobManagerThread(this.masterJobPollSleep, this.daysToPollWindow, this.startupSleep);
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

    /**
     * Helper method to load configuration values, cast to ints, and report errors.
     */
    private void parseConfiguration() {
        String poolSizeString = ConfigContext.getCurrentContextConfig().getProperty(WORKER_THREAD_POOL_SIZE_PARAM);
        String masterJobSleep = ConfigContext.getCurrentContextConfig().getProperty(BATCH_MASTER_POLL_SLEEP_PARAM);
        String workerPollSleep = ConfigContext.getCurrentContextConfig().getProperty(BATCH_POLLER_SLEEP_PARAM);
        String daysString = ConfigContext.getCurrentContextConfig().getProperty(BATCH_DAY_WINDOW_FOR_POLLING_PARAM);
        String workStartup = ConfigContext.getCurrentContextConfig().getProperty(BATCH_WORK_STARUP_SLEEP);

        try {
            this.threadPoolSize = Integer.parseInt(poolSizeString);
            this.masterJobPollSleep = Integer.parseInt(masterJobSleep);
            this.daysToPollWindow = Integer.parseInt(daysString);
            this.workerEntryPollSleep = Integer.parseInt(workerPollSleep);
            this.startupSleep = Integer.parseInt(workStartup);
        } catch (NumberFormatException nfe) {
            LOG.warn("Bad parameter when setting up Batch Configuration. using defaults");
        }
    }
}
