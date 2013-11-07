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
package org.kuali.kpme.core.api.task;

import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;
import org.kuali.kpme.core.api.util.HrApiConstants;
import org.kuali.kpme.core.api.workarea.WorkAreaContract;

/**
 * <p>TaskContract interface.</p>
 *
 */
public interface TaskContract extends HrBusinessObjectContract {
	
	public static final String CACHE_NAME = HrApiConstants.CacheNamespace.NAMESPACE_PREFIX + "Task";
	
	/**
	 * The Primary Key of a Task entry saved in a database
	 *
	 * <p>
	 * tkTaskId of Task
	 * </p>
	 * 
	 * @return tkTaskId for Task
	 */
	public String getTkTaskId() ;
	
	/**
	 * Numeric value for the task 
	 *
	 * <p>
	 * task of Task
	 * </p>
	 * 
	 * @return task for Task
	 */
	public Long getTask();
	
	/**
	 * WorkArea string of the WorkArea object associated with a Task
	 *
	 * <p>
	 * workArea of Task
	 * </p>
	 * 
	 * @return workArea for Task
	 */
	public Long getWorkArea();

	/**
	 * Text field used to identify the task. This description is presented to the employee 
	 * when selecting the assignment to clock in or manually record their time
	 *
	 * <p>
	 * description of Task
	 * </p>
	 * 
	 * @return description for Task
	 */
    public String getDescription();
    
    /**
	 * Additional description field. This could be longer since it is not going to be displayed
	 * on the timesheet assignment drop down.
	 *
	 * <p>
	 * administrativeDescription of Task
	 * </p>
	 * 
	 * @return administrativeDescription for Task
	 */
    public String getAdministrativeDescription() ;
    
    /**
   	 * TODO: is this field needed???
   	 * 
   	 * @return userPricipalId for Task
   	 */
    public String getUserPrincipalId();

    /**
	 * WorkArea object associated with a Task
	 *
	 * <p>
	 * workAreaObj of Task
	 * </p>
	 * 
	 * @return workAreaObj for Task
	 */
	public WorkAreaContract getWorkAreaObj();	
}
