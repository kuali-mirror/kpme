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
package org.kuali.kpme.edo.item;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kuali.kpme.core.IntegrationTest;
import org.kuali.kpme.edo.EdoUnitTestBase;
import org.kuali.kpme.edo.api.item.EdoItem;
import org.kuali.kpme.edo.service.EdoServiceLocator;

@IntegrationTest
public class EdoItemServiceTest extends EdoUnitTestBase {

	private final String edoItemID = "1000";
	private final String edoChecklistItemID = "1000";
	private final String edoDossierID = "1000";
	private final String edoReviewLayerDefID = "1000";
	private final String uploader = "admin";
	
	@Before
	public void setUp() throws Exception {
	super.setUp();
	
	}
	
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testGetEdoItem() throws Exception {

		EdoItem edoItem = EdoServiceLocator.getEdoItemService().getEdoItem(edoItemID);
		assertEquals("testEdoItemFileDescription", edoItem.getFileDescription());
	}

	@Test
	public void testSaveOrUpdate() throws Exception {
		
		EdoItem edoItem = EdoServiceLocator.getEdoItemService().getEdoItem(edoItemID);
		EdoItemBo edoItemBo = EdoItemBo.from(edoItem);
		edoItemBo.setFileDescription("Research Paper");
		EdoServiceLocator.getEdoItemService().saveOrUpdate(EdoItemBo.to(edoItemBo));
		
		edoItem = EdoServiceLocator.getEdoItemService().getEdoItem(edoItemID);
		assertEquals("Research Paper", edoItem.getFileDescription());
	}
	
	@Test
	public void testGetPendingItemsByDossierId() throws Exception {
    	List <EdoItem> items = EdoServiceLocator.getEdoItemService().getPendingItemsByDossierId(edoDossierID, edoChecklistItemID);
    	assertEquals("returned the correct number of results", 1, items.size());
    }
    
	@Test
    public void testGetItemsByDossierIdForAddendumFalgZero() throws Exception {
    	List <EdoItem> items = EdoServiceLocator.getEdoItemService().getItemsByDossierIdForAddendumFalgZero(edoDossierID, edoChecklistItemID);
    	assertEquals("returned the correct number of results", 2, items.size());
    }

    @Test
	public void testGetNextRowIndexNum() throws Exception {
		
		int nextRow = EdoServiceLocator.getEdoItemService().getNextRowIndexNum(edoChecklistItemID, uploader);
		assertEquals(4, nextRow);
	}
	
	@Test
	public void testDeleteItem() throws Exception {
		
		EdoItem edoItem = EdoServiceLocator.getEdoItemService().getEdoItem(edoItemID);
		EdoServiceLocator.getEdoItemService().deleteItem(edoItem);
		
		edoItem = EdoServiceLocator.getEdoItemService().getEdoItem(edoItemID);
		assertNull(edoItem);
	}	
	
	@Test
	public void testGetReviewLetterEdoItems() throws Exception {

		List<EdoItem> items = EdoServiceLocator.getEdoItemService().getReviewLetterEdoItems(edoDossierID, edoReviewLayerDefID);
		assertEquals("returned the correct number of results", 1, items.size());
	}
	
	@Test
	public void testGetListOfEdoItems() throws Exception {

		List<String> idList = new ArrayList<String>();
		idList.add("1000");
		idList.add("1002");

	    List<EdoItem> items = EdoServiceLocator.getEdoItemService().getListOfEdoItems(idList);
	    assertEquals("returned the correct number of results", 2, items.size());
	}



}