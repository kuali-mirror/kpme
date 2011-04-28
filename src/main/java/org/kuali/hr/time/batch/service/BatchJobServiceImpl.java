package org.kuali.hr.time.batch.service;

import org.kuali.hr.time.batch.BatchJob;
import org.kuali.hr.time.batch.dao.BatchJobDao;

import java.util.List;

public class BatchJobServiceImpl implements BatchJobService {
    private BatchJobDao batchJobDao;

    public void setBatchJobDao(BatchJobDao batchJobDao) {
        this.batchJobDao = batchJobDao;
    }

    public BatchJob getBatchJob(Long batchJobId){
        return batchJobDao.getBatchJob(batchJobId);
    }

    @Override
    public List<BatchJob> getBatchJobs(Long payCalendarEntryId) {
        return batchJobDao.getBatchJobs(payCalendarEntryId);
    }

    @Override
    public List<BatchJob> getBatchJobs(Long payCalendarEntryId, String batchJobStatus) {
        return batchJobDao.getPayCalendarEntries(payCalendarEntryId, batchJobStatus);
    }
    
    @Override
    public void saveBatchJob(BatchJob batchJob) {
    	this.batchJobDao.saveOrUpdate(batchJob);
    }
}
