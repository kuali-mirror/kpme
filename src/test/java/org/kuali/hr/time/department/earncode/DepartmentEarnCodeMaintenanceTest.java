package org.kuali.hr.time.department.earncode;

import java.sql.Date;

import org.joda.time.DateTime;
import org.junit.Test;
import org.kuali.hr.time.dept.earncode.DepartmentEarnCode;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kns.service.KNSServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class DepartmentEarnCodeMaintenanceTest extends TkTestCase{
	
	private static String TEST_CODE_INVALID_DEPT_ID ="INVALID";
	private static String TEST_CODE_INVALID_EARN_CODE_ID ="INV";
	private static String TEST_CODE_INVALID_SAL_GROUP_ID ="INVALID";
	private static final java.sql.Date TEST_DATE = new Date((new DateTime(2009, 1, 1, 0, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE)).getMillis());
	private static final String EARN_CODE = "RGN";
	private static final String DEPT = "TEST-DEPT";
	private static final String SAL_GROUP = "SD1";
	
	private static Long departmentEarnCodeId = 1002L;

	private static Long tkDeptEarnCodeId;
	private static Long dupTkDeptEarnCodeId;

	@Test
	public void testDepartmentEarnCodeMaint() throws Exception {
		HtmlPage deptEarnCodeLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.DEPARTMENT_EARN_CODE_MAINT_URL);
		deptEarnCodeLookup = HtmlUnitUtil.clickInputContainingText(deptEarnCodeLookup, "search");
		HtmlUnitUtil.createTempFile(deptEarnCodeLookup);
		assertTrue("Page contains test DepartmentEarnCode", deptEarnCodeLookup.asText().contains(DEPT.toString()));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(deptEarnCodeLookup, "edit");	
		assertTrue("Maintenance Page contains test DepartmentEarnCode",maintPage.asText().contains(DEPT));
	}
	
	@Test
	public void testDepartmentEarnCodeMaintForErrorMessages() throws Exception {
		HtmlPage deptEarnCodeLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.DEPARTMENT_EARN_CODE_MAINT_URL);
		deptEarnCodeLookup = HtmlUnitUtil.clickInputContainingText(deptEarnCodeLookup, "search");
		assertTrue("Page contains test DepartmentEarnCode", deptEarnCodeLookup.asText().contains(DEPT.toString()));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(deptEarnCodeLookup, "edit");
		
		HtmlInput inputForDescription = HtmlUnitUtil.getInputContainingText(
				maintPage, "* Document Description");
		inputForDescription.setValueAttribute("Test_Description");
		HtmlPage resultantPageAfterEdit = HtmlUnitUtil
				.clickInputContainingText(maintPage, "submit");		
		HtmlUnitUtil.createTempFile(resultantPageAfterEdit);
		assertTrue("Maintenance Page contains test deptErrormessage",
				resultantPageAfterEdit.asText().contains(
						"The specified department '"
								+ DEPT
								+ "' does not exist."));
		
		assertTrue("Maintenance Page contains test SalGroupErrormessage",
				resultantPageAfterEdit.asText().contains(
						"The specified Salgroup '"
								+ SAL_GROUP
								+ "' does not exist."));	
		
		assertTrue("Maintenance Page contains test Earncode",
				resultantPageAfterEdit.asText().contains(
						"The specified Earncode '"
								+ EARN_CODE
								+ "' does not exist."));
				
		
	}
	
	@Test
	public void testEditingDepartmentEarnCodeMaint() throws Exception {
		HtmlPage deptEarnCodeLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.DEPARTMENT_EARN_CODE_MAINT_URL);
		deptEarnCodeLookup = HtmlUnitUtil.clickInputContainingText(deptEarnCodeLookup, "search");
		assertTrue("Page contains TEST-DEPT", deptEarnCodeLookup.asText().contains(DEPT));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(deptEarnCodeLookup, "edit", tkDeptEarnCodeId.toString());
		assertTrue("Maintenance Page contains Warnings",maintPage.asText().contains("Warnings for this Section:"));
		assertTrue("Maintenance Page contains Warning message",maintPage.asText().contains("There is a newer version of this Department Earn Code."));
		
		this.createDuplicateDeptEarnCode();
		deptEarnCodeLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.DEPARTMENT_EARN_CODE_MAINT_URL);
		deptEarnCodeLookup = HtmlUnitUtil.clickInputContainingText(deptEarnCodeLookup, "search");
		maintPage = HtmlUnitUtil.clickAnchorContainingText(deptEarnCodeLookup, "edit", dupTkDeptEarnCodeId.toString());
		assertTrue("Maintenance Page contains Warnings",maintPage.asText().contains("Warnings for this Section:"));
		assertTrue("Maintenance Page contains Warning message",maintPage.asText().contains("There is a newer version of this Department Earn Code."));
		assertTrue("Maintenance Page contains Warning message",maintPage.asText().contains("There is an exact duplicate version of this Department Earn Code."));
	}
	
	public void createNewDeptEarnCode() {
		DepartmentEarnCode deptEarnCode = new DepartmentEarnCode();
		deptEarnCode.setActive(true);
		deptEarnCode.setEarnCode(EARN_CODE);
		deptEarnCode.setEffectiveDate(TEST_DATE);
		deptEarnCode.setDept(DEPT);
		deptEarnCode.setTkSalGroup(SAL_GROUP);
		deptEarnCode.setEmployee(false);
		deptEarnCode.setEffectiveDate(TEST_DATE);
		deptEarnCode.setLocation("test");
		
		KNSServiceLocator.getBusinessObjectService().save(deptEarnCode);	
		tkDeptEarnCodeId = deptEarnCode.getTkDeptEarnCodeId();	
	}
	
	public void createDuplicateDeptEarnCode() {
		DepartmentEarnCode deptEarnCode = new DepartmentEarnCode();
		deptEarnCode.setActive(true);
		deptEarnCode.setEarnCode(EARN_CODE);
		deptEarnCode.setEffectiveDate(TEST_DATE);
		deptEarnCode.setDept(DEPT);
		deptEarnCode.setTkSalGroup(SAL_GROUP);
		deptEarnCode.setEmployee(false);
		deptEarnCode.setEffectiveDate(TEST_DATE);
		deptEarnCode.setLocation("test");
		
		KNSServiceLocator.getBusinessObjectService().save(deptEarnCode);	
		dupTkDeptEarnCodeId = deptEarnCode.getTkDeptEarnCodeId();	
	}
	
	@Override
	public void tearDown() throws Exception {
		DepartmentEarnCode deptEarnCodeObj = KNSServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(DepartmentEarnCode.class, tkDeptEarnCodeId);			
		KNSServiceLocator.getBusinessObjectService().delete(deptEarnCodeObj);
		deptEarnCodeObj = KNSServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(DepartmentEarnCode.class, dupTkDeptEarnCodeId);			
		KNSServiceLocator.getBusinessObjectService().delete(deptEarnCodeObj);
		super.tearDown();
	}
	

	@Override
	public void setUp() throws Exception {
		super.setUp();
		this.createNewDeptEarnCode();
		
		/*DepartmentEarnCode departmentEarnCode = new DepartmentEarnCode();
		departmentEarnCode.setApprover(true);
		Random randomObj = new Random();
		//search for the dept which doesn't exist
		for (;;) {
			long deptIdIndex = randomObj.nextInt();
			
			Criteria crit = new Criteria();
			crit.addEqualTo("dept", deptIdIndex);		
			Query query = QueryFactory.newQuery(Department.class, crit);
			int count = PersistenceBrokerFactory.defaultPersistenceBroker().getCount(query);		
			
		 
			if (count == 0) {
				TEST_CODE_INVALID_DEPT_ID = Long.toString(deptIdIndex);
				break;
			}
			
		}
		departmentEarnCode.setDept(TEST_CODE_INVALID_DEPT_ID);
		//search for the earnCode which doesn't exist
		for (;;) {
			long earnCodeIndex = randomObj.nextInt();
			if(Long.toString(earnCodeIndex).length() > 3 ){
				String earnCodeIndexStr = Long.toString(earnCodeIndex);
				earnCodeIndex = Long.parseLong(earnCodeIndexStr.substring(0, 2)); 
				
			}
			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			Criteria crit = new Criteria();
			crit.addEqualTo("earnCode", earnCodeIndex);		
			Query query = QueryFactory.newQuery(EarnCode.class, crit);
			int count = PersistenceBrokerFactory.defaultPersistenceBroker().getCount(query);
			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			if (count == 0 ) {
				TEST_CODE_INVALID_EARN_CODE_ID = earnCodeIndex;
				break;
			}
		}		
		departmentEarnCode.setEarnCode(TEST_CODE_INVALID_EARN_CODE_ID.toString());
		//search for the salGroup which doesn't exist
		for (;;) {
			long salGroupIndex = randomObj.nextInt();
			Criteria crit = new Criteria();
			crit.addEqualTo("tkSalGroup", departmentEarnCode.getEarnCode());		
			Query query = QueryFactory.newQuery(SalGroup.class, crit);
			int count = PersistenceBrokerFactory.defaultPersistenceBroker().getCount(query);	
			if(count == 0){
				TEST_CODE_INVALID_SAL_GROUP_ID = salGroupIndex;
				break;
			}
		}
		departmentEarnCode.setTkSalGroup(TEST_CODE_INVALID_SAL_GROUP_ID.toString());
		departmentEarnCode.setEmployee(false);
		departmentEarnCode.setOrg_admin(false);
		KNSServiceLocator.getBusinessObjectService().save(departmentEarnCode);
		departmentEarnCodeId=departmentEarnCode.getTkDeptEarnCodeId();*/
	}
}
