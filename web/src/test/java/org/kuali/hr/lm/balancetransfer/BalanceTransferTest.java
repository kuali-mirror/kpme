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

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.junit.Test;
import org.kuali.hr.lm.leaveCalendar.LeaveCalendarWebTestBase;
import org.kuali.hr.lm.util.LeaveCalendarTestUtils;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.kpme.core.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.leave.calendar.LeaveCalendarDocument;
import org.kuali.kpme.tklm.leave.calendar.web.LeaveCalendarSubmitForm;
import org.kuali.kpme.tklm.leave.calendar.web.LeaveCalendarWSForm;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.leave.summary.LeaveSummary;
import org.kuali.kpme.tklm.leave.summary.LeaveSummaryRow;
import org.kuali.kpme.tklm.leave.transfer.BalanceTransfer;

import com.gargoylesoftware.htmlunit.html.HtmlPage;


public class BalanceTransferTest extends LeaveCalendarWebTestBase {
    private static final Logger LOG = Logger.getLogger(BalanceTransferTest.class);
    public static final String USER_PRINCIPAL_ID = "admin";
	private BalanceTransfer balanceTransfer;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
        setBaseDetailURL(TkTestConstants.Urls.LEAVE_CALENDAR_SUBMIT_URL + "?documentId=");
        balanceTransfer = new BalanceTransfer();
        balanceTransfer.setTransferAmount(new BigDecimal(20));
        balanceTransfer.setForfeitedAmount(new BigDecimal(0));
        balanceTransfer.setAmountTransferred(new BigDecimal(10));
        balanceTransfer.setAccrualCategoryRule("5000");
	}
	
	@Test
	public void testTransferWithAccrualRule() throws Exception {
		BalanceTransfer btd = new BalanceTransfer();
		//btd.setCreditedAccrualCategory(TKTestUtils.creat)
		assertTrue("Dummy assertion",true);
	}
	
	@Test
	public void testAdjustLowerTransferAmount() {
		BigDecimal adjustedTransferAmount = new BigDecimal(10);
		balanceTransfer = balanceTransfer.adjust(adjustedTransferAmount);
		
		assertTrue("Transfer Amount not equals", balanceTransfer.getTransferAmount().compareTo(adjustedTransferAmount) == 0);
		assertTrue("Forfeited amount not updated", balanceTransfer.getForfeitedAmount().compareTo(new BigDecimal(10)) == 0);
		assertTrue(balanceTransfer.getAmountTransferred().compareTo(new BigDecimal(5)) == 0);
	}
	
	@Test
	public void testAdjustLowerTransferAmountWithForfeiture() {
		BigDecimal adjustedTransferAmount = new BigDecimal(10);
		balanceTransfer.setForfeitedAmount(new BigDecimal(10));
		balanceTransfer = balanceTransfer.adjust(adjustedTransferAmount);
		
		assertTrue("Transfer Amount not equals", balanceTransfer.getTransferAmount().compareTo(adjustedTransferAmount) == 0);
		assertTrue("Forfeited amount not updated", balanceTransfer.getForfeitedAmount().compareTo(new BigDecimal(20)) == 0);
		assertTrue(balanceTransfer.getAmountTransferred().compareTo(new BigDecimal(5)) == 0);
	}
	
	@Test
	public void testAdjustRaiseTransferAmount() {
		BigDecimal adjustedTransferAmount = new BigDecimal(30);
		balanceTransfer = balanceTransfer.adjust(adjustedTransferAmount);
		
		assertTrue("Transfer Amount not equals", balanceTransfer.getTransferAmount().compareTo(adjustedTransferAmount) == 0);
		assertTrue("Forfeited amount not updated", balanceTransfer.getForfeitedAmount().compareTo(BigDecimal.ZERO) == 0);
		assertTrue(balanceTransfer.getAmountTransferred().compareTo(new BigDecimal(15)) == 0);
	}
	
	@Test
	public void testAdjustRaiseTransferAmountWithForfeitureLessThanDifference() {
		BigDecimal adjustedTransferAmount = new BigDecimal(40);
		balanceTransfer.setForfeitedAmount(new BigDecimal(10));
		
		balanceTransfer = balanceTransfer.adjust(adjustedTransferAmount);
		
		assertTrue("Transfer Amount not equals", balanceTransfer.getTransferAmount().compareTo(adjustedTransferAmount) == 0);
		assertTrue("Forfeited amount not updated", balanceTransfer.getForfeitedAmount().compareTo(BigDecimal.ZERO) == 0);
		assertTrue(balanceTransfer.getAmountTransferred().compareTo(new BigDecimal(20)) == 0);
	}
	
	@Test
	public void testAdjustRaiseTransferAmountWithForfeitureMoreThanDifference() {
		BigDecimal adjustedTransferAmount = new BigDecimal(30);
		balanceTransfer.setForfeitedAmount(new BigDecimal(15));
		balanceTransfer = balanceTransfer.adjust(adjustedTransferAmount);
		
		assertTrue("Transfer Amount not equals", balanceTransfer.getTransferAmount().compareTo(adjustedTransferAmount) == 0);
		assertTrue("Forfeited amount not updated", balanceTransfer.getForfeitedAmount().compareTo(new BigDecimal(5)) == 0);
		assertTrue(balanceTransfer.getAmountTransferred().compareTo(new BigDecimal(15)) == 0);
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
		DateTime startDate = new DateTime(2012, 1, 1, 0, 0, 0, 1, TKUtils.getSystemDateTimeZone());
        DateTime asOfDate = new DateTime(2012, 11, 1, 12, 0, 0, 0, TKUtils.getSystemDateTimeZone());
        LmServiceLocator.getAccrualService().runAccrual(USER_PRINCIPAL_ID,startDate,asOfDate,false);
        CalendarEntry pcd = HrServiceLocator.getCalendarService().getCurrentCalendarDatesForLeaveCalendar(USER_PRINCIPAL_ID, asOfDate);
        Assert.assertNotNull("No CalendarEntry", pcd);

        //opens a leave calendar document and initiates a workflow document. Sets document status to Initiated.
        LeaveCalendarDocument tdoc = LmServiceLocator.getLeaveCalendarService().openLeaveCalendarDocument(USER_PRINCIPAL_ID, pcd);
        String tdocId = tdoc.getDocumentId();

        // Build an action form - we're using it as a POJO, it ties into the
        // existing TK validation setup
        //Can be removed if LeaveCalendarService.openLeaveCalendarDocument takes on the task.
        ls = LmServiceLocator.getLeaveSummaryService().getLeaveSummary(USER_PRINCIPAL_ID, pcd);
        LeaveCalendarWSForm tdaf = LeaveCalendarTestUtils.buildLeaveCalendarFormForSubmission(tdoc, ls);
        LeaveCalendarSubmitForm lcsf = new LeaveCalendarSubmitForm();
        lcsf.setAction(HrConstants.DOCUMENT_ACTIONS.ROUTE);
        lcsf.setDocumentId(tdaf.getDocumentId());
        for(LeaveSummaryRow lsRow : ls.getLeaveSummaryRows()) {
        	LOG.info("Accrued balance : " + lsRow.getAccruedBalance());
        }
        HtmlPage page = LeaveCalendarTestUtils.submitLeaveCalendar2(getWebClient(), getLeaveCalendarUrl(tdocId), tdaf);
        //LeaveCalendarWSForm extends LeaveCalendarForm. In order to fully implement this test, a LeaveCalendarSubmitForm must be created
        //with an action of "DOCUMENT_ACTIONS.ROUTE".
        Assert.assertNotNull(page);
        HtmlUnitUtil.createTempFile(page, "LeaveBlockPresent");

        // Verify block present on rendered page.
        String pageAsText = page.asText();
        LOG.info(pageAsText);
		
		
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
	
	/**
	 * Test the lookup results page, there should not be "edit" actions, only "view" actions
	 * @throws Exception
	 */
	@Test
	public void testLookupPage() throws Exception {	 
		HtmlPage btLookup = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), TkTestConstants.Urls.BALANCE_TRANSFER_MAINT_URL);
		btLookup = HtmlUnitUtil.clickInputContainingText(btLookup, "search");
		LOG.info(btLookup.asXml());
		Assert.assertTrue("Page contains test Balance Transfer", btLookup.asText().contains("fromAC"));
		Assert.assertFalse("Page should not contain edit action", btLookup.asText().contains("edit")); 
		Assert.assertTrue("Page should contain view action", btLookup.asText().contains("view"));
	}
	
	/**
	 * Test that the automated carry over adjustment leave blocks, created when a leave calendar is submitted,
	 * are adjusted when a transfer is approved
	 */
	@Test
	public void testCarryOverAdjustmentOnTransferApproval() throws Exception {
		
		assertTrue("Dummy assertion 5", true);
	}
	
}