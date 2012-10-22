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
package org.kuali.hr.time.task.service;

import java.sql.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.task.Task;
import org.kuali.hr.time.task.dao.TaskDao;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workarea.WorkArea;

public class TaskServiceImpl implements TaskService {

    private TaskDao taskDao;
    
	@Override
	public Task getTask(String tkTaskId) {
		return taskDao.getTask(tkTaskId);
	}

    @Override
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
    public List<Task> getTasks(String task, String description, String workArea, Date fromEffdt, Date toEffdt) {
        Long taskNumber = StringUtils.isEmpty(task) ? null : Long.parseLong(task);
        Long workAreaNumber = StringUtils.isEmpty(workArea) ? null : Long.parseLong(workArea);
        
        return taskDao.getTasks(taskNumber, description, workAreaNumber, fromEffdt, toEffdt);
    }
    
    @Override
    public int getTaskCount(Long task) {
    	return taskDao.getTaskCount(task);
    }
}
