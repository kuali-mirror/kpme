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
import org.kuali.kpme.edo.service.EdoServiceLocator;

@IntegrationTest
public class EdoGroupDefinitionServiceTest extends EdoUnitTestBase {

	private final String edoGroupDefId = "1000";
	private final String edoWorkflowId = "1000";

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testGetEdoGroupDefinition() throws Exception {
		EdoGroupDefinition edoGroupDefinition = EdoServiceLocator.getEdoGroupDefinitionService().getEdoGroupDefinition(edoGroupDefId);
		assertEquals("PT", edoGroupDefinition.getDossierType());
	}
	
	@Test
	public void testGetEdoGroupDefinitionsByWorkflowId() throws Exception {		
		List<EdoGroupDefinition> edoGroupDefinitions = EdoServiceLocator.getEdoGroupDefinitionService().getEdoGroupDefinitionsByWorkflowId(edoWorkflowId);
		assertEquals("returned the correct number of results", 2, edoGroupDefinitions.size());
	}
}