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
package org.kuali.kpme.edo.reviewlayerdef;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kuali.kpme.core.IntegrationTest;
import org.kuali.kpme.edo.EdoUnitTestBase;
import org.kuali.kpme.edo.api.reviewlayerdef.EdoSuppReviewLayerDefinition;
import org.kuali.kpme.edo.service.EdoServiceLocator;


@IntegrationTest
public class EdoSuppReviewLayerDefinitionServiceTest extends EdoUnitTestBase {	
	
	@Before
	public void setUp() throws Exception {
		
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		
		super.tearDown();
	}
	
	
	@Test
	public void testGetSuppReviewLayerDefinitions() throws Exception {
		
		List<EdoSuppReviewLayerDefinition> edoSuppReviewLayerDefinitions = EdoServiceLocator.getEdoSuppReviewLayerDefinitionService().getSuppReviewLayerDefinitions("1000");
		assertEquals(2, edoSuppReviewLayerDefinitions.size());
	}
	
	@Test
	public void testGetAuthorizedSupplementalNodes() throws Exception {
		
		List<String> authorizedSupplementalNodes = EdoServiceLocator.getEdoSuppReviewLayerDefinitionService().getAuthorizedSupplementalNodes("1000");
		assertEquals(2, authorizedSupplementalNodes.size());
	}

}
