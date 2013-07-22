package org.kuali.kpme.core.block;

import static org.junit.Assert.*;

import java.util.List;

import org.codehaus.groovy.tools.shell.util.Logger;
import org.junit.Test;
import org.kuali.kpme.core.CoreUnitTestCase;
import org.kuali.kpme.core.service.HrServiceLocator;

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
		List<CalendarBlock> calendarBlocks = HrServiceLocator.getCalendarBlockService().getAllCalendarBlocks();
		//actual number of entries depends on proper creation/deletion of leave blocks during the running of test suite.
		assertEquals("number of returned entries should be 85", 85, calendarBlocks.size());
		for(CalendarBlock calendarBlock : calendarBlocks) {
			assertNotNull("Concrete block id should never be null", calendarBlock.getConcreteBlockId());
			assertNotNull("Concrete block type should never be null", calendarBlock.getConcreteBlockType());
		}
	}
	
}
