package org.kuali.hr.time.test;


public final class TkTestConstants {
	public static String BASE_URL = HtmlUnitUtil.getBaseURL();

	public static class Urls {
		public static final String DEPT_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.department.Department&returnLocation="+
																BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
		public static final String ASSIGNMENT_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.assignment.Assignment&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
		
		public static final String ASSIGNMENT_ACCOUNT_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.assignment.AssignmentAccount&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
		
		public static final String TIME_COLLECTION_RULE_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.TimeCollectionRule&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
		
		public static final String WORK_SCHEDULE_ENTRY_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.workschedule.WorkScheduleEntry&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
		
		public static final String WORK_SCHEDULE_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.workschedule.WorkSchedule&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
		
		
	}
	
}
