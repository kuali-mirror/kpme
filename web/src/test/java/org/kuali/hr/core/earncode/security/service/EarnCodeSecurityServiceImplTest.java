/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.hr.core.earncode.security.service;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.hr.KPMEWebTestCase;
import org.kuali.kpme.core.FunctionalTest;
import org.kuali.kpme.core.api.earncode.security.EarnCodeSecurityContract;
import org.kuali.kpme.core.api.earncode.security.service.EarnCodeSecurityService;
import org.kuali.kpme.core.service.HrServiceLocator;

@FunctionalTest
public class EarnCodeSecurityServiceImplTest extends KPMEWebTestCase {

	public static final String TEST_TEST_DEPT = "TEST-DEPT";
	public static final String TEST_LORA = "LORA-DEPT";
	public static final String TEST_SAL_GROUP_A10 = "A10";
	public static final String TEST_SAL_GROUP_A = "A";
	public static final String TEST_LOCATION = "";
	private static final DateTime TEST_DATE = new DateTime();
	private EarnCodeSecurityService earnCodeSecurityService = null;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		earnCodeSecurityService=HrServiceLocator.getEarnCodeSecurityService();
	}
	
	@Test
	public void testGetEarnCodeSecurities() throws Exception {		
		// Testing Wildcard on department.
		List<? extends EarnCodeSecurityContract> earnCodeSecurities = earnCodeSecurityService.getEarnCodeSecurities(TEST_LORA, TEST_SAL_GROUP_A10, TEST_LOCATION, TEST_DATE.toLocalDate());
		Assert.assertEquals("Wrong number of earn codes returned.", 5, earnCodeSecurities.size());
		// Test sorting of earn codes
		Assert.assertEquals("First Earn Code should be RGH", "RGH", earnCodeSecurities.get(0).getEarnCode());
		Assert.assertEquals("Second Earn Code should be SCK", "SCK", earnCodeSecurities.get(1).getEarnCode());
		Assert.assertEquals("Third Earn Code should be VAC", "VAC", earnCodeSecurities.get(2).getEarnCode());
		Assert.assertEquals("Forth Earn Code should be XYZ", "XYZ", earnCodeSecurities.get(3).getEarnCode());
		Assert.assertEquals("Fifth Earn Code should be XZZ", "XZZ", earnCodeSecurities.get(4).getEarnCode());
		
		for (EarnCodeSecurityContract ec : earnCodeSecurities) {
			Assert.assertTrue("Wrong department earn code.", ((ec.getDept()).equals("LORA-DEPT") || (ec.getDept()).equals("%")) );
			Assert.assertTrue("Wrong SAL_GROUP.", (ec.getHrSalGroup()).equals(TEST_SAL_GROUP_A10) || (ec.getHrSalGroup()).equals("%") );
		}
		
		// Testing Wildcard on dept and salGroup.
		List<? extends EarnCodeSecurityContract> earnCodesSec1 = earnCodeSecurityService.getEarnCodeSecurities(TEST_TEST_DEPT, TEST_SAL_GROUP_A, TEST_LOCATION, TEST_DATE.toLocalDate());
		Assert.assertEquals("Wrong number of earn codes returned.", 2, earnCodesSec1.size());
		
		for (EarnCodeSecurityContract ec1 : earnCodesSec1) {
			Assert.assertTrue("Wrong department earn code.", ((ec1.getDept()).equals(TEST_TEST_DEPT) || (ec1.getDept()).equals("%")) );
			Assert.assertTrue("Wrong SAL_GROUP.", (ec1.getHrSalGroup()).equals(TEST_SAL_GROUP_A) || (ec1.getHrSalGroup()).equals("%") );
		}
	}
	
	@Ignore
	@Test
	public void testSearchEarnCodeSecurities() throws Exception {
		List<? extends EarnCodeSecurityContract> allResults = HrServiceLocator.getEarnCodeSecurityService().searchEarnCodeSecurities("admin", null, null, null, null, null, null, "Y", "N");
		Assert.assertEquals("Search returned the wrong number of results.", 19, allResults.size());
		
		List<? extends EarnCodeSecurityContract> restrictedResults = HrServiceLocator.getEarnCodeSecurityService().searchEarnCodeSecurities("testuser6", null, null, null, null, null, null, "Y", "N");
		Assert.assertEquals("Search returned the wrong number of results.", 11, restrictedResults.size());
	}
	
	@Test
	public void testGetEarnCodeSecurityList() throws Exception {
		// wild card for dept and sal_group
		List<? extends EarnCodeSecurityContract> allResults = HrServiceLocator.getEarnCodeSecurityService().getEarnCodeSecurityList("test", "test", "XZZ", "Y", null, null, null, "Y", TEST_DATE.toLocalDate());
		Assert.assertEquals("Search returned the wrong number of results.", 1, allResults.size());
		
		// wild card for dept
		allResults = HrServiceLocator.getEarnCodeSecurityService().getEarnCodeSecurityList("test", "test", "XYZ", "Y", null, null, null, "Y", TEST_DATE.toLocalDate());
		Assert.assertEquals("Search returned the wrong number of results.", 0, allResults.size());
		
		allResults = HrServiceLocator.getEarnCodeSecurityService().getEarnCodeSecurityList("test", "A10", "XYZ", "Y", null, null, null, "Y", TEST_DATE.toLocalDate());
		Assert.assertEquals("Search returned the wrong number of results.", 1, allResults.size());
		
		// wild card for loction
		allResults = HrServiceLocator.getEarnCodeSecurityService().getEarnCodeSecurityList("TEST-DEPT", "SD1", "VAC", "Y", null, null, "%", "Y", TEST_DATE.toLocalDate());
		Assert.assertEquals("Search returned the wrong number of results.", 1, allResults.size());
	}

}