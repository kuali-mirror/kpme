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
		Assert.assertTrue("Warning message not right", warnings.get(0).contains("Warning: Total hours entered (4.00) for Accrual Category \"Tex accrual cat(TEX)\" has exceeded balance (0.00)."));
	}

}
