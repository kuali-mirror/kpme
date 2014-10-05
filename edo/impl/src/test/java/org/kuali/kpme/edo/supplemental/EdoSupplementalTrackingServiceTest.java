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
package org.kuali.kpme.edo.supplemental;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kuali.kpme.core.IntegrationTest;
import org.kuali.kpme.edo.EdoUnitTestBase;
import org.kuali.kpme.edo.api.item.EdoItem;
import org.kuali.kpme.edo.api.supplemental.EdoSupplementalTracking;
import org.kuali.kpme.edo.service.EdoServiceLocator;

@IntegrationTest
public class EdoSupplementalTrackingServiceTest extends EdoUnitTestBase {

	private final String edoSupplementalTrackingId = "1000";
	private final String edoDossierId = "1000";
	
	@Before
	public void setUp() throws Exception {
	super.setUp();
	
	}
	
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testGetSupplementalTrackingEntryObj() throws Exception {

		EdoSupplementalTracking edoSupplementalTracking = EdoServiceLocator.getEdoSupplementalTrackingService().getSupplementalTrackingEntryObj(edoDossierId, new BigDecimal("1"));
		assertEquals("1000", edoSupplementalTracking.getEdoSupplementalTrackingId());
	}

	@Test
	public void testSaveOrUpdate() throws Exception {
		
		EdoSupplementalTracking edoSupplementalTracking = EdoServiceLocator.getEdoSupplementalTrackingService().getSupplementalTrackingEntry(edoSupplementalTrackingId);
		EdoSupplementalTrackingBo edoSupplementalTrackingBo = EdoSupplementalTrackingBo.from(edoSupplementalTracking);
		edoSupplementalTrackingBo.setAcknowledged(false);
		EdoServiceLocator.getEdoSupplementalTrackingService().saveOrUpdate(EdoSupplementalTrackingBo.to(edoSupplementalTrackingBo));
		
		edoSupplementalTracking = EdoServiceLocator.getEdoSupplementalTrackingService().getSupplementalTrackingEntry(edoSupplementalTrackingId);
		assertEquals(false, edoSupplementalTracking.isAcknowledged());
	}
	

}