package org.kuali.hr.time.batch.service;

import java.util.List;

import org.kuali.hr.time.batch.BatchJobEntry;
import org.kuali.hr.time.batch.dao.BatchJobEntryDao;

public class BatchJobEntryServiceImpl implements BatchJobEntryService {
	 private BatchJobEntryDao batchJobEntryDao;

	    public void setBatchJobEntryDao(BatchJobEntryDao batchJobEntryDao) {
	        this.batchJobEntryDao = batchJobEntryDao;
	    }
	    @Override
	    public BatchJobEntry getBatchJobEntry(Long batchJobEntryId){
	        return batchJobEntryDao.getBatchJobEntry(batchJobEntryId);
	    }
	    
	    @Override
	    public void saveBatchJobEntry(BatchJobEntry batchJobEntry){
	    	this.batchJobEntryDao.saveOrUpdate(batchJobEntry);
	    }
	    
	    @Override
	    public List<BatchJobEntry> getBatchJobEntries(Long batchJobId) {
	        return batchJobEntryDao.getBatchJobEntries(batchJobId);
	    }
}
