package org.kuali.hr.time.batch.service;

import org.kuali.hr.time.batch.BatchJob;

/**
 * Created by IntelliJ IDEA.
 * User: kenneth
 * Date: 4/26/11
 * Time: 1:39 PM
 * To change this template use File | Settings | File Templates.
 */
public interface BatchJobService {
	/**
	 * Fetch a BatchJob by a given ID
	 * @param batchJobId
	 * @return
	 */
	public BatchJob getBatchJob(Long batchJobId);
}
