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
package org.kuali.kpme.core.bo.principal;

import java.util.Date;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.KPMEConstants;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.calendar.Calendar;
import org.kuali.kpme.core.bo.leaveplan.LeavePlan;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

public class PrincipalHRAttributes extends HrBusinessObject {

	private static final long serialVersionUID = 6843318899816055301L;

	public static final String CACHE_NAME = KPMEConstants.APPLICATION_NAMESPACE_CODE + "/" + "PrincipalHRAttributes";

	private String hrPrincipalAttributeId;
	private String principalId;
	private String leaveCalendar;
	private String payCalendar;
	private String leavePlan;
	private Date serviceDate;
	private boolean fmlaEligible;
	private boolean workersCompEligible;
	private String timezone;
	private Boolean history;
	// KPME-1268 Kagata added recordTime and recordLeave variables
	// KPME-1676 
//	private String recordTime;
//	private String recordLeave;
	
	private transient Calendar calendar;
	private transient Calendar leaveCalObj;
	private transient Person person;
	private transient LeavePlan leavePlanObj;


	public String getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
		person = KimApiServiceLocator.getPersonService().getPerson(this.principalId);
	}

	public String getName() {
		 if (person == null) {
	            person = KimApiServiceLocator.getPersonService().getPerson(this.principalId);
	    }
	    return (person != null) ? person.getName() : "";
	}

	public String getPayCalendar() {
		return payCalendar;
	}

	public void setPayCalendar(String payCalendar) {
		this.payCalendar = payCalendar;
	}

	public String getLeavePlan() {
		return leavePlan;
	}

	public void setLeavePlan(String leavePlan) {
		this.leavePlan = leavePlan;
	}

	public Date getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(Date serviceDate) {
		this.serviceDate = serviceDate;
	}
	
	public LocalDate getServiceLocalDate() {
		return LocalDate.fromDateFields(serviceDate);
	}
	
	public void setServiceLocalDate(LocalDate serviceLocalDate) {
		this.serviceDate = serviceLocalDate != null ? serviceLocalDate.toDate() : null;
	}

	public boolean isFmlaEligible() {
		return fmlaEligible;
	}

	public void setFmlaEligible(boolean fmlaEligible) {
		this.fmlaEligible = fmlaEligible;
	}

	public boolean isWorkersCompEligible() {
		return workersCompEligible;
	}

	public void setWorkersCompEligible(boolean workersCompEligible) {
		this.workersCompEligible = workersCompEligible;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public Calendar getCalendar() {
		return calendar;
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public LeavePlan getLeavePlanObj() {
		return leavePlanObj;
	}

	public void setLeavePlanObj(LeavePlan leavePlanObj) {
		this.leavePlanObj = leavePlanObj;
	}

	@Override
	protected String getUniqueKey() {
		return principalId + "_" + payCalendar == null ? "" : payCalendar + "_"
				+ leaveCalendar == null ? "" : leaveCalendar;
	}

	public String getLeaveCalendar() {
		return leaveCalendar;
	}

	public void setLeaveCalendar(String leaveCalendar) {
		this.leaveCalendar = leaveCalendar;
	}

	@Override
	public String getId() {
		return this.getHrPrincipalAttributeId();
	}
	@Override
	public void setId(String id) {
		setHrPrincipalAttributeId(id);
	}

	public Calendar getLeaveCalObj() {
		return leaveCalObj;
	}

	public void setLeaveCalObj(Calendar leaveCalObj) {
		this.leaveCalObj = leaveCalObj;
	}

	public String getHrPrincipalAttributeId() {
		return hrPrincipalAttributeId;
	}

	public void setHrPrincipalAttributeId(String hrPrincipalAttributeId) {
		this.hrPrincipalAttributeId = hrPrincipalAttributeId;
	}

	public Boolean getHistory() {
		return history;
	}

	public void setHistory(Boolean history) {
		this.history = history;
	}
}
