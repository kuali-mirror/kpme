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
import java.util.Map;

import org.kuali.hr.time.batch.BatchJobEntry;
import org.kuali.hr.time.batch.dao.BatchJobEntryDao;

public class BatchJobEntryServiceImpl implements BatchJobEntryService {
    private BatchJobEntryDao batchJobEntryDao;

    public void setBatchJobEntryDao(BatchJobEntryDao batchJobEntryDao) {
        this.batchJobEntryDao = batchJobEntryDao;
    }

    @Override
    public BatchJobEntry getBatchJobEntry(Long batchJobEntryId) {
        return batchJobEntryDao.getBatchJobEntry(batchJobEntryId);
    }

    @Override
    public void saveBatchJobEntry(BatchJobEntry batchJobEntry) {
        this.batchJobEntryDao.saveOrUpdate(batchJobEntry);
    }

    @Override
    public List<BatchJobEntry> getBatchJobEntries(Long batchJobId) {
        return batchJobEntryDao.getBatchJobEntries(batchJobId);
    }

    @Override
    public List<BatchJobEntry> getBatchJobEntries(String ip, String status) {
       return batchJobEntryDao.getBatchJobEntries(ip, status);
    }

    @Override
    public List<BatchJobEntry> getBatchJobEntries(Map<String, Object> criteria) {
        return batchJobEntryDao.getBatchJobEntries(criteria);
    }
}
