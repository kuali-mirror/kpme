/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.tklm.api.time.timesheet;

import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.document.calendar.CalendarDocumentContract;
import org.kuali.kpme.core.api.job.JobContract;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlockContract;
import org.kuali.kpme.tklm.api.time.timesummary.TimeSummaryContract;



/**
 * <p>TimesheetDocumentContract interface</p>
 *
 */
public interface TimesheetDocumentContract extends CalendarDocumentContract {

	/**
	 * The list of Job objects associated with the TimesheetDocument
	 * 
	 * <p>
	 * jobs of a TimesheetDocument
	 * <p>
	 * 
	 * @return jobs for TimesheetDocument
	 */
	public List<? extends JobContract> getJobs();

	/**
	 * The list of TimeBlock objects associated with the TimesheetDocument
	 * 
	 * <p>
	 * timeBlocks of a TimesheetDocument
	 * <p>
	 * 
	 * @return timeBlocks for TimesheetDocument
	 */
	public List<TimeBlock> getTimeBlocks();
	
	/**
	 * The TimeSummary object associated with the TimesheetDocument
	 * 
	 * <p>
	 * timeSummary of a TimesheetDocument
	 * <p>
	 * 
	 * @return timeSummary for TimesheetDocument
	 */
	public TimeSummaryContract getTimeSummary();

	/**
	 * The principal id of the document header associated with the TimesheetDocument
	 * 
	 * <p>
	 * getDocumentHeader().getPrincipalId() of a TimesheetDocument
	 * <p>
	 * 
	 * @return getDocumentHeader().getPrincipalId() for TimesheetDocument
	 */
	public String getPrincipalId();

	/**
	 * The Job object associated with the jobNumber
	 * 
	 * <p>
	 * job associated with the jobNumber
	 * <p>
	 * 
	 * @param jobNumber number to retrieve Job object from jobNumberToJobMap
	 * @return Job object associated with the jobNumber
	 */
	public JobContract getJob(Long jobNumber);

	/**
	 * The end period date of the calendar entry associated with the TimesheetDocument 
	 * 
	 * <p>
	 * getCalendarEntry().getEndPeriodFullDateTime().toLocalDate() of a TimesheetDocument
	 * <p>
	 * 
	 * @return getCalendarEntry().getEndPeriodFullDateTime().toLocalDate() for TimesheetDocument
	 */
    public LocalDate getDocEndDate();

    /**
	 * The document id of the document header associated with the TimesheetDocument
	 * 
	 * <p>
	 * this.getDocumentHeader().getDocumentId() of a TimesheetDocument
	 * <p>
	 * 
	 * @return this.getDocumentHeader().getDocumentId() for TimesheetDocument
	 */
	public String getDocumentId();

	/**
	 * TODO: Put a better comment
	 * The map of earn code name and a list of begin date of time blocks associated with the TimesheetDocument
	 * 
	 * <p>
	 * earnCodeMap of a TimesheetDocument
	 * <p>
	 * 
	 * @return earnCodeMap for TimesheetDocument
	 */
    public Map<String, List<LocalDate>> getEarnCodeMap();
	
    /**
	 * TODO: Put a comment
	 * The map of assignment descriptions associated with the TimesheetDocument
	 * 
	 * <p>
	 * <p>
	 * 
	 * @param clockOnlyAssignments
	 * @return assignmentDescriptions
	 */
    public Map<String, String> getAssignmentDescriptions(boolean clockOnlyAssignments, LocalDate date);

}
