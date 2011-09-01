package org.kuali.hr.time.task.service;


import org.kuali.hr.job.Job;
import org.kuali.hr.time.task.Task;

import java.sql.Date;
import java.util.List;

public interface TaskService {
	/**
	 * Fetch Task of a particular date
	 * @param task
	 * @param asOfDate
	 * @return
	 */
    public Task getTask(Long task, Date asOfDate);
    /**
     * Save a given Task
     * @param task
     */
    public void saveTask(Task task);
    /**
     * Save a List of Tasks
     * @param tasks
     */
    public void saveTasks(List<Task> tasks);
    
	public Task getMaxTaskByWorkArea(Long workArea);

}
