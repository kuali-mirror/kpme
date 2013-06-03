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
package org.kuali.kpme.core.earncode.group;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.core.KPMETestCase;
import org.kuali.kpme.core.earncode.group.EarnCodeGroup;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrTestConstants;
import org.kuali.kpme.core.util.HtmlUnitUtil;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class EarnCodeGroupServiceTest extends KPMETestCase{
	@Test
	public void testEarnGroupFetch() throws Exception{
		EarnCodeGroup earnGroup = HrServiceLocator.getEarnCodeGroupService().getEarnCodeGroup("REG", LocalDate.now());
		Assert.assertTrue("Test Earn Group fetch failed", earnGroup!=null && StringUtils.equals("REG", earnGroup.getEarnCodeGroup()));
		Assert.assertTrue("Test earn group def fetch failed", earnGroup.getEarnCodeGroups()!=null && earnGroup.getEarnCodeGroups().get(0).getHrEarnCodeGroupId().equals("100"));
	}
	
	@Test
	public void testEarnGroupMaintenancePage() throws Exception{				
		HtmlPage earnCodeLookUp = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), HrTestConstants.Urls.EARN_CODE_GROUP_MAINT_URL);
		earnCodeLookUp = HtmlUnitUtil.clickInputContainingText(earnCodeLookUp, "search");
		HtmlUnitUtil.createTempFile(earnCodeLookUp);
		Assert.assertTrue("Page contains REG entry", earnCodeLookUp.asText().contains("REG"));	
		
		EarnCodeGroup earnGroup = HrServiceLocator.getEarnCodeGroupService().getEarnCodeGroup("REG", LocalDate.now());
		String earnGroupId = earnGroup.getHrEarnCodeGroupId().toString();
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(earnCodeLookUp, "edit", earnGroupId);		
		HtmlUnitUtil.createTempFile(maintPage);
		Assert.assertTrue("Maintenance Page contains REG entry",maintPage.asText().contains("REG"));		
	}	
}
