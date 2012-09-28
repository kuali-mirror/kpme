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

public interface BatchJobService {

	/**
	 * Fetch a BatchJob by a given ID
	 * @param batchJobId Database ID of the BatchJob to fetch.
	 * @return The BatchJob matching batchJobId.
	 */
	public BatchJob getBatchJob(Long batchJobId);

    /**
     * Provides a List of BatchJob objects that match the indicated hrPyCalendarEntryId.
     * @param hrPyCalendarEntryId The id of PayCalendarEntries objects to match.
     * @return List of BatchJob objects.
     */
    public List<BatchJob> getBatchJobs(String hrPyCalendarEntryId);

    /**
     * Get a List of BatchJob objects for the given parameters.
     *
     * @param hrPyCalendarEntryId The pay calendar entry we are looking for.
     * @param batchJobStatus Only jobs of this status will be returned.
     * @return List of BatchJob objects.
     */
    public List<BatchJob> getBatchJobs(String hrPyCalendarEntryId, String batchJobStatus);

    /**
     * Saves or updates the provided BatchJob.
     * @param batchJob The object to save.
     */
    public void saveBatchJob(BatchJob batchJob);
}
