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
package org.kuali.kpme.core.leaveplan;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.core.KPMEUnitTestCase;
import org.kuali.kpme.core.leaveplan.web.LeavePlanInquirableImpl;

public class LeavePlanInquirableImplTest extends KPMEUnitTestCase {
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetBusinessObject() throws Exception {
		Map fieldValues = new HashMap();
		fieldValues.put("leavePlan", "TestLeavePlan");
		fieldValues.put("effectiveDate", "02/01/2012");
		LeavePlan lp = (LeavePlan) new LeavePlanInquirableImpl().getBusinessObject(fieldValues);
		Assert.assertNotNull("No leave plan found", lp);
		String dateString =  new SimpleDateFormat("MM/dd/yyyy").format(lp.getEffectiveDate());
		// in LeavePlanInquirableImplTest, 3 leave plans are added to database with 3 effectiveDate 12/01/2011, 01/01/2012
		// and 03/01/2012, both have "TestLeavePlan" as leavePlan
		Assert.assertTrue("Wrong leave plan found", StringUtils.equals(dateString, "01/01/2012"));
				
		
	}

}
