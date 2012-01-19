package org.kuali.hr.time.calendar.web;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.kns.util.DateUtils;
import org.kuali.rice.kns.util.GlobalVariables;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CalendarEntryAction extends TkAction {

	public ActionForward createCalendarEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CalendarEntryActionForm ceaf = (CalendarEntryActionForm) form;
		if (ceaf.getNoOfPeriods() == null || ceaf.getNoOfPeriods() < 1) {
			GlobalVariables.getMessageMap().putError("document.noOfPeriods",
					"periods.greater.than.one");
			return mapping.findForward("basic");
		} else if (ceaf.getHrPyCalendarEntryId() == null) {
			GlobalVariables.getMessageMap().putError(
					"document.hrPyCalendarEntryId",
					"error.calendar.not.available");
			return mapping.findForward("basic");
		}
		CalendarEntries calendarEntries = TkServiceLocator
				.getCalendarEntriesSerivce().getCalendarEntries(
						ceaf.getHrPyCalendarEntryId().toString());
		if (calendarEntries == null) {
			GlobalVariables.getMessageMap().putError(
					"document.hrPyCalendarEntryId",
					"error.calendar.not.available");
		} else {
			CalendarEntries nextCalendarEntries = TkServiceLocator
					.getCalendarEntriesSerivce()
					.getNextCalendarEntriesByCalendarId(
							calendarEntries.getHrCalendarId(), calendarEntries);
			if (nextCalendarEntries != null) {
				GlobalVariables.getMessageMap().putError(
						"document.hrPyCalendarEntryId",
						"error.next.calendar.available");
			} else {
				for (int i = 0; i < ceaf.getNoOfPeriods(); i++) {
					TkServiceLocator.getCalendarEntriesSerivce()
							.createNextCalendarEntry(calendarEntries);
					
					calendarEntries.setBeginPeriodDateTime(DateUtils.addDays(calendarEntries.getBeginPeriodDateTime(), 14));
					calendarEntries.setEndPeriodDateTime(DateUtils.addDays(calendarEntries.getEndPeriodDateTime(), 14));
					calendarEntries.setBatchInitiateDate(new java.sql.Date(DateUtils.addDays(calendarEntries.getBatchInitiateDate(), 14).getTime()));
					calendarEntries.setBatchEndPayPeriodDate(new java.sql.Date(DateUtils.addDays(calendarEntries.getBatchEndPayPeriodDate(), 14).getTime()));
					calendarEntries.setBatchEmployeeApprovalDate(new java.sql.Date(DateUtils.addDays(calendarEntries.getBatchEmployeeApprovalDate(), 14).getTime()));
					calendarEntries.setBatchSupervisorApprovalDate(new java.sql.Date(DateUtils.addDays(calendarEntries.getBatchSupervisorApprovalDate(), 14).getTime()));
				}
				ceaf.setMessage("Calendar entry sucessfully created.");
			}
		}
		return mapping.findForward("basic");
	}
	
}
