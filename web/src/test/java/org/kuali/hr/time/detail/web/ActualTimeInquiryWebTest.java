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
package org.kuali.hr.time.detail.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.KPMEWebTestCase;
import org.kuali.hr.util.HtmlUnitUtil;
import org.kuali.kpme.core.FunctionalTest;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.calendar.entry.CalendarEntryBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.rules.graceperiod.GracePeriodRule;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timeblock.TimeBlockBo;
import org.kuali.kpme.tklm.time.timehourdetail.TimeHourDetailBo;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.kpme.tklm.utils.TkTestConstants;
import org.kuali.rice.krad.service.KRADServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;

@FunctionalTest
public class ActualTimeInquiryWebTest extends KPMEWebTestCase {
	private String documentId;
	private TimeBlockBo timeBlock;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        CalendarEntry calendarEntry = HrServiceLocator.getCalendarEntryService().getCalendarEntry("5000");
        TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().openTimesheetDocument("admin", calendarEntry);
        documentId = timesheetDocument.getDocumentId();
    }

    @Test
	public void testActualTimeInquiry() throws Exception {
		String baseUrl = TkTestConstants.Urls.TIME_DETAIL_URL;
    	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), baseUrl);
    	Assert.assertNotNull(page);
    	Assert.assertTrue("Clock Page contains Actual Time Inquiry Button", page.asText().contains("Actual Time Inquiry"));
	  	
	  	String atiUrl = baseUrl + "?methodToCall=actualTimeInquiry";
		HtmlPage testPage1 = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), atiUrl);
		Assert.assertNotNull(testPage1);
		Assert.assertTrue("Actual Time Inquiry page contains close Button", testPage1.asText().contains("Close"));
		Assert.assertTrue("Actual Time Inquiry page contains No value found message", testPage1.asText().contains("No values match this search."));
    	
    	this.createTB();
    	this.changeGracePeriodRule();
    	atiUrl = baseUrl + "?methodToCall=actualTimeInquiry&documentId=" + documentId;
    	HtmlPage testPage2 = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), atiUrl);
    	Assert.assertTrue("Actual Time Inquiry page contains One item retrived message", testPage2.asText().contains("One item retrieved."));
	}

	public void createTB() {
		timeBlock = new TimeBlockBo();
		timeBlock.setUserPrincipalId("admin");
		timeBlock.setPrincipalId("admin");
        timeBlock.setGroupKeyCode("IU-BL");
		timeBlock.setJobNumber(2L);
		timeBlock.setWorkArea(1234L);
		timeBlock.setTask(1L);
		timeBlock.setEarnCode("RGN");
		timeBlock.setBeginTimestamp(TKUtils.getCurrentTimestamp());
		timeBlock.setEndTimestamp(TKUtils.getCurrentTimestamp());
		TimeHourDetailBo timeHourDetail = new TimeHourDetailBo();
		timeHourDetail.setEarnCode("RGN");
		timeHourDetail.setHours(new BigDecimal(2.0));
		timeBlock.getTimeHourDetails().add(timeHourDetail);
		timeBlock.setHours(new BigDecimal(2.0));
		timeBlock.setClockLogCreated(Boolean.TRUE);
		List<TimeBlock> tbList = new ArrayList<TimeBlock>();
		timeBlock.setDocumentId(documentId);
		tbList.add(TimeBlockBo.to(timeBlock));
		TkServiceLocator.getTimeBlockService().saveTimeBlocks(tbList);

		TimesheetDocument td = TkServiceLocator.getTimesheetService().getTimesheetDocument(documentId);
		td.setTimeBlocks(tbList);
	}
	
	public void changeGracePeriodRule() {
		GracePeriodRule gracePeriodRule = TkServiceLocator.getGracePeriodService().getGracePeriodRule(timeBlock.getBeginDateTime().toLocalDate());
		gracePeriodRule.setHourFactor(new BigDecimal("1"));
		KRADServiceLocatorWeb.getLegacyDataAdapter().save(gracePeriodRule);
	}

}