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
package org.kuali.kpme.edo.dossier.type;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kuali.kpme.core.IntegrationTest;
import org.kuali.kpme.edo.EdoUnitTestBase;
import org.kuali.kpme.edo.api.dossier.type.EdoDossierType;
import org.kuali.kpme.edo.service.EdoServiceLocator;

@IntegrationTest
public class EdoDossierTypeServiceTest extends EdoUnitTestBase {	

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Test
	public void testGetEdoDossierTypeById() throws Exception {

		EdoDossierType edoDossierType = (EdoDossierType)EdoServiceLocator.getEdoDossierTypeService().getEdoDossierTypeById("1000");
		
		assertEquals("aa", edoDossierType.getDossierTypeCode());
	}
	
	@Test
	public void testGetEdoDossierTypeByCode() throws Exception {

		EdoDossierType edoDossierType = (EdoDossierType)EdoServiceLocator.getEdoDossierTypeService().getEdoDossierTypeByCode("bb");
		
		assertEquals("bb", edoDossierType.getDossierTypeCode());
	}
	
	@Test
	public void testGetEdoDossierTypeByName() throws Exception {

		EdoDossierType edoDossierType = (EdoDossierType)EdoServiceLocator.getEdoDossierTypeService().getEdoDossierTypeByName("dossierTypeName0");
		
		assertEquals("dossierTypeName0", edoDossierType.getDossierTypeName());
	}
	
	@Test
	public void testGetEdoDossierTypeList() throws Exception {
		List<EdoDossierType> edoDossierTypes = new ArrayList<EdoDossierType>();
				
		edoDossierTypes = EdoServiceLocator.getEdoDossierTypeService().getEdoDossierTypeList();
			
		assertTrue(edoDossierTypes.size() == 2);
		
		if ( edoDossierTypes.size() == 2) {
			assertEquals("aa", edoDossierTypes.get(0).getDossierTypeCode());
			assertEquals("bb", edoDossierTypes.get(1).getDossierTypeCode());
		}
	}
	
}
