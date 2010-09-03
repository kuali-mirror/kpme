package org.kuali.hr.time.department.lunch.rule;

import java.math.BigDecimal;
import java.util.Calendar;

import org.junit.Test;
import org.kuali.hr.time.dept.lunch.DeptLunchRule;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.rice.kns.service.KNSServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class DepartmentLunchRuleMaintTest extends TkTestCase{

	private static final String TEST_CODE="_TST";
	private static final String TEST_CODE_ONE_CHAR="Y";
	private static final Long TEST_ID=20L;
	private static final BigDecimal TEST_NO=new BigDecimal(10);
	
	private static Long deptLunchRuleId;	
	private static final java.sql.Date TEST_DATE = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
	
	@Test
	public void testDepartmentLunchRuleMaint() throws Exception {
		HtmlPage departmentLunchRuleLookUp = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.DEPT_LUNCH_RULE_MAINT_URL);
		departmentLunchRuleLookUp = HtmlUnitUtil.clickInputContainingText(departmentLunchRuleLookUp, "search");
		assertTrue("Page contains test DepartmentLunchRule", departmentLunchRuleLookUp.asText().contains(TEST_CODE.toString()));		
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(departmentLunchRuleLookUp, "edit",deptLunchRuleId.toString());		
		assertTrue("Maintenance Page contains test DepartmentLunchRule",maintPage.asText().contains(TEST_CODE.toString()));		
	}
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		DeptLunchRule deptLunchRule = new DeptLunchRule();
		deptLunchRule.setActive(true);
		deptLunchRule.setDeptId(TEST_CODE);
		deptLunchRule.setEffectiveDate(TEST_DATE);
		deptLunchRule.setJobNumber(TEST_ID);
		deptLunchRule.setMaxMins(TEST_NO);
		deptLunchRule.setPrincipalId(TEST_CODE);
		deptLunchRule.setRequiredClockFl(TEST_CODE_ONE_CHAR);		
		deptLunchRule.setUserPrincipalId(TEST_CODE);
		deptLunchRule.setWorkArea(TEST_ID);		
		KNSServiceLocator.getBusinessObjectService().save(deptLunchRule);		
		deptLunchRuleId=deptLunchRule.getDeptLunchRuleId();		
	}

	@Override
	public void tearDown() throws Exception {		
		DeptLunchRule deptLunchRuleObj= KNSServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(DeptLunchRule.class, deptLunchRuleId);			
		KNSServiceLocator.getBusinessObjectService().delete(deptLunchRuleObj);				
		super.tearDown();
	}

}
