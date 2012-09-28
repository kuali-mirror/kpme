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
