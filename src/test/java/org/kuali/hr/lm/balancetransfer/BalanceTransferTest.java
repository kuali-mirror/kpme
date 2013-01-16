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
package org.kuali.hr.lm.balancetransfer;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Test;
import org.kuali.hr.lm.leave.web.LeaveCalendarWSForm;
import org.kuali.hr.lm.leaveCalendar.LeaveCalendarWebTestBase;
import org.kuali.hr.lm.leaveSummary.LeaveSummary;
import org.kuali.hr.lm.leaveSummary.LeaveSummaryRow;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;
import org.kuali.hr.lm.leavecalendar.web.LeaveCalendarSubmitForm;
import org.kuali.hr.lm.util.LeaveCalendarTestUtils;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;


public class BalanceTransferTest extends LeaveCalendarWebTestBase {
	
    public static final String USER_PRINCIPAL_ID = "admin";
	private Date JAN_AS_OF_DATE = new Date((new DateTime(2010, 1, 1, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
        setBaseDetailURL(TkTestConstants.Urls.LEAVE_CALENDAR_SUBMIT_URL + "?documentId=");
	}
	
	@Test
	public void testTransferWithAccrualRule() throws Exception {
		BalanceTransfer btd = new BalanceTransfer();
		//btd.setCreditedAccrualCategory(TKTestUtils.creat)
		assertTrue("Dummy assertion",true);
	}
	
	/**
	 * Tests balance transfers triggered by max balance action frequency "leave approve"
	 * Specifically, when a single accrual category has exceeded the max balance defined by the
	 * accrual category's rules, max balance action frequency is leave approve, and action at max balance is transfer.
	 * Would be better placed in LeaveCalendarWorkflowIntegrationTest.
	 * @throws Exception 
	 */
	@Test
	public void testSingleLeaveApproveBalanceTransfer() throws Exception {
		//Create a leave summary with a single row containing an accrual category that has exceeded max balance limit.
		//Generate accrual entries for debitAC1 and creditAC1
		LeaveSummary ls = new LeaveSummary();

		//Leave summary is created on the fly
		Date startDate = new Date((new DateTime(2012, 1, 1, 0, 0, 0, 1, TKUtils.getSystemDateTimeZone())).getMillis());
        Date asOfDate = new Date((new DateTime(2012, 11, 1, 12, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
        TkServiceLocator.getAccrualService().runAccrual(USER_PRINCIPAL_ID,startDate,asOfDate,false);
        CalendarEntries pcd = TkServiceLocator.getCalendarService().getCurrentCalendarDatesForLeaveCalendar(USER_PRINCIPAL_ID, asOfDate);
        Assert.assertNotNull("No CalendarEntries", pcd);

        //opens a leave calendar document and initiates a workflow document. Sets document status to Initiated.
        LeaveCalendarDocument tdoc = TkServiceLocator.getLeaveCalendarService().openLeaveCalendarDocument(USER_PRINCIPAL_ID, pcd);
        String tdocId = tdoc.getDocumentId();

        // Build an action form - we're using it as a POJO, it ties into the
        // existing TK validation setup
        //Can be removed if LeaveCalendarService.openLeaveCalendarDocument takes on the task.
        ls = TkServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_PRINCIPAL_ID, pcd);
        LeaveCalendarWSForm tdaf = LeaveCalendarTestUtils.buildLeaveCalendarFormForSubmission(tdoc, ls);
        LeaveCalendarSubmitForm lcsf = new LeaveCalendarSubmitForm();
        lcsf.setAction(TkConstants.DOCUMENT_ACTIONS.ROUTE);
        lcsf.setDocumentId(tdaf.getDocumentId());
        for(LeaveSummaryRow lsRow : ls.getLeaveSummaryRows()) {
        	System.out.println("Accrued balance : " + lsRow.getAccruedBalance());
        }
        HtmlPage page = LeaveCalendarTestUtils.submitLeaveCalendar2(getLeaveCalendarUrl(tdocId), tdaf);
        //LeaveCalendarWSForm extends LeaveCalendarForm. In order to fully implement this test, a LeaveCalendarSubmitForm must be created
        //with an action of "DOCUMENT_ACTIONS.ROUTE".
        Assert.assertNotNull(page);
        HtmlUnitUtil.createTempFile(page, "LeaveBlockPresent");

        // Verify block present on rendered page.
        String pageAsText = page.asText();
        System.out.print(pageAsText);
		
		
		//LeaveCalendarWSForm mockForm = LeaveCalendarTestUtils.buildLeaveCalendarForm(tdoc, assignment, earnCode, start, end, null, true);
		//Attach leave summary to relevant object (leavecalendardocument?)
		//load LeaveCalendarDocument into web container
		//leave calendar document must have status "initiated"
		//mock the submit action on behalf of the principal who owns the leave calendar.
		//redirect should occur, which loads the balance transfer document.
		//balance transfer document should have all fields locked except for transfer amount.
		//mock transfer action on behalf of the principal.
		//verify leave block creation
		assertTrue("Dummy assertion 2", true);
	}
	
	/**
	 * Tests balance transfers triggered by max balance action frequency "leave approve"
	 * Specifically, when multiple accrual categories have exceeded the max balance defined by the
	 * accrual category's rules, max balance action frequency is leave approve, and action at max balance is transfer.
	 */
	@Test
	public void testMultipleLeaveApproveBalanceTransfer() throws Exception {
		//Create a leave summary with multiple rows containing an accrual category that has exceeded max balance limit.
		//Attach leave summary to relevant object (leavecalendardocument?)
		//load LeaveCalendarDocument into web container
		//leave calendar document must have status "initiated"
		//mock the submit action on behalf of the principal who owns the leave calendar.
		//redirect should occur, which loads the balance transfer document.
		//balance transfer document should have all fields locked except for transfer amount.
		//mock transfer action on behalf of the principal.
		//verify transfer action has been triggered the correct number of times.
		//verify the correct number of leave blocks have been generated?
		assertTrue("Dummy assertion 3",true);
	}
	
	/**
	 * Tests leave calendar submit action does not trigger action redirects for balance transfer
	 * submission when no accrual category is eligable for transfer.
	 */
	@Test
	public void testNoEligibleAccrualCategorysForLeaveApproveBalanceTransfer() throws Exception {
		//Create leave summary with x number of rows, none of which contain an accrual category that has exceeded its limt.
		//Attach leave summary to relevant object (leavecalendardocument?)
		//load LeaveCalendarDocument into web container
		//leave calendar document must have status "initiated"
		//mock the submit action on behalf of the principal who owns the leave calendar.
		//redirect should occur, which loads the balance transfer document.
		//balance transfer document should have all fields locked except for transfer amount.
		//mock transfer action on behalf of the principal.
		//verify transfer action has been triggered the correct number of times.
		//verify the correct number of leave blocks have been generated?
		assertTrue("Dummy assertion 4", true);
	}
	
	@Test
	public void testAdjust() throws Exception {
		assertNull(null);
	}
	

	
}
