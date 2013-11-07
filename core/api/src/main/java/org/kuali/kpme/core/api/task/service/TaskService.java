/**
 * Copyright 2004-2013 The Kuali Foundation
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
package org.kuali.kpme.core.api.task.service;


import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.task.TaskContract;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

public interface TaskService {
	/**
	 * Fetch Task of a particular id
	 * @param tkTaskId
	 * @return
	 */
    @Cacheable(value= TaskContract.CACHE_NAME, key="'tkTaskId=' + #p0")
	public TaskContract getTask(String tkTaskId);
	/**
	 * Fetch Task of a particular date
	 * @param task
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= TaskContract.CACHE_NAME, key="'task=' + #p0 + '|' + 'asOfDate=' + #p1")
    public TaskContract getTask(Long task, LocalDate asOfDate);
    /**
     * Save a given Task
     * @param task
     */
    @CacheEvict(value={TaskContract.CACHE_NAME}, allEntries = true)
    public void saveTask(TaskContract task);
    /**
     * Save a List of Tasks
     * @param tasks
     */
    @CacheEvict(value={TaskContract.CACHE_NAME}, allEntries = true)
    public void saveTasks(List<? extends TaskContract> tasks);
    
	public TaskContract getMaxTask();

    List<? extends TaskContract> getTasks(String task, String description, String workArea, LocalDate fromEffdt, LocalDate toEffdt);
    
    /**
     * get the count of Tasks by given task
     * @param task
     * @return int
     */
    public int getTaskCount(Long task);
}
