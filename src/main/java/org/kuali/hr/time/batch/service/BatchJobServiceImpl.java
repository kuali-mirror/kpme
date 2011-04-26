package org.kuali.hr.time.batch.service;

import org.kuali.hr.time.batch.BatchJob;
import org.kuali.hr.time.batch.dao.BatchJobDao;

/**
 * Created by IntelliJ IDEA.
 * User: kenneth
 * Date: 4/26/11
 * Time: 1:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class BatchJobServiceImpl implements BatchJobService {
	 private BatchJobDao batchJobDao;
	 
    public void setBatchJobDao(BatchJobDao batchJobDao) {
        this.batchJobDao = batchJobDao;
    }
	 public BatchJob getBatchJob(Long batchJobId){
		 return batchJobDao.getBatchJob(batchJobId);
		 
	 }
	 
	 
}
