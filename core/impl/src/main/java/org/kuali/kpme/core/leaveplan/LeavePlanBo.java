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
package org.kuali.kpme.core.leaveplan;

import java.sql.Time;
import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalTime;
import org.kuali.kpme.core.api.department.Department;
import org.kuali.kpme.core.api.leaveplan.LeavePlan;
import org.kuali.kpme.core.api.leaveplan.LeavePlanContract;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.util.HrConstants;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class LeavePlanBo extends HrBusinessObject implements LeavePlanContract {
	
	private static final String LEAVE_PLAN = "leavePlan";
	
	public static final String CACHE_NAME = HrConstants.CacheNamespace.NAMESPACE_PREFIX + "LeavePlan";
	//KPME-2273/1965 Primary Business Keys List.
	public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
	            .add(LEAVE_PLAN)
	            .build();

	private static final long serialVersionUID = 1L;
	private String lmLeavePlanId;
	private String leavePlan;
	private String descr;
	private String calendarYearStart;
	private String planningMonths;
	private String batchPriorYearCarryOverStartDate;
	private Time batchPriorYearCarryOverStartTime;
	
	@Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
    	return  new ImmutableMap.Builder<String, Object>()
			.put(LEAVE_PLAN, this.getLeavePlan())
			.build();
	}
	

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

    public LocalTime getBatchPriorYearCarryOverStartLocalTime() {
        return new LocalTime(batchPriorYearCarryOverStartTime);
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

    public static LeavePlanBo from(LeavePlan im) {
        LeavePlanBo lp = new LeavePlanBo();

        lp.setLmLeavePlanId(im.getLmLeavePlanId());
        lp.setLeavePlan(im.getLeavePlan());
        lp.setDescr(im.getDescr());
        lp.setCalendarYearStart(im.getCalendarYearStart());
        lp.setPlanningMonths(im.getPlanningMonths());
        lp.setBatchPriorYearCarryOverStartDate(im.getBatchPriorYearCarryOverStartDate());
        lp.setBatchPriorYearCarryOverStartTime(im.getBatchPriorYearCarryOverStartLocalTime() == null ? null : new Time(im.getBatchPriorYearCarryOverStartLocalTime().getMillisOfDay()));

        lp.setEffectiveDate(im.getEffectiveLocalDate() == null ? null : im.getEffectiveLocalDate().toDate());
        lp.setActive(im.isActive());
        if (im.getCreateTime() != null) {
            lp.setTimestamp(new Timestamp(im.getCreateTime().getMillis()));
        }
        lp.setUserPrincipalId(im.getUserPrincipalId());
        lp.setVersionNumber(im.getVersionNumber());
        lp.setObjectId(im.getObjectId());

        return lp;
    }

    public static LeavePlan to(LeavePlanBo bo) {
        if (bo == null) {
            return null;
        }

        return LeavePlan.Builder.create(bo).build();
    }
}
