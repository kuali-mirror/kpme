/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.pm.position;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kuali.kpme.core.IntegrationTest;
import org.kuali.kpme.pm.PMIntegrationTestCase;
import org.kuali.kpme.pm.api.position.PositionContract;
import org.kuali.kpme.pm.service.base.PmServiceLocator;

@IntegrationTest
public class PositionServiceTest extends PMIntegrationTestCase {

	private final String hrPositionId = "1";
	private final String positionNum = "1";
	private final String description = "testPosition";
	private final String institution = "IU";
	private final String location = "IN";
	private final String classificationTitle = "HOURLY";
	private final String positionType = "HOURLY";
	private final String poolEligible = "N";
	private final String positionStatus = "NEW";
	private final String active = "Y";
	private final String showHistory = "Y";

	@Before
	public void setUp() throws Exception {
	super.setUp();
	
	}
	
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Test
	public void testGetPositionById() throws Exception {

		PositionContract positionContract = (PositionContract)PmServiceLocator.getPositionService().getPosition(hrPositionId);
		
		assertEquals("1", positionContract.getPositionNumber());
	}
	
	
	@Test
	public void testGetPositions() throws Exception {
		List<? extends PositionContract> positionContracts = PmServiceLocator.getPositionService().getPositions(
				positionNum, description, location, institution, classificationTitle, 
				positionType, poolEligible, positionStatus, null, null, active, showHistory);
			
		assertTrue(positionContracts.size() == 1);
	}
	
}
