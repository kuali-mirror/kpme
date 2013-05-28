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
package org.kuali.kpme.core.service;

import org.kuali.kpme.core.accrualcategory.rule.service.AccrualCategoryRuleService;
import org.kuali.kpme.core.accrualcategory.service.AccrualCategoryService;
import org.kuali.kpme.core.assignment.service.AssignmentService;
import org.kuali.kpme.core.calendar.entry.service.CalendarEntryService;
import org.kuali.kpme.core.calendar.service.CalendarService;
import org.kuali.kpme.core.department.service.DepartmentService;
import org.kuali.kpme.core.earncode.group.service.EarnCodeGroupService;
import org.kuali.kpme.core.earncode.security.service.EarnCodeSecurityService;
import org.kuali.kpme.core.earncode.service.EarnCodeService;
import org.kuali.kpme.core.institution.service.InstitutionService;
import org.kuali.kpme.core.job.service.JobService;
import org.kuali.kpme.core.leaveplan.service.LeavePlanService;
import org.kuali.kpme.core.location.service.LocationService;
import org.kuali.kpme.core.paygrade.service.PayGradeService;
import org.kuali.kpme.core.paystep.service.PayStepService;
import org.kuali.kpme.core.paytype.service.PayTypeService;
import org.kuali.kpme.core.position.service.PositionService;
import org.kuali.kpme.core.principal.service.PrincipalHRAttributesService;
import org.kuali.kpme.core.salarygroup.service.SalaryGroupService;
import org.kuali.kpme.core.task.service.TaskService;
import org.kuali.kpme.core.workarea.service.WorkAreaService;
import org.kuali.kpme.core.service.group.HRGroupService;
import org.kuali.kpme.core.service.notification.KPMENotificationService;
import org.kuali.kpme.core.service.permission.HRPermissionService;
import org.kuali.kpme.core.service.role.HRRoleService;
import org.kuali.kpme.core.service.timezone.TimezoneService;
import org.kuali.rice.core.impl.cache.DistributedCacheManagerDecorator;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class HrServiceLocator implements ApplicationContextAware {

	public static String SPRING_BEANS = "classpath:org/kuai/kpme/core/config/CORESpringBeans.xml";
	public static ApplicationContext CONTEXT;
	
	public static final String HR_PAY_CALENDAR_SERVICE = "calendarService";
	public static final String HR_PAY_CALENDAR_ENTRY_SERVICE = "calendarEntryService";
	public static final String HR_SALARY_GROUP_SERVICE = "salaryGroupService";
	public static final String HR_ACCRUAL_CATEGORY_SERVICE = "accrualCategoryService";
	public static final String HR_ACCRUAL_CATEGORY_RULE_SERVICE = "accrualCategoryRuleService";
    public static final String HR_TASK_SERVICE = "taskService";
    public static final String HR_LOCATION_SERVICE = "locationService";
    public static final String HR_PAY_GRADE_SERVICE = "payGradeService";
    public static final String HR_PERSON_SERVICE = "tkPersonService";
    public static final String HR_BATCH_JOB_SERVICE = "batchJobService";
    public static final String HR_BATCH_JOB_ENTRY_SERVICE = "batchJobEntryService";
    public static final String HR_POSITION_SERVICE = "positionService";
    public static final String HR_LEAVE_PLAN_SERVICE = "leavePlanService";
	public static final String HR_PRINCIPAL_HR_ATTRIBUTES_SERVICE = "principalHRAttributesService";
	public static final String HR_ASSIGNMENT_SERVICE = "assignmentService";
	public static final String HR_JOB_SERVICE = "jobService";
	public static final String HR_PAY_TYPE_SERVICE = "payTypeService";
	public static final String HR_WORK_AREA_SERVICE = "workAreaService";
	public static final String HR_DEPARTMENT_SERVICE = "departmentService";
	public static final String HR_EARN_CODE = "earnCodeService";
	public static final String HR_EARN_CODE_SECURITY = "earnCodeSecurityService";
	public static final String HR_TIME_EARN_CODE_GROUP_SERVICE = "earnCodeGroupService";
	public static final String HR_INSTITUTION_SERVICE = "institutionService";
	public static final String HR_PAY_STEP_SERVICE = "payStepService";
	public static final String TK_TIME_ZONE_SERVICE = "timezoneService";
	
    public static final String HR_GROUP_SERVICE = "hrGroupService";
    public static final String HR_PERMISSION_SERVICE = "hrPermissionService";
    public static final String HR_ROLE_SERVICE = "hrRoleService";
    
    //Move to [Sys|Hr]ServiceLocator
	public static final String KPME_DISTRIBUTED_CACHE_MANAGER = "kpmeDistributedCacheManager";
    public static final String KPME_NOTIFICATION_SERVICE = "kpmeNotificationService";    
    
	public static PayStepService getPayStepService() {
		return (PayStepService) CONTEXT.getBean(HR_PAY_STEP_SERVICE);
	}
	
    public static InstitutionService getInstitutionService() {
    	return (InstitutionService) CONTEXT.getBean(HR_INSTITUTION_SERVICE);
    }
    
    public static TaskService getTaskService() {
        return (TaskService) CONTEXT.getBean(HR_TASK_SERVICE);
    }

	public static SalaryGroupService getSalaryGroupService() {
		return (SalaryGroupService) CONTEXT.getBean(HR_SALARY_GROUP_SERVICE);
	}

	public static DepartmentService getDepartmentService() {
		return (DepartmentService) CONTEXT.getBean(HR_DEPARTMENT_SERVICE);
	}

	public static WorkAreaService getWorkAreaService() {
	    return (WorkAreaService)CONTEXT.getBean(HR_WORK_AREA_SERVICE);
	}
	public static AssignmentService getAssignmentService(){
	    return (AssignmentService)CONTEXT.getBean(HR_ASSIGNMENT_SERVICE);
	}

	public static JobService getJobService() {
		return (JobService)CONTEXT.getBean(HR_JOB_SERVICE);
	}

	public static PayTypeService getPayTypeService() {
		return (PayTypeService)CONTEXT.getBean(HR_PAY_TYPE_SERVICE);
	}

	public static EarnCodeSecurityService getEarnCodeSecurityService() {
		return (EarnCodeSecurityService) CONTEXT.getBean(HR_EARN_CODE_SECURITY);
	}

	public static EarnCodeService getEarnCodeService() {
		return (EarnCodeService) CONTEXT.getBean(HR_EARN_CODE);
	}

	public static EarnCodeGroupService getEarnCodeGroupService(){
		return (EarnCodeGroupService) CONTEXT.getBean(HR_TIME_EARN_CODE_GROUP_SERVICE);
	}

	public static AccrualCategoryService getAccrualCategoryService() {
	    return (AccrualCategoryService)CONTEXT.getBean(HR_ACCRUAL_CATEGORY_SERVICE);
	}
	
	public static AccrualCategoryRuleService getAccrualCategoryRuleService() {
	    return (AccrualCategoryRuleService)CONTEXT.getBean(HR_ACCRUAL_CATEGORY_RULE_SERVICE);
	}
	
	public static LocationService getLocationService() {
	    return (LocationService)CONTEXT.getBean(HR_LOCATION_SERVICE);
	}

	public static PayGradeService getPayGradeService() {
	    return (PayGradeService)CONTEXT.getBean(HR_PAY_GRADE_SERVICE);
	}
	
	public static PositionService getPositionService(){
		return (PositionService) CONTEXT.getBean(HR_POSITION_SERVICE);
	}
	
	public static LeavePlanService getLeavePlanService(){
		return (LeavePlanService)CONTEXT.getBean(HR_LEAVE_PLAN_SERVICE);
	}
    
    public static PrincipalHRAttributesService getPrincipalHRAttributeService(){
    	return (PrincipalHRAttributesService)CONTEXT.getBean(HR_PRINCIPAL_HR_ATTRIBUTES_SERVICE);
    }
    
	public static CalendarService getCalendarService() {
		return (CalendarService)CONTEXT.getBean(HR_PAY_CALENDAR_SERVICE);
	}
	
	public static CalendarEntryService getCalendarEntryService() {
		return (CalendarEntryService)CONTEXT.getBean(HR_PAY_CALENDAR_ENTRY_SERVICE);
	}

	public static TimezoneService getTimezoneService(){
		return (TimezoneService) CONTEXT.getBean(TK_TIME_ZONE_SERVICE);
	}
	
	/* 
	 * 
	 * Group, Role and Permission Services
	 * 
	 */
    public static HRGroupService getHRGroupService() {
    	return (HRGroupService) CONTEXT.getBean(HR_GROUP_SERVICE);
    }
    public static HRPermissionService getHRPermissionService() {
    	return (HRPermissionService) CONTEXT.getBean(HR_PERMISSION_SERVICE);
    }
    public static HRRoleService getHRRoleService() {
    	return (HRRoleService) CONTEXT.getBean(HR_ROLE_SERVICE);
    }
    
    
    public static KPMENotificationService getKPMENotificationService() {
    	return (KPMENotificationService) CONTEXT.getBean(KPME_NOTIFICATION_SERVICE);
    }
    
    public static DistributedCacheManagerDecorator getDistributedCacheManager() {
        return (DistributedCacheManagerDecorator)CONTEXT.getBean(KPME_DISTRIBUTED_CACHE_MANAGER);
    }
	
	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		CONTEXT = arg0;
	}

}
