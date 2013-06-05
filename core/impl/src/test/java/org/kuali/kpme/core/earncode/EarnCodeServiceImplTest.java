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
package org.kuali.kpme.core.earncode;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.kuali.kpme.core.KPMEUnitTestCase;
import org.kuali.kpme.core.earncode.service.EarnCodeService;
import org.kuali.kpme.core.service.HrServiceLocator;

public class EarnCodeServiceImplTest extends KPMEUnitTestCase {


	public static final String TEST_USER = "admin";
	public static final Long TEST_ASSIGNMENT_JOB_NUMBER = 1L;
	public static final Long TEST_ASSIGNMENT_JOB_NUMBER_2 = 2L;
	public static final Long TEST_ASSIGNMENT_JOB_NUMBER_3 = 3L;
	public static final Long TEST_ASSIGNMENT_JOB_NUMBER_4 = 4L;

	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(EarnCodeServiceImplTest.class);

	EarnCodeService earnCodeService = null;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		earnCodeService=HrServiceLocator.getEarnCodeService();
	}

	@Test
	public void testGetEarnCodesForDisplay() throws Exception{
        //create the testPrincipal object for the earn code service parm, from the TEST_USER string
        //Principal testPrincipal = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName("testUser");
        //Map<String, String> earnCodesDisplay = earnCodeService.getEarnCodesForDisplay(testPrincipal.getPrincipalId(), false);
//  assertions commented out until earn code service finished. 20120918tv
//		Assert.assertNotNull("earnCodesDisplay should not be null", earnCodesDisplay);
//		Assert.assertEquals("There should be 2 earnCode found for principal_id 'testUser', not " + earnCodesDisplay.size(), earnCodesDisplay.size(), 2);
//		Assert.assertTrue("earnCodesDisplay should contain Value 'EC1 : test1'", earnCodesDisplay.containsValue("EC1 : test1"));
	}
}
