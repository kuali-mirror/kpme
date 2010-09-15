package org.kuali.hr.time.workflow;

import java.sql.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.paycalendar.PayCalendarDates;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.timesheet.service.TimesheetService;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.web.TkLoginFilter;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;

public class WorkflowTimesheetTest extends TkTestCase {

	private static final Logger LOG = Logger.getLogger(WorkflowTimesheetTest.class);
	
	@Test
	public void testRouting() throws Exception {
		TimesheetService tsvc = TkServiceLocator.getTimesheetService();
		assertNotNull("timesheet service null", tsvc);
		TKUser user = getPopulatedTkUser();
		assertNotNull("user was null", user);

		Date currentDate = TKUtils.getTimelessDate(null);
		List<Job> jobs = TkServiceLocator.getJobSerivce().getJobs(user.getPrincipalId(), currentDate);
		assertNotNull("No jobs", jobs);
		assertTrue("No jobs", jobs.size() > 0);
		PayCalendarDates pcd = TkServiceLocator.getPayCalendarSerivce().getCurrentPayCalendarDates(user.getPrincipalId(), jobs.get(0), currentDate);
		assertNotNull("No PayCalendarDates", pcd);
		TimesheetDocument tdoc = tsvc.openTimesheetDocument(user.getPrincipalId(), pcd);
		
		tsvc.routeTimesheet(user.getPrincipalId(), tdoc);
		
		LOG.debug("Routing document: " + tdoc.getDocumentHeader().getDocumentId());
//		WorkflowDocument wd = new WorkflowDocument("admin","TimesheetDocument");
//		wd.getRouteHeaderId();
//		wd.saveDocument("saved");
	}
	
	public TKUser getPopulatedTkUser() {
		TKUser user = new TKUser();

		Person person = KIMServiceLocator.getPersonService().getPerson(TkLoginFilter.TEST_ID);
		user.setActualPerson(person);
		
		return user;
	}
}
