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
package org.kuali.hr.time.earncode;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.kpme.core.bo.earncode.EarnCode;
import org.kuali.kpme.core.bo.earncode.EarnCodeInquirableImpl;

public class EarnCodeInquirableImplTest extends KPMETestCase {
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetBusinessObject() throws Exception {
		Map fieldValues = new HashMap();
		fieldValues.put("earnCode", "TestEarnCode");
		fieldValues.put("effectiveDate", "02/01/2012");
		EarnCode ec = (EarnCode) new EarnCodeInquirableImpl().getBusinessObject(fieldValues);
		Assert.assertNotNull("No Earn Code found", ec);
		String dateString =  new SimpleDateFormat("MM/dd/yyyy").format(ec.getEffectiveDate());
		// in this Test, 3 earn codes are added to database with 3 effectiveDate 12/01/2011, 01/01/2012
		// and 03/01/2012, both have "TestEarnCode" as earnCode
		Assert.assertTrue("Wrong Earn Code found", StringUtils.equals(dateString, "01/01/2012"));
	}
}
