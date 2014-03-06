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
package org.kuali.kpme.tklm.leave.batch;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.leaveplan.LeavePlan;
import org.kuali.kpme.core.leaveplan.LeavePlanBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.tklm.common.BatchJobService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class CarryOverSchedulerJob extends QuartzJobBean {

	private int leavePlanPollingWindow;
	private static final Logger LOG = Logger.getLogger(CarryOverSchedulerJob.class);
	
	private static BatchJobService batchJobService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		LocalDate asOfDate = LocalDate.now();
		List<LeavePlan> leavePlans = HrServiceLocator.getLeavePlanService().getLeavePlansNeedsCarryOverScheduled(getLeavePlanPollingWindow(), asOfDate);
        try {
        	if(CollectionUtils.isNotEmpty(leavePlans)) {
        		// schedule batch job for all LeavePlans who fall in leave polling window.
        		for(LeavePlan leavePlan : leavePlans) {
        		    getBatchJobService().scheduleLeaveCarryOverJobs(leavePlan);
        		}
        	}
        } catch (SchedulerException se) {
        	LOG.warn("Exception thrown during job scheduling of Carry over for Leave", se);
//        	throw new JobExecutionException("Exception thrown during job scheduling of Carry over for Leave", se);
        }
	}
	
	public int getLeavePlanPollingWindow() {
		return leavePlanPollingWindow;
	}

	public void setLeavePlanPollingWindow(int leavePlanPollingWindow) {
        this.leavePlanPollingWindow = leavePlanPollingWindow;
	}

	public static BatchJobService getBatchJobService() {
		return CarryOverSchedulerJob.batchJobService;
	}

	public void setBatchJobService(BatchJobService batchJobService) {
		CarryOverSchedulerJob.batchJobService = batchJobService;
	}

}
