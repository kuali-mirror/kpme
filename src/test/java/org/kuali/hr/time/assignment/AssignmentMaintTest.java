package org.kuali.hr.time.assignment;


import org.junit.Test;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kns.service.KNSServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class AssignmentMaintTest extends org.kuali.hr.time.test.TkTestCase {
	
	//data defined in boot strap script
	private static final String TEST_CODE="admin";
	private static Long TEST_CODE_WORKAREA_INVALID=5555L;
	private static Long assignmentId = 3L;	
	
	@Test
	public void testAssignmentMaint() throws Exception {
		HtmlPage assignmentLookUp = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.ASSIGNMENT_MAINT_URL);
		assignmentLookUp = HtmlUnitUtil.clickInputContainingText(assignmentLookUp, "search");
		assertTrue("Page contains test assignment", assignmentLookUp.asText().contains(TEST_CODE.toString()));		
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(assignmentLookUp, "edit",assignmentId.toString());		
		assertTrue("Maintenance Page contains test assignment",maintPage.asText().contains(TEST_CODE.toString()));		
	}

	/**
	 * Test to check whether it is showing error message for WorkArea on maintenance screen
	 * if we supply non exist workArea
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAssignmentMaintForWorkAreaErrorMessage()
			throws Exception {
		HtmlPage assignmentLookup = HtmlUnitUtil
				.gotoPageAndLogin(TkTestConstants.Urls.ASSIGNMENT_MAINT_URL);
		assignmentLookup = HtmlUnitUtil.clickInputContainingText(
				assignmentLookup, "search");
		assertTrue("Page contains test timeCollectionRule",
				assignmentLookup.asText().contains(TEST_CODE));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(
				assignmentLookup, "edit",
				assignmentId.toString());
		HtmlInput inputForWorkArea = HtmlUnitUtil.getInputContainingText(maintPage,
				Long.toString(TEST_CODE_WORKAREA_INVALID));
		inputForWorkArea.setValueAttribute(Long.toString(TEST_CODE_WORKAREA_INVALID));
		HtmlInput inputForDescription = HtmlUnitUtil.getInputContainingText(
				maintPage, "* Document Description");
		inputForDescription.setValueAttribute("Description");
		HtmlPage resultantPageAfterEdit = HtmlUnitUtil
				.clickInputContainingText(maintPage, "submit");
		System.out.println(resultantPageAfterEdit.asText());
		assertTrue("Maintenance Page contains contains error message for workarea",
				resultantPageAfterEdit.asText().contains(
						"The specified workarea '" + TEST_CODE_WORKAREA_INVALID
								+ "' does not exist."));
	}
	
	@Override
	public void tearDown() throws Exception {		
		Assignment assignmentObj= KNSServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(Assignment.class, assignmentId);			
		 KNSServiceLocator.getBusinessObjectService().delete(assignmentObj);				
		super.tearDown();
	}
}
