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
package org.kuali.kpme.tklm.api.time.rules.overtime.daily;

import java.math.BigDecimal;

import org.kuali.kpme.core.api.department.DepartmentContract;
import org.kuali.kpme.core.api.earncode.EarnCodeContract;
import org.kuali.kpme.core.api.earncode.group.EarnCodeGroupContract;
import org.kuali.kpme.core.api.location.LocationContract;
import org.kuali.kpme.core.api.paytype.PayTypeContract;
import org.kuali.kpme.core.api.task.TaskContract;
import org.kuali.kpme.core.api.workarea.WorkAreaContract;
import org.kuali.kpme.tklm.api.time.rules.TkRuleContract;

/**
 * <p>DailyOvertimeRuleContract interface</p>
 *
 */
public interface DailyOvertimeRuleContract extends TkRuleContract {
	
	/**
	 * The primary key of a DailyOvertimeRule entry saved in a database
	 * 
	 * <p>
	 * tkDailyOvertimeRuleId of a DailyOvertimeRule
	 * <p>
	 * 
	 * @return tkDailyOvertimeRuleId for DailyOvertimeRule
	 */
	public String getTkDailyOvertimeRuleId();
	
	/**
	 * The Location name associated with the DailyOvertimeRule
	 * 
	 * <p>
	 * If a location is defined, only entries associated with a job in this location will be subject to the defined rule. 
	 * <p>
	 * 
	 * @return location for DailyOvertimeRule
	 */
	public String getLocation();	

	/**
	 * The maximum gap of time in minutes that is allowed before daily overtime is considered invalid
	 * 
	 * <p>
	 * For example, a rule that states daily overtime is given for working over 8 hours with a max gap of 30 minutes 
	 * implies that employee must work a total of 8 hours in the day and have a gap of no more than 30 minutes 
	 * in the reporting of those hours to be eligible
	 * <p>
	 * 
	 * @return maxGap for DailyOvertimeRule
	 */
	public BigDecimal getMaxGap();

	/**
	 * The userPrincipalId associated with the DailyOvertimeRule
	 * 
	 * <p>
	 * userPrincipalId of a DailyOvertimeRule
	 * <p>
	 * 
	 * @return userPrincipalId for DailyOvertimeRule
	 */
	public String getUserPrincipalId();	

	/**
	 * The Department object associated with the DailyOvertimeRule
	 * 
	 * <p>
	 * If a department is defined, only entries associated with a job in this department will be subject to the defined rule.  
	 * <p>
	 * 
	 * @return departmentObj for DailyOvertimeRule
	 */
	public DepartmentContract getDepartmentObj();	

	/**
	 * The PayType name associated with the DailyOvertimeRule
	 * 
	 * <p>
	 * If a paytype is defined, only entries associated with a job of this pay type will be subject to the defined rule 
	 * <p>
	 * 
	 * @return paytype for DailyOvertimeRule
	 */
	public String getPaytype();

	/**
	 * The Department name associated with the DailyOvertimeRule
	 * 
	 * <p>
	 * If a department is defined, only entries associated with a job in this department will be subject to the defined rule.  
	 * <p>
	 * 
	 * @return dept for DailyOvertimeRule
	 */
	public String getDept();
	
	/**
	 * TODO: Make sure this comment is right
	 * The Task object associated with the DailyOvertimeRule
	 * 
	 * <p>
	 * If a task is defined, only entries associated with a job that has this task will be subject to the defined rule.  
	 * <p>
	 * 
	 * @return taskObj for DailyOvertimeRule
	 */
	public TaskContract getTaskObj();

	/**
	 * The WorkArea object associated with the DailyOvertimeRule
	 * 
	 * <p>
	 * If a work area is defined, only entries associated with a job that has this work area will be subject to the defined rule. 
	 * <p>
	 * 
	 * @return workAreaObj for DailyOvertimeRule
	 */
	public WorkAreaContract getWorkAreaObj();
	
	/**
	 * The WorkArea name(long) associated with the DailyOvertimeRule
	 * 
	 * <p>
	 * If a work area is defined, only entries associated with a job that has this work area will be subject to the defined rule. 
	 * <p>
	 * 
	 * @return workArea for DailyOvertimeRule
	 */
	public Long getWorkArea();

	/**
	 * The PayType object associated with the DailyOvertimeRule
	 * 
	 * <p>
	 * If a paytype is defined, only entries associated with a job of this pay type will be subject to the defined rule 
	 * <p>
	 * 
	 * @return payTypeObj for DailyOvertimeRule
	 */
	public PayTypeContract getPayTypeObj();	

	/**
	 * The earn group defined (using the Earn Group maint doc) which contains the list of earn codes 
	 * that are summed to the daily max hours to be converted to overtime
	 * 
	 * <p>
	 * fromEarnGroup of a DailyOvertimeRule
	 * <p>
	 * 
	 * @return fromEarnGroup for DailyOvertimeRule
	 */
	public String getFromEarnGroup();	

	/**
	 * The EarnCode name associated with the DailyOvertimeRule
	 * 
	 * <p>
	 * earnCode of a DailyOvertimeRule
	 * <p>
	 * 
	 * @return earnCode for DailyOvertimeRule
	 */
	public String getEarnCode();	

	/**
	 * The minimum hours associated with the DailyOvertimeRule
	 * 
	 * <p>
	 * minHours of a DailyOvertimeRule
	 * <p>
	 * 
	 * @return minHours for DailyOvertimeRule
	 */
	public BigDecimal getMinHours();	

	/**
	 * The EarnCodeGroup object associated with the DailyOvertimeRule
	 * 
	 * <p>
	 * fromEarnGroupObj of a DailyOvertimeRule
	 * <p>
	 * 
	 * @return fromEarnGroupObj for DailyOvertimeRule
	 */
	public EarnCodeGroupContract getFromEarnGroupObj();	

	/**
	 * The EarnCode object associated with the DailyOvertimeRule
	 * 
	 * <p>
	 * earnCodeObj of a DailyOvertimeRule
	 * <p>
	 * 
	 * @return earnCodeObj for DailyOvertimeRule
	 */
	public EarnCodeContract getEarnCodeObj();	

	/**
	 * The Location object associated with the DailyOvertimeRule
	 * 
	 * <p>
	 * If a location is defined, only entries associated with a job in this location will be subject to the defined rule. 
	 * <p>
	 * 
	 * @return location for DailyOvertimeRule
	 */
	public LocationContract getLocationObj();	

	/**
	 * The history flag of the DailyOvertimeRule
	 * 
	 * <p>
	 * history flag of a DailyOvertimeRule
	 * <p>
	 * 
	 * @return Y if on, N if not
	 */
	public boolean isHistory();	

	/**
	 * 	Indicates if this earn code may be used for overtime
	 * 
	 * <p>
	 * ovtEarnCode falg of a DailyOvertimeRule
	 * <p>
	 * 
	 * @return Y if used for overtime, N if not
	 */	
	public Boolean getOvtEarnCode();	

	/**
	 * The id of the WorkArea object associated wtih the DailyOvertimeRule
	 * 
	 * <p>
	 * tkWorkAreaId of a DailyOvertimeRule
	 * <p>
	 * 
	 * @return tkWorkAreaId for DailyOvertimeRule
	 */
	public String getTkWorkAreaId();	

	/**
	 * The id of the Department object associated wtih the DailyOvertimeRule
	 * 
	 * <p>
	 * hrDeptId of a DailyOvertimeRule
	 * <p>
	 * 
	 * @return hrDeptId for DailyOvertimeRule
	 */
	public String getHrDeptId();

	/**
	 * The id of the Location object associated wtih the DailyOvertimeRule
	 * 
	 * <p>
	 * hrLocationId of a DailyOvertimeRule
	 * <p>
	 * 
	 * @return hrLocationId for DailyOvertimeRule
	 */
	public String getHrLocationId();
	
}
