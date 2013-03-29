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
package org.kuali.hr.lm.leave.web;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.calendar.CalendarEntry;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;

import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class LeaveCalendarWebTest extends KPMETestCase {
	
	private String documentId;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		
		CalendarEntry firstCalendarEntry = TkServiceLocator.getCalendarEntryService().getCalendarEntry("10000");
        LeaveCalendarDocument firstLeaveCalendarDocument = TkServiceLocator.getLeaveCalendarService().openLeaveCalendarDocument("admin", firstCalendarEntry);
        documentId = firstLeaveCalendarDocument.getDocumentId();
        
        CalendarEntry secondCalendarEntry = TkServiceLocator.getCalendarEntryService().getCalendarEntry("10001");
        TkServiceLocator.getLeaveCalendarService().openLeaveCalendarDocument("admin", secondCalendarEntry);
        
        CalendarEntry thirdCalendarEntry = TkServiceLocator.getCalendarEntryService().getCalendarEntry("10002");
        TkServiceLocator.getLeaveCalendarService().openLeaveCalendarDocument("admin", thirdCalendarEntry);
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testLeaveCalendarPage() throws Exception {
		// get the page and Login
		HtmlPage leaveCalendarPage = HtmlUnitUtil
				.gotoPageAndLogin(getWebClient(), TkTestConstants.Urls.LEAVE_CALENDAR_URL+"?documentId=" + documentId, true);
		Assert.assertNotNull("Leave Request page not found" ,leaveCalendarPage);

		//this.setWebClient(leaveCalendarPage.getWebClient());

        DateTime dt = new DateTime();
		Assert.assertTrue("Page does not have Current calendar ", leaveCalendarPage.asText().contains("March 2012"));

        // Check for next document
        HtmlButton nextButton = (HtmlButton) leaveCalendarPage
                .getElementById("nav_next_lc");
        Assert.assertNotNull(nextButton);
        //TODO: click not working.  Not even getting to the 'execute' method in LeaveCalendarAction
        HtmlPage page = nextButton.click();
        Assert.assertNotNull(page);

		// Check for previous document
		HtmlButton prevButton = (HtmlButton) page
				.getElementById("nav_prev_lc");
		Assert.assertNotNull(prevButton);
        page = prevButton.click();
        Assert.assertNotNull(page);

	}

}
