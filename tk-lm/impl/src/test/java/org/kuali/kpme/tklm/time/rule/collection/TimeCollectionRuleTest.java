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
package org.kuali.kpme.tklm.time.rule.collection;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.core.KPMETestCase;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.time.rules.timecollection.TimeCollectionRule;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;

public class TimeCollectionRuleTest extends KPMETestCase{

	@Test
	public void testTimeCollectionRuleFetch() throws Exception{
		loadData();
		TimeCollectionRule timeCollection = TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRule("TEST-DEPT",
												1234L, LocalDate.now());
		Assert.assertTrue("Time collection rule present" , timeCollection!=null);

		timeCollection = TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRule("TEST-ME",
				1234L, LocalDate.now());
		Assert.assertTrue("Time collection rule present" , timeCollection!=null);

		timeCollection = TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRule("TEST-DEPT",
				234L, LocalDate.now());
		Assert.assertTrue("Time collection rule present" , timeCollection!=null);

		timeCollection = TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRule("TEST-ME",
				234L, LocalDate.now());
		Assert.assertTrue("Time collection rule present" , timeCollection!=null);
	}

	private void loadData()
	{
		TimeCollectionRule timeCollectionRule = new TimeCollectionRule();
		timeCollectionRule.setDept("TEST-DEPT");
		timeCollectionRule.setWorkArea(1234L);
		timeCollectionRule.setEffectiveLocalDate(LocalDate.now());
		timeCollectionRule.setActive(true);
		timeCollectionRule.setTimestamp(TKUtils.getCurrentTimestamp());
		KRADServiceLocator.getBusinessObjectService().save(timeCollectionRule);

		TimeCollectionRule timeCollectionRule2 = new TimeCollectionRule();
		timeCollectionRule2.setDept("TEST-ME");
		timeCollectionRule2.setWorkArea(234L);
		timeCollectionRule2.setEffectiveLocalDate(LocalDate.now());
		timeCollectionRule2.setActive(true);
		timeCollectionRule2.setTimestamp(TKUtils.getCurrentTimestamp());
		KRADServiceLocator.getBusinessObjectService().save(timeCollectionRule2);
	}
}
