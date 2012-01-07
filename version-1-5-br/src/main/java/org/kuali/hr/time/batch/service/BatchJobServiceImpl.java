package org.kuali.hr.time.batch.service;

import java.util.List;

import org.kuali.hr.time.batch.BatchJob;
import org.kuali.hr.time.batch.dao.BatchJobDao;

public class BatchJobServiceImpl implements BatchJobService {
    private BatchJobDao batchJobDao;

    public void setBatchJobDao(BatchJobDao batchJobDao) {
        this.batchJobDao = batchJobDao;
    }

    public BatchJob getBatchJob(Long batchJobId){
        return batchJobDao.getBatchJob(batchJobId);
    }

    @Override
    public List<BatchJob> getBatchJobs(String hrPyCalendarEntryId) {
        return batchJobDao.getBatchJobs(hrPyCalendarEntryId);
    }

    @Override
    public List<BatchJob> getBatchJobs(String hrPyCalendarEntryId, String batchJobStatus) {
        return batchJobDao.getPayCalendarEntries(hrPyCalendarEntryId, batchJobStatus);
    }
    
    @Override
    public void saveBatchJob(BatchJob batchJob) {
    	this.batchJobDao.saveOrUpdate(batchJob);
    }

}
