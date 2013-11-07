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
package org.kuali.kpme.core.api.job;

import java.math.BigDecimal;

import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;
import org.kuali.kpme.core.api.department.DepartmentContract;
import org.kuali.kpme.core.api.location.LocationContract;
import org.kuali.kpme.core.api.paygrade.PayGradeContract;
import org.kuali.kpme.core.api.paytype.PayTypeContract;
import org.kuali.kpme.core.api.position.PositionBaseContract;
import org.kuali.kpme.core.api.salarygroup.SalaryGroupContract;
import org.kuali.kpme.core.api.util.HrApiConstants;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.rice.kim.api.identity.Person;

/**
 * <p>JobContract interface.</p>
 *
 */
public interface JobContract extends HrBusinessObjectContract {
	
	public static final String CACHE_NAME = HrApiConstants.CacheNamespace.NAMESPACE_PREFIX + "Job";
	
	/**
	 * Indicates if Job is FLSA exempt or non-exempt. 
	 * 
	 * <p>
	 * flsaStatus of Job
	 * </p>
	 * 
	 * @return flsaStatus for Job
	 */
	public String getFlsaStatus();

	/**
	 * Rate used by the accrual service for calculating leave accrued by on FTE. 
	 * 
	 * <p>
	 * fte of Job
	 * </p>
	 * 
	 * @return fte for Job
	 */
	public BigDecimal getFte();
	
	/**
	 * The pay grade field of the PayGrad object associated with the Job
	 * 
	 * <p>
	 * payGrade of Job
	 * </p>
	 * 
	 * @return payGrade for Job
	 */
	public String getPayGrade();

	/**
	 * The standard hours for this job. This drives holiday proration and “ready to approve criteria” for the timesheet
	 * (only timesheets meeting standard hours can be approved). Hourly jobs can have 0 standard hours
	 * 
	 * <p>
	 * standardHours of Job
	 * </p>
	 * 
	 * @return standardHours for Job
	 */
	public BigDecimal getStandardHours();

	/**
	 * Identifier of the employee that holds the Job
	 * 
	 * <p>
	 * principalId of Job
	 * </p>
	 * 
	 * @return principalId for Job
	 */
	public String getPrincipalId();
	
	/**
	 * First name of the employee that holds the Job
	 * 
	 * <p>
	 * firstName of Job
	 * </p>
	 * 
	 * @return firstName for Job
	 */
	public String getFirstName();

	/**
	 * Last name of the employee that holds the Job
	 * 
	 * <p>
	 * lastName of Job
	 * </p>
	 * 
	 * @return lastName for Job
	 */
	public String getLastName();

	/**
	 * Name of the employee that holds the Job
	 * 
	 * <p>
	 * name of Job
	 * </p>
	 * 
	 * @return name for Job
	 */
	public String getName();
	
	/**
	 * CompositeName the employee that holds the Job
	 * 
	 * <p>
	 * principalName of Job
	 * </p>
	 * 
	 * @return principalName for Job
	 */
	public String getPrincipalName();
	
	/**
	 * Sequential number which uniquely identify the job occurrence for the employee. 
	 * 
	 * <p>
	 * jobNumber of Job
	 * </p>
	 * 
	 * @return jobNumber for Job
	 */
	public Long getJobNumber();
	
	/**
	 * History flag for Job lookups 
	 * 
	 * <p>
	 * history of Job
	 * </p>
	 * 
	 * @return true if want to show history, false if not
	 */
	public Boolean getHistory();
	
	/**
	 * The location the job is associated with. 
	 * 
	 * <p>
	 * location of Job
	 * </p>
	 * 
	 * @return location for Job
	 */
	public String getLocation();
	
	/**
	 * The pay type value of the PayType object associated with the Job
	 * 
	 * <p>
	 * payType of Job
	 * </p>
	 * 
	 * @return payType for Job
	 */
	public String getHrPayType() ;

	/**
	 * The Primary Key of a Job entry saved in a database
	 * 
	 * <p>
	 * hrJobId of a Job
	 * <p>
	 * 
	 * @return hrJobId for Job
	 */
	public String getHrJobId() ;

	/**
	 * The name of the Department associated with the Job
	 * 
	 * <p>
	 * dept of a Job
	 * <p>
	 * 
	 * @return dept for Job
	 */
	public String getDept();

	/**
	 * The name of the SalaryGroup associated with the Job
	 * 
	 * <p>
	 * hrSalGroup of a Job
	 * <p>
	 * 
	 * @return hrSalGroup for Job
	 */
	public String getHrSalGroup();

	/**
	 * The hourly rate for this job
	 * 
	 * <p>
	 * compRate of a Job
	 * <p>
	 * 
	 * @return compRate for Job
	 */
	public KualiDecimal getCompRate();

	/**
	 * The Department object associated with the Job
	 * 
	 * <p>
	 * deptObj of a Job
	 * <p>
	 * 
	 * @return deptObj for Job
	 */
	public DepartmentContract getDeptObj();

	/**
	 * The PayType object associated with the Job
	 * 
	 * <p>
	 * payTypeObj of a Job
	 * <p>
	 * 
	 * @return payTypeObj for Job
	 */
	public PayTypeContract getPayTypeObj();

	/**
	 * The employee that holds this job
	 * 
	 * <p>
	 * principal of a Job
	 * <p>
	 * 
	 * @return principal for Job
	 */
	public Person getPrincipal();
	
	/**
	 * Indicates if the Job is the primary job for the employee
	 * 
	 * <p>
	 * primaryIndicator of a Job
	 * <p>
	 * 
	 * @return true if is primary, false if not
	 */
	public Boolean getPrimaryIndicator();
	
	/**
	 * The Location object associated with the Job
	 * 
	 * <p>
	 * locationObj of a Job
	 * <p>
	 * 
	 * @return locationObj for Job
	 */
	public LocationContract getLocationObj();

	/**
	 * The PayGrade object associated with the Job
	 * 
	 * <p>
	 * payGradeObj of a Job
	 * <p>
	 * 
	 * @return payGradeObj for Job
	 */
	public PayGradeContract getPayGradeObj();

	/**
	 * The SalaryGroup object associated with the Job
	 * 
	 * <p>
	 * salaryGroupObj of a Job
	 * <p>
	 * 
	 * @return salaryGroupObj for Job
	 */
	public SalaryGroupContract getSalaryGroupObj();
	
	/**
	 * Position associated with the job. 
	 * 
	 * <p>
	 * positionNumber of a Job
	 * <p>
	 * 
	 * @return positionNumber for Job
	 */
	public String getPositionNumber();

	/**
	 * PositionBase Object associated with the job. 
	 * 
	 * <p>
	 * positionObj of a Job
	 * <p>
	 * 
	 * @return positionObj for Job
	 */
	public PositionBaseContract getPositionObj() ;

	/**
	 * Combination of multiple string fields to identify the job. 
	 * 
	 * <p>
	 * uniqueKey of a Job
	 * <p>
	 * 
	 * @return uniqueKey for Job
	 */
	public String getUniqueKey();
	
	/**
	 * Id of the job. 
	 * 
	 * <p>
	 * id of a Job
	 * <p>
	 * 
	 * @return id for Job
	 */
	public String getId();
	
	/**
	 * Indicates if the Job is eligible for leave benefits
	 * 
	 * <p>
	 * eligibleForLeave of a Job
	 * <p>
	 * 
	 * @return true if yes, false if not
	 */
	public boolean isEligibleForLeave();
	
}
