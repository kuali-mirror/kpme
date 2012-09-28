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

public interface BatchJobEntryService {
	/**
	 * Fetch a BatchJobEntry by a given ID
	 * @param batchJobEntryId
	 * @return
	 */
	public BatchJobEntry getBatchJobEntry(Long batchJobEntryId);

    /**
     * Saves or updates the provided BatchJobEntry.
     * @param batchJobEntry The object we want to save.
     */
	public void saveBatchJobEntry(BatchJobEntry batchJobEntry);

    /**
     * For the indicated batchJobId, grab a List of BatchJobEntry objects.
     * @param batchJobId The ID to query against.
     * @return A List of BatchJobEntry objects.
     */
	public List<BatchJobEntry> getBatchJobEntries(Long batchJobId);

    /**
     * For the given parameters, return a List of BatchJobEntry objects.
     * @param ip The IP address we are interested in.
     * @param status The status.
     * @return A List of BatchJobEntry objects.
     */
    public List<BatchJobEntry> getBatchJobEntries(String ip, String status);

      /**
     * Fetch a list of BatchJob objects by the given criteria.
     * @param criteria
     * @return List of BatchJob objects.
     */
    List<BatchJobEntry> getBatchJobEntries(Map<String, Object> criteria);
}
