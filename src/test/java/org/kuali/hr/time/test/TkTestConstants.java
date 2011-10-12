package org.kuali.hr.time.test;


public final class TkTestConstants {
	public static String BASE_URL = HtmlUnitUtil.getBaseURL();
	public static String DOC_NEW_ELEMENT_ID_PREFIX = "document.newMaintainableObject.";

	public static class Urls {
		public static final String DEPT_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.department.Department&returnLocation="+
																BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
		public static final String ASSIGNMENT_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.assignment.Assignment&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
		
		public static final String ASSIGNMENT_ACCOUNT_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.assignment.AssignmentAccount&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
		
		public static final String TIME_COLLECTION_RULE_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.collection.rule.TimeCollectionRule&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
		
		public static final String WORK_SCHEDULE_ENTRY_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.workschedule.WorkScheduleEntry&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
		
		public static final String WORK_SCHEDULE_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.workschedule.WorkSchedule&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
		
		public static final String SHIFT_DIFFERENTIAL_RULE_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.shiftdiff.rule.ShiftDifferentialRule&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";

		public static final String WEEKLY_OVERTIME_RULE_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.overtime.weekly.rule.WeeklyOvertimeRule&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
		
		public static final String DEPARTMENT_EARN_CODE_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.dept.earncode.DepartmentEarnCode&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
		
		public static final String DAILY_OVERTIME_RULE_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.overtime.daily.rule.DailyOvertimeRule&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
		
		public static final String CLOCK_LOG_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.clocklog.ClockLog&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
		
		public static final String DEPT_LUNCH_RULE_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.dept.lunch.DeptLunchRule&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
		
		public static final String ACCURAL_CATEGORY_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.accrual.AccrualCategory&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
		
		public static final String SAL_GROUP_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.salgroup.SalGroup&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
		
		public static final String PRIN_CAL_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.principal.calendar.PrincipalCalendar&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
		
		public static final String DOC_HEADER_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.workflow.TimesheetDocumentHeader&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
		
		public static final String EARN_CODE_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.earncode.EarnCode&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
		
		public static final String EARN_GROUP_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.earngroup.EarnGroup&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
		
		public static final String JOB_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.job.Job&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
		
		public static final String PAYTYPE_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.paytype.PayType&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
		
		public static final String HOLIDAY_CALENDAR_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.holidaycalendar.HolidayCalendar&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
		
		public static final String TIME_OFF_ACCRUAL_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.accrual.TimeOffAccrual&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
		
		public static final String WORK_AREA_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.workarea.WorkArea&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
		
		public static final String TASK_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.task.Task&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
		
		public static final String PAY_CALENDAR_ENTRIES_MAINT_NEW_URL = BASE_URL + "/kr/maintenance.do?businessObjectClassName=org.kuali.hr.time.paycalendar.PayCalendarEntries&methodToCall=start";
		
		public static final String ASSIGNMENT_MAINT_NEW_URL = BASE_URL + "/kr/maintenance.do?businessObjectClassName=org.kuali.hr.time.assignment.Assignment&methodToCall=start";
		
		public static final String HOLIDAY_CALENDAR_MAINT_NEW_URL = BASE_URL + "/kr/maintenance.do?businessObjectClassName=org.kuali.hr.time.holidaycalendar.HolidayCalendar&methodToCall=start";
		
		public static final String WORK_AREA_MAINT_NEW_URL = BASE_URL + "/kr/maintenance.do?businessObjectClassName=org.kuali.hr.time.workarea.WorkArea&methodToCall=start";
		
		public static final String WEEKLY_OVERTIME_RULE_MAINT_NEW_URL = BASE_URL + "/kr/maintenance.do?businessObjectClassName=org.kuali.hr.time.overtime.weekly.rule.WeeklyOvertimeRuleGroup&tkWeeklyOvertimeRuleGroupId=1&returnLocation=" +
		BASE_URL + "/Admin.do&methodToCall=edit";
		
		public static final String LEAVE_PLAN_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.lm.leaveplan.LeavePlan&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
		
		public static final String CLOCK_URL = BASE_URL + "/Clock.do";
		
		public static final String TIME_DETAIL_URL = BASE_URL + "/TimeDetail.do";
		
		public static final String PERSON_INFO_URL = BASE_URL + "/PersonInfo.do";
	}
	
	public static class FormElementTypes {
		public static final String DROPDOWN = "dropDown";
		public static final String CHECKBOX = "checkBox";
		public static final String TEXTAREA = "textArea";
	}
	
}
