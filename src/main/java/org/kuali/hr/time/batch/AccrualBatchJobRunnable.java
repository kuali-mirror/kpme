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

import org.joda.time.DateTime;
import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;

public class AccrualBatchJobRunnable extends BatchJobEntryRunnable {

    public AccrualBatchJobRunnable(BatchJobEntry entry) {
        super(entry);
    }

	@Override
	public void doWork() throws Exception {
		BatchJobEntry accrualBatchJobEntry = TkServiceLocator.getBatchJobEntryService().getBatchJobEntry(getTkBatchJobEntryId());
		CalendarEntries calendarEntries = TkServiceLocator.getCalendarEntriesService().getCalendarEntries(accrualBatchJobEntry.getHrPyCalendarEntryId());
		DateTime calendarStartDate = new DateTime(calendarEntries.getBeginPeriodDate().getTime());
		
		DateTime startDate = new DateTime();
		if (calendarStartDate.isAfter(startDate)) {
			String principalId = accrualBatchJobEntry.getPrincipalId();
			PrincipalHRAttributes principalHRAttributes = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, TKUtils.getCurrentDate());
			if (principalHRAttributes != null) {
				LeavePlan leavePlan = TkServiceLocator.getLeavePlanService().getLeavePlan(principalHRAttributes.getLeavePlan(), principalHRAttributes.getEffectiveDate());
				if (leavePlan != null) {
					DateTime endDate = new DateTime(startDate).plusMonths(Integer.parseInt(leavePlan.getPlanningMonths()));
					TkServiceLocator.getAccrualService().runAccrual(principalId, new java.sql.Date(startDate.toDate().getTime()), new java.sql.Date(endDate.toDate().getTime()), true, TkConstants.BATCH_JOB_USER_PRINCIPAL_ID);
				}
			}
		}
	}
	
}