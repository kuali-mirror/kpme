package org.kuali.hr.time.workflow;

import java.util.Date;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.timesheet.service.TimesheetService;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.web.TkLoginFilter;
import org.kuali.rice.kew.service.WorkflowDocument;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;

public class WorkflowTimesheetTest extends TkTestCase {

	private static final Logger LOG = Logger.getLogger(WorkflowTimesheetTest.class);
	
	@Ignore
	public void testRouting() throws Exception {
		TimesheetService tsvc = TkServiceLocator.getTimesheetService();
		assertNotNull("timesheet service null", tsvc);
		TKUser user = getPopulatedTkUser();
		assertNotNull("user was null", user);
		Date payEndDate = TKUtils.getPayEndDate(user, (new DateTime()).toDate());
		TimesheetDocument tdoc = tsvc.openTimesheetDocument(user.getPrincipalId(), payEndDate);
		
		tsvc.routeTimesheet(user.getPrincipalId(), tdoc);
		
		LOG.debug("Routing document: " + tdoc.getDocumentHeader().getDocumentId());
		tdoc.getDocumentHeader().getWorkflowDocument().routeDocument("route");
//		WorkflowDocument wd = new WorkflowDocument("admin","TimesheetDocument");
//		wd.getRouteHeaderId();
//		wd.saveDocument("saved");
	}
	
	public TKUser getPopulatedTkUser() {
		TKUser user = new TKUser();

		Person person = KIMServiceLocator.getPersonService().getPerson(TkLoginFilter.TEST_ID);
		user.setActualPerson(person);
		user.setJobs(TkServiceLocator.getJobSerivce().getJobs(user.getPrincipalId()));
		
		return user;
	}
}
