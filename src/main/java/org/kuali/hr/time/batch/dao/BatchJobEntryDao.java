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
package org.kuali.hr.time.batch.dao;

import java.util.List;
import java.util.Map;

import org.kuali.hr.time.batch.BatchJobEntry;

public interface BatchJobEntryDao {
	/**
	 * Save or update batch job entry
	 * @param batchJobEntry
	 */
    public void saveOrUpdate(BatchJobEntry batchJobEntry);
    /**
     * Get batch job entry by id
     * @param batchJobEntryId
     * @return
     */
    public BatchJobEntry getBatchJobEntry(Long batchJobEntryId);
    /**
     * Get batch job entry by batch job id
     * @param batchJobEntryId
     * @return
     */
    public List<BatchJobEntry> getBatchJobEntries(Long batchJobEntryId);
    /**
     * Get batch job entries for ip and status
     * @param ip
     * @param status
     * @return
     */
    public List<BatchJobEntry> getBatchJobEntries(String ip, String status);
    List<BatchJobEntry> getBatchJobEntries(Map<String, Object> criteria);
}
