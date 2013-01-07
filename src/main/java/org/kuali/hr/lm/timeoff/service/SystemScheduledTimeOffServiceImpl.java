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
package org.kuali.hr.lm.timeoff.service;

import org.apache.log4j.Logger;
import org.kuali.hr.job.Job;
import org.kuali.hr.lm.timeoff.SystemScheduledTimeOff;
import org.kuali.hr.lm.timeoff.dao.SystemScheduledTimeOffDao;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TkConstants;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
			String leavePlan, Date startDate, Date endDate) {
		return getSystemScheduledTimeOffDao().getSystemScheduledTimeOffForPayPeriod(leavePlan, startDate, endDate);
	}

	@Override
	public SystemScheduledTimeOff getSystemScheduledTimeOffByDate(
			String leavePlan, Date startDate) {
		return getSystemScheduledTimeOffDao().getSystemScheduledTimeOffByDate(leavePlan, startDate);
	}	
	@Override
	public Assignment getAssignmentToApplyHolidays(TimesheetDocument timesheetDocument, java.sql.Date payEndDate) {
		Job primaryJob = TkServiceLocator.getJobService().getPrimaryJob(timesheetDocument.getPrincipalId(), payEndDate);
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
    public List<SystemScheduledTimeOff> getSystemScheduledTimeOffs(Date fromEffdt, Date toEffdt, String earnCode, Date fromAccruedDate, Date toAccruedDate, 
    															   Date fromSchTimeOffDate, Date toSchTimeOffDate, String active, String showHist) {
        return systemScheduledTimeOffDao.getSystemScheduledTimeOffs(fromEffdt, toEffdt, earnCode, fromAccruedDate, toAccruedDate, fromSchTimeOffDate, 
        															toSchTimeOffDate, active, showHist);
    }

}
