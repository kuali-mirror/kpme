/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.time.batch.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.exec.util.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.hr.core.document.CalendarDocumentHeaderContract;
import org.kuali.hr.job.Job;
import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;
import org.kuali.hr.lm.workflow.service.LeaveCalendarDocumentHeaderService;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.service.AssignmentService;
import org.kuali.hr.time.batch.BatchJobUtil;
import org.kuali.hr.time.batch.EmployeeApprovalJob;
import org.kuali.hr.time.batch.EndPayPeriodJob;
import org.kuali.hr.time.batch.EndReportingPeriodJob;
import org.kuali.hr.time.batch.InitiateJob;
import org.kuali.hr.time.batch.MissedPunchApprovalJob;
import org.kuali.hr.time.batch.SupervisorApprovalJob;
import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.calendar.service.CalendarService;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.clocklog.service.ClockLogService;
import org.kuali.hr.time.missedpunch.MissedPunchDocument;
import org.kuali.hr.time.missedpunch.service.MissedPunchService;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.principal.service.PrincipalHRAttributesService;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.kuali.hr.time.workflow.service.TimesheetDocumentHeaderService;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;

public class BatchJobServiceImpl implements BatchJobService {
	
	private static final Logger LOG = Logger.getLogger(BatchJobServiceImpl.class);
	
	private Scheduler scheduler;
	
	private AssignmentService assignmentService;
	private CalendarService calendarService;
	private ClockLogService clockLogService;
	private LeaveCalendarDocumentHeaderService leaveCalendarDocumentHeaderService;
	private MissedPunchService missedPunchService;
	private PrincipalHRAttributesService principalHRAttributesService;
	private TimesheetDocumentHeaderService timesheetDocumentHeaderService;
	
	@Override
	public void scheduleInitiateJobs(CalendarEntries calendarEntry) throws SchedulerException {
		scheduleInitiateJobs(calendarEntry, calendarEntry.getBatchInitiateDateTime());
	}

    @Override
    public void scheduleInitiateJobs(CalendarEntries calendarEntry, Date scheduleDate) throws SchedulerException {
		Calendar calendar = getCalendarService().getCalendar(calendarEntry.getHrCalendarId());
		String calendarTypes = calendar.getCalendarTypes();
		String calendarName = calendar.getCalendarName();
		java.util.Date beginDate = calendarEntry.getBeginPeriodDateTime();
		java.util.Date endDate = calendarEntry.getEndPeriodDateTime();
		
		if (StringUtils.equals(calendarTypes, "Pay")) {
			List<PrincipalHRAttributes> principalHRAttributes = getPrincipalHRAttributesService().getActiveEmployeesForPayCalendar(calendarName, scheduleDate);
			
			for (PrincipalHRAttributes principalHRAttribute : principalHRAttributes) {
				String principalId = principalHRAttribute.getPrincipalId();
				List<Assignment> assignments = getAssignmentService().getAssignmentsByCalEntryForTimeCalendar(principalId, calendarEntry);
				
				for (Assignment assignment : assignments) {
					Job job = assignment.getJob();
					
					if (StringUtils.equalsIgnoreCase(job.getFlsaStatus(), TkConstants.FLSA_STATUS_EXEMPT)) {
						TimesheetDocumentHeader timesheetDocumentHeader = getTimesheetDocumentHeaderService().getDocumentHeader(principalId, beginDate, endDate);
						if (timesheetDocumentHeader == null || StringUtils.equals(timesheetDocumentHeader.getDocumentStatus(), TkConstants.ROUTE_STATUS.CANCEL)) {
							scheduleInitiateJob(calendarEntry, scheduleDate, assignment.getPrincipalId());
						}
					}
				}
			}
		} else if (StringUtils.equals(calendarTypes, "Leave")) {
			List<PrincipalHRAttributes> principalHRAttributes = getPrincipalHRAttributesService().getActiveEmployeesForLeaveCalendar(calendarName, scheduleDate);
			
			for (PrincipalHRAttributes principalHRAttribute : principalHRAttributes) {
				String principalId = principalHRAttribute.getPrincipalId();
				List<Assignment> assignments = getAssignmentService().getAssignmentsByCalEntryForLeaveCalendar(principalId, calendarEntry);
				
				for (Assignment assignment : assignments) {
					Job job = assignment.getJob();
					
					if (job.isEligibleForLeave() && StringUtils.equalsIgnoreCase(job.getFlsaStatus(), TkConstants.FLSA_STATUS_NON_EXEMPT)) {
						LeaveCalendarDocumentHeader leaveCalendarDocumentHeader = getLeaveCalendarDocumentHeaderService().getDocumentHeader(principalId, beginDate, endDate);
						if (leaveCalendarDocumentHeader == null || StringUtils.equals(leaveCalendarDocumentHeader.getDocumentStatus(), TkConstants.ROUTE_STATUS.CANCEL)) {
							scheduleInitiateJob(calendarEntry, scheduleDate, assignment.getPrincipalId());
						}
					}
				}
			}
		}
    }
    
	private void scheduleInitiateJob(CalendarEntries calendarEntry, Date scheduleDate, String principalId) throws SchedulerException {
        Map<String, String> jobGroupDataMap = new HashMap<String, String>();
        jobGroupDataMap.put("hrCalendarEntriesId", calendarEntry.getHrCalendarEntriesId());
		
		Map<String, String> jobDataMap = new HashMap<String, String>();
        jobDataMap.put("principalId", principalId);
        
		scheduleJob(InitiateJob.class, scheduleDate, jobGroupDataMap, jobDataMap);
	}
	
	@Override
	public void scheduleEndReportingPeriodJobs(CalendarEntries calendarEntry) throws SchedulerException {
		scheduleEndReportingPeriodJobs(calendarEntry, calendarEntry.getEndPeriodDateTime());
	}
	
	@Override
	public void scheduleEndReportingPeriodJobs(CalendarEntries calendarEntry, Date scheduleDate) throws SchedulerException {
    	Calendar calendar = getCalendarService().getCalendar(calendarEntry.getHrCalendarId());
    	String calendarTypes = calendar.getCalendarTypes();
    	String calendarName = calendar.getCalendarName();
		java.util.Date beginDate = calendarEntry.getBeginPeriodDateTime();
		java.util.Date endDate = calendarEntry.getEndPeriodDateTime();
    	
		if (StringUtils.equals(calendarTypes, "Pay")) {
			List<PrincipalHRAttributes> principalHRAttributes = getPrincipalHRAttributesService().getActiveEmployeesForPayCalendar(calendarName, scheduleDate);
			
			for (PrincipalHRAttributes principalHRAttribute : principalHRAttributes) {
				String principalId = principalHRAttribute.getPrincipalId();
				TimesheetDocumentHeader timesheetDocumentHeader = getTimesheetDocumentHeaderService().getDocumentHeader(principalId, beginDate, endDate);
				
				if (timesheetDocumentHeader != null) {
					scheduleEndReportingPeriodJob(calendarEntry, scheduleDate, principalId);
				}
			}
		} else if (StringUtils.equals(calendarTypes, "Leave")) {
			List<PrincipalHRAttributes> principalHRAttributes = getPrincipalHRAttributesService().getActiveEmployeesForLeaveCalendar(calendarName, scheduleDate);
			
			for (PrincipalHRAttributes principalHRAttribute : principalHRAttributes) {
				String principalId = principalHRAttribute.getPrincipalId();
				LeaveCalendarDocumentHeader leaveCalendarDocumentHeader = getLeaveCalendarDocumentHeaderService().getDocumentHeader(principalId, beginDate, endDate);
				
				if (leaveCalendarDocumentHeader != null) {
					scheduleEndReportingPeriodJob(calendarEntry, scheduleDate, principalId);
				}
			}
		}
	}
	
	private void scheduleEndReportingPeriodJob(CalendarEntries calendarEntry, Date scheduleDate, String principalId) throws SchedulerException {
        Map<String, String> jobGroupDataMap = new HashMap<String, String>();
        jobGroupDataMap.put("hrCalendarEntriesId", calendarEntry.getHrCalendarEntriesId());

		Map<String, String> jobDataMap = new HashMap<String, String>();
        jobDataMap.put("principalId", principalId);
        
		scheduleJob(EndReportingPeriodJob.class, scheduleDate, jobGroupDataMap, jobDataMap);
	}
	
	@Override
	public void scheduleEndPayPeriodJobs(CalendarEntries calendarEntry) throws SchedulerException {
		scheduleEndPayPeriodJobs(calendarEntry, calendarEntry.getBatchEndPayPeriodDateTime());
	}
	
	@Override
	public void scheduleEndPayPeriodJobs(CalendarEntries calendarEntry, Date scheduleDate) throws SchedulerException {
		String calendarName = calendarEntry.getCalendarName();
		    	
    	List<PrincipalHRAttributes> principalHRAttributes = getPrincipalHRAttributesService().getActiveEmployeesForPayCalendar(calendarName, scheduleDate);
        for (PrincipalHRAttributes principalHRAttribute : principalHRAttributes) {
        	String principalId = principalHRAttribute.getPrincipalId();
            
        	List<Assignment> assignments = getAssignmentService().getAssignmentsByCalEntryForTimeCalendar(principalId, calendarEntry);
    		for (Assignment assignment : assignments) {
    			String jobNumber = String.valueOf(assignment.getJobNumber());
    			String workArea = String.valueOf(assignment.getWorkArea());
    			String task = String.valueOf(assignment.getTask());
    			
    			ClockLog lastClockLog = getClockLogService().getLastClockLog(principalId, jobNumber, workArea, task, calendarEntry);
		    	if (lastClockLog != null && TkConstants.ON_THE_CLOCK_CODES.contains(lastClockLog.getClockAction())) {
		    		scheduleEndPayPeriodJob(calendarEntry, scheduleDate, lastClockLog);
		    	}
    		}
        }
	}
	
	private void scheduleEndPayPeriodJob(CalendarEntries calendarEntry, Date scheduleDate, ClockLog clockLog) throws SchedulerException {
        Map<String, String> jobGroupDataMap = new HashMap<String, String>();
        jobGroupDataMap.put("hrCalendarEntriesId", calendarEntry.getHrCalendarEntriesId());
		
		Map<String, String> jobDataMap = new HashMap<String, String>();
        jobDataMap.put("tkClockLogId", clockLog.getTkClockLogId());
		
        scheduleJob(EndPayPeriodJob.class, scheduleDate, jobGroupDataMap, jobDataMap);
	}
	
	@Override
	public void scheduleEmployeeApprovalJobs(CalendarEntries calendarEntry) throws SchedulerException {
		scheduleEmployeeApprovalJobs(calendarEntry, calendarEntry.getBatchEmployeeApprovalDateTime());
	}
	
	@Override
	public void scheduleEmployeeApprovalJobs(CalendarEntries calendarEntry, Date scheduleDate) throws SchedulerException {
    	java.util.Date beginDate = calendarEntry.getBeginPeriodDateTime();
    	java.util.Date endDate = calendarEntry.getEndPeriodDateTime();
    	Calendar calendar = getCalendarService().getCalendar(calendarEntry.getHrCalendarId());
    	
    	if (StringUtils.equals(calendar.getCalendarTypes(), "Pay")) {
	        List<TimesheetDocumentHeader> timesheetDocumentHeaders = getTimesheetDocumentHeaderService().getDocumentHeaders(beginDate, endDate);
	        for (TimesheetDocumentHeader timesheetDocumentHeader : timesheetDocumentHeaders) {
	        	scheduleEmployeeApprovalJob(calendarEntry, scheduleDate, timesheetDocumentHeader);
	        }
    	} else if (StringUtils.equals(calendar.getCalendarTypes(), "Leave")) {
	        List<LeaveCalendarDocumentHeader> leaveCalendarDocumentHeaders = getLeaveCalendarDocumentHeaderService().getDocumentHeaders(beginDate, endDate);
	        for (LeaveCalendarDocumentHeader leaveCalendarDocumentHeader : leaveCalendarDocumentHeaders) {
	        	scheduleEmployeeApprovalJob(calendarEntry, scheduleDate, leaveCalendarDocumentHeader);
	        }
    	}
	}
	
	private void scheduleEmployeeApprovalJob(CalendarEntries calendarEntry, Date scheduleDate, CalendarDocumentHeaderContract calendarDocumentHeaderContract) throws SchedulerException {
        Map<String, String> jobGroupDataMap = new HashMap<String, String>();
        jobGroupDataMap.put("hrCalendarEntriesId", calendarEntry.getHrCalendarEntriesId());
		
		Map<String, String> jobDataMap = new HashMap<String, String>();
        jobDataMap.put("documentId", calendarDocumentHeaderContract.getDocumentId());
		
        scheduleJob(EmployeeApprovalJob.class, scheduleDate, jobGroupDataMap, jobDataMap);
	}
	
	@Override
	public void scheduleMissedPunchApprovalJobs(CalendarEntries calendarEntry) throws SchedulerException {
		scheduleMissedPunchApprovalJobs(calendarEntry, calendarEntry.getBatchSupervisorApprovalDateTime());
	}

	@Override
	public void scheduleMissedPunchApprovalJobs(CalendarEntries calendarEntry, Date scheduleDate) throws SchedulerException {
		java.util.Date beginDate = calendarEntry.getBeginPeriodDateTime();
		java.util.Date endDate = calendarEntry.getEndPeriodDateTime();
   	
		List<TimesheetDocumentHeader> timesheetDocumentHeaders = getTimesheetDocumentHeaderService().getDocumentHeaders(beginDate, endDate);
		for (TimesheetDocumentHeader timesheetDocumentHeader : timesheetDocumentHeaders) {
			List<MissedPunchDocument> missedPunchDocuments = getMissedPunchService().getMissedPunchDocsByTimesheetDocumentId(timesheetDocumentHeader.getDocumentId());
			for (MissedPunchDocument missedPunchDocument : missedPunchDocuments) {
				if (StringUtils.equals(missedPunchDocument.getDocumentStatus(), DocumentStatus.ENROUTE.getCode())) {
					scheduleMissedPunchApprovalJob(calendarEntry, scheduleDate, missedPunchDocument);
				}
			}
		}
	}
	
	private void scheduleMissedPunchApprovalJob(CalendarEntries calendarEntry, Date scheduleDate, MissedPunchDocument missedPunchDocument) throws SchedulerException {
        Map<String, String> jobGroupDataMap = new HashMap<String, String>();
        jobGroupDataMap.put("hrCalendarEntriesId", calendarEntry.getHrCalendarEntriesId());
		
		Map<String, String> jobDataMap = new HashMap<String, String>();
        jobDataMap.put("principalId", missedPunchDocument.getPrincipalId());
		
        scheduleJob(MissedPunchApprovalJob.class, scheduleDate, jobGroupDataMap, jobDataMap);
	}
	
	@Override
	public void scheduleSupervisorApprovalJobs(CalendarEntries calendarEntry) throws SchedulerException {
		scheduleSupervisorApprovalJobs(calendarEntry, calendarEntry.getBatchSupervisorApprovalDateTime());
	}
	
	@Override
	public void scheduleSupervisorApprovalJobs(CalendarEntries calendarEntry, Date scheduleDate) throws SchedulerException {
    	java.util.Date beginDate = calendarEntry.getBeginPeriodDateTime();
    	java.util.Date endDate = calendarEntry.getEndPeriodDateTime();
    	Calendar calendar = getCalendarService().getCalendar(calendarEntry.getHrCalendarId());

    	if (StringUtils.equals(calendar.getCalendarTypes(), "Pay")) {
	        List<TimesheetDocumentHeader> timesheetDocumentHeaders = getTimesheetDocumentHeaderService().getDocumentHeaders(beginDate, endDate);
	        for (TimesheetDocumentHeader timesheetDocumentHeader : timesheetDocumentHeaders) {
	        	scheduleSupervisorApprovalJob(calendarEntry, scheduleDate, timesheetDocumentHeader);
	        }
    	} else if (StringUtils.equals(calendar.getCalendarTypes(), "Leave")) {
	        List<LeaveCalendarDocumentHeader> leaveCalendarDocumentHeaders = getLeaveCalendarDocumentHeaderService().getDocumentHeaders(beginDate, endDate);
	        for (LeaveCalendarDocumentHeader leaveCalendarDocumentHeader : leaveCalendarDocumentHeaders) {
	        	scheduleSupervisorApprovalJob(calendarEntry, scheduleDate, leaveCalendarDocumentHeader);
	        }
    	}
	}
	
	private void scheduleSupervisorApprovalJob(CalendarEntries calendarEntry, Date scheduleDate, CalendarDocumentHeaderContract calendarDocumentHeaderContract) throws SchedulerException {
        Map<String, String> jobGroupDataMap = new HashMap<String, String>();
        jobGroupDataMap.put("hrCalendarEntriesId", calendarEntry.getHrCalendarEntriesId());
		
		Map<String, String> jobDataMap = new HashMap<String, String>();
        jobDataMap.put("documentId", calendarDocumentHeaderContract.getDocumentId());
		
        scheduleJob(SupervisorApprovalJob.class, scheduleDate, jobGroupDataMap, jobDataMap);
	}
	
	@SuppressWarnings("unchecked")
	private void scheduleJob(Class<?> jobClass, Date jobDate, Map<String, String> jobGroupDataMap, Map<String, String> jobDataMap) throws SchedulerException {
		String jobGroupName = BatchJobUtil.getJobGroupName(jobClass, jobGroupDataMap);
		String jobName = BatchJobUtil.getJobName(jobClass, jobDataMap);
    	String[] jobNames = getScheduler().getJobNames(jobGroupName);
    	if (!ArrayUtils.contains(jobNames, jobName)) {
    		Map<String, String> mergedDataMap = MapUtils.merge(jobGroupDataMap, jobDataMap);
    		
    		JobDetail jobDetail = new JobDetail(jobName, jobGroupName, jobClass, false, true, false);
    		jobDetail.setJobDataMap(new JobDataMap(mergedDataMap));
    		
	        String triggerGroupName = BatchJobUtil.getTriggerGroupName(jobClass, MapUtils.merge(jobGroupDataMap, jobDataMap));
	        String triggerName = BatchJobUtil.getTriggerName(jobClass, jobDate);
	        Trigger trigger = new SimpleTrigger(triggerName, triggerGroupName, jobDate);
	        trigger.setJobGroup(jobGroupName);
	        trigger.setJobName(jobName);
	        
	        LOG.info("Scheduing " + jobDetail.getFullName() + " to be run on " + jobDate);
	        
	        getScheduler().scheduleJob(jobDetail, trigger);
    	}
	}

	public Scheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public AssignmentService getAssignmentService() {
		return assignmentService;
	}

	public void setAssignmentService(AssignmentService assignmentService) {
		this.assignmentService = assignmentService;
	}

	public CalendarService getCalendarService() {
		return calendarService;
	}

	public void setCalendarService(CalendarService calendarService) {
		this.calendarService = calendarService;
	}

	public ClockLogService getClockLogService() {
		return clockLogService;
	}

	public void setClockLogService(ClockLogService clockLogService) {
		this.clockLogService = clockLogService;
	}

	public LeaveCalendarDocumentHeaderService getLeaveCalendarDocumentHeaderService() {
		return leaveCalendarDocumentHeaderService;
	}

	public void setLeaveCalendarDocumentHeaderService(LeaveCalendarDocumentHeaderService leaveCalendarDocumentHeaderService) {
		this.leaveCalendarDocumentHeaderService = leaveCalendarDocumentHeaderService;
	}

	public MissedPunchService getMissedPunchService() {
		return missedPunchService;
	}

	public void setMissedPunchService(MissedPunchService missedPunchService) {
		this.missedPunchService = missedPunchService;
	}

	public PrincipalHRAttributesService getPrincipalHRAttributesService() {
		return principalHRAttributesService;
	}

	public void setPrincipalHRAttributesService(PrincipalHRAttributesService principalHRAttributesService) {
		this.principalHRAttributesService = principalHRAttributesService;
	}

	public TimesheetDocumentHeaderService getTimesheetDocumentHeaderService() {
		return timesheetDocumentHeaderService;
	}

	public void setTimesheetDocumentHeaderService(TimesheetDocumentHeaderService timesheetDocumentHeaderService) {
		this.timesheetDocumentHeaderService = timesheetDocumentHeaderService;
	}

}