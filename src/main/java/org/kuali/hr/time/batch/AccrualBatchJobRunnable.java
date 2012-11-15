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