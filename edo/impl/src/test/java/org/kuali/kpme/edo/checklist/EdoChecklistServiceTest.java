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
package org.kuali.kpme.edo.checklist;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kuali.kpme.core.IntegrationTest;
import org.kuali.kpme.edo.EdoUnitTestBase;
import org.kuali.kpme.edo.api.checklist.EdoChecklist;
import org.kuali.kpme.edo.service.EdoServiceLocator;

@IntegrationTest
public class EdoChecklistServiceTest extends EdoUnitTestBase {

	private final String edoChecklistId = "EDO_CHECKLIST_ID_0001";

	@Before
	public void setUp() throws Exception {
	super.setUp();
	
	}
	
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testGetEdoChecklistById() throws Exception {

		EdoChecklist edoChecklist = EdoServiceLocator.getChecklistService().getChecklistByID(edoChecklistId);
		assertEquals("TA", edoChecklist.getDossierTypeCode());
		assertEquals("DEFAULT", edoChecklist.getDepartmentID());
	}
}