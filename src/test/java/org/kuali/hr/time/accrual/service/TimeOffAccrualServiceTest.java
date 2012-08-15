package org.kuali.hr.time.accrual.service;

import java.sql.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.TkTestUtils;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;

public class TimeOffAccrualServiceTest extends KPMETestCase {

	@Test
	public void testValidateAccrualHoursLimit() {
        // Should fix the date in time. Stops NPE. Date set here is arbitrary.
        Date TEST_DATE = new Date((new DateTime(2011, 3, 1, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
		TimesheetDocument doc = TkTestUtils.populateTimesheetDocument(TEST_DATE);
		List<String> warnings = TkServiceLocator.getTimeOffAccrualService().validateAccrualHoursLimit(doc);
		Assert.assertTrue("There should be 1 warning message", warnings.size() == 1);
		Assert.assertTrue("Warning message not right", warnings.toString().contains("Warning: Total hours entered (4.00) for Accrual Category TEX has exceeded balance (0.00)."));
	}

}
