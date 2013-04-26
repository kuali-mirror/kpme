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
package org.kuali.hr.tklm.time.service;

import org.kuali.hr.core.bo.assignment.dao.AssignmentDao;
import org.kuali.hr.core.service.group.HRGroupService;
import org.kuali.hr.core.service.notification.KPMENotificationService;
import org.kuali.hr.core.service.permission.HRPermissionService;
import org.kuali.hr.core.service.role.HRRoleService;
import org.kuali.hr.core.service.timezone.TimezoneService;
import org.kuali.hr.tklm.time.approval.service.TimeApproveService;
import org.kuali.hr.tklm.time.clocklog.service.ClockLogService;
import org.kuali.hr.tklm.time.docsearch.TkSearchableAttributeService;
import org.kuali.hr.tklm.time.missedpunch.service.MissedPunchService;
import org.kuali.hr.tklm.time.rules.TkRuleControllerService;
import org.kuali.hr.tklm.time.rules.clocklocation.service.ClockLocationRuleService;
import org.kuali.hr.tklm.time.rules.graceperiod.service.GracePeriodService;
import org.kuali.hr.tklm.time.rules.lunch.department.service.DepartmentLunchRuleService;
import org.kuali.hr.tklm.time.rules.lunch.sys.service.SystemLunchRuleService;
import org.kuali.hr.tklm.time.rules.overtime.daily.service.DailyOvertimeRuleService;
import org.kuali.hr.tklm.time.rules.overtime.weekly.service.WeeklyOvertimeRuleService;
import org.kuali.hr.tklm.time.rules.shiftdifferential.service.ShiftDifferentialRuleService;
import org.kuali.hr.tklm.time.rules.timecollection.service.TimeCollectionRuleService;
import org.kuali.hr.tklm.time.service.permission.TKPermissionService;
import org.kuali.hr.tklm.time.service.role.TKRoleService;
import org.kuali.hr.tklm.time.timeblock.service.TimeBlockHistoryDetailService;
import org.kuali.hr.tklm.time.timeblock.service.TimeBlockHistoryService;
import org.kuali.hr.tklm.time.timeblock.service.TimeBlockService;
import org.kuali.hr.tklm.time.timehourdetail.service.TimeHourDetailService;
import org.kuali.hr.tklm.time.timesheet.service.TimesheetService;
import org.kuali.hr.tklm.time.timesummary.service.TimeSummaryService;
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
	public static final String TK_ASSIGNMENT_DAO     = "assignmentDao";
	public static final String TK_TIME_BLOCK_SERVICE = "timeBlockService";
	public static final String TK_TIME_BLOCK_HISTORY_SERVICE = "timeBlockHistoryService";
	public static final String TK_TIME_BLOCK_HISTORY_DETAIL_SERVICE = "timeBlockHistoryDetailService";
	public static final String TK_PERSISTENCE_BROKER_TEMPLATE = "tkPersistenceBrokerTemplate";
	public static final String TK_CACHE_MANAGER_SERVICE = "cacheManager";
	public static final String TK_TIMESHEET_SERVICE = "timesheetService";
	public static final String TK_TIMESHEET_DOCUMENT_HEADER_SERVICE = "timesheetDocumentHeaderService";
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
	public static final String TK_TIME_OFF_ACCRUAL_SERVICE = "timeOffAccrualService";
    public static final String TK_APPROVE_SERVICE = "timeApproveService";
    public static final String TK_MISSED_PUNCH_SERVICE = "missedPunchService";
    public static final String TK_WARNINGS_SERVICE = "tkWarningService";
    public static final String TK_SEARCH_ATTR_SERVICE = "tkSearchableAttributeService";

    //Move to [Sys|Hr]ServiceLocator
	public static final String KPME_DISTRIBUTED_CACHE_MANAGER = "kpmeDistributedCacheManager";
    public static final String KPME_NOTIFICATION_SERVICE = "kpmeNotificationService";    
    
    //Move to HrServiceLocator
    public static final String HR_GROUP_SERVICE = "hrGroupService";
    public static final String HR_PERMISSION_SERVICE = "hrPermissionService";
    public static final String HR_ROLE_SERVICE = "hrRoleService";

	public static final String TK_TIME_ZONE_SERVICE = "timezoneService";

    
    public static final String TK_PERMISSION_SERVICE = "tkPermissionService";
    public static final String TK_ROLE_SERVICE = "tkRoleService";
    
    public static MissedPunchService getMissedPunchService() {
        return (MissedPunchService) CONTEXT.getBean(TK_MISSED_PUNCH_SERVICE);
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

	public static ClockLogService getClockLogService(){
	    return (ClockLogService)CONTEXT.getBean(TK_CLOCK_LOG_SERVICE);
	}

    public static DistributedCacheManagerDecorator getDistributedCacheManager() {
        return (DistributedCacheManagerDecorator)CONTEXT.getBean(KPME_DISTRIBUTED_CACHE_MANAGER);
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

	public static PersistenceBrokerTemplate getTkPersistenceBrokerTemplate() {
	    return (PersistenceBrokerTemplate) CONTEXT.getBean(TK_PERSISTENCE_BROKER_TEMPLATE);
	}

	public static TkRuleControllerService getTkRuleControllerService(){
		return (TkRuleControllerService) CONTEXT.getBean("tkRuleControllerService");
	}

	public static TimeCollectionRuleService getTimeCollectionRuleService() {
		return (TimeCollectionRuleService) CONTEXT.getBean(TK_TIME_COLLECTION_RULE_SERVICE);
	}

	public static TimeSummaryService getTimeSummaryService(){
		return (TimeSummaryService) CONTEXT.getBean(TK_TIME_SUMMARY_SERVICE);
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

	public static TimeApproveService getTimeApproveService(){
		return (TimeApproveService)CONTEXT.getBean(TK_APPROVE_SERVICE);
	}

	public static TkWarningService getWarningService(){
		return (TkWarningService) CONTEXT.getBean(TK_WARNINGS_SERVICE);
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

    public static TKRoleService getTKRoleService() {
    	return (TKRoleService) CONTEXT.getBean(TK_ROLE_SERVICE);
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
