package org.kuali.hr.time.workflow;

import java.sql.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.timesheet.service.TimesheetService;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.web.TkLoginFilter;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
@Ignore
public class WorkflowTimesheetTest extends TkTestCase {

	private static final Logger LOG = Logger.getLogger(WorkflowTimesheetTest.class);
	
	@Test
	public void testRouting() throws Exception {
		TimesheetService timesheetService = TkServiceLocator.getTimesheetService();
		Assert.assertNotNull("timesheet service null", timesheetService);
		TKUser user = getPopulatedTkUser();
		Assert.assertNotNull("user was null", user);

		Date asOfDate = new Date((new DateTime(2010, 8, 1, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());

		
		List<Job> jobs = TkServiceLocator.getJobService().getJobs(user.getPrincipalId(), asOfDate);
		Assert.assertNotNull("No jobs", jobs);
		Assert.assertTrue("Should only be two Jobs.", jobs.size() == 2);
		CalendarEntries pcd = TkServiceLocator.getCalendarService().getCurrentCalendarDates(user.getPrincipalId(), asOfDate);
		Assert.assertNotNull("No PayCalendarDates", pcd);
		
		TimesheetDocument tdoc = timesheetService.openTimesheetDocument(user.getPrincipalId(), pcd);
		String kewSourceDocumentStatus = KEWServiceLocator.getRouteHeaderService().getDocumentStatus(tdoc.getDocumentHeader().getDocumentId());
		String tkSourceDocumentStatus  = tdoc.getDocumentHeader().getDocumentStatus();
		Assert.assertEquals("Status should be equal.", kewSourceDocumentStatus, tkSourceDocumentStatus);
		Assert.assertEquals("Document is already routed.", "I", tkSourceDocumentStatus);
		timesheetService.routeTimesheet(user.getPrincipalId(), tdoc);
		LOG.debug("Routing document: " + tdoc.getDocumentHeader().getDocumentId());
		
		kewSourceDocumentStatus = KEWServiceLocator.getRouteHeaderService().getDocumentStatus(tdoc.getDocumentHeader().getDocumentId());
		tkSourceDocumentStatus  = tdoc.getDocumentHeader().getDocumentStatus();
		
		Assert.assertEquals("Status should be equal.", kewSourceDocumentStatus, tkSourceDocumentStatus);
	}
	
	public TKUser getPopulatedTkUser() {
		TKUser user = new TKUser();

		Person person = KimApiServiceLocator.getPersonService().getPerson(TkLoginFilter.TEST_ID);
		user.setActualPerson(person);
		
		return user;
	}
}
