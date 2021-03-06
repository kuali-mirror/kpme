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
package org.kuali.kpme.core.service;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.api.accrualcategory.rule.AccrualCategoryRuleService;
import org.kuali.kpme.core.api.accrualcategory.AccrualCategoryService;
import org.kuali.kpme.core.api.assignment.service.AssignmentService;
import org.kuali.kpme.core.api.block.service.CalendarBlockService;
import org.kuali.kpme.core.api.calendar.entry.service.CalendarEntryService;
import org.kuali.kpme.core.api.calendar.service.CalendarService;
import org.kuali.kpme.core.api.groupkey.HrGroupKeyService;
import org.kuali.kpme.core.api.department.DepartmentService;
import org.kuali.kpme.core.api.departmentaffiliation.service.DepartmentAffiliationService;
import org.kuali.kpme.core.api.earncode.group.service.EarnCodeGroupService;
import org.kuali.kpme.core.api.earncode.security.service.EarnCodeSecurityService;
import org.kuali.kpme.core.api.earncode.service.EarnCodeService;
import org.kuali.kpme.core.api.institution.service.InstitutionService;
import org.kuali.kpme.core.api.job.service.JobService;
import org.kuali.kpme.core.api.leaveplan.LeavePlanService;
import org.kuali.kpme.core.api.location.service.LocationService;
import org.kuali.kpme.core.api.paygrade.service.PayGradeService;
import org.kuali.kpme.core.api.paystep.service.PayStepService;
import org.kuali.kpme.core.api.paytype.service.PayTypeService;
import org.kuali.kpme.core.api.permission.HRPermissionService;
import org.kuali.kpme.core.api.position.service.PositionBaseService;
import org.kuali.kpme.core.api.principal.service.PrincipalHRAttributesService;
import org.kuali.kpme.core.api.salarygroup.service.SalaryGroupService;
import org.kuali.kpme.core.api.task.service.TaskService;
import org.kuali.kpme.core.api.workarea.service.WorkAreaService;
import org.kuali.kpme.core.service.group.KPMEGroupService;
import org.kuali.kpme.core.service.notification.KPMENotificationService;
import org.kuali.kpme.core.service.role.KPMERoleService;
import org.kuali.kpme.core.service.timezone.TimezoneService;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.impl.cache.DistributedCacheManagerDecorator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

public class HrServiceLocator implements ApplicationContextAware {

	public static String SPRING_BEANS = "classpath:org/kuai/kpme/core/config/CORESpringBeans.xml";
	public static ApplicationContext CONTEXT;
	private static final String HR_CALENDAR_BLOCK_SERVICE = "calendarBlockService";
	
	public static final String HR_PAY_CALENDAR_SERVICE = "calendarService";
	public static final String HR_PAY_CALENDAR_ENTRY_SERVICE = "calendarEntryService";
	public static final String HR_SALARY_GROUP_SERVICE = "salaryGroupService";
	public static final String HR_ACCRUAL_CATEGORY_SERVICE = "accrualCategoryService";
	public static final String HR_ACCRUAL_CATEGORY_RULE_SERVICE = "accrualCategoryRuleService";
    public static final String HR_GROUP_KEY_SERVICE = "hrGroupKeyService";
    public static final String HR_TASK_SERVICE = "taskService";
    public static final String HR_LOCATION_SERVICE = "locationService";
    public static final String HR_PAY_GRADE_SERVICE = "payGradeService";
    public static final String HR_POSITION_SERVICE = "positionBaseService";
    public static final String HR_LEAVE_PLAN_SERVICE = "leavePlanService";
	public static final String HR_PRINCIPAL_HR_ATTRIBUTES_SERVICE = "principalHRAttributesService";
	public static final String HR_ASSIGNMENT_SERVICE = "assignmentService";
	public static final String HR_JOB_SERVICE = "jobService";
	public static final String HR_PAY_TYPE_SERVICE = "payTypeService";
	public static final String HR_WORK_AREA_SERVICE = "workAreaService";
	public static final String HR_DEPARTMENT_SERVICE = "departmentService";
    public static final String DEPT_AFFL_SERVICE = "departmentAffiliationService";
	public static final String HR_EARN_CODE = "earnCodeService";
	public static final String HR_EARN_CODE_SECURITY = "earnCodeSecurityService";
	public static final String HR_TIME_EARN_CODE_GROUP_SERVICE = "earnCodeGroupService";
	public static final String HR_INSTITUTION_SERVICE = "institutionService";
	public static final String HR_PAY_STEP_SERVICE = "payStepService";
	public static final String TK_TIME_ZONE_SERVICE = "timezoneService";
	
    public static final String HR_PERMISSION_SERVICE = "hrPermissionService";

    //Move to [Sys|Hr]ServiceLocator
	public static final String KPME_DISTRIBUTED_CACHE_MANAGER = "kpmeDistributedCacheManager";
    public static final String KPME_GROUP_SERVICE = "kpmeGroupService";
	public static final String KPME_NOTIFICATION_SERVICE = "kpmeNotificationService";    
    public static final String KPME_ROLE_SERVICE = "kpmeRoleService";
    
    public static Object getBean(String beanName) {
        return CONTEXT.getBean(beanName);
    }
    
	public static PlatformTransactionManager getPlatformTransactionManager() {
		return (PlatformTransactionManager)CONTEXT.getBean("transactionManager");
	}

	public static TransactionTemplate getTransactionTemplate() {
		return new TransactionTemplate(getPlatformTransactionManager());
	}
       
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

    public static HrGroupKeyService getHrGroupKeyService() {
        return (HrGroupKeyService) CONTEXT.getBean(HR_GROUP_KEY_SERVICE);
    }

	public static DepartmentService getDepartmentService() {
		return (DepartmentService) CONTEXT.getBean(HR_DEPARTMENT_SERVICE);
	}

    public static DepartmentAffiliationService getDepartmentAffiliationService() {
        return (DepartmentAffiliationService) CONTEXT.getBean(DEPT_AFFL_SERVICE);
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
	
	public static PositionBaseService getPositionService(){
		return (PositionBaseService) CONTEXT.getBean(HR_POSITION_SERVICE);
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
    
    public static DistributedCacheManagerDecorator getDistributedCacheManager() {
        return (DistributedCacheManagerDecorator)CONTEXT.getBean(KPME_DISTRIBUTED_CACHE_MANAGER);
    }
    
    public static KPMEGroupService getKPMEGroupService() {
    	return (KPMEGroupService) CONTEXT.getBean(KPME_GROUP_SERVICE);
    }
    
    public static KPMENotificationService getKPMENotificationService() {
    	return (KPMENotificationService) CONTEXT.getBean(KPME_NOTIFICATION_SERVICE);
    }
    
    public static HRPermissionService getHRPermissionService() {
    	return (HRPermissionService) CONTEXT.getBean(HR_PERMISSION_SERVICE);
    }
    
    public static KPMERoleService getKPMERoleService() {
    	return (KPMERoleService) CONTEXT.getBean(KPME_ROLE_SERVICE);
    }
	
	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		CONTEXT = arg0;
	}

	public static CalendarBlockService getCalendarBlockService() {
		return (CalendarBlockService) CONTEXT.getBean(HR_CALENDAR_BLOCK_SERVICE );
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
