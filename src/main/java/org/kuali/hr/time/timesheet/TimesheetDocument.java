package org.kuali.hr.time.timesheet;

import java.util.LinkedList;
import java.util.List;

import org.kuali.hr.job.Job;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.paycalendar.PayCalendarDates;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesummary.TimeSummary;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;


public class TimesheetDocument  {

	public static final String TIMESHEET_DOCUMENT_TYPE = "TimesheetDocument";
	public static final String TIMESHEET_DOCUMENT_TITLE = "TimesheetDocument";
	
	private TimesheetDocumentHeader documentHeader;
	private List<Assignment> assignments = new LinkedList<Assignment>();
	private List<Job> jobs = new LinkedList<Job>();
	private List<TimeBlock> timeBlocks = new LinkedList<TimeBlock>();
	private PayCalendarDates payCalendarEntry = new PayCalendarDates();
	private TimeSummary timeSummary = new TimeSummary();
	
	public TimesheetDocument(TimesheetDocumentHeader documentHeader) {
		this.documentHeader = documentHeader;
	}

	public TimesheetDocumentHeader getDocumentHeader() {
		return documentHeader;
	}

	public void setDocumentHeader(TimesheetDocumentHeader documentHeader) {
		this.documentHeader = documentHeader;
	}

	public List<Assignment> getAssignments() {
		return assignments;
	}

	public void setAssignments(List<Assignment> assignments) {
		this.assignments = assignments;
	}

	public List<Job> getJobs() {
		return jobs;
	}

	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}

	public List<TimeBlock> getTimeBlocks() {
		return timeBlocks;
	}

	public void setTimeBlocks(List<TimeBlock> timeBlocks) {
		this.timeBlocks = timeBlocks;
	}

	public PayCalendarDates getPayCalendarEntry() {
		return payCalendarEntry;
	}

	public void setPayCalendarEntry(PayCalendarDates payCalendarEntry) {
		this.payCalendarEntry = payCalendarEntry;
	}

	public void setTimeSummary(TimeSummary timeSummary) {
		this.timeSummary = timeSummary;
	}

	public TimeSummary getTimeSummary() {
		return timeSummary;
	}
}
