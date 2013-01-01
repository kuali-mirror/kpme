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
package org.kuali.hr.time.workflow.service;

import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;

public class TimesheetDocumentHeaderServiceImplTest extends KPMETestCase {
	
	@Test
	public void testGetDocumentHeaderForDate() {
		String principalId = "admin";
		// there is NO TimesheetDocumentHeader that covers 05/03/2012
		Date asOfDate = new Date((new DateTime(2012, 5, 3, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
		TimesheetDocumentHeader tdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeaderForDate(principalId,asOfDate);
		Assert.assertNull("TimesheetDocumentHeader for 05/03/2012 should be null.", tdh);
		
		// there is a TimesheetDocumentHeader that covers 03/08/2012
		asOfDate = new Date((new DateTime(2012, 3, 8, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
		tdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeaderForDate(principalId,asOfDate);
		Assert.assertNotNull("TimesheetDocumentHeader for 03/08/2012 should NOT be null.", tdh);
	}
	

}
