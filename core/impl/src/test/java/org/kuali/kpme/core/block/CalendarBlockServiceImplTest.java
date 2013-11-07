/**
 * Copyright 2004-2013 The Kuali Foundation
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
package org.kuali.kpme.core.block;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.codehaus.groovy.tools.shell.util.Logger;
import org.junit.Test;
import org.kuali.kpme.core.CoreUnitTestCase;
import org.kuali.kpme.core.IntegrationTest;
import org.kuali.kpme.core.service.HrServiceLocator;

@IntegrationTest
public class CalendarBlockServiceImplTest extends CoreUnitTestCase {

	private static Logger LOG = Logger.create(CalendarBlockServiceImplTest.class);
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
	}
	
	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Test
	public void testGetAllCalendarBlocks() {
		assertNull(null);
		List<CalendarBlock> calendarBlocks = (List<CalendarBlock>) HrServiceLocator.getCalendarBlockService().getAllCalendarBlocks();
		assertEquals("number of returned entries should be 2", 2, calendarBlocks.size());
		for(CalendarBlock calendarBlock : calendarBlocks) {
			assertNotNull("Concrete block id should never be null", calendarBlock.getConcreteBlockId());
			assertNotNull("Concrete block type should never be null", calendarBlock.getConcreteBlockType());
		}
	}
	
}
