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
package org.kuali.kpme.core.task;

import org.kuali.kpme.core.api.task.TaskContract;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.workarea.WorkArea;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class Task extends HrBusinessObject implements TaskContract {

	private static final String TASK = "task";

	private static final long serialVersionUID = -7536342291963303862L;

	public static final String CACHE_NAME = HrConstants.CacheNamespace.NAMESPACE_PREFIX + "Task";
	//KPME-2273/1965 Primary Business Keys List.	
	public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
            .add(TASK)
            .build();

    private String tkTaskId;
    private Long task;
    private Long workArea;
    private String description;
    private String administrativeDescription;
    
    private WorkArea workAreaObj;
	
	@Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
		return new ImmutableMap.Builder<String, Object>()
				.put(TASK, this.getTask())		
				.build();
	}
    
	public String getTkTaskId() {
		return tkTaskId;
	}

	public void setTkTaskId(String tkTaskId) {
		this.tkTaskId = tkTaskId;
	}
	
	public Long getTask() {
		return task;
	}

	public void setTask(Long task) {
		this.task = task;
	}

	public Long getWorkArea() {
		return workArea;
	}

	public void setWorkArea(Long workArea) {
		this.workArea = workArea;
	}

    public String getDescription() {
    	return description;
    }

    public void setDescription(String description) {
    	this.description = description;
    }
    
    public String getAdministrativeDescription() {
    	return administrativeDescription;
    }

    public void setAdministrativeDescription(String administrativeDescription) {
    	this.administrativeDescription = administrativeDescription;
    }

	public WorkArea getWorkAreaObj() {
		return workAreaObj;
	}

	public void setWorkAreaObj(WorkArea workAreaObj) {
		this.workAreaObj = workAreaObj;
	}

	@Override
	public String getUniqueKey() {
		return workArea + "_" + task;
	}

	@Override
	public String getId() {
		return getTkTaskId();
	}

	@Override
	public void setId(String id) {
		setTkTaskId(id);
	}

}