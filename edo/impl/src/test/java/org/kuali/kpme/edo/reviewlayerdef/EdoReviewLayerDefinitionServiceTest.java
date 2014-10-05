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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kuali.kpme.core.IntegrationTest;
import org.kuali.kpme.edo.EdoUnitTestBase;
import org.kuali.kpme.edo.api.reviewlayerdef.EdoReviewLayerDefinition;
import org.kuali.kpme.edo.service.EdoServiceLocator;


@IntegrationTest
public class EdoReviewLayerDefinitionServiceTest extends EdoUnitTestBase {	
	
	@Before
	public void setUp() throws Exception {
		
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		
		super.tearDown();
	}
	
	@Test
	public void testGetReviewLayerDefinitionById() throws Exception {

		EdoReviewLayerDefinition edoReviewlayerDifinition = (EdoReviewLayerDefinition)EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinitionById("1000");
		
		assertEquals("1000", edoReviewlayerDifinition.getEdoReviewLayerDefinitionId());
		assertEquals("Returned a correct number of results", 2, edoReviewlayerDifinition.getSuppReviewLayerDefinitions().size()); // KPME-3711
	}
	
	@Test
	public void testGetReviewLayerDefinitionByWorkflowIdAndNodeName() throws Exception {
		EdoReviewLayerDefinition edoReviewlayerDifinition = (EdoReviewLayerDefinition)EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinition("1000", "nodeName");
		
		assertEquals("1000", edoReviewlayerDifinition.getEdoReviewLayerDefinitionId());
	}
	
	@Test
	public void testGetReviewLayerDefinitions() throws Exception {
		
		Collection<EdoReviewLayerDefinition> edoReviewLayerDefinitions = EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinitions();
		assertEquals(3, edoReviewLayerDefinitions.size());
	}
	
	@Test
	public void testGetReviewLayerDefinitionsByWorkflowId() throws Exception {
		Collection<EdoReviewLayerDefinition> edoReviewLayerDefinitions = EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinitionsByWorkflowId("1000");
		
		assertEquals(2, edoReviewLayerDefinitions.size());
	}
	
	@Test
	public void testSaveOrUpdate() throws Exception {
		EdoReviewLayerDefinition immutable = (EdoReviewLayerDefinition)EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinitionById("1010");
		EdoReviewLayerDefinitionBo edoReviewLayerDefinitionBo = EdoReviewLayerDefinitionBo.from(immutable);
		assertEquals("P", immutable.getVoteType());
		
		edoReviewLayerDefinitionBo.setVoteType("T");
		
		// KPME-3711
		List<EdoSuppReviewLayerDefinitionBo> supps = new ArrayList<EdoSuppReviewLayerDefinitionBo>();
		EdoSuppReviewLayerDefinitionBo supp = new EdoSuppReviewLayerDefinitionBo();
		supp.setAcknowledgeFlag(true);
		supp.setEdoReviewLayerDefinitionId("1010");
		supp.setEdoSuppReviewLayerDefinitionId("2010");
		supp.setSuppNodeName("node1");
		supps.add(supp);
		edoReviewLayerDefinitionBo.setSuppReviewLayerDefinitions(supps);
		
		EdoServiceLocator.getEdoReviewLayerDefinitionService().saveOrUpdate(EdoReviewLayerDefinition.Builder.create(edoReviewLayerDefinitionBo).build());
		
		immutable = (EdoReviewLayerDefinition)EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinitionById("1010");
		assertEquals("T", immutable.getVoteType());
		assertEquals("Returned a correct number of results", 1, immutable.getSuppReviewLayerDefinitions().size()); // KPME-3711
	}
	
	@Test
	public void testGetReviewLayerDefinitionByNodeNameVoteTypeAndReviewLetter() throws Exception {
		Collection<EdoReviewLayerDefinition> edoReviewLayerDefinitions = EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinitions("nodeName", "T", "Y");
		
		assertEquals(1, edoReviewLayerDefinitions.size());
		
		if ( edoReviewLayerDefinitions.size() == 1 ){
			for ( EdoReviewLayerDefinition edoReviewLayerDefinition : edoReviewLayerDefinitions){
				assertEquals("1000", edoReviewLayerDefinition.getEdoReviewLayerDefinitionId());
			}
		}
		//assertEquals("1000", edoReviewLayerDefinitions.contains(o).getEdoReviewLayerDefinitionId());
	}
}
