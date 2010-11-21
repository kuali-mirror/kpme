package org.kuali.hr.time.workflow;

import java.sql.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.paycalendar.PayCalendarDates;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.timesheet.service.TimesheetService;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.web.TkLoginFilter;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;
@Ignore
public class WorkflowTimesheetTest extends TkTestCase {

	private static final Logger LOG = Logger.getLogger(WorkflowTimesheetTest.class);
	
	@Test
	public void testRouting() throws Exception {
		TimesheetService timesheetService = TkServiceLocator.getTimesheetService();
		assertNotNull("timesheet service null", timesheetService);
		TKUser user = getPopulatedTkUser();
		assertNotNull("user was null", user);

		Date asOfDate = new Date((new DateTime(2010, 8, 1, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());

		
		List<Job> jobs = TkServiceLocator.getJobSerivce().getJobs(user.getPrincipalId(), asOfDate);
		assertNotNull("No jobs", jobs);
		assertTrue("Should only be two Jobs.", jobs.size() == 2);
		PayCalendarDates pcd = TkServiceLocator.getPayCalendarSerivce().getCurrentPayCalendarDates(user.getPrincipalId(), asOfDate);
		assertNotNull("No PayCalendarDates", pcd);
		
		TimesheetDocument tdoc = timesheetService.openTimesheetDocument(user.getPrincipalId(), pcd);
		String kewSourceDocumentStatus = KEWServiceLocator.getWorkflowUtilityService().getDocumentStatus(tdoc.getDocumentHeader().getDocumentId());
		String tkSourceDocumentStatus  = tdoc.getDocumentHeader().getDocumentStatus();
		assertEquals("Status should be equal.", kewSourceDocumentStatus, tkSourceDocumentStatus);
		assertEquals("Document is already routed.", "I", tkSourceDocumentStatus);
		timesheetService.routeTimesheet(user.getPrincipalId(), tdoc);
		LOG.debug("Routing document: " + tdoc.getDocumentHeader().getDocumentId());
		
		kewSourceDocumentStatus = KEWServiceLocator.getWorkflowUtilityService().getDocumentStatus(tdoc.getDocumentHeader().getDocumentId());
		tkSourceDocumentStatus  = tdoc.getDocumentHeader().getDocumentStatus();
		
		assertEquals("Status should be equal.", kewSourceDocumentStatus, tkSourceDocumentStatus);
	}
	
	public TKUser getPopulatedTkUser() {
		TKUser user = new TKUser();

		Person person = KIMServiceLocator.getPersonService().getPerson(TkLoginFilter.TEST_ID);
		user.setActualPerson(person);
		
		return user;
	}
}
