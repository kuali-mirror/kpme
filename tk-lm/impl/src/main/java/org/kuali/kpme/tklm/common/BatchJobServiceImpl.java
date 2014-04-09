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
package org.kuali.kpme.tklm.common;

import org.apache.commons.exec.util.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.kuali.kpme.core.api.assignment.Assignment;
import org.kuali.kpme.core.api.assignment.service.AssignmentService;
import org.kuali.kpme.core.api.calendar.Calendar;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.api.calendar.service.CalendarService;
import org.kuali.kpme.core.api.document.calendar.CalendarDocumentHeaderContract;
import org.kuali.kpme.core.api.job.Job;
import org.kuali.kpme.core.api.leaveplan.LeavePlan;
import org.kuali.kpme.core.api.principal.service.PrincipalHRAttributesService;
import org.kuali.kpme.core.batch.BatchJob;
import org.kuali.kpme.core.batch.BatchJobUtil;
import org.kuali.kpme.core.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.tklm.leave.batch.CarryOverJob;
import org.kuali.kpme.tklm.leave.batch.LeaveCalendarDelinquencyJob;
import org.kuali.kpme.tklm.leave.workflow.LeaveCalendarDocumentHeader;
import org.kuali.kpme.tklm.leave.workflow.service.LeaveCalendarDocumentHeaderService;
import org.kuali.kpme.tklm.time.batch.BatchJobStatus;
import org.kuali.kpme.tklm.time.batch.ClockedInEmployeeJob;
import org.kuali.kpme.tklm.time.batch.EmployeeApprovalJob;
import org.kuali.kpme.tklm.time.batch.EndPayPeriodJob;
import org.kuali.kpme.tklm.time.batch.EndReportingPeriodJob;
import org.kuali.kpme.tklm.time.batch.InitiateJob;
import org.kuali.kpme.tklm.time.batch.MissedPunchApprovalJob;
import org.kuali.kpme.tklm.time.batch.SupervisorApprovalJob;
import org.kuali.kpme.tklm.api.time.clocklog.ClockLogService;
import org.kuali.kpme.tklm.time.missedpunch.document.MissedPunchDocumentService;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.kpme.tklm.time.workflow.service.TimesheetDocumentHeaderService;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BatchJobServiceImpl implements BatchJobService {
	
	private static final Logger LOG = Logger.getLogger(BatchJobServiceImpl.class);
    protected static final String SCHEDULE_JOB_NAME = "scheduleJob";
    public static final String JOB_STATUS_PARAMETER = "status";
	
	private Scheduler scheduler;
	
	private AssignmentService assignmentService;
	private CalendarService calendarService;
	private ClockLogService clockLogService;
	private LeaveCalendarDocumentHeaderService leaveCalendarDocumentHeaderService;
	private MissedPunchDocumentService missedPunchDocumentService;
	private PrincipalHRAttributesService principalHRAttributesService;
	private TimesheetDocumentHeaderService timesheetDocumentHeaderService;
	
    protected static final List<String> jobStatuses = new ArrayList<String>();

    static {
        jobStatuses.add(SCHEDULED_JOB_STATUS_CODE);
        jobStatuses.add(SUCCEEDED_JOB_STATUS_CODE);
        jobStatuses.add(CANCELLED_JOB_STATUS_CODE);
        jobStatuses.add(RUNNING_JOB_STATUS_CODE);
        jobStatuses.add(FAILED_JOB_STATUS_CODE);
    }
	
    public List<String> getJobStatuses() {
        return jobStatuses;
    }
    
	@Override
	public void scheduleInitiateJobs(CalendarEntry calendarEntry) throws SchedulerException {
		scheduleInitiateJobs(calendarEntry, calendarEntry.getBatchInitiateFullDateTime());
	}

    @Override
    public void scheduleInitiateJobs(CalendarEntry calendarEntry, DateTime scheduleDate) throws SchedulerException {
		Calendar calendar = getCalendarService().getCalendar(calendarEntry.getHrCalendarId());
		String calendarTypes = calendar.getCalendarTypes();
		String calendarName = calendar.getCalendarName();
		DateTime beginDate = calendarEntry.getBeginPeriodFullDateTime();
		DateTime endDate = calendarEntry.getEndPeriodFullDateTime();
		
		if (StringUtils.equals(calendarTypes, "Pay")) {
			List<PrincipalHRAttributes> principalHRAttributes = (List<PrincipalHRAttributes>) getPrincipalHRAttributesService().getActiveEmployeesForPayCalendar(calendarName, scheduleDate.toLocalDate());
			
			for (PrincipalHRAttributes principalHRAttribute : principalHRAttributes) {
				String principalId = principalHRAttribute.getPrincipalId();
				List<Assignment> assignments = getAssignmentService().getAllAssignmentsByCalEntryForTimeCalendar(principalId, calendarEntry);
				
				for (Assignment assignment : assignments) {
					Job job = assignment.getJob();
					
					if (StringUtils.equalsIgnoreCase(job.getFlsaStatus(), HrConstants.FLSA_STATUS_NON_EXEMPT)) {
						TimesheetDocumentHeader timesheetDocumentHeader = getTimesheetDocumentHeaderService().getDocumentHeader(principalId, beginDate, endDate);
						if (timesheetDocumentHeader == null || StringUtils.equals(timesheetDocumentHeader.getDocumentStatus(), HrConstants.ROUTE_STATUS.CANCEL)) {
							scheduleInitiateJob(calendarEntry, scheduleDate, assignment.getPrincipalId());
						}
					}
				}
			}
		} else if (StringUtils.equals(calendarTypes, "Leave")) {
			List<PrincipalHRAttributes> principalHRAttributes = (List<PrincipalHRAttributes>) getPrincipalHRAttributesService().getActiveEmployeesForLeaveCalendar(calendarName, scheduleDate.toLocalDate());
			
			for (PrincipalHRAttributes principalHRAttribute : principalHRAttributes) {
				String principalId = principalHRAttribute.getPrincipalId();
				List<Assignment> assignments = getAssignmentService().getAllAssignmentsByCalEntryForLeaveCalendar(principalId, calendarEntry);
				
				for (Assignment assignment : assignments) {
					Job job = assignment.getJob();
					
					if (job.isEligibleForLeave() && StringUtils.equalsIgnoreCase(job.getFlsaStatus(), HrConstants.FLSA_STATUS_EXEMPT)) {
						LeaveCalendarDocumentHeader leaveCalendarDocumentHeader = getLeaveCalendarDocumentHeaderService().getDocumentHeader(principalId, beginDate, endDate);
						if (leaveCalendarDocumentHeader == null || StringUtils.equals(leaveCalendarDocumentHeader.getDocumentStatus(), HrConstants.ROUTE_STATUS.CANCEL)) {
							scheduleInitiateJob(calendarEntry, scheduleDate, assignment.getPrincipalId());
						}
					}
				}
			}
		}
    }
    
	private void scheduleInitiateJob(CalendarEntry calendarEntry, DateTime scheduleDate, String principalId) throws SchedulerException {
        Map<String, String> jobGroupDataMap = new HashMap<String, String>();
        jobGroupDataMap.put("hrCalendarEntryId", calendarEntry.getHrCalendarEntryId());
		
		Map<String, String> jobDataMap = new HashMap<String, String>();
        jobDataMap.put("principalId", principalId);
        
		scheduleJob(InitiateJob.class, scheduleDate, jobGroupDataMap, jobDataMap);
	}
	
	@Override
	public void scheduleEndReportingPeriodJobs(CalendarEntry calendarEntry) throws SchedulerException {
		scheduleEndReportingPeriodJobs(calendarEntry, calendarEntry.getEndPeriodFullDateTime());
	}
	
	@Override
	public void scheduleEndReportingPeriodJobs(CalendarEntry calendarEntry, DateTime scheduleDate) throws SchedulerException {
    	Calendar calendar = getCalendarService().getCalendar(calendarEntry.getHrCalendarId());
    	String calendarTypes = calendar.getCalendarTypes();
    	String calendarName = calendar.getCalendarName();
    	DateTime beginDate = calendarEntry.getBeginPeriodFullDateTime();
		DateTime endDate = calendarEntry.getEndPeriodFullDateTime();
    	
		if (StringUtils.equals(calendarTypes, "Pay")) {
			List<PrincipalHRAttributes> principalHRAttributes = (List<PrincipalHRAttributes>) getPrincipalHRAttributesService().getActiveEmployeesForPayCalendar(calendarName, scheduleDate.toLocalDate());
			
			for (PrincipalHRAttributes principalHRAttribute : principalHRAttributes) {
				String principalId = principalHRAttribute.getPrincipalId();
				TimesheetDocumentHeader timesheetDocumentHeader = getTimesheetDocumentHeaderService().getDocumentHeader(principalId, beginDate, endDate);
				
				if (timesheetDocumentHeader != null) {
					scheduleEndReportingPeriodJob(calendarEntry, scheduleDate, principalId);
				}
			}
		} else if (StringUtils.equals(calendarTypes, "Leave")) {
			List<PrincipalHRAttributes> principalHRAttributes = (List<PrincipalHRAttributes>) getPrincipalHRAttributesService().getActiveEmployeesForLeaveCalendar(calendarName, scheduleDate.toLocalDate());
			
			for (PrincipalHRAttributes principalHRAttribute : principalHRAttributes) {
				String principalId = principalHRAttribute.getPrincipalId();
				LeaveCalendarDocumentHeader leaveCalendarDocumentHeader = getLeaveCalendarDocumentHeaderService().getDocumentHeader(principalId, beginDate, endDate);
				
				if (leaveCalendarDocumentHeader != null) {
					scheduleEndReportingPeriodJob(calendarEntry, scheduleDate, principalId);
				}
			}
		}
	}
	
	private void scheduleEndReportingPeriodJob(CalendarEntry calendarEntry, DateTime scheduleDate, String principalId) throws SchedulerException {
        Map<String, String> jobGroupDataMap = new HashMap<String, String>();
        jobGroupDataMap.put("hrCalendarEntryId", calendarEntry.getHrCalendarEntryId());

		Map<String, String> jobDataMap = new HashMap<String, String>();
        jobDataMap.put("principalId", principalId);
        
		scheduleJob(EndReportingPeriodJob.class, scheduleDate, jobGroupDataMap, jobDataMap);
	}
	
	@Override
	public void scheduleEndPayPeriodJobs(CalendarEntry calendarEntry) throws SchedulerException {
		TkServiceLocator.getBatchJobService().scheduleEndPayPeriodJobs(calendarEntry, calendarEntry.getBatchEndPayPeriodFullDateTime());
	}
	
	@Override
	public void scheduleEndPayPeriodJobs(CalendarEntry calendarEntry, DateTime scheduleDate) throws SchedulerException {
		Map<String, String> jobGroupDataMap = new HashMap<String, String>();
        jobGroupDataMap.put("hrCalendarEntryId", calendarEntry.getHrCalendarEntryId());
		
		Map<String, String> jobDataMap = new HashMap<String, String>();
        jobDataMap.put("hrCalendarEntryId", calendarEntry.getHrCalendarEntryId());
		
        scheduleJob(EndPayPeriodJob.class, scheduleDate, jobGroupDataMap, jobDataMap);
	}
		
	@Override
	public void scheduleEmployeeApprovalJobs(CalendarEntry calendarEntry) throws SchedulerException {
		scheduleEmployeeApprovalJobs(calendarEntry, calendarEntry.getBatchEmployeeApprovalFullDateTime());
	}
	
	@Override
	public void scheduleEmployeeApprovalJobs(CalendarEntry calendarEntry, DateTime scheduleDate) throws SchedulerException {
//    	DateTime beginDate = calendarEntry.getBeginPeriodFullDateTime();
//    	DateTime endDate = calendarEntry.getEndPeriodFullDateTime();
//    	Calendar calendar = getCalendarService().getCalendar(calendarEntry.getHrCalendarId());
    	
    	Map<String, String> jobGroupDataMap = new HashMap<String, String>();
        jobGroupDataMap.put("hrCalendarEntryId", calendarEntry.getHrCalendarEntryId());
  		
  		Map<String, String> jobDataMap = new HashMap<String, String>();
        jobDataMap.put("hrCalendarEntryId",  calendarEntry.getHrCalendarEntryId());
  		
        scheduleJob(EmployeeApprovalJob.class, scheduleDate, jobGroupDataMap, jobDataMap);
    	
//    	if (StringUtils.equals(calendar.getCalendarTypes(), "Pay")) {
//	        List<TimesheetDocumentHeader> timesheetDocumentHeaders = getTimesheetDocumentHeaderService().getDocumentHeaders(beginDate, endDate);
//	        for (TimesheetDocumentHeader timesheetDocumentHeader : timesheetDocumentHeaders) {
//	        	scheduleEmployeeApprovalJob(calendarEntry, scheduleDate, timesheetDocumentHeader);
//	        }
//    	} else if (StringUtils.equals(calendar.getCalendarTypes(), "Leave")) {
//	        List<LeaveCalendarDocumentHeader> leaveCalendarDocumentHeaders = getLeaveCalendarDocumentHeaderService().getDocumentHeaders(beginDate, endDate);
//	        for (LeaveCalendarDocumentHeader leaveCalendarDocumentHeader : leaveCalendarDocumentHeaders) {
//	        	scheduleEmployeeApprovalJob(calendarEntry, scheduleDate, leaveCalendarDocumentHeader);
//	        }
//    	}
	}
		
//	private void scheduleEmployeeApprovalJob(CalendarEntry calendarEntry, DateTime scheduleDate, CalendarDocumentHeaderContract calendarDocumentHeaderContract) throws SchedulerException {
//        Map<String, String> jobGroupDataMap = new HashMap<String, String>();
//        jobGroupDataMap.put("hrCalendarEntryId", calendarEntry.getHrCalendarEntryId());
//		
//		Map<String, String> jobDataMap = new HashMap<String, String>();
//        jobDataMap.put("documentId", calendarDocumentHeaderContract.getDocumentId());
//		
//        scheduleJob(EmployeeApprovalJob.class, scheduleDate, jobGroupDataMap, jobDataMap);
//	}

	@Override
	public void scheduleMissedPunchApprovalJobs(CalendarEntry calendarEntry) throws SchedulerException {
		scheduleMissedPunchApprovalJobs(calendarEntry, calendarEntry.getBatchSupervisorApprovalFullDateTime());
	}

	@Override
	public void scheduleMissedPunchApprovalJobs(CalendarEntry calendarEntry, DateTime scheduleDate) throws SchedulerException {
		Map<String, String> jobGroupDataMap = new HashMap<String, String>();
        jobGroupDataMap.put("hrCalendarEntryId", calendarEntry.getHrCalendarEntryId());
		
		Map<String, String> jobDataMap = new HashMap<String, String>();
        jobDataMap.put("hrCalendarEntryId", calendarEntry.getHrCalendarEntryId());
		
        scheduleJob(MissedPunchApprovalJob.class, scheduleDate, jobGroupDataMap, jobDataMap);
		
		
		
//		DateTime beginDate = calendarEntry.getBeginPeriodFullDateTime();
//		DateTime endDate = calendarEntry.getEndPeriodFullDateTime();
//   	
//		List<TimesheetDocumentHeader> timesheetDocumentHeaders = getTimesheetDocumentHeaderService().getDocumentHeaders(beginDate, endDate);
//		for (TimesheetDocumentHeader timesheetDocumentHeader : timesheetDocumentHeaders) {
//			List<MissedPunchDocument> missedPunchDocuments = getMissedPunchService().getMissedPunchDocumentsByTimesheetDocumentId(timesheetDocumentHeader.getDocumentId());
//			for (MissedPunchDocument missedPunchDocument : missedPunchDocuments) {
//				DocumentStatus documentStatus = KewApiServiceLocator.getWorkflowDocumentService().getDocumentStatus(missedPunchDocument.getDocumentNumber());
//				if (DocumentStatus.ENROUTE.equals(documentStatus)) {
//					scheduleMissedPunchApprovalJob(calendarEntry, scheduleDate, missedPunchDocument.getMissedPunch());
//				}
//			}
//		}
	}
	
//	private void scheduleMissedPunchApprovalJob(CalendarEntry calendarEntry, DateTime scheduleDate, MissedPunch missedPunch) throws SchedulerException {
//        Map<String, String> jobGroupDataMap = new HashMap<String, String>();
//        jobGroupDataMap.put("hrCalendarEntryId", calendarEntry.getHrCalendarEntryId());
//		
//		Map<String, String> jobDataMap = new HashMap<String, String>();
//        jobDataMap.put("principalId", missedPunch.getPrincipalId());
//		
//        scheduleJob(MissedPunchApprovalJob.class, scheduleDate, jobGroupDataMap, jobDataMap);
//	}
	
	@Override
	public void scheduleSupervisorApprovalJobs(CalendarEntry calendarEntry) throws SchedulerException {
		scheduleSupervisorApprovalJobs(calendarEntry, calendarEntry.getBatchSupervisorApprovalFullDateTime());
	}
	
	@Override
	public void scheduleSupervisorApprovalJobs(CalendarEntry calendarEntry, DateTime scheduleDate) throws SchedulerException {
		
		 Map<String, String> jobGroupDataMap = new HashMap<String, String>();
	     jobGroupDataMap.put("hrCalendarEntryId", calendarEntry.getHrCalendarEntryId());
			
		 Map<String, String> jobDataMap = new HashMap<String, String>();
	     jobDataMap.put("hrCalendarEntryId", calendarEntry.getHrCalendarEntryId());
			
	     scheduleJob(SupervisorApprovalJob.class, scheduleDate, jobGroupDataMap, jobDataMap);
				
//		DateTime beginDate = calendarEntry.getBeginPeriodFullDateTime();
//		DateTime endDate = calendarEntry.getEndPeriodFullDateTime();
//    	Calendar calendar = getCalendarService().getCalendar(calendarEntry.getHrCalendarId());
//
//    	if (StringUtils.equals(calendar.getCalendarTypes(), "Pay")) {
//	        List<TimesheetDocumentHeader> timesheetDocumentHeaders = getTimesheetDocumentHeaderService().getDocumentHeaders(beginDate, endDate);
//	        for (TimesheetDocumentHeader timesheetDocumentHeader : timesheetDocumentHeaders) {
//	        	scheduleSupervisorApprovalJob(calendarEntry, scheduleDate, timesheetDocumentHeader);
//	        }
//    	} else if (StringUtils.equals(calendar.getCalendarTypes(), "Leave")) {
//	        List<LeaveCalendarDocumentHeader> leaveCalendarDocumentHeaders = getLeaveCalendarDocumentHeaderService().getDocumentHeaders(beginDate, endDate);
//	        for (LeaveCalendarDocumentHeader leaveCalendarDocumentHeader : leaveCalendarDocumentHeaders) {
//	        	scheduleSupervisorApprovalJob(calendarEntry, scheduleDate, leaveCalendarDocumentHeader);
//	        }
//    	}
	}
	
//	private void scheduleSupervisorApprovalJob(CalendarEntry calendarEntry, DateTime scheduleDate, CalendarDocumentHeaderContract calendarDocumentHeaderContract) throws SchedulerException {
//        Map<String, String> jobGroupDataMap = new HashMap<String, String>();
//        jobGroupDataMap.put("hrCalendarEntryId", calendarEntry.getHrCalendarEntryId());
//		
//		Map<String, String> jobDataMap = new HashMap<String, String>();
//        jobDataMap.put("documentId", calendarDocumentHeaderContract.getDocumentId());
//		
//        scheduleJob(SupervisorApprovalJob.class, scheduleDate, jobGroupDataMap, jobDataMap);
//	}
	
	
	@Override
	public void scheduleLeaveCarryOverJobs(LeavePlan leavePlan) throws SchedulerException {
		DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd");
		DateTime scheduleDate = formatter.parseDateTime(leavePlan.getBatchPriorYearCarryOverStartDate()).plus(leavePlan.getBatchPriorYearCarryOverStartLocalTime().getMillisOfDay());
		scheduleLeaveCarryOverJob(leavePlan, scheduleDate);
	}
	
	@Override
	public void scheduleLeaveCarryOverJobs(LeavePlan leavePlan, DateTime scheduleDate) throws SchedulerException {
		scheduleLeaveCarryOverJob(leavePlan, scheduleDate);
	}

	protected void scheduleLeaveCarryOverJob(LeavePlan leavePlan, DateTime scheduleDate) throws SchedulerException {
        Map<String, String> jobGroupDataMap = new HashMap<String, String>();
        jobGroupDataMap.put("date", BatchJobUtil.FORMAT.print(scheduleDate));
		Map<String, String> jobDataMap = new HashMap<String, String>();
		jobDataMap.put("leavePlan", leavePlan.getLeavePlan());
        scheduleJob(CarryOverJob.class, scheduleDate, jobGroupDataMap, jobDataMap);
	}
	
	@Override
	public void schedulePayrollApprovalJobs(CalendarEntry calendarEntry) throws SchedulerException {
		schedulePayrollApprovalJobs(calendarEntry, calendarEntry.getBatchPayrollApprovalFullDateTime());
	}
	
	@Override
	public void schedulePayrollApprovalJobs(CalendarEntry calendarEntry, DateTime scheduleDate) throws SchedulerException {
		Map<String, String> jobGroupDataMap = new HashMap<String, String>();
        jobGroupDataMap.put("hrCalendarEntryId", calendarEntry.getHrCalendarEntryId());
		
		Map<String, String> jobDataMap = new HashMap<String, String>();
        jobDataMap.put("hrCalendarEntryId", calendarEntry.getHrCalendarEntryId());
		
        scheduleJob(PayrollApprovalJob.class, scheduleDate, jobGroupDataMap, jobDataMap);
	        
//		DateTime beginDate = calendarEntry.getBeginPeriodFullDateTime();
//		DateTime endDate = calendarEntry.getEndPeriodFullDateTime();
//    	Calendar calendar = getCalendarService().getCalendar(calendarEntry.getHrCalendarId());
//
//    	if (StringUtils.equals(calendar.getCalendarTypes(), "Pay")) {
//	        List<TimesheetDocumentHeader> timesheetDocumentHeaders = getTimesheetDocumentHeaderService().getDocumentHeaders(beginDate, endDate);
//	        for (TimesheetDocumentHeader timesheetDocumentHeader : timesheetDocumentHeaders) {
//	        	schedulePayrollApprovalJob(calendarEntry, scheduleDate, timesheetDocumentHeader);
//	        }
//    	} else if (StringUtils.equals(calendar.getCalendarTypes(), "Leave")) {
//	        List<LeaveCalendarDocumentHeader> leaveCalendarDocumentHeaders = getLeaveCalendarDocumentHeaderService().getDocumentHeaders(beginDate, endDate);
//	        for (LeaveCalendarDocumentHeader leaveCalendarDocumentHeader : leaveCalendarDocumentHeaders) {
//	        	schedulePayrollApprovalJob(calendarEntry, scheduleDate, leaveCalendarDocumentHeader);
//	        }
//    	}
	}
	
//	private void schedulePayrollApprovalJob(CalendarEntry calendarEntry, DateTime scheduleDate, CalendarDocumentHeaderContract calendarDocumentHeaderContract) throws SchedulerException {
//        Map<String, String> jobGroupDataMap = new HashMap<String, String>();
//        jobGroupDataMap.put("hrCalendarEntryId", calendarEntry.getHrCalendarEntryId());
//		
//		Map<String, String> jobDataMap = new HashMap<String, String>();
//        jobDataMap.put("documentId", calendarDocumentHeaderContract.getDocumentId());
//		
//        scheduleJob(PayrollApprovalJob.class, scheduleDate, jobGroupDataMap, jobDataMap);
//	}
		
	@Override
	public void scheduleLeaveCalendarDelinquencyJobs(CalendarEntry calendarEntry) throws SchedulerException {
		scheduleLeaveCalendarDelinquencyJobs(calendarEntry, calendarEntry.getBatchPayrollApprovalFullDateTime());
	}
	
	@Override
	public void scheduleLeaveCalendarDelinquencyJobs(CalendarEntry calendarEntry, DateTime scheduleDate) throws SchedulerException {
		DateTime beginDate = calendarEntry.getBeginPeriodFullDateTime();
		DateTime endDate = calendarEntry.getEndPeriodFullDateTime();
    	Calendar calendar = getCalendarService().getCalendar(calendarEntry.getHrCalendarId());

    	if (StringUtils.equals(calendar.getCalendarTypes(), "Pay")) {
	        List<TimesheetDocumentHeader> timesheetDocumentHeaders = getTimesheetDocumentHeaderService().getDocumentHeaders(beginDate, endDate);
	        for (TimesheetDocumentHeader timesheetDocumentHeader : timesheetDocumentHeaders) {
	        	scheduleLeaveCalendarDelinquencyJob(calendarEntry, scheduleDate, timesheetDocumentHeader);
	        }
    	} else if (StringUtils.equals(calendar.getCalendarTypes(), "Leave")) {
	        List<LeaveCalendarDocumentHeader> leaveCalendarDocumentHeaders = getLeaveCalendarDocumentHeaderService().getDocumentHeaders(beginDate, endDate);
	        for (LeaveCalendarDocumentHeader leaveCalendarDocumentHeader : leaveCalendarDocumentHeaders) {
	        	scheduleLeaveCalendarDelinquencyJob(calendarEntry, scheduleDate, leaveCalendarDocumentHeader);
	        }
    	}
	}
	private void scheduleLeaveCalendarDelinquencyJob(CalendarEntry calendarEntry, DateTime scheduleDate, CalendarDocumentHeaderContract calendarDocumentHeaderContract) throws SchedulerException {
        Map<String, String> jobGroupDataMap = new HashMap<String, String>();
        jobGroupDataMap.put("hrCalendarEntryId", calendarEntry.getHrCalendarEntryId());
		
		Map<String, String> jobDataMap = new HashMap<String, String>();
        jobDataMap.put("documentId", calendarDocumentHeaderContract.getDocumentId());
		
        scheduleJob(LeaveCalendarDelinquencyJob.class, scheduleDate, jobGroupDataMap, jobDataMap);
	}

    @Override
    public void scheduleClockedInEmployeeJob(DateTime scheduleDate) throws SchedulerException {
        Map<String, String> jobGroupDataMap = new HashMap<String, String>();
        jobGroupDataMap.put("scheduleDate",scheduleDate.toString());
        Map<String, String> jobDataMap = new HashMap<String, String>();
        jobDataMap.put("scheduleDate",scheduleDate.toString());
        scheduleJob(ClockedInEmployeeJob.class, scheduleDate, jobGroupDataMap, jobDataMap);
    }

	@SuppressWarnings("unchecked")
	private void scheduleJob(Class<?> jobClass, DateTime jobDate, Map<String, String> jobGroupDataMap, Map<String, String> jobDataMap) throws SchedulerException {
		if(jobDate == null) {
			return;
		}
		
		String jobGroupName = BatchJobUtil.getJobGroupName(jobClass, jobGroupDataMap);
		String jobName = BatchJobUtil.getJobName(jobClass, jobDataMap);
    	String[] jobNames = getScheduler().getJobNames(jobGroupName);
    	if (!ArrayUtils.contains(jobNames, jobName)) {
    		jobDataMap.put("date", jobDate.toString());
    		Map<String, String> mergedDataMap = MapUtils.merge(jobGroupDataMap, jobDataMap);
    		JobDetail jobDetail = new JobDetail(jobName, jobGroupName, jobClass, false, true, false);
    		jobDetail.setJobDataMap(new JobDataMap(mergedDataMap));
    		
	        String triggerGroupName = BatchJobUtil.getTriggerGroupName(jobClass, MapUtils.merge(jobGroupDataMap, jobDataMap));
	        String triggerName = BatchJobUtil.getTriggerName(jobClass, jobDate);
	        Trigger trigger = new SimpleTrigger(triggerName, triggerGroupName, jobDate.toDate());
	        trigger.setJobGroup(jobGroupName);
	        trigger.setJobName(jobName);

	        LOG.info("Scheduing " + jobDetail.getFullName() + " to be run on " + jobDate);
	        this.updateStatus(jobDetail, BatchJobService.SCHEDULED_JOB_STATUS_CODE);
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

	public MissedPunchDocumentService getMissedPunchDocumentService() {
		return missedPunchDocumentService;
	}

	public void setMissedPunchDocumentService(MissedPunchDocumentService missedPunchDocumentService) {
		this.missedPunchDocumentService = missedPunchDocumentService;
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

	public List<BatchJobStatus> getJobs(String jobNm, String jobStatus, String hrCalendarId, Date startDate, Date endDate) throws SchedulerException {
		ArrayList<BatchJobStatus> jobs = new ArrayList<BatchJobStatus>();
		boolean isConsiderJobNm = (jobNm != null && !jobNm.trim().isEmpty()) ? true : false;
		boolean isConsiderJobStatus = (jobStatus != null && !jobStatus.trim().isEmpty()) ? true : false;
		boolean isConsiderCalId = (hrCalendarId != null && !hrCalendarId.trim().isEmpty()) ? true : false;
		for (String jobGroup : scheduler.getJobGroupNames()) {
            for (String jobName : scheduler.getJobNames(jobGroup)) {
                try {
                	if(isConsiderJobNm) {
                		if(!jobName.toLowerCase().contains(jobNm.toLowerCase())) {
                			continue;
                		}
                	}
                	JobDetail jobDetail = scheduler.getJobDetail(jobName, jobGroup);
                	if(isConsiderJobStatus) {
                		String statusString = jobDetail.getJobDataMap().getString("status");
                		if(statusString == null || !statusString.equalsIgnoreCase(jobStatus)) {
                			continue;
                		}
                	}
                	if(isConsiderCalId) {
                		String calendarString = jobDetail.getJobDataMap().getString("hrCalendarEntryId");
                		if(calendarString == null || !calendarString.equalsIgnoreCase(hrCalendarId)) {
                			continue;
                		}
                	}
                    String dateString = jobDetail.getJobDataMap().getString("date");
                    if(dateString != null) {
                    	DateTimeFormatter dateFormat = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                    	//dateFormat.setLenient(false);
                        DateTime date = dateFormat.parseDateTime(dateString);
                    	if(startDate != null && endDate != null) {
	                    	if(date.toDate().compareTo(startDate) >=0 && date.toDate().compareTo(endDate) <= 0) {
	                    		BatchJobStatus batchJobStatus = new BatchJobStatus(jobDetail);
	                    		batchJobStatus.setScheduledDate(date.toDate());
	                    		jobs.add(batchJobStatus);
	                    	}
                    	} else {
                    		BatchJobStatus batchJobStatus = new BatchJobStatus(jobDetail);
                    		batchJobStatus.setScheduledDate(date.toDate());
                    		jobs.add(batchJobStatus);
                    	}
                    } else {
                		BatchJobStatus batchJobStatus = new BatchJobStatus(jobDetail);
                		jobs.add(batchJobStatus);
                    }
                }
                catch (Exception ex) {
                    // do nothing, ignore jobs not defined in spring
                	ex.printStackTrace();
                    LOG.warn("Attempt to find bean " + jobGroup + "." + jobName + " failed - not in Spring context");
                }
            }
        }
		return jobs;
	}
	
    public void updateStatus(JobDetail jobDetail, String jobStatus) {
        if ( LOG.isInfoEnabled() ) {
            LOG.info("Updating status of job: "+jobDetail.getName()+"="+jobStatus);
        }
        jobDetail.getJobDataMap().put("status", jobStatus);
    }
    
    public boolean isJobRunning(String jobName) {
        List<JobExecutionContext> runningJobs = getRunningJobs();
        for (JobExecutionContext jobCtx : runningJobs) {
            if (jobCtx.getJobDetail().getName().equals(jobName)) {
                return true;
            }
        }
        return false;
    }
    
    public List<JobExecutionContext> getRunningJobs() {
        try {
            List<JobExecutionContext> jobContexts = scheduler.getCurrentlyExecutingJobs();
            return jobContexts;
        }
        catch (SchedulerException ex) {
            throw new RuntimeException("Unable to get list of running jobs.", ex);
        }
    }
    
    public String getStatus(JobDetail jobDetail) {
        if ( jobDetail == null ) {
            return FAILED_JOB_STATUS_CODE;
        }
        //get status from job detail data map 
        return jobDetail.getJobDataMap().getString("status");
    }
    
}
