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
package org.kuali.hr.lm.employeeoverride;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.core.KPMETestCase;
import org.kuali.kpme.core.inquirable.KPMEInquirableImpl;
import org.kuali.kpme.tklm.leave.override.EmployeeOverride;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;

public class EmployeeOverrideInquirableTest extends KPMETestCase {

	private String INQUIRY_URL = "inquiry.do?businessObjectClassName=org.kuali.kpme.core.accrualcategory.AccrualCategory&methodToCall=start&effectiveDate=03%2F04%2F2012&accrualCategory=testAC&leavePlan=testLeavePlan&lmAccrualCategoryId=";
	@Test
	public void testGetInquiryUrl() throws Exception {
		EmployeeOverride eo = new EmployeeOverride();
		eo.setAccrualCategory("testAC");
		eo.setLeavePlan("testLeavePlan");
		eo.setEffectiveLocalDate(new LocalDate(2012, 3, 4));
		
		HtmlData hd = (HtmlData) new KPMEInquirableImpl().getInquiryUrl(eo, "accrualCategory", false);
		Assert.assertNotNull("No HtmlData found", hd);
		String inquiryUrl = ((AnchorHtmlData) hd).getHref();
		Assert.assertTrue("Inquiry url is wrong", StringUtils.contains(inquiryUrl, INQUIRY_URL));
		
	}
}
