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
package org.kuali.kpme.core.api.assignment;

import java.util.List;

import org.kuali.kpme.core.api.assignment.account.AssignmentAccountContract;
import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;
import org.kuali.kpme.core.api.job.JobContract;
import org.kuali.kpme.core.api.task.TaskContract;
import org.kuali.kpme.core.api.util.HrApiConstants;
import org.kuali.kpme.core.api.workarea.WorkAreaContract;
import org.kuali.rice.kim.api.identity.Person;

/**
 * <p>AssignmentContract interface.</p>
 *
 */
public interface AssignmentContract extends HrBusinessObjectContract {
	
	public static final String CACHE_NAME = HrApiConstants.CacheNamespace.NAMESPACE_PREFIX + "Assignment";
	
	/**
	 * List of AssignmentAccounts associated with this Assignment
	 * 
	 * <p>
	 * assignmentAccounts of Assignment
	 * </p>
	 * 
	 * @return assignmentAccounts for Assignment
	 */
	public List<? extends AssignmentAccountContract> getAssignmentAccounts();
	
	/**
	 * Identifier of the employee associated with this Assignment
	 * 
	 * <p>
	 * principalId of Assignment
	 * </p>
	 * 
	 * @return principalId for Assignment
	 */
	public String getPrincipalId() ;

	/**
	 * Name of the employee associated with this Assignment
	 * 
	 * <p>
	 * employee name of Assignment
	 * </p>
	 * 
	 * @return employee name for Assignment
	 */
	public String getName();

	/**
	 * Job object associated with this Assignment
	 * 
	 * <p>
	 * job of Assignment
	 * </p>
	 * 
	 * @return job for Assignment
	 */
	public JobContract getJob();

	/**
	 * Job number of Job object associated with this Assignment
	 * 
	 * <p>
	 * jobNumber of Assignment
	 * </p>
	 * 
	 * @return jobNumber for Assignment
	 */
	public Long getJobNumber();

	/**
	 * Primary key of Job object associated with this Assignment
	 * 
	 * <p>
	 * hrJobId of Assignment
	 * </p>
	 * 
	 * @return hrJobId for Assignment
	 */
	public String getHrJobId();

	/**
	 * Provides us with the text to display to the user for clock actions on
	 * this assignment.
	 *
	 * <p>
	 * clockText of Assignment
	 * </p>
	 * 
	 * @return clockText for Assignment
	 */
	public String getClockText();

	/**
	 * The Primary Key of an Assignment entry saved in a database
	 *
	 * <p>
	 * tkAssignmentId of Assignment
	 * </p>
	 * 
	 * @return tkAssignmentId for Assignment
	 */
	public String getTkAssignmentId();

	/**
	 * Department based on the Job object associated with the Assignment
	 *
	 * <p>
	 * dept of Assignment
	 * </p>
	 * 
	 * @return dept for Assignment
	 */
	public String getDept();

	/**
	 * The work area for recording time which also drives approval routing. 
	 * Valid values based on the department on the job. 
	 *
	 * <p>
	 * workAreaObj of Assignment
	 * </p>
	 * 
	 * @return workAreaObj for Assignment
	 */
	public WorkAreaContract getWorkAreaObj();
	
	/**
	 * WorkArea field of WorkArea object associated with the Assignment
	 *
	 * <p>
	 * workArea of Assignment
	 * </p>
	 * 
	 * @return workArea for Assignment
	 */
	public Long getWorkArea();

	/**
	 * Task field of Task object associated with the Assignment
	 *
	 * <p>
	 * task of Assignment
	 * </p>
	 * 
	 * @return task for Assignment
	 */
	public Long getTask();

	/**
	 * Task field of Task object associated with the Assignment
	 *
	 * <p>
	 * task of Assignment
	 * </p>
	 * 
	 * @return task for Assignment
	 */
	public String getAssignmentDescription() ;

	/**
	 * Employee associated with the Assignment
	 *
	 * <p>
	 * principal of Assignment
	 * </p>
	 * 
	 * @return principal for Assignment
	 */
	public Person getPrincipal();

	/**
	 * Task object associated with the Assignment
	 *
	 * <p>
	 * taskObj of Assignment
	 * </p>
	 * 
	 * @return taskObj for Assignment
	 */
	public TaskContract getTaskObj();
	
	/**
	 * History flag for Assignment lookups 
	 * 
	 * <p>
	 * history of Assignment
	 * </p>
	 * 
	 * @return true if want to show history, false if not
	 */
	public Boolean getHistory();
	
	/**
	 * CalendarGroup associated with the Assignment
	 *
	 * <p>
	 * calGroup of Assignment
	 * </p>
	 * 
	 * @return calGroup for Assignment
	 */
    public String getCalGroup();

    /**
	 * String combination job, workarea and task of AssignmentDescriptionKey build with the Assignment
	 *
	 * <p>
	 * assignmentKey of Assignment
	 * </p>
	 * 
	 * @return assignmentKey for Assignment
	 */
    public String getAssignmentKey();
}
