package org.kuali.hr.time.service.base;

import org.kuali.hr.job.service.JobService;
import org.kuali.hr.location.service.LocationService;
import org.kuali.hr.paygrade.service.PayGradeService;
import org.kuali.hr.time.accrual.service.AccrualCategoryService;
import org.kuali.hr.time.accrual.service.TimeOffAccrualService;
import org.kuali.hr.time.assignment.dao.AssignmentDao;
import org.kuali.hr.time.assignment.service.AssignmentService;
import org.kuali.hr.time.cache.CacheManagementService;
import org.kuali.hr.time.clock.location.service.ClockLocationRuleService;
import org.kuali.hr.time.clocklog.service.ClockLogService;
import org.kuali.hr.time.department.service.DepartmentService;
import org.kuali.hr.time.dept.earncode.service.DepartmentEarnCodeService;
import org.kuali.hr.time.dept.lunch.service.DepartmentLunchRuleService;
import org.kuali.hr.time.earncode.service.EarnCodeService;
import org.kuali.hr.time.earngroup.service.EarnGroupService;
import org.kuali.hr.time.graceperiod.service.GracePeriodService;
import org.kuali.hr.time.holidaycalendar.service.HolidayCalendarService;
import org.kuali.hr.time.overtime.daily.rule.service.DailyOvertimeRuleService;
import org.kuali.hr.time.overtime.weekly.rule.service.WeeklyOvertimeRuleService;
import org.kuali.hr.time.paycalendar.service.PayCalendarDatesService;
import org.kuali.hr.time.paycalendar.service.PayCalendarService;
import org.kuali.hr.time.paytype.service.PayTypeService;
import org.kuali.hr.time.principal.calendar.service.PrincipalCalendarService;
import org.kuali.hr.time.roles.service.TkRoleService;
import org.kuali.hr.time.rule.TkRuleControllerService;
import org.kuali.hr.time.salgroup.service.SalGroupService;
import org.kuali.hr.time.shiftdiff.rule.service.ShiftDifferentialRuleService;
import org.kuali.hr.time.syslunch.service.SystemLunchRuleService;
import org.kuali.hr.time.task.service.TaskService;
import org.kuali.hr.time.timeblock.service.TimeBlockHistoryService;
import org.kuali.hr.time.timeblock.service.TimeBlockService;
import org.kuali.hr.time.timecollection.rule.service.TimeCollectionRuleService;
import org.kuali.hr.time.timehourdetail.service.TimeHourDetailService;
import org.kuali.hr.time.timesheet.service.TimesheetService;
import org.kuali.hr.time.timesummary.service.TimeSummaryService;
import org.kuali.hr.time.timezone.service.TimezoneService;
import org.kuali.hr.time.user.pref.service.UserPreferenceService;
import org.kuali.hr.time.workarea.service.WorkAreaService;
import org.kuali.hr.time.workflow.service.TimesheetDocumentHeaderService;
import org.kuali.hr.time.workschedule.service.WorkScheduleService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springmodules.orm.ojb.PersistenceBrokerTemplate;

public class TkServiceLocator implements ApplicationContextAware {
	public static String SPRING_BEANS = "classpath:SpringBeans.xml";
	public static ApplicationContext CONTEXT;
	public static final String TK_CLOCK_LOG_SERVICE = "clockLogService";
	public static final String TK_ASSIGNMENT_SERVICE = "assignmentService";
	public static final String TK_ASSIGNMENT_DAO     = "assignmentDao";
	public static final String TK_TIME_BLOCK_SERVICE = "timeBlockService";
	public static final String TK_TIME_BLOCK_HISTORY_SERVICE = "timeBlockHistoryService";
	public static final String TK_JOB_SERVICE = "jobService";
	public static final String TK_PAY_TYPE_SERVICE = "payTypeService";
	public static final String TK_PAY_CALENDAR_SERVICE = "payCalendarService";
	public static final String TK_PAY_CALENDAR_DATES_SERVICE = "payCalendarDatesService";
	public static final String TK_PERSISTENCE_BROKER_TEMPLATE = "tkPersistenceBrokerTemplate";
	public static final String TK_CACHE_MANAGER_SERVICE = "cacheManager";
	public static final String TK_WORK_AREA_SERVICE = "workAreaService";
	public static final String TK_TIMESHEET_SERVICE = "timesheetService";
	public static final String TK_TIMESHEET_DOCUMENT_HEADER_SERVICE = "timesheetDocumentHeaderService";
	public static final String TK_DEPARTMENT_SERVICE = "departmentService";
	public static final String TK_DEPARTMENT_EARN_CODE = "deptEarnCodeService";
	public static final String TK_EARN_CODE = "earnCodeService";
	public static final String TK_TIME_COLLECTION_RULE_SERVICE = "timeCollectionRuleService";
	public static final String TK_ROLE_SERVICE = "tkRoleService";
	public static final String TK_TIME_SUMMARY_SERVICE = "timeSummaryService";
	public static final String TK_TIME_EARN_GROUP_SERVICE = "earnGroupService";
	public static final String TK_TIME_HOUR_DETAIL_SERVICE= "timeHourDetailService";
	public static final String TK_DAILY_OVERTIME_RULE_SERVICE = "dailyOvertimeRuleService";
	public static final String TK_WEEKLY_OVERTIME_RULE_SERVICE = "weeklyOvertimeRuleService";
	public static final String TK_SHIFT_DIFFERENTIAL_RULE_SERVICE = "shiftDifferentialRuleService";
	public static final String TK_WORK_SCHEDULE_SERVICE = "workScheduleService";
	public static final String TK_CLOCK_LOCATION_RULE_SERVICE = "clockLocationService";
	public static final String TK_GRACE_PERIOD_SERVICE = "gracePeriodService";
	public static final String TK_SYSTEM_LUNCH_RULE_SERVICE = "systemLunchRuleService";
	public static final String TK_DEPT_LUNCH_RULE_SERVICE = "deptLunchRuleService";
	public static final String TK_PRINCIPAL_CALENDAR_SERVICE = "principalCalendarService";
	public static final String TK_HOLIDAY_CALENDAR_SERVICE = "holidayCalendarService";
	public static final String TK_USER_PREF_SERVICE = "userPrefService";
	public static final String TK_TIME_ZONE_SERVICE = "timezoneService";
	public static final String TK_TIME_OFF_ACCRUAL_SERVICE = "timeOffAccrualService";
	public static final String TK_SAL_GROUP_SERVICE = "salGroupService";
	public static final String TK_ACCRUAL_CATEGORY_SERVICE = "accrualCategoryService";
    public static final String TK_TASK_SERVICE = "taskService";
    public static final String TK_LOCATION_SERVICE = "locationService";
    public static final String TK_PAY_GRADE_SERVICE = "payGradeService";

	public static void start() throws Exception {
//		CONTEXT = new ClassPathXmlApplicationContext(SPRING_BEANS);
//		CONTEXT.start();
	}

	public static void stop() throws Exception {
//		CONTEXT.stop();
	}

    public static TaskService getTaskService() {
        return (TaskService) CONTEXT.getBean(TK_TASK_SERVICE);
    }

	public static SalGroupService getSalGroupService() {
		return (SalGroupService) CONTEXT.getBean(TK_SAL_GROUP_SERVICE);
	}

	public static DepartmentService getDepartmentService() {
		return (DepartmentService) CONTEXT.getBean(TK_DEPARTMENT_SERVICE);
	}

	public static ShiftDifferentialRuleService getShiftDifferentialRuleService() {
		return (ShiftDifferentialRuleService) CONTEXT.getBean(TK_SHIFT_DIFFERENTIAL_RULE_SERVICE);
	}

	public static WorkScheduleService getWorkScheduleService() {
		return (WorkScheduleService) CONTEXT.getBean(TK_WORK_SCHEDULE_SERVICE);
	}

	public static WeeklyOvertimeRuleService getWeeklyOvertimeRuleService() {
		return (WeeklyOvertimeRuleService) CONTEXT.getBean(TK_WEEKLY_OVERTIME_RULE_SERVICE);
	}

	public static DailyOvertimeRuleService getDailyOvertimeRuleService() {
		return (DailyOvertimeRuleService) CONTEXT.getBean(TK_DAILY_OVERTIME_RULE_SERVICE);
	}

	public static TkRoleService getTkRoleService() {
		return (TkRoleService) CONTEXT.getBean(TK_ROLE_SERVICE);
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

	public static AssignmentService getAssignmentService(){
	    return (AssignmentService)CONTEXT.getBean(TK_ASSIGNMENT_SERVICE);
	}

	public static TimeBlockService getTimeBlockService(){
		return (TimeBlockService)CONTEXT.getBean(TK_TIME_BLOCK_SERVICE);
	}

	public static TimeBlockHistoryService getTimeBlockHistoryService(){
		return (TimeBlockHistoryService)CONTEXT.getBean(TK_TIME_BLOCK_HISTORY_SERVICE);
	}

	public static AssignmentDao getAssignmentDao() {
	    return (AssignmentDao)CONTEXT.getBean(TK_ASSIGNMENT_DAO);
	}

	public static JobService getJobSerivce() {
		return (JobService)CONTEXT.getBean(TK_JOB_SERVICE);
	}

	public static PayTypeService getPayTypeSerivce() {
		return (PayTypeService)CONTEXT.getBean(TK_PAY_TYPE_SERVICE);
	}

	public static PayCalendarService getPayCalendarSerivce() {
		return (PayCalendarService)CONTEXT.getBean(TK_PAY_CALENDAR_SERVICE);
	}

	public static PayCalendarDatesService getPayCalendarDatesSerivce() {
		return (PayCalendarDatesService)CONTEXT.getBean(TK_PAY_CALENDAR_DATES_SERVICE);
	}

	public static PersistenceBrokerTemplate getTkPersistenceBrokerTemplate() {
	    return (PersistenceBrokerTemplate) CONTEXT.getBean(TK_PERSISTENCE_BROKER_TEMPLATE);
	}

	public static TkRuleControllerService getTkRuleControllerService(){
		return (TkRuleControllerService) CONTEXT.getBean("tkRuleControllerService");
	}

	public static CacheManagementService getCacheManagerService(){
		return (CacheManagementService) CONTEXT.getBean(TK_CACHE_MANAGER_SERVICE);
	}

	public static DepartmentEarnCodeService getDepartmentEarnCodeService() {
		return (DepartmentEarnCodeService) CONTEXT.getBean(TK_DEPARTMENT_EARN_CODE);
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

	public static EarnGroupService getEarnGroupService(){
		return (EarnGroupService) CONTEXT.getBean(TK_TIME_EARN_GROUP_SERVICE);
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

	public static PrincipalCalendarService getPrincipalCalendarService(){
		return (PrincipalCalendarService) CONTEXT.getBean(TK_PRINCIPAL_CALENDAR_SERVICE);
	}

	public static HolidayCalendarService getHolidayCalendarService(){
		return (HolidayCalendarService) CONTEXT.getBean(TK_HOLIDAY_CALENDAR_SERVICE);
	}

	public static UserPreferenceService getUserPreferenceService(){
		return (UserPreferenceService) CONTEXT.getBean(TK_USER_PREF_SERVICE);
	}

	public static TimezoneService getTimezoneService(){
		return (TimezoneService) CONTEXT.getBean(TK_TIME_ZONE_SERVICE);
	}

	public static TimeOffAccrualService getTimeOffAccrualService(){
		return (TimeOffAccrualService) CONTEXT.getBean(TK_TIME_OFF_ACCRUAL_SERVICE);
	}

	public static AccrualCategoryService getAccrualCategoryService() {
	    return (AccrualCategoryService)CONTEXT.getBean(TK_ACCRUAL_CATEGORY_SERVICE);
	}
	
	public static LocationService getLocationService() {
	    return (LocationService)CONTEXT.getBean(TK_LOCATION_SERVICE);
	}
	
	public static PayGradeService getPayGradeService() {
	    return (PayGradeService)CONTEXT.getBean(TK_PAY_GRADE_SERVICE);
	}
	
	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
	    CONTEXT = arg0;
	}
}
