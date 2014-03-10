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
package org.kuali.hr.time.workflow;

import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.hr.KPMEWebTestCase;
import org.kuali.kpme.core.FunctionalTest;
import org.kuali.kpme.core.api.job.Job;
import org.kuali.kpme.core.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.job.JobBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.kpme.tklm.time.timesheet.service.TimesheetService;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;
@Ignore
@FunctionalTest
public class WorkflowTimesheetTest extends KPMEWebTestCase {

	private static final Logger LOG = Logger.getLogger(WorkflowTimesheetTest.class);
	
	@Test
	public void testRouting() throws Exception {
		TimesheetService timesheetService = TkServiceLocator.getTimesheetService();
		Assert.assertNotNull("timesheet service null", timesheetService);

		DateTime asOfDate = new DateTime(2010, 8, 1, 12, 0, 0, 0, TKUtils.getSystemDateTimeZone());

		List<Job> jobs = HrServiceLocator.getJobService().getJobs(GlobalVariables.getUserSession().getPrincipalId(), asOfDate.toLocalDate());
		Assert.assertNotNull("No jobs", jobs);
		Assert.assertTrue("Should only be two Jobs.", jobs.size() == 2);
		CalendarEntry pcd = (CalendarEntry) HrServiceLocator.getCalendarEntryService().getCurrentCalendarDates(GlobalVariables.getUserSession().getPrincipalId(), asOfDate);
		Assert.assertNotNull("No PayCalendarDates", pcd);
		
		TimesheetDocument tdoc = timesheetService.openTimesheetDocument(GlobalVariables.getUserSession().getPrincipalId(), pcd);
		String kewSourceDocumentStatus = KEWServiceLocator.getRouteHeaderService().getDocumentStatus(tdoc.getDocumentHeader().getDocumentId());
		String tkSourceDocumentStatus  = tdoc.getDocumentHeader().getDocumentStatus();
		Assert.assertEquals("Status should be equal.", kewSourceDocumentStatus, tkSourceDocumentStatus);
		Assert.assertEquals("Document is already routed.", "I", tkSourceDocumentStatus);
		timesheetService.routeTimesheet(GlobalVariables.getUserSession().getPrincipalId(), tdoc);
		LOG.debug("Routing document: " + tdoc.getDocumentHeader().getDocumentId());
		
		kewSourceDocumentStatus = KEWServiceLocator.getRouteHeaderService().getDocumentStatus(tdoc.getDocumentHeader().getDocumentId());
		tkSourceDocumentStatus  = tdoc.getDocumentHeader().getDocumentStatus();
		
		Assert.assertEquals("Status should be equal.", kewSourceDocumentStatus, tkSourceDocumentStatus);
	}

}