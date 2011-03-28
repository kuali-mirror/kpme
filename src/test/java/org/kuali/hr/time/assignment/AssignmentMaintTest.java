package org.kuali.hr.time.assignment;


import org.junit.Test;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.rice.kns.service.KNSServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class AssignmentMaintTest extends org.kuali.hr.time.test.TkTestCase {
	
	//data defined in boot strap script
	private static final String TEST_CODE="admin";
	private static Long assignmentId = 3L;	
	
	@Test
	public void testAssignmentMaint() throws Exception {
		HtmlPage assignmentLookUp = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.ASSIGNMENT_MAINT_URL);
		assignmentLookUp = HtmlUnitUtil.clickInputContainingText(assignmentLookUp, "search");
		assertTrue("Page contains test assignment", assignmentLookUp.asText().contains(TEST_CODE.toString()));		
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(assignmentLookUp, "edit",assignmentId.toString());		
		assertTrue("Maintenance Page contains test assignment",maintPage.asText().contains(TEST_CODE.toString()));		
	}

	
	@Override
	public void tearDown() throws Exception {		
		Assignment assignmentObj= KNSServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(Assignment.class, assignmentId);			
		 KNSServiceLocator.getBusinessObjectService().delete(assignmentObj);				
		super.tearDown();
	}
}
