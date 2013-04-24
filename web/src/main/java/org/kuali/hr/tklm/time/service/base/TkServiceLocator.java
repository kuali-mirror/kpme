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
package org.kuali.hr.tklm.time.service.base;

import org.kuali.hr.core.accrualcategory.rule.service.AccrualCategoryRuleService;
import org.kuali.hr.core.accrualcategory.service.AccrualCategoryService;
import org.kuali.hr.core.assignment.dao.AssignmentDao;
import org.kuali.hr.core.assignment.service.AssignmentService;
import org.kuali.hr.core.department.service.DepartmentService;
import org.kuali.hr.core.earncode.service.EarnCodeService;
import org.kuali.hr.core.earncodegroup.service.EarnCodeGroupService;
import org.kuali.hr.core.earncodesec.service.EarnCodeSecurityService;
import org.kuali.hr.core.group.service.HRGroupService;
import org.kuali.hr.core.job.service.JobService;
import org.kuali.hr.core.leaveplan.service.LeavePlanService;
import org.kuali.hr.core.location.service.LocationService;
import org.kuali.hr.core.notification.service.KPMENotificationService;
import org.kuali.hr.core.paygrade.service.PayGradeService;
import org.kuali.hr.core.paytype.service.PayTypeService;
import org.kuali.hr.core.permission.service.HRPermissionService;
import org.kuali.hr.core.position.service.PositionService;
import org.kuali.hr.core.principal.service.PrincipalHRAttributesService;
import org.kuali.hr.core.role.service.HRRoleService;
import org.kuali.hr.core.salgroup.service.SalGroupService;
import org.kuali.hr.core.task.service.TaskService;
import org.kuali.hr.core.workarea.service.WorkAreaService;
import org.kuali.hr.tklm.leave.accrual.service.AccrualCategoryMaxBalanceService;
import org.kuali.hr.tklm.leave.accrual.service.AccrualCategoryMaxCarryOverService;
import org.kuali.hr.tklm.leave.accrual.service.AccrualService;
import org.kuali.hr.tklm.leave.accrual.service.PrincipalAccrualRanService;
import org.kuali.hr.tklm.leave.balancetransfer.service.BalanceTransferService;
import org.kuali.hr.tklm.leave.employeeoverride.service.EmployeeOverrideService;
import org.kuali.hr.tklm.leave.leave.approval.service.LeaveApprovalService;
import org.kuali.hr.tklm.leave.leaveSummary.service.LeaveSummaryService;
import org.kuali.hr.tklm.leave.leaveadjustment.service.LeaveAdjustmentService;
import org.kuali.hr.tklm.leave.leaveblock.service.LeaveBlockHistoryService;
import org.kuali.hr.tklm.leave.leaveblock.service.LeaveBlockService;
import org.kuali.hr.tklm.leave.leavecalendar.service.LeaveCalendarService;
import org.kuali.hr.tklm.leave.leavedonation.service.LeaveDonationService;
import org.kuali.hr.tklm.leave.leavepayout.service.LeavePayoutService;
import org.kuali.hr.tklm.leave.leaverequest.service.LeaveRequestDocumentService;
import org.kuali.hr.tklm.leave.permission.service.LMPermissionService;
import org.kuali.hr.tklm.leave.role.service.LMRoleService;
import org.kuali.hr.tklm.leave.timeoff.service.SystemScheduledTimeOffService;
import org.kuali.hr.tklm.leave.workflow.service.LeaveCalendarDocumentHeaderService;
import org.kuali.hr.tklm.time.approval.service.TimeApproveService;
import org.kuali.hr.tklm.time.batch.service.BatchJobService;
import org.kuali.hr.tklm.time.calendar.service.CalendarEntryService;
import org.kuali.hr.tklm.time.calendar.service.CalendarService;
import org.kuali.hr.tklm.time.clock.location.service.ClockLocationRuleService;
import org.kuali.hr.tklm.time.clocklog.service.ClockLogService;
import org.kuali.hr.tklm.time.dept.lunch.service.DepartmentLunchRuleService;
import org.kuali.hr.tklm.time.docsearch.TkSearchableAttributeService;
import org.kuali.hr.tklm.time.graceperiod.service.GracePeriodService;
import org.kuali.hr.tklm.time.missedpunch.service.MissedPunchService;
import org.kuali.hr.tklm.time.overtime.daily.rule.service.DailyOvertimeRuleService;
import org.kuali.hr.tklm.time.overtime.weekly.rule.service.WeeklyOvertimeRuleService;
import org.kuali.hr.tklm.time.permission.service.TKPermissionService;
import org.kuali.hr.tklm.time.person.service.PersonService;
import org.kuali.hr.tklm.time.role.service.TKRoleService;
import org.kuali.hr.tklm.time.rule.TkRuleControllerService;
import org.kuali.hr.tklm.time.shiftdiff.rule.service.ShiftDifferentialRuleService;
import org.kuali.hr.tklm.time.syslunch.service.SystemLunchRuleService;
import org.kuali.hr.tklm.time.timeblock.service.TimeBlockHistoryDetailService;
import org.kuali.hr.tklm.time.timeblock.service.TimeBlockHistoryService;
import org.kuali.hr.tklm.time.timeblock.service.TimeBlockService;
import org.kuali.hr.tklm.time.timecollection.rule.service.TimeCollectionRuleService;
import org.kuali.hr.tklm.time.timehourdetail.service.TimeHourDetailService;
import org.kuali.hr.tklm.time.timesheet.service.TimesheetService;
import org.kuali.hr.tklm.time.timesummary.service.TimeSummaryService;
import org.kuali.hr.tklm.time.timezone.service.TimezoneService;
import org.kuali.hr.tklm.time.user.pref.service.UserPreferenceService;
import org.kuali.hr.tklm.time.warning.TkWarningService;
import org.kuali.hr.tklm.time.workflow.service.TimesheetDocumentHeaderService;
import org.kuali.rice.core.impl.cache.DistributedCacheManagerDecorator;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springmodules.orm.ojb.PersistenceBrokerTemplate;

public class TkServiceLocator implements ApplicationContextAware {
	public static String SPRING_BEANS = "classpath:SpringBeans.xml";
	private static ApplicationContext CONTEXT;
	public static final String TK_CLOCK_LOG_SERVICE = "clockLogService";
	public static final String TK_ASSIGNMENT_SERVICE = "assignmentService";
	public static final String TK_ASSIGNMENT_DAO     = "assignmentDao";
	public static final String TK_TIME_BLOCK_SERVICE = "timeBlockService";
	public static final String TK_TIME_BLOCK_HISTORY_SERVICE = "timeBlockHistoryService";
	public static final String TK_TIME_BLOCK_HISTORY_DETAIL_SERVICE = "timeBlockHistoryDetailService";
	public static final String TK_JOB_SERVICE = "jobService";
	public static final String TK_PAY_TYPE_SERVICE = "payTypeService";
	public static final String TK_PAY_CALENDAR_SERVICE = "calendarService";
	public static final String TK_PAY_CALENDAR_ENTRY_SERVICE = "calendarEntryService";
	public static final String TK_PERSISTENCE_BROKER_TEMPLATE = "tkPersistenceBrokerTemplate";
	public static final String TK_CACHE_MANAGER_SERVICE = "cacheManager";
	public static final String TK_WORK_AREA_SERVICE = "workAreaService";
	public static final String TK_TIMESHEET_SERVICE = "timesheetService";
	public static final String TK_TIMESHEET_DOCUMENT_HEADER_SERVICE = "timesheetDocumentHeaderService";
	public static final String TK_DEPARTMENT_SERVICE = "departmentService";
	public static final String TK_EARN_CODE_SECURITY = "earnCodeSecurityService";
	public static final String TK_EARN_CODE = "earnCodeService";
	public static final String TK_TIME_COLLECTION_RULE_SERVICE = "timeCollectionRuleService";
	public static final String TK_TIME_SUMMARY_SERVICE = "timeSummaryService";
	public static final String TK_TIME_EARN_CODE_GROUP_SERVICE = "earnCodeGroupService";
	public static final String TK_TIME_HOUR_DETAIL_SERVICE= "timeHourDetailService";
	public static final String TK_DAILY_OVERTIME_RULE_SERVICE = "dailyOvertimeRuleService";
	public static final String TK_WEEKLY_OVERTIME_RULE_SERVICE = "weeklyOvertimeRuleService";
	public static final String TK_SHIFT_DIFFERENTIAL_RULE_SERVICE = "shiftDifferentialRuleService";
	public static final String TK_CLOCK_LOCATION_RULE_SERVICE = "clockLocationService";
	public static final String TK_GRACE_PERIOD_SERVICE = "gracePeriodService";
	public static final String TK_SYSTEM_LUNCH_RULE_SERVICE = "systemLunchRuleService";
	public static final String TK_DEPT_LUNCH_RULE_SERVICE = "deptLunchRuleService";
	public static final String TK_PRINCIPAL_CALENDAR_SERVICE = "principalCalendarService";
	public static final String TK_USER_PREF_SERVICE = "userPrefService";
	public static final String TK_TIME_ZONE_SERVICE = "timezoneService";
	public static final String TK_TIME_OFF_ACCRUAL_SERVICE = "timeOffAccrualService";
	public static final String hr_sal_group_SERVICE = "salGroupService";
	public static final String TK_ACCRUAL_CATEGORY_SERVICE = "accrualCategoryService";
	public static final String TK_ACCRUAL_CATEGORY_RULE_SERVICE = "accrualCategoryRuleService";
    public static final String TK_TASK_SERVICE = "taskService";
    public static final String TK_LOCATION_SERVICE = "locationService";
    public static final String TK_PAY_GRADE_SERVICE = "payGradeService";
    public static final String TK_PERSON_SERVICE = "tkPersonService";
    public static final String TK_APPROVE_SERVICE = "timeApproveService";
    public static final String TK_BATCH_JOB_SERVICE = "batchJobService";
    public static final String TK_MISSED_PUNCH_SERVICE = "missedPunchService";
    public static final String TK_BATCH_JOB_ENTRY_SERVICE = "batchJobEntryService";
    public static final String TK_WARNINGS_SERVICE = "tkWarningService";
    public static final String HR_POSITION_SERVICE = "positionService";
    public static final String TK_SEARCH_ATTR_SERVICE = "tkSearchableAttributeService";
    public static final String LM_ACCRUAL_SERVICE = "accrualService";
    public static final String LM_ACCRUAL_CATEGORY_SERVICE = "leaveAccrualCategoryService";
    public static final String LM_LEAVE_PLAN_SERVICE = "leavePlanService";
    public static final String LM_LEAVE_DONATION_SERVICE = "leaveDonationService";
    public static final String LM_SYS_SCH_TIMEOFF_SERVICE = "systemScheduledTimeOffService";
    public static final String LM_LEAVE_BLOCK_SERVICE = "leaveBlockService";
    public static final String LM_LEAVE_CALENDAR_SERVICE = "leaveCalendarService";
    public static final String LM_LEAVE_CALENDAR_DOCUMENT_HEADER_SERVICE = "leaveCalendarDocumentHeaderService";
    public static final String LM_EMPLOYEE_OVERRIDE_SERVICE = "employeeOverrideService";
	public static final String TK_PRINCIPAL_HR_ATTRIBUTES_SERVICE = "principalHRAttributesService";
	public static final String LM_LEAVE_ADJUSTMENT_SERVICE = "leaveAdjustmentService";
	public static final String LM_LEAVE_BLOCK_HISTORY_SERVICE = "leaveBlockHistoryService";
	public static final String LM_PRINCIPAL_ACCRUAL_RAN_SERVICE = "principalAccrualRanService";
	public static final String LM_LEAVE_SUMMARY_SERVICE = "leaveSummaryService";
	public static final String LM_LEAVE_APPROVAL_SERVICE = "leaveApprovalService";
    public static final String LM_BALANCE_TRANSFER_SERVICE = "balanceTransferService";
    public static final String LM_ACCRUAL_CATEGORY_MAX_BALANCE_SERVICE = "accrualCategoryMaxBalanceService";
    public static final String LM_LEAVE_REQUEST_DOC_SERVICE = "leaveRequestDocumentService";
	public static final String LM_ACCRUAL_CATEGORY_MAX_CARRY_OVER_SERVICE = "accrualCategoryMaxCarryOverService";
    public static final String LM_LEAVE_PAYOUT_SERVICE = "leavePayoutService";
	public static final String KPME_DISTRIBUTED_CACHE_MANAGER = "kpmeDistributedCacheManager";
    public static final String KPME_NOTIFICATION_SERVICE = "kpmeNotificationService";
	
    public static final String HR_GROUP_SERVICE = "hrGroupService";
    public static final String HR_PERMISSION_SERVICE = "hrPermissionService";
    public static final String HR_ROLE_SERVICE = "hrRoleService";
    
    public static final String TK_PERMISSION_SERVICE = "tkPermissionService";
    public static final String LM_PERMISSION_SERVICE = "lmPermissionService";
    public static final String TK_ROLE_SERVICE = "tkRoleService";
    public static final String LM_ROLE_SERVICE = "lmRoleService";
    
    public static MissedPunchService getMissedPunchService() {
        return (MissedPunchService) CONTEXT.getBean(TK_MISSED_PUNCH_SERVICE);
    }

    public static TaskService getTaskService() {
        return (TaskService) CONTEXT.getBean(TK_TASK_SERVICE);
    }

	public static SalGroupService getSalGroupService() {
		return (SalGroupService) CONTEXT.getBean(hr_sal_group_SERVICE);
	}

	public static DepartmentService getDepartmentService() {
		return (DepartmentService) CONTEXT.getBean(TK_DEPARTMENT_SERVICE);
	}

	public static ShiftDifferentialRuleService getShiftDifferentialRuleService() {
		return (ShiftDifferentialRuleService) CONTEXT.getBean(TK_SHIFT_DIFFERENTIAL_RULE_SERVICE);
	}

	public static WeeklyOvertimeRuleService getWeeklyOvertimeRuleService() {
		return (WeeklyOvertimeRuleService) CONTEXT.getBean(TK_WEEKLY_OVERTIME_RULE_SERVICE);
	}

	public static DailyOvertimeRuleService getDailyOvertimeRuleService() {
		return (DailyOvertimeRuleService) CONTEXT.getBean(TK_DAILY_OVERTIME_RULE_SERVICE);
	}
	
	public static TimesheetDocumentHeaderService getTimesheetDocumentHeaderService() {
		return (TimesheetDocumentHeaderService) CONTEXT.getBean(TK_TIMESHEET_DOCUMENT_HEADER_SERVICE);
	}

	public static TimesheetService getTimesheetService() {
		return (TimesheetService) CONTEXT.getBean(TK_TIMESHEET_SERVICE);
	}

	public static WorkAreaService getWorkAreaService() {
	    return (WorkAreaService)CONTEXT.getBean(TK_WORK_AREA_SERVICE);
	}

	public static ClockLogService getClockLogService(){
	    return (ClockLogService)CONTEXT.getBean(TK_CLOCK_LOG_SERVICE);
	}

    public static DistributedCacheManagerDecorator getDistributedCacheManager() {
        return (DistributedCacheManagerDecorator)CONTEXT.getBean(KPME_DISTRIBUTED_CACHE_MANAGER);
    }




	public static AssignmentService getAssignmentService(){
	    return (AssignmentService)CONTEXT.getBean(TK_ASSIGNMENT_SERVICE);
	}

	public static TimeBlockService getTimeBlockService(){
		return (TimeBlockService)CONTEXT.getBean(TK_TIME_BLOCK_SERVICE);
	}

	public static TimeBlockHistoryService getTimeBlockHistoryService(){
		return (TimeBlockHistoryService)CONTEXT.getBean(TK_TIME_BLOCK_HISTORY_SERVICE);
	}
	
	public static TimeBlockHistoryDetailService getTimeBlockHistoryDetailService(){
		return (TimeBlockHistoryDetailService)CONTEXT.getBean(TK_TIME_BLOCK_HISTORY_DETAIL_SERVICE);
	}

	public static AssignmentDao getAssignmentDao() {
	    return (AssignmentDao)CONTEXT.getBean(TK_ASSIGNMENT_DAO);
	}

	public static JobService getJobService() {
		return (JobService)CONTEXT.getBean(TK_JOB_SERVICE);
	}

	public static PayTypeService getPayTypeService() {
		return (PayTypeService)CONTEXT.getBean(TK_PAY_TYPE_SERVICE);
	}

	public static PersistenceBrokerTemplate getTkPersistenceBrokerTemplate() {
	    return (PersistenceBrokerTemplate) CONTEXT.getBean(TK_PERSISTENCE_BROKER_TEMPLATE);
	}

	public static TkRuleControllerService getTkRuleControllerService(){
		return (TkRuleControllerService) CONTEXT.getBean("tkRuleControllerService");
	}

	public static EarnCodeSecurityService getEarnCodeSecurityService() {
		return (EarnCodeSecurityService) CONTEXT.getBean(TK_EARN_CODE_SECURITY);
	}

	public static EarnCodeService getEarnCodeService() {
		return (EarnCodeService) CONTEXT.getBean(TK_EARN_CODE);
	}

	public static TimeCollectionRuleService getTimeCollectionRuleService() {
		return (TimeCollectionRuleService) CONTEXT.getBean(TK_TIME_COLLECTION_RULE_SERVICE);
	}

	public static TimeSummaryService getTimeSummaryService(){
		return (TimeSummaryService) CONTEXT.getBean(TK_TIME_SUMMARY_SERVICE);
	}

	public static EarnCodeGroupService getEarnCodeGroupService(){
		return (EarnCodeGroupService) CONTEXT.getBean(TK_TIME_EARN_CODE_GROUP_SERVICE);
	}

	public static TimeHourDetailService getTimeHourDetailService(){
		return (TimeHourDetailService) CONTEXT.getBean(TK_TIME_HOUR_DETAIL_SERVICE);
	}

	public static ClockLocationRuleService getClockLocationRuleService(){
		return (ClockLocationRuleService) CONTEXT.getBean(TK_CLOCK_LOCATION_RULE_SERVICE);
	}

	public static GracePeriodService getGracePeriodService(){
		return (GracePeriodService) CONTEXT.getBean(TK_GRACE_PERIOD_SERVICE);
	}

	public static SystemLunchRuleService getSystemLunchRuleService(){
		return (SystemLunchRuleService) CONTEXT.getBean(TK_SYSTEM_LUNCH_RULE_SERVICE);
	}

	public static DepartmentLunchRuleService getDepartmentLunchRuleService(){
		return (DepartmentLunchRuleService) CONTEXT.getBean(TK_DEPT_LUNCH_RULE_SERVICE);
	}

	public static UserPreferenceService getUserPreferenceService(){
		return (UserPreferenceService) CONTEXT.getBean(TK_USER_PREF_SERVICE);
	}

	public static TimezoneService getTimezoneService(){
		return (TimezoneService) CONTEXT.getBean(TK_TIME_ZONE_SERVICE);
	}

	public static AccrualCategoryService getAccrualCategoryService() {
	    return (AccrualCategoryService)CONTEXT.getBean(TK_ACCRUAL_CATEGORY_SERVICE);
	}
	
	public static AccrualService getAccrualService() {
	    return (AccrualService)CONTEXT.getBean(LM_ACCRUAL_SERVICE);
	}
	
	public static AccrualCategoryRuleService getAccrualCategoryRuleService() {
	    return (AccrualCategoryRuleService)CONTEXT.getBean(TK_ACCRUAL_CATEGORY_RULE_SERVICE);
	}
	
	public static LocationService getLocationService() {
	    return (LocationService)CONTEXT.getBean(TK_LOCATION_SERVICE);
	}

	public static PayGradeService getPayGradeService() {
	    return (PayGradeService)CONTEXT.getBean(TK_PAY_GRADE_SERVICE);
	}

	public static PersonService getPersonService(){
		return (PersonService)CONTEXT.getBean(TK_PERSON_SERVICE);
	}
	public static TimeApproveService getTimeApproveService(){
		return (TimeApproveService)CONTEXT.getBean(TK_APPROVE_SERVICE);
	}

	public static BatchJobService getBatchJobService(){
		return (BatchJobService)CONTEXT.getBean(TK_BATCH_JOB_SERVICE);
	}

	public static TkWarningService getWarningService(){
		return (TkWarningService) CONTEXT.getBean(TK_WARNINGS_SERVICE);
	}
	
	public static PositionService getPositionService(){
		return (PositionService) CONTEXT.getBean(HR_POSITION_SERVICE);
	}
	
	public static TkSearchableAttributeService getTkSearchableAttributeService(){
		return (TkSearchableAttributeService) CONTEXT.getBean(TK_SEARCH_ATTR_SERVICE);
	}

	public static PlatformTransactionManager getPlatformTransactionManager() {
		return (PlatformTransactionManager)CONTEXT.getBean("transactionManager");
	}

	public static TransactionTemplate getTransactionTemplate() {
		return new TransactionTemplate(getPlatformTransactionManager());
	}
	
	public static AccrualService getLeaveAccrualService(){
		return (AccrualService) CONTEXT.getBean(LM_ACCRUAL_SERVICE);
	}
	
	public static AccrualCategoryService getLeaveAccrualCategoryService(){
		return (AccrualCategoryService)CONTEXT.getBean(LM_ACCRUAL_CATEGORY_SERVICE);
	}
	
	public static LeavePlanService getLeavePlanService(){
		return (LeavePlanService)CONTEXT.getBean(LM_LEAVE_PLAN_SERVICE);
	}
	
	/*public static AccrualCategoryRuleService getLeaveAccrualCategoryRuleService(){
		return (AccrualCategoryRuleService)CONTEXT.getBean(LM_ACCRUAL_CATEGORY_RULE_SERVICE);
	}*/
	
	public static LeaveDonationService getLeaveDonationService(){
		return (LeaveDonationService)CONTEXT.getBean(LM_LEAVE_DONATION_SERVICE);
	}
	
	public static SystemScheduledTimeOffService getSysSchTimeOffService(){
		return (SystemScheduledTimeOffService)CONTEXT.getBean(LM_SYS_SCH_TIMEOFF_SERVICE);
	}

    public static LeaveBlockService getLeaveBlockService(){
		return (LeaveBlockService)CONTEXT.getBean(LM_LEAVE_BLOCK_SERVICE);
	}

    public static LeaveBlockHistoryService getLeaveBlockHistoryService(){
		return (LeaveBlockHistoryService)CONTEXT.getBean(LM_LEAVE_BLOCK_HISTORY_SERVICE);
	}
    
    public static LeaveCalendarService getLeaveCalendarService(){
		return (LeaveCalendarService)CONTEXT.getBean(LM_LEAVE_CALENDAR_SERVICE);
	}

    public static LeaveCalendarDocumentHeaderService getLeaveCalendarDocumentHeaderService(){
		return (LeaveCalendarDocumentHeaderService)CONTEXT.getBean(LM_LEAVE_CALENDAR_DOCUMENT_HEADER_SERVICE);
	}
    
    public static EmployeeOverrideService getEmployeeOverrideService(){
		return (EmployeeOverrideService)CONTEXT.getBean(LM_EMPLOYEE_OVERRIDE_SERVICE);
	}
    
    public static LeaveAdjustmentService getLeaveAdjustmentService(){
		return (LeaveAdjustmentService)CONTEXT.getBean(LM_LEAVE_ADJUSTMENT_SERVICE);
	}
    
    public static PrincipalHRAttributesService getPrincipalHRAttributeService(){
    	return (PrincipalHRAttributesService)CONTEXT.getBean(TK_PRINCIPAL_HR_ATTRIBUTES_SERVICE);
    }
    
	public static CalendarService getCalendarService() {
		return (CalendarService)CONTEXT.getBean(TK_PAY_CALENDAR_SERVICE);
	}
	
	public static CalendarEntryService getCalendarEntryService() {
		return (CalendarEntryService)CONTEXT.getBean(TK_PAY_CALENDAR_ENTRY_SERVICE);
	}
	
	public static PrincipalAccrualRanService getPrincipalAccrualRanService() {
		return (PrincipalAccrualRanService)CONTEXT.getBean(LM_PRINCIPAL_ACCRUAL_RAN_SERVICE);
	}
	public static LeaveSummaryService getLeaveSummaryService() {
		return (LeaveSummaryService)CONTEXT.getBean(LM_LEAVE_SUMMARY_SERVICE);
	}
	public static LeaveApprovalService getLeaveApprovalService() {
		return (LeaveApprovalService)CONTEXT.getBean(LM_LEAVE_APPROVAL_SERVICE);
	}
    public static BalanceTransferService getBalanceTransferService() {
    	return (BalanceTransferService) CONTEXT.getBean(LM_BALANCE_TRANSFER_SERVICE);
    }
    public static AccrualCategoryMaxBalanceService getAccrualCategoryMaxBalanceService() {
    	return (AccrualCategoryMaxBalanceService) CONTEXT.getBean(LM_ACCRUAL_CATEGORY_MAX_BALANCE_SERVICE);
    }
    public static LeavePayoutService getLeavePayoutService() {
        return (LeavePayoutService) CONTEXT.getBean(LM_LEAVE_PAYOUT_SERVICE);
    }
    public static KPMENotificationService getKPMENotificationService() {
    	return (KPMENotificationService) CONTEXT.getBean(KPME_NOTIFICATION_SERVICE);
    }
    
    public static HRGroupService getHRGroupService() {
    	return (HRGroupService) CONTEXT.getBean(HR_GROUP_SERVICE);
    }
    public static HRPermissionService getHRPermissionService() {
    	return (HRPermissionService) CONTEXT.getBean(HR_PERMISSION_SERVICE);
    }
    public static HRRoleService getHRRoleService() {
    	return (HRRoleService) CONTEXT.getBean(HR_ROLE_SERVICE);
    }
    
    public static TKPermissionService getTKPermissionService() {
    	return (TKPermissionService) CONTEXT.getBean(TK_PERMISSION_SERVICE);
    }
    public static LMPermissionService getLMPermissionService() {
    	return (LMPermissionService) CONTEXT.getBean(LM_PERMISSION_SERVICE);
    }
    public static TKRoleService getTKRoleService() {
    	return (TKRoleService) CONTEXT.getBean(TK_ROLE_SERVICE);
    }
    public static LMRoleService getLMRoleService() {
    	return (LMRoleService) CONTEXT.getBean(LM_ROLE_SERVICE);
    }
    
    public static LeaveRequestDocumentService getLeaveRequestDocumentService() {
        return (LeaveRequestDocumentService) CONTEXT.getBean(LM_LEAVE_REQUEST_DOC_SERVICE);
    }
    public static AccrualCategoryMaxCarryOverService getAccrualCategoryMaxCarryOverService() {
    	return (AccrualCategoryMaxCarryOverService) CONTEXT.getBean(LM_ACCRUAL_CATEGORY_MAX_CARRY_OVER_SERVICE);
    }
	
	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
	    CONTEXT = arg0;
	}

    public static Object getBean(String beanName) {
        return CONTEXT.getBean(beanName);
    }
	

    /**
     * This is for the approval only
     */
    public static JdbcTemplate getTkJdbcTemplate() {
		return (JdbcTemplate) CONTEXT.getBean("tkJdbcTemplate");
	}
    
    public static JdbcTemplate getRiceJdbcTemplate() {
		return (JdbcTemplate) CONTEXT.getBean("riceJdbcTemplate");
	}
}
