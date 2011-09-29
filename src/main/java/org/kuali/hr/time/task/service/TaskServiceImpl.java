package org.kuali.hr.time.task.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.task.Task;
import org.kuali.hr.time.task.dao.TaskDao;

public class TaskServiceImpl implements TaskService {

    private TaskDao taskDao;

    @Override
    public Task getTask(Long task, Date asOfDate) {
        return taskDao.getTask(task, asOfDate);
    }

    @Override
    public void saveTask(Task task) {
        taskDao.saveOrUpdate(task);
    }

    @Override
    public void saveTasks(List<Task> tasks) {
        taskDao.saveOrUpdate(tasks);
    }

    public void setTaskDao(TaskDao taskDao) {
        this.taskDao = taskDao;
    } 

	@Override
	public Task getMaxTask(){
		return taskDao.getMaxTask();
	}
	
	
}
