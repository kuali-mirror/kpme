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
package org.kuali.hr.tklm.leave.timeoff.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.kuali.hr.core.assignment.Assignment;
import org.kuali.hr.core.job.Job;
import org.kuali.hr.core.service.HrServiceLocator;
import org.kuali.hr.tklm.common.TkConstants;
import org.kuali.hr.tklm.leave.timeoff.SystemScheduledTimeOff;
import org.kuali.hr.tklm.leave.timeoff.dao.SystemScheduledTimeOffDao;
import org.kuali.hr.tklm.time.timesheet.TimesheetDocument;

public class SystemScheduledTimeOffServiceImpl implements SystemScheduledTimeOffService {

	private static final Logger LOG = Logger.getLogger(SystemScheduledTimeOffServiceImpl.class);
	private SystemScheduledTimeOffDao systemScheduledTimeOffDao;

	public SystemScheduledTimeOffDao getSystemScheduledTimeOffDao() {
		return systemScheduledTimeOffDao;
	}

	public void setSystemScheduledTimeOffDao(
			SystemScheduledTimeOffDao systemScheduledTimeOffDao) {
		this.systemScheduledTimeOffDao = systemScheduledTimeOffDao;
	}

	@Override
	public SystemScheduledTimeOff getSystemScheduledTimeOff(String lmSystemScheduledTimeOffId) {
		return getSystemScheduledTimeOffDao().getSystemScheduledTimeOff(lmSystemScheduledTimeOffId);
	}

	@Override
	public List<SystemScheduledTimeOff> getSystemScheduledTimeOffForPayPeriod(
			String leavePlan, LocalDate startDate, LocalDate endDate) {
		return getSystemScheduledTimeOffDao().getSystemScheduledTimeOffForPayPeriod(leavePlan, startDate, endDate);
	}

	@Override
	public SystemScheduledTimeOff getSystemScheduledTimeOffByDate(
			String leavePlan, LocalDate startDate) {
		return getSystemScheduledTimeOffDao().getSystemScheduledTimeOffByDate(leavePlan, startDate);
	}	
	@Override
	public Assignment getAssignmentToApplyHolidays(TimesheetDocument timesheetDocument, LocalDate payEndDate) {
		Job primaryJob = HrServiceLocator.getJobService().getPrimaryJob(timesheetDocument.getPrincipalId(), payEndDate);
		for(Assignment assign : timesheetDocument.getAssignments()){
			if(assign.getJobNumber().equals(primaryJob.getJobNumber())){
				return assign;
			}
		}
		return null;
	}

    @Override
    public BigDecimal calculateSysSchTimeOffHours(Job job, BigDecimal sstoHours) {
        BigDecimal fte = job.getStandardHours().divide(new BigDecimal(40.0),TkConstants.BIG_DECIMAL_SCALE);
        return fte.multiply(sstoHours).setScale(TkConstants.BIG_DECIMAL_SCALE);
    }

    @Override
    public List<SystemScheduledTimeOff> getSystemScheduledTimeOffs(LocalDate fromEffdt, LocalDate toEffdt, String earnCode, LocalDate fromAccruedDate, LocalDate toAccruedDate, 
    		LocalDate fromSchTimeOffDate, LocalDate toSchTimeOffDate, String active, String showHist) {
        return systemScheduledTimeOffDao.getSystemScheduledTimeOffs(fromEffdt, toEffdt, earnCode, fromAccruedDate, toAccruedDate, fromSchTimeOffDate, 
        															toSchTimeOffDate, active, showHist);
    }
    
    @Override
    public List<SystemScheduledTimeOff> getSystemScheduledTimeOffsForLeavePlan(LocalDate fromAccruedDate, LocalDate toAccruedDate, String leavePlan) {
    	return systemScheduledTimeOffDao.getSystemScheduledTimeOffsForLeavePlan(fromAccruedDate, toAccruedDate, leavePlan);
    }

	@Override
	public String getSSTODescriptionForDate(String leavePlan,
			LocalDate localDate) {
		String description = "";
		SystemScheduledTimeOff ssto = systemScheduledTimeOffDao.getSystemScheduledTimeOffByDate(leavePlan, localDate);
		if(ssto != null)
			description = ssto.getDescr();
		return description;
	}

}
