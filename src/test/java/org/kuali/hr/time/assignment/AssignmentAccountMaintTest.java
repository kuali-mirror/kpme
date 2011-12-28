package org.kuali.hr.time.assignment;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.time.workschedule.WorkSchedule;
import org.kuali.hr.time.workschedule.WorkScheduleEntry;
import org.kuali.rice.kns.service.KNSServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class AssignmentAccountMaintTest extends TkTestCase{
	private static final String TEST_CODE="CD";
	private static final String TEST_ID="1";
	private static final String TEST_ASSIGN_ID="23";
	private static String assignmentAccountId;
	private BigDecimal TEST_PERCENT =  new BigDecimal(1);
	
	@Test
	public void testAssignmentAccountMaint() throws Exception {	 
		HtmlPage assignmentAccountLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.ASSIGNMENT_ACCOUNT_MAINT_URL);
		assignmentAccountLookup = HtmlUnitUtil.clickInputContainingText(assignmentAccountLookup, "search");
		assertTrue("Page contains test assignmentAccount", assignmentAccountLookup.asText().contains(TEST_ASSIGN_ID.toString()));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(assignmentAccountLookup, "edit",assignmentAccountId.toString());
		assertTrue("Maintenance Page contains test assignmentAccount",maintPage.asText().contains(TEST_ASSIGN_ID.toString()));	 
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		AssignmentAccount assignmentAccount = new AssignmentAccount();
		assignmentAccount.setAccountNbr(TEST_ID);
		assignmentAccount.setActive(true);
		assignmentAccount.setTkAssignmentId(TEST_ASSIGN_ID);
		assignmentAccount.setFinCoaCd(TEST_CODE);
		assignmentAccount.setFinObjectCd(TEST_CODE);
		assignmentAccount.setFinSubObjCd(TEST_CODE);
		assignmentAccount.setPercent(TEST_PERCENT);		
		KNSServiceLocator.getBusinessObjectService().save(assignmentAccount);
		assignmentAccountId = assignmentAccount.getTkAssignAcctId();
	}

	@Override
	public void tearDown() throws Exception {				
		AssignmentAccount assignmentAccountObj = KNSServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(AssignmentAccount.class, assignmentAccountId);		 
		KNSServiceLocator.getBusinessObjectService().delete(assignmentAccountObj);
		super.tearDown();
	}
	
}


