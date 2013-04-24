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
package org.kuali.hr.core.leaveplan;

import java.sql.Time;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.core.HrBusinessObject;
import org.kuali.hr.core.KPMEConstants;

public class LeavePlan extends HrBusinessObject {
	
	public static final String CACHE_NAME = KPMEConstants.APPLICATION_NAMESPACE_CODE
			+ "/" + "LeavePlan";
	
	private static final long serialVersionUID = 1L;
	private String lmLeavePlanId;
	private String leavePlan;
	private String descr;
	private String calendarYearStart;
	private Boolean history;
	private String planningMonths;
	private String batchPriorYearCarryOverStartDate;
	private Time batchPriorYearCarryOverStartTime;

	public String getBatchPriorYearCarryOverStartDate() {
		return batchPriorYearCarryOverStartDate;
	}

	public void setBatchPriorYearCarryOverStartDate(
			String batchPriorYearCarryOverStartDate) {
		this.batchPriorYearCarryOverStartDate = batchPriorYearCarryOverStartDate;
	}
	
	public Time getBatchPriorYearCarryOverStartTime() {
		return batchPriorYearCarryOverStartTime;
	}

	public void setBatchPriorYearCarryOverStartTime(
			Time batchPriorYearCarryOverStartTime) {
		this.batchPriorYearCarryOverStartTime = batchPriorYearCarryOverStartTime;
	}

	public String getPlanningMonths() {
		return planningMonths;
	}

	public void setPlanningMonths(String planningMonths) {
		this.planningMonths = planningMonths;
	}

	public String getLmLeavePlanId() {
		return lmLeavePlanId;
	}

	public void setLmLeavePlanId(String lmLeavePlanId) {
		this.lmLeavePlanId = lmLeavePlanId;
	}

	public String getLeavePlan() {
		return leavePlan;
	}

	public void setLeavePlan(String leavePlan) {
		this.leavePlan = leavePlan;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getCalendarYearStart() {
		return calendarYearStart;
	}

	public void setCalendarYearStart(String calendarYearStart) {
		this.calendarYearStart = calendarYearStart;
	}

	public String getCalendarYearStartMonth() {
		if (StringUtils.isEmpty(getCalendarYearStart())) {
			return "01";
		}
		String[] date = getCalendarYearStart().split("/");
		return date[0];
	}

	public String getCalendarYearStartDayOfMonth() {
		if (StringUtils.isEmpty(getCalendarYearStart())) {
			return "01";
		}
		String[] date = getCalendarYearStart().split("/");
		return date[1];
	}


	public Boolean getHistory() {
		return history;
	}

	public void setHistory(Boolean history) {
		this.history = history;
	}

	@Override
	protected String getUniqueKey() {
		return leavePlan;
	}

	@Override
	public String getId() {
		return getLmLeavePlanId();
	}

	@Override
	public void setId(String id) {
		setLmLeavePlanId(id);
	}

}
