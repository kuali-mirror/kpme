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
package org.kuali.kpme.edo.group;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kuali.kpme.core.IntegrationTest;
import org.kuali.kpme.edo.EdoUnitTestBase;
import org.kuali.kpme.edo.api.group.EdoGroupDefinition;
import org.kuali.kpme.edo.api.group.EdoGroupTracking;
import org.kuali.kpme.edo.api.item.type.EdoItemType;
import org.kuali.kpme.edo.item.type.EdoItemTypeBo;
import org.kuali.kpme.edo.service.EdoServiceLocator;

@IntegrationTest
public class EdoGroupTrackingServiceTest extends EdoUnitTestBase {

	private final String edoGroupTrackingId = "1000";

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testGetEdoGroupTracking() throws Exception {
		EdoGroupTracking edoGroupTracking = EdoServiceLocator.getEdoGroupTrackingService().getEdoGroupTracking(edoGroupTrackingId);
		assertEquals("DEFAULT_DEPT", edoGroupTracking.getDepartmentId());
	}
	
	@Test
	public void testGetEdoGroupTrackingByGroupName() throws Exception {		
		EdoGroupTracking edoGroupTracking = EdoServiceLocator.getEdoGroupTrackingService().getEdoGroupTrackingByGroupName("SCHOOL");
		assertEquals("1001", edoGroupTracking.getEdoGroupTrackingId());
	}
	
	@Test
	public void testGetEdoGroupTrackingByDepartmentId() throws Exception {		
		List<EdoGroupTracking> edoGroupTrackings = EdoServiceLocator.getEdoGroupTrackingService().getEdoGroupTrackingByDepartmentId("DEFAULT_DEPT");
		assertEquals("returned a correct number of result", 3, edoGroupTrackings.size());
	}
	
	@Test
	public void testGetEdoGroupTrackingBySchoolId() throws Exception {		
		List<EdoGroupTracking> edoGroupTrackings = EdoServiceLocator.getEdoGroupTrackingService().getEdoGroupTrackingBySchoolId("DEFAULT_OG");
		assertEquals("returned a correct number of result", 3, edoGroupTrackings.size());
	}
	
	// TODO Find out if this is needed.  Maybe a unit test for group key??
	/*
    @Test
	public void testGetEdoGroupTrackingByCampusId() throws Exception {}*/
    
	@Test
	public void testKimGroupTrackingExists() throws Exception {		
		boolean exist = EdoServiceLocator.getEdoGroupTrackingService().kimGroupTrackingExists("DEAN");
		assertEquals(true, exist);
	}
	
	@Test
	public void testSaveOrUpdate() throws Exception {
		
		EdoGroupTracking edoGroupTracking = EdoServiceLocator.getEdoGroupTrackingService().getEdoGroupTracking(edoGroupTrackingId);
		EdoGroupTrackingBo edoGroupTrackingBo = EdoGroupTrackingBo.from(edoGroupTracking);
		edoGroupTrackingBo.setGroupKeyCode("UGA-GA");
		EdoServiceLocator.getEdoGroupTrackingService().saveOrUpdate(edoGroupTrackingBo);
		
		edoGroupTracking = EdoServiceLocator.getEdoGroupTrackingService().getEdoGroupTracking(edoGroupTrackingId);
		assertEquals("UGA-GA", edoGroupTracking.getGroupKeyCode());
	}    
}