package org.kuali.hr.time.timecollection.rule;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.collection.rule.TimeCollectionRule;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;

public class TimeCollectionRuleTest extends KPMETestCase{

	@Test
	public void testTimeCollectionRuleFetch() throws Exception{
		loadData();
		TimeCollectionRule timeCollection = TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRule("TEST-DEPT",
												1234L, new Date(System.currentTimeMillis()));
		Assert.assertTrue("Time collection rule present" , timeCollection!=null);

		timeCollection = TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRule("TEST-ME",
				1234L, new Date(System.currentTimeMillis()));
		Assert.assertTrue("Time collection rule present" , timeCollection!=null);

		timeCollection = TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRule("TEST-DEPT",
				234L, new Date(System.currentTimeMillis()));
		Assert.assertTrue("Time collection rule present" , timeCollection!=null);

		timeCollection = TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRule("TEST-ME",
				234L, new Date(System.currentTimeMillis()));
		Assert.assertTrue("Time collection rule present" , timeCollection!=null);
	}

	private void loadData()
	{
		TimeCollectionRule timeCollectionRule = new TimeCollectionRule();
		timeCollectionRule.setDept("TEST-DEPT");
		timeCollectionRule.setWorkArea(1234L);
		timeCollectionRule.setEffectiveDate(new Date(System.currentTimeMillis()));
		timeCollectionRule.setActive(true);
		timeCollectionRule.setTimestamp(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		KRADServiceLocator.getBusinessObjectService().save(timeCollectionRule);

		TimeCollectionRule timeCollectionRule2 = new TimeCollectionRule();
		timeCollectionRule2.setDept("TEST-ME");
		timeCollectionRule2.setWorkArea(234L);
		timeCollectionRule2.setEffectiveDate(new Date(System.currentTimeMillis()));
		timeCollectionRule2.setActive(true);
		timeCollectionRule2.setTimestamp(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		KRADServiceLocator.getBusinessObjectService().save(timeCollectionRule2);
	}
}
