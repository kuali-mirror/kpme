package org.kuali.hr.core.service;

import org.kuali.hr.core.accrualcategory.rule.service.AccrualCategoryRuleService;
import org.kuali.hr.core.accrualcategory.service.AccrualCategoryService;
import org.kuali.hr.core.assignment.service.AssignmentService;
import org.kuali.hr.core.batch.service.BatchJobService;
import org.kuali.hr.core.calendar.service.CalendarEntryService;
import org.kuali.hr.core.calendar.service.CalendarService;
import org.kuali.hr.core.department.service.DepartmentService;
import org.kuali.hr.core.earncode.group.service.EarnCodeGroupService;
import org.kuali.hr.core.earncode.security.service.EarnCodeSecurityService;
import org.kuali.hr.core.earncode.service.EarnCodeService;
import org.kuali.hr.core.job.service.JobService;
import org.kuali.hr.core.leaveplan.service.LeavePlanService;
import org.kuali.hr.core.location.service.LocationService;
import org.kuali.hr.core.paygrade.service.PayGradeService;
import org.kuali.hr.core.paytype.service.PayTypeService;
import org.kuali.hr.core.position.service.PositionService;
import org.kuali.hr.core.principal.service.PrincipalHRAttributesService;
import org.kuali.hr.core.salgroup.service.SalGroupService;
import org.kuali.hr.core.task.service.TaskService;
import org.kuali.hr.core.workarea.service.WorkAreaService;
import org.kuali.hr.tklm.time.person.service.PersonService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class HrServiceLocator implements ApplicationContextAware {

	public static String SPRING_BEANS = "classpath:SpringBeans.xml";
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

    public static TaskService getTaskService() {
        return (TaskService) CONTEXT.getBean(HR_TASK_SERVICE);
    }

	public static SalGroupService getSalaryGroupService() {
		return (SalGroupService) CONTEXT.getBean(HR_SALARY_GROUP_SERVICE);
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

	public static BatchJobService getBatchJobService(){
		return (BatchJobService)CONTEXT.getBean(HR_BATCH_JOB_SERVICE);
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

	public static PersonService getPersonService(){
		return (PersonService)CONTEXT.getBean(HR_PERSON_SERVICE);
	}	

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		CONTEXT = arg0;
	}

}
