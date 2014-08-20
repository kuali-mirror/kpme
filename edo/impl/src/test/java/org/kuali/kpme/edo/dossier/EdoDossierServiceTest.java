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
package org.kuali.kpme.edo.dossier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.kpme.core.IntegrationTest;
import org.kuali.kpme.edo.EdoUnitTestBase;
import org.kuali.kpme.edo.api.dossier.EdoDossier;
import org.kuali.kpme.edo.service.EdoServiceLocator;
@IntegrationTest
public class EdoDossierServiceTest extends EdoUnitTestBase {	

	@Before
	public void setUp() throws Exception {
		
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		
		super.tearDown();
	}

    @Ignore
	@Test
	public void testGetCurrentDossierPrincipalname() throws Exception {

		EdoDossier edoDossier = (EdoDossier)EdoServiceLocator.getEdoDossierService().getCurrentDossierPrincipalName("admin");
		
		assertEquals("admin", edoDossier.getCandidatePrincipalName());
	}
	
	@Test
	public void testGetEdoDossierById() throws Exception {

		EdoDossier edoDossier = (EdoDossier)EdoServiceLocator.getEdoDossierService().getEdoDossierById("1000");
		
		assertEquals("admin", edoDossier.getCandidatePrincipalName());
	}
	
	@Test
	public void testGetDossierList() throws Exception {
		List<EdoDossier> edoDossiers = new ArrayList<EdoDossier>();
				
		edoDossiers = EdoServiceLocator.getEdoDossierService().getDossierList();
			
		assertTrue(edoDossiers.size() == 2);
		
		if ( edoDossiers.size() == 2) {
			assertEquals("admin", edoDossiers.get(0).getCandidatePrincipalName());
			assertEquals("earncode", edoDossiers.get(1).getCandidatePrincipalName());
		}
	}
	
}
