package org.kuali.hr.time.accrual.service;

import java.util.List;

import org.junit.Test;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestUtils;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKUtils;

public class TimeOffAccrualServiceTest extends TkTestCase {
	
	@Test
	public void testValidateAccrualHoursLimit() {
		TimesheetDocument doc = TkTestUtils.populateTimesheetDocument(TKUtils.getCurrentDate());
		List<String> warnings = TkServiceLocator.getTimeOffAccrualService().validateAccrualHoursLimit(doc);
		assertTrue("There should be 1 warning message", warnings.size() == 1);
		assertTrue("Warning message not right", warnings.toString().contains("Warning: Total hours entered (50.00) for Accrual Category RGN has exceeded balance (30.00)."));
	}

}
