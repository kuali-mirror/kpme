package org.kuali.hr.time.service.base;

import org.kuali.hr.time.assignment.dao.AssignmentDao;
import org.kuali.hr.time.assignment.service.AssignmentService;
import org.kuali.hr.time.clocklog.service.ClockLogService;
import org.kuali.hr.time.role.assign.service.TkRoleAssignService;
import org.kuali.hr.time.rule.TkRuleControllerService;
import org.kuali.hr.time.workarea.service.WorkAreaService;
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
	public static final String TK_WORKAREA_SERVICE = "workAreaService";
	public static final String TK_ROLE_ASSIGN_SERVICE = "tkRoleAssignmentService";
	public static final String TK_PERSISTENCE_BROKER_TEMPLATE = "tkPersistenceBrokerTemplate";
	
	public static void start() throws Exception {
	}

	public static void stop() throws Exception {
	}
	
	public static TkRoleAssignService getRoleAssignmentService() {
	    return (TkRoleAssignService) CONTEXT.getBean(TK_ROLE_ASSIGN_SERVICE);
	}
	
	public static WorkAreaService getWorkAreaService() {
	    return (WorkAreaService)CONTEXT.getBean(TK_WORKAREA_SERVICE);
	}
	
	public static ClockLogService getClockLogService(){
	    return (ClockLogService)CONTEXT.getBean(TK_CLOCK_LOG_SERVICE);
	}
	
	public static AssignmentService getAssignmentService(){
	    return (AssignmentService)CONTEXT.getBean(TK_ASSIGNMENT_SERVICE);
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

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
	    CONTEXT = arg0;
	}
}
