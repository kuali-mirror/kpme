/**
 * Copyright 2004-2013 The Kuali Foundation
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
package org.kuali.hr.time.timesheet;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.kuali.hr.core.document.calendar.CalendarDocumentContract;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.calendar.CalendarEntry;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesummary.TimeSummary;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;


public class TimesheetDocument implements CalendarDocumentContract, Serializable {

	public static final String TIMESHEET_DOCUMENT_TYPE = "TimesheetDocument";

	private TimesheetDocumentHeader documentHeader;
	private List<Assignment> assignments = new LinkedList<Assignment>();
	private List<Job> jobs = new LinkedList<Job>();
	private List<TimeBlock> timeBlocks = new LinkedList<TimeBlock>();
	private CalendarEntry calendarEntry = null; // Was a Hidden NPE, now more exposed // new PayCalendarEntries();
	private TimeSummary timeSummary = new TimeSummary();
	private Map<Long, Job> jobNumberToJobMap = new HashMap<Long,Job>();

	public TimesheetDocument(TimesheetDocumentHeader documentHeader) {
		this.documentHeader = documentHeader;
	}

    @Override
	public TimesheetDocumentHeader getDocumentHeader() {
		return documentHeader;
	}

	public void setDocumentHeader(TimesheetDocumentHeader documentHeader) {
		this.documentHeader = documentHeader;
	}

    @Override
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

    @Override
	public CalendarEntry getCalendarEntry() {
		return calendarEntry;
	}

	public void setCalendarEntry(CalendarEntry calendarEntry) {
		this.calendarEntry = calendarEntry;
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

    @Override
	public java.sql.Date getAsOfDate(){
		return new java.sql.Date(getCalendarEntry().getBeginPeriodDateTime().getTime());
	}
    
    public java.sql.Date getDocEndDate(){
		return new java.sql.Date(getCalendarEntry().getEndPeriodDateTime().getTime());
	}

	public String getDocumentId(){
		return this.getDocumentHeader().getDocumentId();
	}
}
