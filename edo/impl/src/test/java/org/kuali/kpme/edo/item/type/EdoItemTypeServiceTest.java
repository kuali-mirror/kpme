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
package org.kuali.kpme.edo.item.type;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kuali.kpme.core.IntegrationTest;
import org.kuali.kpme.edo.EdoUnitTestBase;
import org.kuali.kpme.edo.api.item.type.EdoItemType;
import org.kuali.kpme.edo.service.EdoServiceLocator;

@IntegrationTest
public class EdoItemTypeServiceTest extends EdoUnitTestBase {

	private final String edoItemTypeID = "1000"; 
	LocalDate asOfDate = new LocalDate(2012,1,1);

	@Before
	public void setUp() throws Exception {
	super.setUp();
	
	}
	
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Test
	public void testGetItemTypeList() throws Exception {

		List<EdoItemType> edoItemTypes = EdoServiceLocator.getEdoItemTypeService().getItemTypeList(asOfDate);
		System.out.println("edoItemTypes size "+edoItemTypes.size());
		assertEquals("returned the correct number of results", 3, edoItemTypes.size());
	}

	@Test
	public void testGetItemType() throws Exception {

		EdoItemType edoItemType = EdoServiceLocator.getEdoItemTypeService().getItemType(edoItemTypeID);
		assertEquals("Supporting Document", edoItemType.getItemTypeName());
	}
	
	@Test
	public void testGetItemTypeID() throws Exception {

		String itemTypeId = EdoServiceLocator.getEdoItemTypeService().getItemTypeID("Review Letter", asOfDate);
		assertEquals("1001", itemTypeId);
	}
	
	@Test
	public void testGetEdoItemTypeJSONString() throws Exception {

		EdoItemType edoItemType = EdoServiceLocator.getEdoItemTypeService().getItemType(edoItemTypeID);
		String jsonString = EdoServiceLocator.getEdoItemTypeService().getEdoItemTypeJSONString(edoItemType);
		assertNotNull(jsonString);
	}
	
	@Test
	public void testSaveOrUpdate() throws Exception {
		
		EdoItemType edoItemType = EdoServiceLocator.getEdoItemTypeService().getItemType(edoItemTypeID);
		EdoItemTypeBo edoItemTypeBo = EdoItemTypeBo.from(edoItemType);
		edoItemTypeBo.setItemTypeName("Extra Supporting Document");
		EdoServiceLocator.getEdoItemTypeService().saveOrUpdate(EdoItemTypeBo.to(edoItemTypeBo));
		
		edoItemType = EdoServiceLocator.getEdoItemTypeService().getItemType(edoItemTypeID);
		assertEquals("Extra Supporting Document", edoItemType.getItemTypeName());
	}
	
	
}