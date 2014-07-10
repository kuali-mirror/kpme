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
package org.kuali.kpme.edo.vote;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kuali.kpme.core.IntegrationTest;
import org.kuali.kpme.edo.EdoUnitTestBase;
import org.kuali.kpme.edo.api.vote.EdoVoteRecord;
import org.kuali.kpme.edo.reviewlayerdef.EdoReviewLayerDefinition;
import org.kuali.kpme.edo.service.EdoServiceLocator;


@IntegrationTest
public class EdoVoteRecordServiceTest extends EdoUnitTestBase {	

	@Before
	public void setUp() throws Exception {
		
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		
		super.tearDown();
	}
	
	@Test
	public void testGetEdoVoteRecordById() throws Exception {

		EdoVoteRecord edoVoteRecord = (EdoVoteRecord)EdoServiceLocator.getEdoVoteRecordService().getEdoVoteRecord("1000");
		
		assertEquals("1000", edoVoteRecord.getEdoVoteRecordID());
	}
	
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
		List<EdoReviewLayerDefinition> edoReviewLayerDefinitions = new ArrayList<EdoReviewLayerDefinition>();
		
		EdoReviewLayerDefinition edoReviewLayerDefinition1 = new EdoReviewLayerDefinition();
		edoReviewLayerDefinition1.setReviewLayerDefinitionId(new BigDecimal(1000));
		edoReviewLayerDefinition1.setReviewLevel(new BigDecimal(1));
		
		EdoReviewLayerDefinition edoReviewLayerDefinition2 = new EdoReviewLayerDefinition();
		edoReviewLayerDefinition2.setReviewLayerDefinitionId(new BigDecimal(1001));
		edoReviewLayerDefinition2.setReviewLevel(new BigDecimal(1));
		
		edoReviewLayerDefinitions.add(edoReviewLayerDefinition1);
		edoReviewLayerDefinitions.add(edoReviewLayerDefinition2);
		
		edoVoteRecords = EdoServiceLocator.getEdoVoteRecordService().getVoteRecords("1000", edoReviewLayerDefinitions);
		// TODO: getVoteRecords needs setup authorizedViewLevels. 	
//		assertTrue(edoVoteRecords.size() == 3);
//		
//		if ( edoVoteRecords.size() == 3) {
//			
//			assertEquals("1000", edoVoteRecords.get(0).getEdoDossierID());	
//		}
	}
	
	@Test
	public void testGetVoteRecordsByEdoReviewLayerDefinitionID() throws Exception {
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
}
