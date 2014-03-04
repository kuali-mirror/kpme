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
package org.kuali.hr.time.timeblock.service;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.KPMEWebTestCase;
import org.kuali.kpme.core.FunctionalTest;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timeblock.TimeBlockBo;
import org.kuali.rice.krad.util.GlobalVariables;

@FunctionalTest
public class TimeBlockServiceTest extends KPMEWebTestCase {
	
	@Test
	public void testIsTimeBlockEditableAdmin() throws Exception {
		TimeBlockBo tb = new TimeBlockBo();
		tb.setJobNumber(new Long(30));
		tb.setBeginDateTime(new DateTime(2010, 1, 1, 0, 0, 0));
		tb.setEndDateTime(new DateTime(2010, 1, 1, 0, 0, 0));
		
		GlobalVariables.getUserSession().setBackdoorUser("admin");
		
		tb.setUserPrincipalId("admin");
		boolean editable = TkServiceLocator.getTimeBlockService().getTimeBlockEditable(TimeBlockBo.to(tb));
		Assert.assertEquals("TimeBlock created by admin should be editable by admin", true, editable);

		tb.setUserPrincipalId("eric");
		editable = TkServiceLocator.getTimeBlockService().getTimeBlockEditable(TimeBlockBo.to(tb));
		Assert.assertEquals("TimeBlock created by eric should be editable by admin", true, editable);
	
		tb.setUserPrincipalId("eric");
		tb.setClockLogCreated(true);
		editable = TkServiceLocator.getTimeBlockService().getTimeBlockEditable(TimeBlockBo.to(tb));
		Assert.assertEquals("TimeBlock created by Clock in/out should be editable by admin", true, editable);
	}
	
	@Test
	public void testIsTimeBlockEditableUser() throws Exception {
		TimeBlockBo tb = new TimeBlockBo();
		tb.setJobNumber(new Long(1));
		tb.setBeginDateTime(new DateTime(2010, 8, 12, 0, 0, 0));
		tb.setEndDateTime(new DateTime(2010, 8, 12, 0, 0, 0));
		
		GlobalVariables.getUserSession().setBackdoorUser("eric");
		
		tb.setUserPrincipalId("admin");
		boolean editable = TkServiceLocator.getTimeBlockService().getTimeBlockEditable(TimeBlockBo.to(tb));
		Assert.assertEquals("TimeBlock created by admin should NOT be editable by eric", false, editable);
		
		tb.setUserPrincipalId("eric");
		tb.setClockLogCreated(true);
		editable = TkServiceLocator.getTimeBlockService().getTimeBlockEditable(TimeBlockBo.to(tb));
		Assert.assertEquals("TimeBlock created by Clock in/out should NOT be editable by eric", false, editable);
	}

}
