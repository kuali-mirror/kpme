package org.kuali.hr.time.timesheet.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.service.AssignmentService;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.workflow.TkDocumentHeader;
import org.kuali.hr.time.workflow.service.DocumentHeaderService;
import org.kuali.hr.time.workflow.service.DocumentService;
import org.kuali.rice.kew.exception.WorkflowException;
import org.kuali.rice.kew.service.WorkflowDocument;

public class TimesheetServiceImpl implements TimesheetService {

	private static final Logger LOG = Logger.getLogger(TimesheetServiceImpl.class);

	// set these IoC style in spring beans?  or we could just use the service locator...
	//
	private DocumentService tkDocumentService;
	private DocumentHeaderService tkDocumentHeaderService;
	private AssignmentService assignmentService;

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
	public TimesheetDocument openTimesheetDocument(String principalId, Date payEndDate) {
		TkDocumentHeader header = tkDocumentHeaderService.getDocumentHeader(principalId, payEndDate);
		TimesheetDocument timesheetDocument = null;

		if (header == null) {
			List<Assignment> assignments = assignmentService.getAssignments(principalId, TKUtils.getTimelessDate(payEndDate));
			if (assignments != null && assignments.size() > 0) {
				timesheetDocument = this.initiateWorkflowDocument(principalId, payEndDate, TimesheetDocument.TIMESHEET_DOCUMENT_TYPE, TimesheetDocument.TIMESHEET_DOCUMENT_TITLE);
			} else {
				// No assignment for principal
				// Need to bounce - throw exception, etc.
			}
		} else {
			timesheetDocument = new TimesheetDocument(header);
		}

		return timesheetDocument;
	}

	private TimesheetDocument initiateWorkflowDocument(String principalId, Date payEndDate, String documentType, String title) {
		TimesheetDocument timesheetDocument = null;
		WorkflowDocument document = tkDocumentService.createWorkflowDocument(principalId, documentType, title);
		try {
			String status = document.getRouteHeader().getDocRouteStatus();
			TkDocumentHeader documentHeader = new TkDocumentHeader(document.getRouteHeaderId(), principalId, payEndDate, status);

			documentHeader.setDocumentNumber(document.getRouteHeaderId().toString());
			documentHeader.setDocumentStatus("I");
			documentHeader.setDocumentDescription("org.kuali.hr.time.timesheet.TimesheetDocument");
			documentHeader.setExplanation(principalId);

			tkDocumentHeaderService.saveOrUpdate(documentHeader);
			timesheetDocument = new TimesheetDocument(documentHeader);
		} catch (WorkflowException e) {
			LOG.error(e);
		}

		return timesheetDocument;
	}

	public void setTkDocumentService(DocumentService documentService) {
		this.tkDocumentService = documentService;
	}

	public void setTkDocumentHeaderService(DocumentHeaderService documentHeaderService) {
		this.tkDocumentHeaderService = documentHeaderService;
	}

	public void setAssignmentService(AssignmentService assignmentService) {
		this.assignmentService = assignmentService;
	}

}