package org.kuali.hr.time.task.service;


import org.kuali.hr.time.task.Task;

import java.sql.Date;
import java.util.List;

public interface TaskService {
    public Task getTask(Long task, Date asOfDate);
    public void saveTask(Task task);
    public void saveTasks(List<Task> tasks);
}
