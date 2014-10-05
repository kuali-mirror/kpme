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
import org.junit.Test;
import org.kuali.kpme.core.IntegrationTest;
import org.kuali.kpme.edo.EdoUnitTestBase;
import org.kuali.kpme.edo.api.dossier.EdoDossierDocumentInfo;
import org.kuali.kpme.edo.service.EdoServiceLocator;
@IntegrationTest
public class EdoDossierDocumentInfoServiceTest extends EdoUnitTestBase {	

	@Before
	public void setUp() throws Exception {
		
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		
		super.tearDown();
	}
	
	@Test
	public void testGetEdoDossierDocumentInfoByDocId() throws Exception {

		EdoDossierDocumentInfo edoDossierDocumentInfo = (EdoDossierDocumentInfo)EdoServiceLocator.getEdoDossierDocumentInfoService().getEdoDossierDocumentInfoByDocId("1000");
		
		assertEquals("1000", edoDossierDocumentInfo.getEdoDocumentId());
	}
	
	@Test
	public void testGetEdoDossierDocumentInfoByDossierId() throws Exception {

		EdoDossierDocumentInfo edoDossierDocumentInfo = (EdoDossierDocumentInfo)EdoServiceLocator.getEdoDossierDocumentInfoService().getEdoDossierDocumentInfoByDossierId("1111");
		
		assertEquals("1001", edoDossierDocumentInfo.getEdoDocumentId());
	}
	
	@Test
	public void testGetListOfEdoDossierDocumentInfoByDocType() throws Exception {
		List<EdoDossierDocumentInfo> edoDossierDocumentInfos = new ArrayList<EdoDossierDocumentInfo>();
				
		edoDossierDocumentInfos = EdoServiceLocator.getEdoDossierDocumentInfoService().getEdoDossierDocumentInfoByDocType("1111", "documentTypeName2");
			
		assertTrue(edoDossierDocumentInfos.size() == 2);
		
		if ( edoDossierDocumentInfos.size() == 2) {
			assertEquals("1111", edoDossierDocumentInfos.get(0).getEdoDossierId());
			assertEquals("1111", edoDossierDocumentInfos.get(1).getEdoDossierId());
		}
	}
	
}
