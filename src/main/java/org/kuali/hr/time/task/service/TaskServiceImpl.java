package org.kuali.hr.time.task.service;

import org.codehaus.plexus.util.StringUtils;
import org.kuali.hr.time.cache.CacheResult;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.task.Task;
import org.kuali.hr.time.task.dao.TaskDao;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workarea.WorkArea;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
        	taskObj.setDescription(TkConstants.TASK_DEFAULT_DESP);
        	taskObj.setTkTaskId("0");
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

    @Override
    public List<Task> getTasks(String task, String description, String workArea, String workAreaDesc, Date fromEffdt, Date toEffdt) {

        Long taskForQuery = StringUtils.isEmpty(task) ? null : Long.parseLong(task);

        List<Task> results = new ArrayList<Task>();
        if (StringUtils.isNotEmpty(workAreaDesc)) {
            List<WorkArea> workAreas = TkServiceLocator.getWorkAreaService().getWorkAreas(null, null, workAreaDesc, null, null, "Y", "N");
            for (WorkArea wa : workAreas) {
                List<Task> tasks = taskDao.getTasks(taskForQuery, description, wa.getWorkArea(), workAreaDesc, fromEffdt, toEffdt);
                results.addAll(tasks);
            }
        } else {
            Long wa = StringUtils.isEmpty(workArea) ? null : Long.parseLong(workArea);
            List<Task> tasks = taskDao.getTasks(taskForQuery, description, wa, workAreaDesc, fromEffdt, toEffdt);
            results.addAll(tasks);
        }

        return results;
    }
	
	
}
