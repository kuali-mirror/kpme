package org.kuali.hr.time.assignment;


import java.util.Calendar;
import java.util.Random;

import org.junit.Test;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kns.service.KNSServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class AssignmentMaintTest extends org.kuali.hr.time.test.TkTestCase {
	
	private static final String TEST_CODE="__TEST";
	private static final Long TEST_ID=20L;
	private static Long TEST_CODE_WORKAREA_INVALID;
	private static Long assignmentId;	
	private static final java.sql.Date TEST_DATE=new java.sql.Date(Calendar.getInstance().getTimeInMillis());
	@Test
	public void testAssignmentMaint() throws Exception {
		HtmlPage assignmentLookUp = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.ASSIGNMENT_MAINT_URL);
		assignmentLookUp = HtmlUnitUtil.clickInputContainingText(assignmentLookUp, "search");
		assertTrue("Page contains test assignment", assignmentLookUp.asText().contains(TEST_CODE.toString()));		
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(assignmentLookUp, "edit",assignmentId.toString());		
		assertTrue("Maintenance Page contains test assignment",maintPage.asText().contains(TEST_CODE.toString()));		
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		Assignment assignment = new Assignment();		
		assignment.setActive(true);
		assignment.setEffectiveDate(TEST_DATE);
		assignment.setTask(TEST_ID);		
		assignment.setPrincipalId(TEST_CODE);
		assignment.setJobNumber(TEST_ID);
//		/assignment.setEarnCode(TEST_ID);
		
		Random randomObj = new Random();
		for (;;) {
			long workAreaIndex = randomObj.nextInt();
			WorkArea workAreaObj = KNSServiceLocator.getBusinessObjectService()
					.findBySinglePrimaryKey(WorkArea.class, workAreaIndex);
			if (workAreaObj == null) {
				TEST_CODE_WORKAREA_INVALID = new Long(workAreaIndex);
				break;
			}
		}
		assignment.setWorkArea(TEST_CODE_WORKAREA_INVALID);		
		KNSServiceLocator.getBusinessObjectService().save(assignment);		
		assignmentId=assignment.getTkAssignmentId();		
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
