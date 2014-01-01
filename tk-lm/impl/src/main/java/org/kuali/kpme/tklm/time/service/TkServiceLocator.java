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
package org.kuali.kpme.tklm.time.service;
//import org.kuali.hr.time.paytype.service.PayTypeService;
//import org.kuali.hr.time.permission.service.TKPermissionService;
import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.tklm.common.BatchJobService;
import org.kuali.kpme.tklm.time.approval.service.TimeApproveService;
import org.kuali.kpme.tklm.time.clocklog.service.ClockLogService;
import org.kuali.kpme.tklm.time.docsearch.TkSearchableAttributeService;
import org.kuali.kpme.tklm.time.missedpunch.service.MissedPunchService;
import org.kuali.kpme.tklm.time.rules.TkRuleControllerService;
import org.kuali.kpme.tklm.time.rules.clocklocation.service.ClockLocationRuleService;
import org.kuali.kpme.tklm.time.rules.graceperiod.service.GracePeriodService;
import org.kuali.kpme.tklm.time.rules.lunch.department.service.DepartmentLunchRuleService;
import org.kuali.kpme.tklm.time.rules.lunch.sys.service.SystemLunchRuleService;
import org.kuali.kpme.tklm.time.rules.overtime.daily.service.DailyOvertimeRuleService;
import org.kuali.kpme.tklm.time.rules.overtime.weekly.service.WeeklyOvertimeRuleService;
import org.kuali.kpme.tklm.time.rules.shiftdifferential.service.ShiftDifferentialRuleService;
import org.kuali.kpme.tklm.time.rules.timecollection.service.TimeCollectionRuleService;
import org.kuali.kpme.tklm.time.service.permission.TKPermissionService;
import org.kuali.kpme.tklm.time.timeblock.service.TimeBlockHistoryDetailService;
import org.kuali.kpme.tklm.time.timeblock.service.TimeBlockHistoryService;
import org.kuali.kpme.tklm.time.timeblock.service.TimeBlockService;
import org.kuali.kpme.tklm.time.timehourdetail.service.TimeHourDetailService;
import org.kuali.kpme.tklm.time.timesheet.service.TimesheetService;
import org.kuali.kpme.tklm.time.timesummary.service.TimeSummaryService;
import org.kuali.kpme.tklm.time.user.pref.service.UserPreferenceService;
import org.kuali.kpme.tklm.time.workflow.service.TimesheetDocumentHeaderService;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springmodules.orm.ojb.PersistenceBrokerTemplate;

public class TkServiceLocator implements ApplicationContextAware {
	public static String SPRING_BEANS = "classpath:org/kuali/kpme/tklm/config/TKLMSpringBeans.xml";
	private static ApplicationContext CONTEXT;
	public static final String HR_BATCH_JOB_SERVICE = "batchJobService";
	public static final String TK_CLOCK_LOG_SERVICE = "clockLogService";
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

    public static final String TK_PERMISSION_SERVICE = "tkPermissionService";
    public static final String TK_ROLE_SERVICE = "tkRoleService";
    
    public static MissedPunchService getMissedPunchService() {
        return (MissedPunchService) CONTEXT.getBean(TK_MISSED_PUNCH_SERVICE);
    }
    
	public static BatchJobService getBatchJobService(){
		return (BatchJobService)CONTEXT.getBean(HR_BATCH_JOB_SERVICE );
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

	public static TimeBlockService getTimeBlockService(){
		return (TimeBlockService)CONTEXT.getBean(TK_TIME_BLOCK_SERVICE);
	}

	public static TimeBlockHistoryService getTimeBlockHistoryService(){
		return (TimeBlockHistoryService)CONTEXT.getBean(TK_TIME_BLOCK_HISTORY_SERVICE);
	}
	
	public static TimeBlockHistoryDetailService getTimeBlockHistoryDetailService(){
		return (TimeBlockHistoryDetailService)CONTEXT.getBean(TK_TIME_BLOCK_HISTORY_DETAIL_SERVICE);
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

	public static TimeApproveService getTimeApproveService(){
		return (TimeApproveService)CONTEXT.getBean(TK_APPROVE_SERVICE);
	}

	public static TkSearchableAttributeService getTkSearchableAttributeService(){
		return (TkSearchableAttributeService) CONTEXT.getBean(TK_SEARCH_ATTR_SERVICE);
	}
    
    public static TKPermissionService getTKPermissionService() {
    	return (TKPermissionService) CONTEXT.getBean(TK_PERMISSION_SERVICE);
    }
	
	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
	    CONTEXT = arg0;
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

    /**
     * Lookups a service by name.
     *
     * @param serviceName name of the Interface class of the service you want
     * @param <T> the type of service you want.
     * @return the service
     * @throws IllegalArgumentException if the service name is blank.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getService(final String serviceName) {

        if (StringUtils.isBlank(serviceName)) {
            throw new IllegalArgumentException("the service name is blank.");
        }

        try {
            return (T) CONTEXT.getBean(serviceName);
        } catch (NoSuchBeanDefinitionException e) {
            // If we don't find this service locally, look for it in the Rice context
            return (T) GlobalResourceLoader.<T>getService(serviceName);
        } catch (Exception ex) {
            //ex.printStackTrace();
            return (T)GlobalResourceLoader.<T>getService(serviceName);
        }
    }

}
