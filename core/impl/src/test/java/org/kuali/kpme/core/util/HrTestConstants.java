package org.kuali.kpme.core.util;

import org.kuali.rice.core.api.config.property.ConfigContext;

public class HrTestConstants {

	public static String BASE_URL = ConfigContext.getCurrentContextConfig().getProperty("application.url");
	public static String DOC_NEW_ELEMENT_ID_PREFIX = "document.newMaintainableObject.";
	public static String EFFECTIVE_DATE_ERROR = "'Effective Date' must be a future date that is NOT more than a year away from current date.";

	public static class Urls {

		public static final String DEPT_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.department.Department&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
	
		public static final String ASSIGNMENT_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.assignment.Assignment&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
		
		public static final String ASSIGNMENT_ACCOUNT_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.assignment.account.AssignmentAccount&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
	
		public static final String EARN_CODE_SECURITY_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.earncode.security.EarnCodeSecurity&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
		
		public static final String ACCRUAL_CATEGORY_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.accrualcategory.AccrualCategory&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
	
		public static final String SAL_GROUP_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.salarygroup.SalaryGroup&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
	
		public static final String PRIN_HR_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.principal.PrincipalHRAttributes&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
	
		public static final String EARN_CODE_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.earncode.EarnCode&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
	
		public static final String EARN_CODE_GROUP_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.earncode.group.EarnCodeGroup&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
	
		public static final String JOB_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.job.Job&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
	
		public static final String PAYTYPE_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.paytype.PayType&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
	
		public static final String WORK_AREA_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.workarea.WorkArea&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
	
		public static final String TASK_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.task.Task&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
		
		public static final String ASSIGNMENT_MAINT_NEW_URL = BASE_URL + "/kr/maintenance.do?businessObjectClassName=org.kuali.kpme.core.assignment.Assignment&methodToCall=start";
	
		public static final String WORK_AREA_MAINT_NEW_URL = BASE_URL + "/kr/maintenance.do?businessObjectClassName=org.kuali.kpme.core.workarea.WorkArea&methodToCall=start";
		
		public static final String ACCURAL_CATEGORY_MAINT_NEW_URL = BASE_URL + "/kr/maintenance.do?businessObjectClassName=org.kuali.kpme.core.accrualcategory.AccrualCategory&methodToCall=start";
		
		public static final String PRIN_HR_MAINT_NEW_URL = BASE_URL + "/kr/maintenance.do?businessObjectClassName=org.kuali.kpme.core.principal.PrincipalHRAttributes&methodToCall=start";
		
		public static final String LEAVE_PLAN_MAINT_NEW_URL = BASE_URL + "/kr/maintenance.do?businessObjectClassName=org.kuali.kpme.core.leaveplan.LeavePlan&methodToCall=start";
		
		public static final String LEAVE_PLAN_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.leaveplan.LeavePlan&returnLocation="+BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
		
		public static final String POSITION_MAINT_NEW_URL = BASE_URL + "/kr/maintenance.do?businessObjectClassName=org.kuali.kpme.core.position.Position&methodToCall=start";
		
		public static final String POSITION_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.position.Position&returnLocation="+BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
		
		public static final String PAY_GRADE_MAINT_NEW_URL = BASE_URL + "/kr-krad/maintenance.do?dataObjectClassName=org.kuali.kpme.core.paygrade.PayGrade&methodToCall=start";
		
		public static final String CALENDAR_MAINT_NEW_URL = BASE_URL + "/kr/maintenance.do?businessObjectClassName=org.kuali.kpme.core.calendar.Calendar&methodToCall=start";
		
		public static final String CALENDAR_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.calendar.Calendar&returnLocation="+BASE_URL+"/portal.do&hideReturnLink=true&docFormKey=88888888";
		
	    public static final String PORTAL_URL = BASE_URL + "/portal.do";
	}
	
	public static class FormElementTypes {
		public static final String DROPDOWN = "dropDown";
		public static final String CHECKBOX = "checkBox";
		public static final String TEXTAREA = "textArea";
	}
	
}
