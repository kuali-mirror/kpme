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
package org.kuali.kpme.core.util;

import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.krad.uif.UifConstants.ConfigProperties;

public class HrTestConstants {

	public static String BASE_URL = ConfigContext.getCurrentContextConfig().getProperty("application.url");
	public static String DOC_NEW_ELEMENT_ID_PREFIX = "document.newMaintainableObject.";
	public static String EFFECTIVE_DATE_ERROR = "'Effective Date' must be a future date that is NOT more than a year away from current date.";

	public static class Urls {
		
		
	    public static final String PORTAL_URL = BASE_URL + "/portal.do";
		
		/**
		 * 
		 * 
		 * maintenance document lookup view links
		 * 
		 * 
		 */

		public static final String DEPT_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.department.DepartmentBo&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
	
		public static final String ASSIGNMENT_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.assignment.AssignmentBo&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
		
		public static final String ASSIGNMENT_ACCOUNT_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.assignment.account.AssignmentAccountBo&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
	
		public static final String EARN_CODE_SECURITY_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.earncode.security.EarnCodeSecurityBo&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
		
		public static final String ACCRUAL_CATEGORY_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.accrualcategory.AccrualCategoryBo&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
	
		public static final String SAL_GROUP_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.salarygroup.SalaryGroupBo&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
	
		public static final String PRIN_HR_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.principal.PrincipalHRAttributesBo&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
	
		public static final String EARN_CODE_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.earncode.EarnCodeBo&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
	
		public static final String EARN_CODE_GROUP_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.earncode.group.EarnCodeGroupBo&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
	
		public static final String JOB_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.job.JobBo&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
	
		public static final String PAYTYPE_MAINT_URL = BASE_URL + "/kr-krad/lookup?methodToCall=start&dataObjectClassName=org.kuali.kpme.core.paytype.PayTypeBo&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
	
		public static final String WORK_AREA_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.workarea.WorkArea&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
	
		public static final String TASK_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.task.TaskBo&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
		
		public static final String INSTITUTION_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.institution.InstitutionBo&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
		
		public static final String ACCOUNT_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.kfs.coa.businessobject.Account&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
		
		public static final String CHART_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.kfs.coa.businessobject.Chart&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
		
		public static final String OBJECT_CODE_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.kfs.coa.businessobject.ObjectCode&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";

		public static final String ORGANIZATION_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.kfs.coa.businessobject.Organization&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";

		public static final String PROJECT_CODE_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.kfs.coa.businessobject.ProjectCode&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";

		public static final String SUB_OBJECT_CODE_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.kfs.coa.businessobject.SubObjectCode&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";

		public static final String LEAVE_PLAN_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.leaveplan.LeavePlanBo&returnLocation="+BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";

		public static final String POSITION_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.position.Position&returnLocation="+BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
		
		public static final String CALENDAR_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.calendar.CalendarBo&returnLocation="+BASE_URL+"/portal.do&hideReturnLink=true&docFormKey=88888888";

		public static final String SUB_ACCOUNT_MAINT_URL = BASE_URL + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.kfs.coa.businessobject.SubAccount&returnLocation="+
				BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";

		/**
		 * 
		 * 
		 * "create new" urls for maintenance documents
		 * 
		 * 
		 */
		
		public static final String INSTITUTION_MAINT_NEW_URL = BASE_URL + "/kr/maintenance.do?businessObjectClassName=org.kuali.kpme.core.institution.InstitutionBo&methodToCall=start";

		public static final String PAY_STEP_MAINT_NEW_URL = BASE_URL + "/kr/maintenance.do?businessObjectClassName=org.kuali.kpme.core.paystep.PayStepBo&methodToCall=start";
		
		public static final String ASSIGNMENT_MAINT_NEW_URL = BASE_URL + "/kr/maintenance.do?businessObjectClassName=org.kuali.kpme.core.assignment.AssignmentBo&methodToCall=start";
	
		public static final String WORK_AREA_MAINT_NEW_URL = BASE_URL + "/kr/maintenance.do?businessObjectClassName=org.kuali.kpme.core.workarea.WorkAreaBo&methodToCall=start";
		
		public static final String ACCURAL_CATEGORY_MAINT_NEW_URL = BASE_URL + "/kr/maintenance.do?businessObjectClassName=org.kuali.kpme.core.accrualcategory.AccrualCategoryBo&methodToCall=start";
		
		public static final String PRIN_HR_MAINT_NEW_URL = BASE_URL + "/kr/maintenance.do?businessObjectClassName=org.kuali.kpme.core.principal.PrincipalHRAttributesBo&methodToCall=start";
		
		public static final String LEAVE_PLAN_MAINT_NEW_URL = BASE_URL + "/kr/maintenance.do?businessObjectClassName=org.kuali.kpme.core.leaveplan.LeavePlanBo&methodToCall=start";
		
		public static final String POSITION_MAINT_NEW_URL = BASE_URL + "/kr/maintenance.do?businessObjectClassName=org.kuali.kpme.core.position.Position&methodToCall=start";
		
		public static final String PAY_GRADE_MAINT_NEW_URL = BASE_URL + "/kr-krad/maintenance.do?dataObjectClassName=org.kuali.kpme.core.paygrade.PayGradeBo&methodToCall=start";
		
		public static final String CALENDAR_MAINT_NEW_URL = BASE_URL + "/kr/maintenance.do?businessObjectClassName=org.kuali.kpme.core.calendar.CalendarBo&methodToCall=start";
		
		public static final String PAY_CALENDAR_ENTRY_MAINT_NEW_URL = BASE_URL + "/kr/maintenance.do?businessObjectClassName=org.kuali.kpme.core.calendar.CalendarEntryBo&methodToCall=start";
		
		public static final String ACCOUNT_MAINT_NEW_URL = BASE_URL + "/kr/maintenance.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.kfs.coa.businessobject.Account&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
		
		public static final String CHART_MAINT_NEW_URL = BASE_URL + "/kr/maintenance.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.kfs.coa.businessobject.Chart&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";
		
		public static final String OBJECT_CODE_MAINT_NEW_URL = BASE_URL + "/kr/maintenance.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.kfs.coa.businessobject.ObjectCode&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";

		public static final String ORGANIZATION_MAINT_NEW_URL = BASE_URL + "/kr/maintenance.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.kfs.coa.businessobject.Organization&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";

		public static final String PROJECT_CODE_MAINT_NEW_URL = BASE_URL + "/kr/maintenance.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.kfs.coa.businessobject.ProjectCode&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";

		public static final String SUB_OBJECT_CODE_MAINT_NEW_URL = BASE_URL + "/kr/maintenance.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.kfs.coa.businessobject.SubObjectCode&returnLocation="+
		BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";

		public static final String SUB_ACCOUNT_MAINT_NEW_URL = BASE_URL + "/kr/maintenance.do?methodToCall=start&businessObjectClassName=org.kuali.kpme.core.kfs.coa.businessobject.SubAccount&returnLocation="+
				BASE_URL + "/portal.do&hideReturnLink=true&docFormKey=88888888";

		public static final String MISSED_PUNCH_LOOKUP_URL = ConfigContext.getCurrentContextConfig().getProperty("kew.url") + "/DocumentSearch.do?documentTypeName=MissedPunchDocumentType";

	}
	
	public static class FormElementTypes {
		public static final String DROPDOWN = "dropDown";
		public static final String CHECKBOX = "checkBox";
		public static final String TEXTAREA = "textArea";
	}
	
}
