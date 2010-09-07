package org.kuali.hr.time.assignment;


import java.util.Calendar;
import org.junit.Test;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.rice.kns.service.KNSServiceLocator;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class AssignmentMaintTest extends org.kuali.hr.time.test.TkTestCase {
	
	private static final String TEST_CODE="__TEST";
	private static final Long TEST_ID=20L;
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
		assignment.setTaskId(TEST_ID);		
		assignment.setPrincipalId(TEST_CODE);
		//assignment.setJobNumber(1L);		
		//assignment.setWorkAreaId(3L);				
		KNSServiceLocator.getBusinessObjectService().save(assignment);		
		assignmentId=assignment.getAssignmentId();		
	}

	@Override
	public void tearDown() throws Exception {		
		Assignment assignmentObj= KNSServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(Assignment.class, assignmentId);			
		KNSServiceLocator.getBusinessObjectService().delete(assignmentObj);				
		super.tearDown();
	}
}
