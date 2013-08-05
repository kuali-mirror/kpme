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
package org.kuali.kpme.core.api.principal;

import java.util.Date;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;
import org.kuali.kpme.core.api.calendar.CalendarContract;
import org.kuali.kpme.core.api.leaveplan.LeavePlanContract;
import org.kuali.rice.kim.api.identity.Person;

/**
 * <p>PrincipalHRAttributesContract interface</p>
 *
 */
public interface PrincipalHRAttributesContract extends HrBusinessObjectContract {

	/**
	 * The principalId the PrincipalHRAttributes is associated with
	 * 
	 * <p>
	 * principalId of a PrincipalHRAttributes
	 * </p>
	 * 
	 * @return principalId for PrincipalHRAttributes
	 */
	public String getPrincipalId();
	
	/**
	 * The principalId's names (first, middle and last names) the PrincipalHRAttributes is associated with
	 * 
	 * <p>
	 * It gets a Person object if person is null based on the principalId.
	 * person.getName() constructs the first, middle and last names.
	 * </p>
	 * 
	 * @return person.getName() if person is not null, otherwise "" 
	 */
	public String getName(); 
	
	/**
	 * Defines the calendar associated with the user for their timesheet
	 * 
	 * <p>
	 * payCalendar of a PrincipalHRAttributes
	 * </p>
	 * 
	 * @return payCalendar for PrincipalHRAttributes
	 */
	public String getPayCalendar();
	
	/**
	 * The name of the LeavePlan object the PrincipalHRAttributes is associated with
	 * 
	 * <p>
	 * The employee's Leave Plan that determines the accrual rules and leave codes.
	 * </p>
	 * 
	 * @return leavePlan of PrincipalHRAttributes
	 */
	public String getLeavePlan();
	
	/**
	 * The employee's start date for their leave eligible job
	 * 
	 * <p>
	 * serviceDate of a PrincipalHRAttributes
	 * </p>
	 * 
	 * @return serviceDate for PrincipalHRAttributes
	 */
	public Date getServiceDate();

	/**
	 * The serviceDate (LocalDate) the PrincipalHRAttributes is associated with
	 * 
	 * <p>
	 * serviceDate of a PrincipalHRAttributes
	 * </p>
	 * 
	 * @return serviceDate for PrincipalHRAttributes
	 */
	public LocalDate getServiceLocalDate();

	/**
	 * The fmlaEligible flag to dictate if FMLA leave codes are available for the employee to select on leave calendar
	 * 
	 * <p>
	 * fmlaEligible of a PrincipalHRAttributes
	 * </p>
	 * 
	 * @return true if fmla eligible, false if not
	 */
	public boolean isFmlaEligible();
	
	/**
	 * The workersCompEligible flag to dictate if FMLA leave codes are available for the employee to select on leave calendar
	 * 
	 * <p>
	 * workersCompEligible of a PrincipalHRAttributes
	 * </p>
	 * 
	 * @return true if worker's comp eligible, false if not
	 */
	public boolean isWorkersCompEligible();
	
	/**
	 * The time zone that the employee is located in
	 * 
	 * <p>
	 * timezone of PrincipalHRAttributes
	 * </p>
	 * 
	 * @return timezone for PrincipalHRAttributes
	 */
	public String getTimezone();
	
	/**
	 * The Calendar object the PrincipalHRAttributes is associated with
	 * 
	 * <p>
	 * calendar of a PrincipalHRAttributes
	 * </p>
	 * 
	 * @return calendar for PrincipalHRAttributes
	 */
	public CalendarContract getCalendar();
	
	/**
	 * The Person object the PrincipalHRAttributes is associated with
	 * 
	 * <p>
	 * person of a aPrincipalHRAttributes
	 * </p>
	 * 
	 * @return person for PrincipalHRAttributes
	 */
	public Person getPerson();

	/**
	 * Determines the accrual rules and leave codes  for the employee
	 * 
	 * <p>
	 * leavePlanObj of a PrincipalHRAttributes
	 * </p>
	 * 
	 * @return leavePlanObj for PrincipalHRAttributes
	 */
	public LeavePlanContract getLeavePlanObj();
	
	/**
	 * The name of the LeaveCalendar object the PrincipalHRAttributes is associated with
	 * 
	 * <p>
	 * leaveCalendar of a PrincipalHRAttributes
	 * </p>
	 * 
	 * @return leaveCalendar for PrincipalHRAttributes
	 */
	public String getLeaveCalendar();
	
	/**
	 * The primary key of a PrincipalHRAttributes entry saved in a database
	 * 
	 * <p>
	 * hrPrincipalAttributeId of a PrincipalHRAttributes
	 * <p>
	 * 
	 * @return hrPrincipalAttributeId for PrincipalHRAttributes
	 */
	public String getHrPrincipalAttributeId();

	/**
	 * The history flag for PrincipalHRAttributes lookups 
	 * 
	 * <p>
	 * history of PrincipalHRAttributes
	 * </p>
	 * 
	 * @return true if want to show history, false if not
	 */
	public Boolean getHistory();
	
}
