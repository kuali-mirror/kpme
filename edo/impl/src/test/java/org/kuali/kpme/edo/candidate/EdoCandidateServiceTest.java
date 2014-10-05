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
package org.kuali.kpme.edo.candidate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kuali.kpme.core.IntegrationTest;
import org.kuali.kpme.edo.EdoUnitTestBase;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.api.candidate.EdoCandidate;

@IntegrationTest
public class EdoCandidateServiceTest extends EdoUnitTestBase {	

	@Before
	public void setUp() throws Exception {
		super.setUp();
	
	}
	
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Test
	public void testGetCandidateByPrincipalName() throws Exception {

		EdoCandidate edoCandidate = (EdoCandidate)EdoServiceLocator.getCandidateService().getCandidateByUsername("edo");
		
		assertEquals("edo", edoCandidate.getPrincipalName());
	}
	
	@Test
	public void testGetCandidateByEdoCandidateId() throws Exception {

		EdoCandidate edoCandidate = (EdoCandidate)EdoServiceLocator.getCandidateService().getCandidate("1000");
		
		assertEquals("edo", edoCandidate.getPrincipalName());
	}
	
	
	@Test
	public void testGetCandidateListByPrincipalName() throws Exception {
		List<EdoCandidate> edoCandidates = new ArrayList<EdoCandidate>();
				
		edoCandidates = EdoServiceLocator.getCandidateService().getCandidateListByUsername("abc");
			
		assertTrue(edoCandidates.size() == 1);
		
		if ( edoCandidates.size() == 1) {
			assertEquals("abc", edoCandidates.get(0).getPrincipalName());
		}
	}
	
	@Test
	public void testGetCandidateList() throws Exception {
		List<EdoCandidate> edoCandidates = new ArrayList<EdoCandidate>();
				
		edoCandidates = EdoServiceLocator.getCandidateService().getCandidateList();
			
		assertTrue(edoCandidates.size() == 2);
		
		if ( edoCandidates.size() == 2) {
			assertEquals("edo", edoCandidates.get(0).getPrincipalName());
			assertEquals("abc", edoCandidates.get(1).getPrincipalName());
		}
	}
	
}
