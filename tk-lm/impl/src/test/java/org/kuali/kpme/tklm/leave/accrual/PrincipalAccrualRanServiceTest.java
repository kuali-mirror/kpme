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
package org.kuali.kpme.tklm.leave.accrual;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.TKLMUnitTestCase;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;

@Ignore
public class PrincipalAccrualRanServiceTest extends TKLMUnitTestCase {
	
	/**
	 * 
	 * Test fails. This test uses data that materializes during the test suite lifecycle - likely from AccrualServiceTest.
	 * 
	 * Must manually enter a last ran entry, then run an accrual, so that the location of this test in the suites sequence does not
	 * affect results.
	 */
	@Test
	public void testUpdateInfo() {
		// the database has an entry for principalId testUser dated 2012-05-01
		// run accrual service for testUser
		// the principalAccrualRan entry in database should be changed to today's timestamp
		PrincipalAccrualRan par = LmServiceLocator.getPrincipalAccrualRanService().getLastPrincipalAccrualRan("testUser");
		Assert.assertNotNull("There should be one entry in PrincipalAccrualRan table for 'testUser'", par);
		DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy");
		Assert.assertTrue("Date of the original entry in PrincipalAccrualRan for 'testUser' should be 05/01/2012"
				, formatter.print(par.getLastRanDateTime()).equals("05/01/2012"));
		
		DateTime startDate = new DateTime(2012, 2, 20, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());
		DateTime endDate = new DateTime(2012, 5, 3, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());
		
		boolean rerunFlag = LmServiceLocator.getLeaveAccrualService().statusChangedSinceLastRun("testUser");
		Assert.assertTrue("Status should be changed for 'testUser'", rerunFlag);

        LmServiceLocator.getLeaveAccrualService().runAccrual("testUser", startDate, endDate, true);
        par = LmServiceLocator.getPrincipalAccrualRanService().getLastPrincipalAccrualRan("testUser");
		Assert.assertTrue("Date of the original entry in PrincipalAccrualRan for 'testUser' should be current date"
				, formatter.print(par.getLastRanDateTime()).equals(formatter.print(LocalDate.now())));
		
	}

}
