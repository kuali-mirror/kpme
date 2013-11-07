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

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.core.CoreUnitTestCase;
import org.kuali.kpme.core.IntegrationTest;
import org.kuali.kpme.core.api.earncode.group.EarnCodeGroupContract;
import org.kuali.kpme.core.service.HrServiceLocator;

@IntegrationTest
public class EarnCodeGroupServiceTest extends CoreUnitTestCase{
	@Test
	public void testEarnGroupFetch() throws Exception{
		EarnCodeGroupContract earnGroup = HrServiceLocator.getEarnCodeGroupService().getEarnCodeGroup("REG", LocalDate.now());
		Assert.assertTrue("Test Earn Group fetch failed", earnGroup!=null && StringUtils.equals("REG", earnGroup.getEarnCodeGroup()));
		Assert.assertTrue("Test earn group def fetch failed", earnGroup.getEarnCodeGroups()!=null && earnGroup.getEarnCodeGroups().get(0).getHrEarnCodeGroupId().equals("100"));
	}
	
	// KPME-2529
	@Test
	public void testEarnGroupsFetch() throws Exception{
		List<EarnCodeGroup> earnGroups = (List<EarnCodeGroup>) HrServiceLocator.getEarnCodeGroupService().getEarnCodeGroupsForEarnCode("REG", LocalDate.now());
		Assert.assertTrue("Test Earn Groups fetch succeeded", earnGroups.size()==2);
	}
}
