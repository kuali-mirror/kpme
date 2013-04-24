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
package org.kuali.hr.time.timeblock.service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.tklm.time.service.base.TkServiceLocator;
import org.kuali.hr.tklm.time.timeblock.TimeBlock;
import org.kuali.rice.krad.util.GlobalVariables;

public class TimeBlockServiceTest extends KPMETestCase {
	
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yy");

	@Test
	public void testIsTimeBlockEditableAdmin() throws Exception {
		TimeBlock tb = new TimeBlock();
		tb.setJobNumber(new Long(30));
		tb.setBeginTimestamp(new Timestamp(DATE_FORMAT.parse("01/01/2010").getTime()));
		tb.setEndTimestamp(new Timestamp(DATE_FORMAT.parse("01/01/2010").getTime()));
		
		GlobalVariables.getUserSession().setBackdoorUser("admin");
		
		tb.setUserPrincipalId("admin");
		boolean editable = TkServiceLocator.getTimeBlockService().isTimeBlockEditable(tb);
		Assert.assertEquals("TimeBlock created by admin should be editable by admin", true, editable);

		tb.setUserPrincipalId("eric");
		editable = TkServiceLocator.getTimeBlockService().isTimeBlockEditable(tb);
		Assert.assertEquals("TimeBlock created by eric should be editable by admin", true, editable);
	
		tb.setUserPrincipalId("eric");
		tb.setClockLogCreated(true);
		editable = TkServiceLocator.getTimeBlockService().isTimeBlockEditable(tb);
		Assert.assertEquals("TimeBlock created by Clock in/out should be editable by admin", true, editable);
	}
	
	@Test
	public void testIsTimeBlockEditableUser() throws Exception {
		TimeBlock tb = new TimeBlock();
		tb.setJobNumber(new Long(1));
		tb.setBeginTimestamp(new Timestamp(DATE_FORMAT.parse("08/12/2010").getTime()));
		tb.setEndTimestamp(new Timestamp(DATE_FORMAT.parse("08/12/2010").getTime()));
		
		GlobalVariables.getUserSession().setBackdoorUser("eric");
		
		tb.setUserPrincipalId("admin");
		boolean editable = TkServiceLocator.getTimeBlockService().isTimeBlockEditable(tb);
		Assert.assertEquals("TimeBlock created by admin should NOT be editable by eric", false, editable);
		
		tb.setUserPrincipalId("eric");
		tb.setClockLogCreated(true);
		editable = TkServiceLocator.getTimeBlockService().isTimeBlockEditable(tb);
		Assert.assertEquals("TimeBlock created by Clock in/out should NOT be editable by eric", false, editable);
	}

}
