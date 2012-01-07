package org.kuali.hr.time.department.earncode.service;

import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.kuali.hr.time.dept.earncode.DepartmentEarnCode;
import org.kuali.hr.time.dept.earncode.service.DepartmentEarnCodeService;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.TkTestCase;

public class DepartmentEarnCodeServiceImplTest extends TkTestCase {

	public static final String TEST_TEST_DEPT = "TEST-DEPT";
	public static final String TEST_LORA = "LORA-DEPT";
	public static final String TEST_SAL_GROUP_A10 = "A10";
	public static final String TEST_SAL_GROUP_A = "A";
	public static final String TEST_LOCATION = "";
	private static final java.sql.Date TEST_DATE=new java.sql.Date(Calendar.getInstance().getTimeInMillis());
	private DepartmentEarnCodeService departmentEarnCodeService = null;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		departmentEarnCodeService=TkServiceLocator.getDepartmentEarnCodeService();
	}
	
	@Test
	public void testGetDepartmentEarnCodes() throws Exception {		
		// Testing Wildcard on department.
		List<DepartmentEarnCode> departmentEarnCodes = departmentEarnCodeService.getDepartmentEarnCodes(TEST_LORA, TEST_SAL_GROUP_A10, TEST_LOCATION, TEST_DATE);
		assertEquals("Wrong number of earn codes returned.", 5, departmentEarnCodes.size());
		// Test sorting of earn codes
		assertEquals("First Earn Code should be RGH", "RGH", departmentEarnCodes.get(0).getEarnCode());
		assertEquals("Second Earn Code should be SCK", "SCK", departmentEarnCodes.get(1).getEarnCode());
		assertEquals("Third Earn Code should be VAC", "VAC", departmentEarnCodes.get(2).getEarnCode());
		assertEquals("Forth Earn Code should be XYZ", "XYZ", departmentEarnCodes.get(3).getEarnCode());
		assertEquals("Fifth Earn Code should be XZZ", "XZZ", departmentEarnCodes.get(4).getEarnCode());
		
		for (DepartmentEarnCode ec : departmentEarnCodes) {
			assertTrue("Wrong department earn code.", ((ec.getDept()).equals("LORA-DEPT") || (ec.getDept()).equals("%")) );
			assertTrue("Wrong SAL_GROUP.", (ec.getHrSalGroup()).equals(TEST_SAL_GROUP_A10) || (ec.getHrSalGroup()).equals("%") );
		}
		
		// Testing Wildcard on dept and salGroup.
		List<DepartmentEarnCode> departmentEarnCodes1 = departmentEarnCodeService.getDepartmentEarnCodes(TEST_TEST_DEPT, TEST_SAL_GROUP_A, TEST_LOCATION, TEST_DATE);
		assertEquals("Wrong number of earn codes returned.", 2, departmentEarnCodes1.size());
		
		for (DepartmentEarnCode ec1 : departmentEarnCodes1) {
			assertTrue("Wrong department earn code.", ((ec1.getDept()).equals(TEST_TEST_DEPT) || (ec1.getDept()).equals("%")) );
			assertTrue("Wrong SAL_GROUP.", (ec1.getHrSalGroup()).equals(TEST_SAL_GROUP_A) || (ec1.getHrSalGroup()).equals("%") );
		}
	}

}
