/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.core.task.service;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.task.Task;
import org.kuali.kpme.core.api.task.service.TaskService;
import org.kuali.kpme.core.task.TaskBo;
import org.kuali.kpme.core.task.dao.TaskDao;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.service.SequenceAccessorService;

import java.util.Collections;
import java.util.List;

public class TaskServiceImpl implements TaskService {

    private static final String TK_TASK_S = "TK_TASK_S";
    
	private TaskDao taskDao;
	private SequenceAccessorService sequenceAccessorService;
    private BusinessObjectService businessObjectService;

	@Override
	public Task getTask(String tkTaskId) {
		return TaskBo.to(getTaskDao().getTask(tkTaskId));
	}

    

	@Override
    public Task getTask(Long task, LocalDate asOfDate) {
        TaskBo taskObj =  getTaskDao().getTask(task, asOfDate);
        if(taskObj == null){
        	taskObj = new TaskBo();
        	taskObj.setActive(true);
        	taskObj.setEffectiveLocalDate(asOfDate);
        	// zero out null tasks
        	if(task != null) {
        		taskObj.setTask(task);
        	}
        	else {
        		taskObj.setTask(0L);
        	}
        	taskObj.setDescription(HrConstants.TASK_DEFAULT_DESP);
        	taskObj.setTkTaskId("0");
        }
        return TaskBo.to(taskObj);
    }

    @Override
    public Task saveTask(Task task) {
        TaskBo bo = getBusinessObjectService().save(TaskBo.from(task));
        return TaskBo.to(bo);
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Task> saveTasks(List<Task> tasks) {
        List<TaskBo> bos = ModelObjectUtils.transform(tasks, TaskBo.toTaskBo);
        bos = (List<TaskBo>)getBusinessObjectService().save(bos);
        return CollectionUtils.isEmpty(bos) ? Collections.<Task>emptyList() : ModelObjectUtils.transform(bos, TaskBo.toTask);
    }

    public void setTaskDao(TaskDao taskDao) {
        this.taskDao = taskDao;
    }
    
    public TaskDao getTaskDao() {
		return taskDao;
	}   
    

	@Override
	public Task getMaxTask(Long workArea){
		return TaskBo.to(getTaskDao().getMaxTask(workArea));
	}

    @Override
    public List<Task> getTasks(String task, String description, String workArea, LocalDate fromEffdt, LocalDate toEffdt) {
        Long taskNumber = StringUtils.isEmpty(task) ? null : Long.parseLong(task);
        Long workAreaNumber = StringUtils.isEmpty(workArea) ? null : Long.parseLong(workArea);
        
        return ModelObjectUtils.transform(getTaskDao().getTasks(taskNumber, description, workAreaNumber, fromEffdt, toEffdt), TaskBo.toTask);
    }
    
    @Override
    public int getTaskCount(Long task) {
    	return getTaskDao().getTaskCount(task);
    }

	@Override
	public Long getNextTaskNumber() {
		return getSequenceAccessorService().getNextAvailableSequenceNumber(TK_TASK_S) + 1000;
	}

	public SequenceAccessorService getSequenceAccessorService() {
		if(this.sequenceAccessorService == null) {
			this.setSequenceAccessorService(KRADServiceLocator.getSequenceAccessorService());		
		}
		return sequenceAccessorService;
	}

	public void setSequenceAccessorService(SequenceAccessorService sequenceAccessorService) {
		this.sequenceAccessorService = sequenceAccessorService;
	}



	public BusinessObjectService getBusinessObjectService() {
		if(this.businessObjectService == null) {
			this.setBusinessObjectService(KRADServiceLocator.getBusinessObjectService());
		}
		return businessObjectService;
	}


	public void setBusinessObjectService(BusinessObjectService businessObjectService) {
		this.businessObjectService = businessObjectService;
	}
		
    
}
