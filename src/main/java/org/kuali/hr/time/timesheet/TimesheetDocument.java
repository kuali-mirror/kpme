package org.kuali.hr.time.timesheet;

import org.kuali.hr.job.Job;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesummary.TimeSummary;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class TimesheetDocument  {

	public static final String TIMESHEET_DOCUMENT_TYPE = "TimesheetDocument";
	public static final String TIMESHEET_DOCUMENT_TITLE = "TimesheetDocument";

	private TimesheetDocumentHeader documentHeader;
	private List<Assignment> assignments = new LinkedList<Assignment>();
	private List<Job> jobs = new LinkedList<Job>();
	private List<TimeBlock> timeBlocks = new LinkedList<TimeBlock>();
	private CalendarEntries payCalendarEntry = null; // Was a Hidden NPE, now more exposed // new PayCalendarEntries();
	private TimeSummary timeSummary = new TimeSummary();
	private Map<Long, Job> jobNumberToJobMap = new HashMap<Long,Job>();

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
		jobNumberToJobMap.clear();
		if(jobs!=null){
			for(Job job : jobs){
				jobNumberToJobMap.put(job.getJobNumber(), job);
			}
		}
	}

	public List<TimeBlock> getTimeBlocks() {
		return timeBlocks;
	}

	public void setTimeBlocks(List<TimeBlock> timeBlocks) {
		this.timeBlocks = timeBlocks;
	}

	public CalendarEntries getPayCalendarEntry() {
		return payCalendarEntry;
	}

	public void setPayCalendarEntry(CalendarEntries payCalendarEntry) {
		this.payCalendarEntry = payCalendarEntry;
	}

	public void setTimeSummary(TimeSummary timeSummary) {
		this.timeSummary = timeSummary;
	}

	public TimeSummary getTimeSummary() {
		return timeSummary;
	}

	public String getPrincipalId(){
		return getDocumentHeader().getPrincipalId();
	}

	public Job getJob(Long jobNumber){
		return jobNumberToJobMap.get(jobNumber);
	}

	public java.sql.Date getAsOfDate(){
		return new java.sql.Date(getPayCalendarEntry().getBeginPeriodDateTime().getTime());
	}

	public String getDocumentId(){
		return this.getDocumentHeader().getDocumentId();
	}
}
