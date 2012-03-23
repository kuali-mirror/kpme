package org.kuali.hr.time.task.dao;


import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.task.Task;

public interface TaskDao {
    public Task getTask(Long task, Date asOfDate);

    public void saveOrUpdate(Task task);
    public void saveOrUpdate(List<Task> tasks);
    public Task getMaxTask();

    List<Task> getTasks(Long task, String description, Long workArea, String workAreaDesc, Date fromEffdt, Date toEffdt);
}
