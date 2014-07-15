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
	}
	
	/*
	@Test
	public void testSaveOrUpdate() throws Exception {
		EdoVoteRecord immutable = (EdoVoteRecord)EdoServiceLocator.getEdoVoteRecordService().getEdoVoteRecord("1004");
		EdoVoteRecordBo edoVoteRecordBo = EdoVoteRecordBo.from(immutable);
		assertEquals("P", immutable.getVoteType());
		
		edoVoteRecordBo.setVoteType("T");
		EdoServiceLocator.getEdoVoteRecordService().saveOrUpdate(EdoVoteRecord.Builder.create(edoVoteRecordBo).build());
		
		immutable = (EdoVoteRecord)EdoServiceLocator.getEdoVoteRecordService().getEdoVoteRecord("1004");
		assertEquals("T", immutable.getVoteType());
	}
	
	@Test
	public void testGetVoteRecords() throws Exception {
		List<EdoVoteRecord> edoVoteRecords = new ArrayList<EdoVoteRecord>();
		//Map<String, EdoReviewLayerDefinition> testReviewlayerDefinitionBo;
		
		//testReviewlayerDefinitionBo = new HashMap<String, EdoReviewLayerDefinition>();
		
		EdoReviewLayerDefinition.Builder edoReviewLayerDefinitionBuilder = EdoReviewLayerDefinition.Builder.create();
		edoReviewLayerDefinitionBuilder.setEdoReviewLayerDefinitionId("1000");
		edoReviewLayerDefinitionBuilder.setNodeName("testNodeName");
		edoReviewLayerDefinitionBuilder.setVoteType("tenure");
		edoReviewLayerDefinitionBuilder.setDescription("description");
		edoReviewLayerDefinitionBuilder.setReviewLetter(true);
		edoReviewLayerDefinitionBuilder.setReviewLevel("1");
		edoReviewLayerDefinitionBuilder.setRouteLevel("1");
		edoReviewLayerDefinitionBuilder.setWorkflowId("1000");
		edoReviewLayerDefinitionBuilder.setWorkflowQualifier("workflowQualifier");	
		edoReviewLayerDefinitionBuilder.setUserPrincipalId("admin");
		edoReviewLayerDefinitionBuilder.setVersionNumber(1L);
		edoReviewLayerDefinitionBuilder.setObjectId("0804716a-cbb7-11e3-9cd3-51a754ad6a0a");
		edoReviewLayerDefinitionBuilder.setId(edoReviewLayerDefinitionBuilder.getEdoReviewLayerDefinitionId());
		
		EdoReviewLayerDefinition edoReviewLayerDefinition1 = edoReviewLayerDefinitionBuilder.build();
		
		
		EdoReviewLayerDefinitionBo edoReviewLayerDefinition2 = new EdoReviewLayerDefinitionBo();
		edoReviewLayerDefinition2.setEdoReviewLayerDefinitionId("1001");
		edoReviewLayerDefinition2.setReviewLevel("1");
		
		List<EdoReviewLayerDefinition> edoReviewLayerDefinitions = new ArrayList<EdoReviewLayerDefinition>();
		edoReviewLayerDefinitions.add(edoReviewLayerDefinition1);
		
		
		edoVoteRecords = EdoServiceLocator.getEdoVoteRecordService().getVoteRecords("1000", edoReviewLayerDefinitions);
		
	}
	
	@Test
	public void testGetVoteRecordsByEdoReviewLayerDefinitionId() throws Exception {
		EdoVoteRecord edoVoteRecord = (EdoVoteRecord)EdoServiceLocator.getEdoVoteRecordService().getVoteRecordMostCurrentRound("1000", "1001");
		
		assertEquals("1003", edoVoteRecord.getEdoVoteRecordID());
	}
	
	@Test
	public void testIsNegativeVote() throws Exception {
		boolean isNegative = true;
		
		EdoVoteRecord aPositiveEdoVoteRecord = (EdoVoteRecord)EdoServiceLocator.getEdoVoteRecordService().getEdoVoteRecord("1000");
		isNegative = EdoServiceLocator.getEdoVoteRecordService().isNegativeVote(aPositiveEdoVoteRecord);
		assertEquals(false, isNegative);
		
		EdoVoteRecord aNegativeEdoVoteRecord = (EdoVoteRecord)EdoServiceLocator.getEdoVoteRecordService().getEdoVoteRecord("1002");
		isNegative = EdoServiceLocator.getEdoVoteRecordService().isNegativeVote(aNegativeEdoVoteRecord);
		assertEquals(true, isNegative);
	}
	*/
}
