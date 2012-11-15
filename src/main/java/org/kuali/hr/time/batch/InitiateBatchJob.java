/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.time.batch;

import java.sql.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.hr.job.Job;
import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;


public class InitiateBatchJob extends BatchJob {
	private Logger LOG = Logger.getLogger(InitiateBatchJob.class);
    private CalendarEntries calendarEntry;

    public InitiateBatchJob(CalendarEntries calendarEntry) {
        this.setBatchJobName(TkConstants.BATCH_JOB_NAMES.INITIATE);
        this.setHrPyCalendarEntryId(calendarEntry.getHrCalendarEntriesId());
        this.calendarEntry = calendarEntry;
    }

	@Override
	public void doWork() {
		Date asOfDate = TKUtils.getCurrentDate();
		Calendar calendar = TkServiceLocator.getCalendarService().getCalendar(calendarEntry.getHrCalendarId());
		String calendarTypes = calendar.getCalendarTypes();
		java.util.Date beginDate = calendarEntry.getBeginPeriodDateTime();
		java.util.Date endDate = calendarEntry.getEndPeriodDateTime();
		List<Assignment> assignments = TkServiceLocator.getAssignmentService().getActiveAssignments(asOfDate);
		
		for (Assignment assignment : assignments) {
			Job job = assignment.getJob();
			String principalId = assignment.getPrincipalId();
			if (StringUtils.equals(calendarTypes, "Pay")) {
				if (StringUtils.equalsIgnoreCase(job.getFlsaStatus(), TkConstants.FLSA_STATUS_EXEMPT)) {
					TimesheetDocumentHeader timesheetDocumentHeader = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(principalId, beginDate, endDate);
					if (timesheetDocumentHeader == null || StringUtils.equals(timesheetDocumentHeader.getDocumentStatus(), TkConstants.ROUTE_STATUS.CANCEL)) {
						populateBatchJobEntry(assignment);
					}
				}	
			} else if (StringUtils.equals(calendarTypes, "Leave")) {
				if (job.isEligibleForLeave() && StringUtils.equalsIgnoreCase(job.getFlsaStatus(), TkConstants.FLSA_STATUS_NON_EXEMPT)) {
					LeaveCalendarDocumentHeader leaveCalendarDocumentHeader = TkServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(principalId, beginDate, endDate);
					if (leaveCalendarDocumentHeader == null || StringUtils.equals(leaveCalendarDocumentHeader.getDocumentStatus(), TkConstants.ROUTE_STATUS.CANCEL)) {
						populateBatchJobEntry(assignment);
					}
				}
			}
		}
	}


	@Override
	protected void populateBatchJobEntry(Object o) {
		Assignment assign = (Assignment) o;
		String ip = this.getNextIpAddressInCluster();
		if (StringUtils.isNotBlank(ip)) {
            BatchJobEntry entry = createBatchJobEntry(getBatchJobName(), ip, assign.getPrincipalId(), null, null);
            TkServiceLocator.getBatchJobEntryService().saveBatchJobEntry(entry);
		} else {
			LOG.info("No ip found in cluster to assign batch jobs");
		}
	}

}
