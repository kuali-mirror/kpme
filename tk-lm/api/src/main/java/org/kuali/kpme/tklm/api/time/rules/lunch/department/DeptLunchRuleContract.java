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
package org.kuali.kpme.tklm.api.time.rules.lunch.department;

import java.math.BigDecimal;

import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;
import org.kuali.kpme.core.api.department.DepartmentContract;
import org.kuali.kpme.core.api.job.JobContract;
import org.kuali.kpme.core.api.workarea.WorkAreaContract;
import org.kuali.rice.kim.api.identity.Person;

/**
 * <p>DeptLunchRuleContract interface</p>
 *
 */
public interface DeptLunchRuleContract extends HrBusinessObjectContract {
	
	/**
	 * The principal associated with the DeptLunchRule
	 * 
	 * <p>
	 * If a principal id is defined, only this specific employee will be subject to the automatic lunch deduction.
	 * <p>
	 * 
	 * @return principal for DeptLunchRule
	 */
	public Person getPrincipal();
	
	/**
	 * The Job object associated with the DeptLunchRule
	 * 
	 * <p>
	 * If job # is defined, only this specific employee's job will be subject to the automatic lunch deduction.
	 * <p>
	 * 
	 * @return job for DeptLunchRule
	 */
	public JobContract getJob();

	/**
	 * The WorkArea object associated with the DeptLunchRule
	 * 
	 * <p>
	 * If a work area is defined, only entries associated with a job in this work area will be subject to the automatic lunch deduction. 
	 * <p>
	 * 
	 * @return workAreaObj for DeptLunchRule
	 */
	public WorkAreaContract getWorkAreaObj();

	/**
	 * The Department object associated with the DeptLunchRule
	 * 
	 * <p>
	 * If a department is defined, only entries associated with a job in this department will be subject to the automatic lunch deduction. 
	 * <p>
	 * 
	 * @return departmentObj for DeptLunchRule
	 */
	public DepartmentContract getDepartmentObj();

	/**
	 * The work area associated with the DeptLunchRule
	 * 
	 * <p>
	 * If a work area is defined, only entries associated with a job in this work area will be subject to the automatic lunch deduction. 
	 * <p>
	 * 
	 * @return workArea for DeptLunchRule
	 */
    public Long getWorkArea();
    
    /**
	 * The principalId associated with the DeptLunchRule
	 * 
	 * <p>
	 * If a principal id is defined, only this specific employee will be subject to the automatic lunch deduction.
	 * <p>
	 * 
	 * @return principalId for DeptLunchRule
	 */
    public String getPrincipalId();

    /**
	 * The jobNumber associated with the DeptLunchRule
	 * 
	 * <p>
	 * If job # is defined, only this specific employee's job will be subject to the automatic lunch deduction.
	 * <p>
	 * 
	 * @return jobNumber for DeptLunchRule
	 */
    public Long getJobNumber();
	
    /**
	 * User which set up the lunch rule
	 * 
	 * <p>
	 * userPrincipalId of a DeptLunchRule
	 * <p>
	 * 
	 * @return userPrincipalId for DeptLunchRule
	 */
	public String getUserPrincipalId();
   
	/**
	 * The primary key of a DeptLunchRule entry saved in a database
	 * 
	 * <p>
	 * tkTimeCollectionRuleId of a DeptLunchRule
	 * <p>
	 * 
	 * @return tkTimeCollectionRuleId for DeptLunchRule
	 */
	public String getTkDeptLunchRuleId();

	/**
	 * The dept associated with the DeptLunchRule
	 * 
	 * <p>
	 * If a department is defined, only entries associated with a job in this department will be subject to the automatic lunch deduction. 
	 * <p>
	 * 
	 * @return dept for DeptLunchRule
	 */
	public String getDept();
	
	/**
	 * The amount of minutes to be deducted as a lunch
	 * 
	 * <p>
	 * deductionMins of a DeptLunchRule
	 * <p>
	 * 
	 * @return deductionMins for DeptLunchRule
	 */
	public BigDecimal getDeductionMins();

	/**
	 * The number of hours which must be met in order for the deduction to occur
	 * 
	 * <p>
	 * shiftHours of a DeptLunchRule
	 * <p>
	 * 
	 * @return shiftHours for DeptLunchRule
	 */
	public BigDecimal getShiftHours();

	/**
	 * The id of the WorkArea object associated with the DeptLunchRule
	 * 
	 * <p>
	 * tkWorkAreaId of a DeptLunchRule
	 * <p>
	 * 
	 * @return tkWorkAreaId for DeptLunchRule
	 */
	public String getTkWorkAreaId();
	
	/**
	 * The id of the Department object associated with the DeptLunchRule
	 * 
	 * <p>
	 * hrDeptId of a DeptLunchRule
	 * <p>
	 * 
	 * @return hrDeptId for DeptLunchRule
	 */
	public String getHrDeptId();
	
	/**
	 * The id of the Job object associated with the DeptLunchRule
	 * 
	 * <p>
	 * hrJobId of a DeptLunchRule
	 * <p>
	 * 
	 * @return hrJobId for DeptLunchRule
	 */
	public String getHrJobId();
	
	/**
	 * The history flag of the DeptLunchRule
	 * 
	 * <p>
	 * history flag of a DeptLunchRule
	 * <p>
	 * 
	 * @return Y if on, N if not
	 */
	public boolean isHistory();

}
