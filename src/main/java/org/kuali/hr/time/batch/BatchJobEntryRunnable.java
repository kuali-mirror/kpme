/**
 * Copyright 2004-2012 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.hr.time.batch;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TkConstants;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

public abstract class BatchJobEntryRunnable implements Runnable{

    private Logger LOG = Logger.getLogger(BatchJobEntryRunnable.class);

    long startTime;
    long endTime;
	Long tkBatchJobEntryId;
	Long tkBatchJobId;
    BatchJobEntry batchJobEntry;

    public BatchJobEntryRunnable(BatchJobEntry entry) {
        this.batchJobEntry = entry;
        this.tkBatchJobEntryId = entry.getTkBatchJobEntryId();
        this.tkBatchJobId = entry.getTkBatchJobId();
    }

    /**
     * Method that is called before the user function doWork() is called. Any
     * pre-work maintenance can be done in this method.
     */
    void doBeforeRun() {
        startTime = System.currentTimeMillis();
        LOG.debug("Before run.");
        batchJobEntry.setBatchJobEntryStatus(TkConstants.BATCH_JOB_ENTRY_STATUS.RUNNING);
        TkServiceLocator.getBatchJobEntryService().saveBatchJobEntry(batchJobEntry);
    }

	@Override
	public final void run() {
        	TkServiceLocator.getTransactionTemplate().execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					try {
						doBeforeRun();
						doWork();
						doAfterRun();
					}  catch (Throwable t) {
			            LOG.warn("BatchJobEntry: " + batchJobEntry.getTkBatchJobEntryId() + " in Exception status.");
			            t.printStackTrace();
			            batchJobEntry.setBatchJobException(t.getStackTrace().toString());
			        }
				};
        	});
	}

    /**
     * Implement this method in your subclass. Place business logic here to handle
     * whatever needs to be done for this unit of work.
     *
     * @throws Exception when problem encountered during work processing, exception
     * stored in BatchJobEntry, and transaction rolled back.
     */
    public abstract void doWork() throws Exception;

    /**
     * Method that is called after the user function doWork() is called. Any
     * post-work maintenance can be done in this method.
     */
    void doAfterRun() {
        endTime = System.currentTimeMillis();
        long runtime = endTime - startTime;
        runtime = (runtime > 0) ? runtime : 1; // hack around 0 length job... just in case.
        LOG.debug("Job finished in " + runtime / 1000 + " seconds.");

        if (StringUtils.isEmpty(batchJobEntry.getBatchJobException())) {
            batchJobEntry.setBatchJobEntryStatus(TkConstants.BATCH_JOB_ENTRY_STATUS.FINISHED);
        } else {
            batchJobEntry.setBatchJobEntryStatus(TkConstants.BATCH_JOB_ENTRY_STATUS.EXCEPTION);
        }
        TkServiceLocator.getBatchJobEntryService().saveBatchJobEntry(batchJobEntry);
    }

	public Long getTkBatchJobEntryId() {
		return tkBatchJobEntryId;
	}

	public Long getTkBatchJobId() {
		return tkBatchJobId;
	}

    public BatchJobEntry getBatchJobEntry() {
        return batchJobEntry;
    }
}
