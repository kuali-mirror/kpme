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
package org.kuali.hr.time.detail.web;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.core.calendar.CalendarEntry;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.tklm.time.rules.graceperiod.GracePeriodRule;
import org.kuali.hr.tklm.time.service.base.TkServiceLocator;
import org.kuali.hr.tklm.time.timeblock.TimeBlock;
import org.kuali.hr.tklm.time.timeblock.TimeHourDetail;
import org.kuali.hr.tklm.time.timesheet.TimesheetDocument;
import org.kuali.rice.krad.service.KRADServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class ActualTimeInquiryWebTest extends KPMETestCase {
	private String documentId;
	private TimeBlock timeBlock;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        CalendarEntry calendarEntry = TkServiceLocator.getCalendarEntryService().getCalendarEntry("5000");
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
		timeBlock = new TimeBlock();
		timeBlock.setUserPrincipalId("admin");
		timeBlock.setPrincipalId("admin");
		timeBlock.setJobNumber(2L);
		timeBlock.setWorkArea(1234L);
		timeBlock.setTask(1L);
		timeBlock.setEarnCode("RGN");
		Timestamp beginTimestamp = new Timestamp(System.currentTimeMillis());
		timeBlock.setBeginTimestamp(beginTimestamp);
		Timestamp endTimestamp = new Timestamp(System.currentTimeMillis());
		timeBlock.setEndTimestamp(endTimestamp);
		TimeHourDetail timeHourDetail = new TimeHourDetail();
		timeHourDetail.setEarnCode("RGN");
		timeHourDetail.setHours(new BigDecimal(2.0));
		timeBlock.getTimeHourDetails().add(timeHourDetail);
		timeBlock.setHours(new BigDecimal(2.0));
		timeBlock.setClockLogCreated(Boolean.TRUE);
		List<TimeBlock> tbList = new ArrayList<TimeBlock>();
		timeBlock.setDocumentId(documentId);
		tbList.add(timeBlock);
		TkServiceLocator.getTimeBlockService().saveTimeBlocks(tbList);

		TimesheetDocument td = TkServiceLocator.getTimesheetService().getTimesheetDocument(documentId.toString());
		td.setTimeBlocks(tbList);
	}
	
	public void changeGracePeriodRule() {
		GracePeriodRule gracePeriodRule = TkServiceLocator.getGracePeriodService().getGracePeriodRule(timeBlock.getBeginDateTime().toLocalDate());
		gracePeriodRule.setHourFactor(new BigDecimal("1"));
		KRADServiceLocator.getBusinessObjectService().save(gracePeriodRule);
	}

}