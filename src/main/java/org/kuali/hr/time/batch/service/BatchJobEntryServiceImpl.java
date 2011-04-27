package org.kuali.hr.time.batch.service;

import org.kuali.hr.time.batch.BatchJobEntry;
import org.kuali.hr.time.batch.dao.BatchJobEntryDao;

public class BatchJobEntryServiceImpl implements BatchJobEntryService {
	 private BatchJobEntryDao batchJobEntryDao;

	    public void setBatchJobEntryDao(BatchJobEntryDao batchJobEntryDao) {
	        this.batchJobEntryDao = batchJobEntryDao;
	    }

	    public BatchJobEntry getBatchJobEntry(Long batchJobEntryId){
	        return batchJobEntryDao.getBatchJobEntry(batchJobEntryId);
	    }
}
