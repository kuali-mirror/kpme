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

import java.util.List;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kuali.kpme.core.IntegrationTest;
import org.kuali.kpme.edo.EdoUnitTestBase;
import org.kuali.kpme.edo.api.checklist.EdoChecklistItem;
import org.kuali.kpme.edo.service.EdoServiceLocator;

@IntegrationTest
public class EdoChecklistItemServiceTest extends EdoUnitTestBase {

	private final String edoChecklistItemId = "1000";
	private final String edoCheckListSectionId = "2000";
	private final String edoDossierId = "2000";

	@Before
	public void setUp() throws Exception {
	super.setUp();
	
	}
	
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testGetEdoChecklistItemById() throws Exception {

		EdoChecklistItem edoChecklistItem = EdoServiceLocator.getChecklistItemService().getChecklistItemById(edoChecklistItemId);
		assertEquals("testEdoChecklistItem1", edoChecklistItem.getChecklistItemName());
	}
	
	@Test
	public void testGetChecklistItemsBySectionId() throws Exception {

		LocalDate  asOfDate = new LocalDate(2012,1,1);
		
		List<EdoChecklistItem> items = EdoServiceLocator.getChecklistItemService().getChecklistItemsBySectionId(edoCheckListSectionId, asOfDate);
		assertEquals("returned the correct number of results", 2, items.size());
	}
	
	@Test
	public void testGetChecklistItems() throws Exception {
	
		List<EdoChecklistItem> items = EdoServiceLocator.getChecklistItemService().getChecklistItems("IU-IN", "DEFAULT", "ENGINEERING");
		assertEquals("returned the correct number of results", 4, items.size());
	}
	
	@Test
	public void testGetChecklistItemByDossierId() throws Exception {
	
		EdoChecklistItem item = EdoServiceLocator.getChecklistItemService().getChecklistItemByDossierId(edoDossierId, "testEdoChecklistItem4"); 
		assertEquals("Testing Immutable EdoChecklistItem4", item.getItemDescription());
	}
}