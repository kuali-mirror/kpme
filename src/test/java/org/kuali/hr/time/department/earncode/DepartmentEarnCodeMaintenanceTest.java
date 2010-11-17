package org.kuali.hr.time.department.earncode;

import java.util.Random;

import org.apache.ojb.broker.PersistenceBrokerFactory;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.junit.Test;

import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.dept.earncode.DepartmentEarnCode;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.salgroup.SalGroup;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.rice.kns.service.KNSServiceLocator;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class DepartmentEarnCodeMaintenanceTest extends TkTestCase{
	
	private static String TEST_CODE_INVALID_DEPT_ID ="0";
	private static Long TEST_CODE_INVALID_EARN_CODE_ID =0L;
	private static Long TEST_CODE_INVALID_SAL_GROUP_ID =0L;
	
	private static Long departmentEarnCodeId;

	//TODO Sai - confirm this test is appropriate

	@Test
	public void testDepartmentEarnCodeMaint() throws Exception {
		HtmlPage deptEarnCodeLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.DEPARTMENT_EARN_CODE_MAINT_URL);
		deptEarnCodeLookup = HtmlUnitUtil.clickInputContainingText(deptEarnCodeLookup, "search");
		assertTrue("Page contains test DepartmentEarnCode", deptEarnCodeLookup.asText().contains(TEST_CODE_INVALID_DEPT_ID.toString()));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(deptEarnCodeLookup, "edit",departmentEarnCodeId.toString());
		
		
		assertTrue("Maintenance Page contains test DepartmentEarnCode",maintPage.asText().contains(TEST_CODE_INVALID_DEPT_ID.toString()));
	}
	
	
	@Test
	public void testDepartmentEarnCodeMaintForErrorMessages() throws Exception {
		HtmlPage deptEarnCodeLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.DEPARTMENT_EARN_CODE_MAINT_URL);
		deptEarnCodeLookup = HtmlUnitUtil.clickInputContainingText(deptEarnCodeLookup, "search");
		assertTrue("Page contains test DepartmentEarnCode", deptEarnCodeLookup.asText().contains(TEST_CODE_INVALID_DEPT_ID.toString()));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(deptEarnCodeLookup, "edit",departmentEarnCodeId.toString());
		
		HtmlInput inputForDescription = HtmlUnitUtil.getInputContainingText(
				maintPage, "* Document Description");
		inputForDescription.setValueAttribute("Test_Description");
		HtmlPage resultantPageAfterEdit = HtmlUnitUtil
				.clickInputContainingText(maintPage, "submit");
		
		assertTrue("Maintenance Page contains test deptErrormessage",
				resultantPageAfterEdit.asText().contains(
						"The specified Department '"
								+ TEST_CODE_INVALID_DEPT_ID
								+ "' does not exist."));
		
		assertTrue("Maintenance Page contains test SalGroupErrormessage",
				resultantPageAfterEdit.asText().contains(
						"The specified Salgroup '"
								+ TEST_CODE_INVALID_SAL_GROUP_ID
								+ "' does not exist."));
		
		assertTrue("Maintenance Page contains test Earncode",
				resultantPageAfterEdit.asText().contains(
						"The specified Earncode '"
								+ TEST_CODE_INVALID_EARN_CODE_ID
								+ "' does not exist."));
		
		
		
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		DepartmentEarnCode departmentEarnCode = new DepartmentEarnCode();
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
		departmentEarnCodeId=departmentEarnCode.getTkDeptEarnCodeId();
		
	}

	@Override
	public void tearDown() throws Exception {
		//clean up
		DepartmentEarnCode departmentEarnCodeObj= KNSServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(DepartmentEarnCode.class, departmentEarnCodeId);
		KNSServiceLocator.getBusinessObjectService().delete(departmentEarnCodeObj);
		super.tearDown();
	}
}
