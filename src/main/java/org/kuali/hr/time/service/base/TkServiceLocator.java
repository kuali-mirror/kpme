package org.kuali.hr.time.service.base;

import org.kuali.hr.job.service.JobService;
import org.kuali.hr.time.assignment.dao.AssignmentDao;
import org.kuali.hr.time.assignment.service.AssignmentService;
import org.kuali.hr.time.cache.CacheManagementService;
import org.kuali.hr.time.clocklog.service.ClockLogService;
import org.kuali.hr.time.paycalendar.service.PayCalendarDatesService;
import org.kuali.hr.time.paycalendar.service.PayCalendarService;
import org.kuali.hr.time.paytype.service.PayTypeService;
import org.kuali.hr.time.rule.TkRuleControllerService;
import org.kuali.hr.time.timeblock.service.TimeBlockHistoryService;
import org.kuali.hr.time.timeblock.service.TimeBlockService;
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

	public static void start() throws Exception {
//		CONTEXT = new ClassPathXmlApplicationContext(SPRING_BEANS);
//		CONTEXT.start();
	}

	public static void stop() throws Exception {
//		CONTEXT.stop();
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

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
	    CONTEXT = arg0;
	}
}
