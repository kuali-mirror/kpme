package org.kuali.hr.time.assignment;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.rice.krad.service.KRADServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class AssignmentAccountMaintTest extends KPMETestCase{
	private static final String TEST_CODE="CD";
	private static final String TEST_ID="1";
	private static final String TEST_ASSIGN_ID="23";
	private static String assignmentAccountId;
	private BigDecimal TEST_PERCENT =  new BigDecimal(1);
	
	@Test
	public void testAssignmentAccountMaint() throws Exception {	 
		HtmlPage assignmentAccountLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.ASSIGNMENT_ACCOUNT_MAINT_URL);
		assignmentAccountLookup = HtmlUnitUtil.clickInputContainingText(assignmentAccountLookup, "search");
		Assert.assertTrue("Page contains test assignmentAccount", assignmentAccountLookup.asText().contains(TEST_ASSIGN_ID.toString()));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(assignmentAccountLookup, "edit",assignmentAccountId.toString());
		Assert.assertTrue("Maintenance Page contains test assignmentAccount",maintPage.asText().contains(TEST_ASSIGN_ID.toString()));	 
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
		KRADServiceLocator.getBusinessObjectService().save(assignmentAccount);
		assignmentAccountId = assignmentAccount.getTkAssignAcctId();
	}

	@Override
	public void tearDown() throws Exception {				
		AssignmentAccount assignmentAccountObj = KRADServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(AssignmentAccount.class, assignmentAccountId);		 
		KRADServiceLocator.getBusinessObjectService().delete(assignmentAccountObj);
		super.tearDown();
	}
	
}


