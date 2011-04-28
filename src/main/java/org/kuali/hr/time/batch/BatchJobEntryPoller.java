package org.kuali.hr.time.batch;

import org.apache.log4j.Logger;
import org.kuali.hr.time.service.base.TkServiceLocator;

/**
 * Runs on each worker node, schedules jobs to run on the thread pool.
 */
public class BatchJobEntryPoller extends Thread  {
    private static final Logger LOG = Logger.getLogger(BatchJobEntryPoller.class);

    BatchJobEntryThreadManager manager;
    int secondsToSleep;

    public BatchJobEntryPoller(BatchJobEntryThreadManager manager, int sleepSeconds) {
        this.manager = manager;
        this.secondsToSleep = sleepSeconds;
    }

	@Override
	public void run() {
        while(true) {
            try {

    		    //TODO: Find any jobs for this ip address that have a status of S

	        	//TODO: Add jobs ot the manager
                Thread.sleep(1000 * secondsToSleep);
            } catch (Exception e) {
                LOG.error(e);
            }
        }
	}

}
