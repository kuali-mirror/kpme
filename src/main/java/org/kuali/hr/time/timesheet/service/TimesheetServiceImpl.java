package org.kuali.hr.time.timesheet.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.paycalendar.PayCalendarDates;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kew.exception.WorkflowException;
import org.kuali.rice.kew.service.WorkflowDocument;
import org.kuali.rice.kns.service.KNSServiceLocator;

public class TimesheetServiceImpl implements TimesheetService {

	private static final Logger LOG = Logger.getLogger(TimesheetServiceImpl.class);

	@Override
	public void routeTimesheet(String principalId, TimesheetDocument timesheetDocument) {
		WorkflowDocument wd = null;
		if (timesheetDocument != null) {
			try {
				wd = new WorkflowDocument(principalId, timesheetDocument.getDocumentHeader().getDocumentId());
				wd.routeDocument("route");
			} catch (WorkflowException e) {
				LOG.error(e);
			}
		}
	}

	@Override
	public TimesheetDocument openTimesheetDocument(String principalId, PayCalendarDates payCalendarDates) throws WorkflowException {
		TimesheetDocumentHeader header = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(principalId, payCalendarDates.getEndPeriodDate());
		TimesheetDocument timesheetDocument = null;

		if (header == null) {
			List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignments(principalId, TKUtils.getTimelessDate(payCalendarDates.getBeginPeriodDate()));
			List<Job> jobs = TkServiceLocator.getJobSerivce().getJobs(principalId, TKUtils.getTimelessDate(payCalendarDates.getBeginPeriodDate()));
			if ((assignments != null && assignments.size() > 0) && (jobs != null && jobs.size() > 0) ) {
				timesheetDocument = this.initiateWorkflowDocument(principalId, payCalendarDates.getEndPeriodDate(), TimesheetDocument.TIMESHEET_DOCUMENT_TYPE, TimesheetDocument.TIMESHEET_DOCUMENT_TITLE);
				timesheetDocument.setAssignments(assignments);
				timesheetDocument.setJobs(jobs);
			} else {
				throw new RuntimeException("No assignments/jobs for this user.");
			}
		} else {
			timesheetDocument = this.getTimesheetDocument(header.getDocumentId());
		}

		return timesheetDocument;
	}

	private TimesheetDocument initiateWorkflowDocument(String principalId, Date payEndDate, String documentType, String title) throws WorkflowException {
		TimesheetDocument timesheetDocument = null;		
		WorkflowDocument workflowDocument = null;

		workflowDocument = new WorkflowDocument(principalId, documentType);
		workflowDocument.setTitle(title);

		
		String status = workflowDocument.getRouteHeader().getDocRouteStatus();
		TimesheetDocumentHeader documentHeader = new TimesheetDocumentHeader(workflowDocument.getRouteHeaderId(), principalId, payEndDate, status);

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
			List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignments(tdh.getPrincipalId(), TKUtils.getTimelessDate(tdh.getPayBeginDate()));
			List<Job> jobs = TkServiceLocator.getJobSerivce().getJobs(tdh.getPrincipalId(), TKUtils.getTimelessDate(tdh.getPayBeginDate()));
			timesheetDocument.setAssignments(assignments);
			timesheetDocument.setJobs(jobs);
		} else {
			throw new RuntimeException("Could not find TimesheetDocumentHeader for DocumentID: " + documentId);
		}
		return timesheetDocument;
	}

}