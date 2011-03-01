package org.kuali.hr.time.timesheet.service;

import org.apache.log4j.Logger;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.holidaycalendar.HolidayCalendar;
import org.kuali.hr.time.holidaycalendar.HolidayCalendarDateEntry;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.hr.time.principal.calendar.PrincipalCalendar;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kew.exception.WorkflowException;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kew.service.WorkflowDocument;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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
				String kewStatus = KEWServiceLocator.getWorkflowUtilityService().getDocumentStatus(Long.parseLong(timesheetDocument.getDocumentHeader().getDocumentId()));
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
	public TimesheetDocument openTimesheetDocument(String principalId, PayCalendarEntries payCalendarDates) throws WorkflowException {
		TimesheetDocument timesheetDocument = null;

		Date begin = payCalendarDates.getBeginPeriodDateTime();		
		Date end = payCalendarDates.getEndPeriodDateTime(); 

		TimesheetDocumentHeader header = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(principalId, begin, end);

		if (header == null) {
			timesheetDocument = this.initiateWorkflowDocument(principalId, begin, end, TimesheetDocument.TIMESHEET_DOCUMENT_TYPE, TimesheetDocument.TIMESHEET_DOCUMENT_TITLE);
			this.loadTimesheetDocumentData(timesheetDocument, principalId, begin, end);
			this.loadHolidaysOnTimesheet(timesheetDocument, principalId, begin, end);
		} else {
			timesheetDocument = this.getTimesheetDocument(header.getDocumentId());
		}
		timesheetDocument.setPayCalendarEntry(payCalendarDates);
		timesheetDocument.setTimeSummary(TkServiceLocator.getTimeSummaryService().getTimeSummary(timesheetDocument));
		return timesheetDocument;
	}
	
	public void loadHolidaysOnTimesheet(TimesheetDocument timesheetDocument, String principalId, Date beginDate, Date endDate){
		PrincipalCalendar principalCalendar = TkServiceLocator.getPrincipalCalendarService().getPrincipalCalendar(principalId, new java.sql.Date(beginDate.getTime()));
		HolidayCalendar holidayCalendar = TkServiceLocator.getHolidayCalendarService().getHolidayCalendarByGroup(principalCalendar.getHolidayCalendarGroup());
		if (holidayCalendar != null) {
			List<HolidayCalendarDateEntry> lstHolidays = TkServiceLocator.getHolidayCalendarService().getHolidayCalendarDateEntriesForPayPeriod(holidayCalendar.getHolidayCalendarId(), 
															beginDate, endDate);
			for(HolidayCalendarDateEntry holiday : lstHolidays){
				Assignment holidayAssign = TkServiceLocator.getHolidayCalendarService().getAssignmentToApplyHolidays(timesheetDocument, TKUtils.getTimelessDate(endDate));
				BigDecimal holidayCalcHours = TkServiceLocator.getHolidayCalendarService().calculateHolidayHours(holidayAssign.getJob(), holiday.getHolidayHours());
				TimeBlock timeBlock = TkServiceLocator.getTimeBlockService().createTimeBlock(timesheetDocument, new Timestamp(holiday.getHolidayDate().getTime()), 
								new Timestamp(holiday.getHolidayDate().getTime()), holidayAssign, TkConstants.HOLIDAY_EARN_CODE, holidayCalcHours, false);
				timesheetDocument.getTimeBlocks().add(timeBlock);
			}
			
			//If holidays are loaded will need to save them to the database
			if(!lstHolidays.isEmpty()){
				TkServiceLocator.getTimeBlockService().saveTimeBlocks(new LinkedList<TimeBlock>(), timesheetDocument.getTimeBlocks());
			}
		}
	
	}

	private TimesheetDocument initiateWorkflowDocument(String principalId, Date payBeginDate, Date payEndDate, String documentType, String title) throws WorkflowException {
		TimesheetDocument timesheetDocument = null;		
		WorkflowDocument workflowDocument = null;

		workflowDocument = new WorkflowDocument(principalId, documentType);
		workflowDocument.setTitle(title);

		String status = workflowDocument.getRouteHeader().getDocRouteStatus();
		TimesheetDocumentHeader documentHeader = new TimesheetDocumentHeader(workflowDocument.getRouteHeaderId().toString(), principalId, payBeginDate, payEndDate, status);

		documentHeader.setDocumentId(workflowDocument.getRouteHeaderId().toString());
		documentHeader.setDocumentStatus("I");

		TkServiceLocator.getTimesheetDocumentHeaderService().saveOrUpdate(documentHeader);
		timesheetDocument = new TimesheetDocument(documentHeader);

		return timesheetDocument;
	}
	
	public List<TimeBlock> getPrevDocumentTimeBlocks(String principalId, Date payBeginDate){
		TimesheetDocumentHeader prevTdh = TkServiceLocator.getTimesheetDocumentHeaderService().getPreviousDocumentHeader(principalId, payBeginDate);
		if(prevTdh == null){
			return new ArrayList<TimeBlock>();
		}
		return TkServiceLocator.getTimeBlockService().getTimeBlocks(Long.parseLong(prevTdh.getDocumentId()));
	}

	@Override
	public TimesheetDocument getTimesheetDocument(String documentId) {
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
		List<TimeBlock> timeBlocks = TkServiceLocator.getTimeBlockService().getTimeBlocks(Long.parseLong(tdoc.getDocumentHeader().getDocumentId()));
		
		tdoc.setAssignments(assignments);
		tdoc.setJobs(jobs);
		tdoc.setTimeBlocks(timeBlocks);
	}
	
	public boolean isSynchronousUser(){
		List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignments(TKContext.getUser().getPrincipalId(), TKUtils.getCurrentDate());
		boolean isSynchronousUser = true;
		for(Assignment assignment: assignments){
			isSynchronousUser &= assignment.isSynchronous();
		}
		return isSynchronousUser;
	}

}