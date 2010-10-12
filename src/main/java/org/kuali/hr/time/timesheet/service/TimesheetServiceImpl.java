package org.kuali.hr.time.timesheet.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.paycalendar.PayCalendarDates;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kew.exception.WorkflowException;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kew.service.WorkflowDocument;

public class TimesheetServiceImpl implements TimesheetService {

	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(TimesheetServiceImpl.class);

	@Override
	public void routeTimesheet(String principalId, TimesheetDocument timesheetDocument) {
		WorkflowDocument wd = null;
		if (timesheetDocument != null) {
			try {
				wd = new WorkflowDocument(principalId, timesheetDocument.getDocumentHeader().getDocumentId());
				wd.routeDocument("route");
				String kewStatus = KEWServiceLocator.getWorkflowUtilityService().getDocumentStatus(timesheetDocument.getDocumentHeader().getDocumentId());
				if (!kewStatus.equals(timesheetDocument.getDocumentHeader().getDocumentStatus())) {
					timesheetDocument.getDocumentHeader().setDocumentStatus(kewStatus);
					TkServiceLocator.getTimesheetDocumentHeaderService().saveOrUpdate(timesheetDocument.getDocumentHeader());
				}
			} catch (WorkflowException e) {
				throw new RuntimeException("Exception during route", e);
			}
		}
	}

	@Override
	public TimesheetDocument openTimesheetDocument(String principalId, PayCalendarDates payCalendarDates) throws WorkflowException {
		TimesheetDocument timesheetDocument = null;

		// We need to merge the date and time together, so we can pass a correct java.util.Date, since we're storing the values
		// separately in the pay calendar table.
		//
		// Should move this code to a util class and investigate the performance.  Could also go right on the PayCalendarDates pojo.
		Calendar dCal = Calendar.getInstance();
		Calendar tCal = Calendar.getInstance();
		dCal.setTime(payCalendarDates.getBeginPeriodDate());
		tCal.setTime(payCalendarDates.getBeginPeriodTime());
		dCal.set(Calendar.HOUR_OF_DAY, tCal.get(Calendar.HOUR_OF_DAY));
		dCal.set(Calendar.MINUTE, tCal.get(Calendar.MINUTE));
		dCal.set(Calendar.SECOND, tCal.get(Calendar.SECOND));
		dCal.set(Calendar.MILLISECOND, tCal.get(Calendar.MILLISECOND));
		Date begin = dCal.getTime();		
			
		dCal.setTime(payCalendarDates.getEndPeriodDate());
		tCal.setTime(payCalendarDates.getEndPeriodTime());
		dCal.set(Calendar.HOUR_OF_DAY, tCal.get(Calendar.HOUR_OF_DAY));
		dCal.set(Calendar.MINUTE, tCal.get(Calendar.MINUTE));
		dCal.set(Calendar.SECOND, tCal.get(Calendar.SECOND));
		dCal.set(Calendar.MILLISECOND, tCal.get(Calendar.MILLISECOND));
		Date end = dCal.getTime();

		TimesheetDocumentHeader header = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(principalId, begin, end);

		if (header == null) {
			timesheetDocument = this.initiateWorkflowDocument(principalId, begin, end, TimesheetDocument.TIMESHEET_DOCUMENT_TYPE, TimesheetDocument.TIMESHEET_DOCUMENT_TITLE);
			this.loadTimesheetDocumentData(timesheetDocument, principalId, begin, end);
		} else {
			timesheetDocument = this.getTimesheetDocument(header.getDocumentId());
		}
		timesheetDocument.setPayCalendarEntry(payCalendarDates);
		timesheetDocument.setTimeSummary(TkServiceLocator.getTimeSummaryService().getTimeSummary(timesheetDocument));
		return timesheetDocument;
	}

	private TimesheetDocument initiateWorkflowDocument(String principalId, Date payBeginDate, Date payEndDate, String documentType, String title) throws WorkflowException {
		TimesheetDocument timesheetDocument = null;		
		WorkflowDocument workflowDocument = null;

		workflowDocument = new WorkflowDocument(principalId, documentType);
		workflowDocument.setTitle(title);

		String status = workflowDocument.getRouteHeader().getDocRouteStatus();
		TimesheetDocumentHeader documentHeader = new TimesheetDocumentHeader(workflowDocument.getRouteHeaderId(), principalId, payBeginDate, payEndDate, status);

		documentHeader.setDocumentNumber(workflowDocument.getRouteHeaderId().toString());
		documentHeader.setDocumentStatus("I");
		documentHeader.setDocumentDescription("org.kuali.hr.time.timesheet.TimesheetDocument");
		documentHeader.setExplanation(principalId);

		TkServiceLocator.getTimesheetDocumentHeaderService().saveOrUpdate(documentHeader);
		timesheetDocument = new TimesheetDocument(documentHeader);

		return timesheetDocument;
	}

	@Override
	public TimesheetDocument getTimesheetDocument(Long documentId) {
		TimesheetDocument timesheetDocument = null;
		TimesheetDocumentHeader tdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(documentId);
	
		if (tdh != null) {
			timesheetDocument = new TimesheetDocument(tdh);
			loadTimesheetDocumentData(timesheetDocument, tdh.getPrincipalId(), tdh.getPayBeginDate(), tdh.getPayEndDate());
		} else {
			throw new RuntimeException("Could not find TimesheetDocumentHeader for DocumentID: " + documentId);
		}
		return timesheetDocument;
	}
	
	private void loadTimesheetDocumentData(TimesheetDocument tdoc, String principalId, Date payPeriodBegin, Date payPeriodEnd) {
		List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignments(principalId, TKUtils.getTimelessDate(payPeriodBegin));
		List<Job> jobs = TkServiceLocator.getJobSerivce().getJobs(principalId, TKUtils.getTimelessDate(payPeriodBegin));
		List<TimeBlock> timeBlocks = TkServiceLocator.getTimeBlockService().getTimeBlocksByPeriod(principalId, payPeriodBegin, payPeriodEnd);
		
		tdoc.setAssignments(assignments);
		tdoc.setJobs(jobs);
		tdoc.setTimeBlocks(timeBlocks);
	}
	
	public boolean isSynchronousUser(){
		List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignments(TKContext.getUser().getPrincipalId(), TKUtils.getCurrentDate());
		boolean isSynchronousUser = false;
		for(Assignment assignment: assignments){
			isSynchronousUser &= assignment.isSynchronous();
		}
		return isSynchronousUser;
	}

}