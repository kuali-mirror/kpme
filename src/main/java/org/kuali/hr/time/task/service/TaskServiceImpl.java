package org.kuali.hr.time.task.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.cache.CacheResult;
import org.kuali.hr.time.task.Task;
import org.kuali.hr.time.task.dao.TaskDao;
import org.kuali.hr.time.util.TkConstants;

public class TaskServiceImpl implements TaskService {

    private TaskDao taskDao;

    @Override
    @CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
    public Task getTask(Long task, Date asOfDate) {
        Task taskObj =  taskDao.getTask(task, asOfDate);
        if(taskObj == null){
        	taskObj = new Task();
        	taskObj.setActive(true);
        	taskObj.setEffectiveDate(asOfDate);
        	taskObj.setTask(task);
        	taskObj.setDescription("Default");
        }
        return taskObj;
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
