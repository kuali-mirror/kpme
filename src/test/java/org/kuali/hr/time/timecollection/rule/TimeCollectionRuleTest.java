package org.kuali.hr.time.timecollection.rule;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

import org.junit.Test;
import org.kuali.hr.time.collection.rule.TimeCollectionRule;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kns.service.KNSServiceLocator;

public class TimeCollectionRuleTest extends TkTestCase{

	@Test
	public void testTimeCollectionRuleFetch() throws Exception{
		loadData();
		TimeCollectionRule timeCollection = TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRule("TEST-DEPT", 
												1234L, new Date(System.currentTimeMillis()));
		assertTrue("Time collection rule present" , timeCollection!=null && timeCollection.getTkTimeCollectionRuleId().longValue() == 1L);
		
		timeCollection = TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRule("TEST-ME", 
				1234L, new Date(System.currentTimeMillis()));
		assertTrue("Time collection rule present" , timeCollection!=null && timeCollection.getTkTimeCollectionRuleId().longValue() == 2L);
		
		timeCollection = TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRule("TEST-DEPT", 
				234L, new Date(System.currentTimeMillis()));
		assertTrue("Time collection rule present" , timeCollection!=null && timeCollection.getTkTimeCollectionRuleId().longValue() == 3L);
		
		timeCollection = TkServiceLocator.getTimeCollectionRuleService().getTimeCollectionRule("TEST-ME", 
				234L, new Date(System.currentTimeMillis()));
		assertTrue("Time collection rule present" , timeCollection!=null && timeCollection.getTkTimeCollectionRuleId().longValue() == 4L);
		
		
	}

	private void loadData() 
	{
		TimeCollectionRule timeCollectionRule = new TimeCollectionRule();
		timeCollectionRule.setDept("TEST-DEPT");
		timeCollectionRule.setWorkArea(1234L);
		timeCollectionRule.setEffDate(new Date(System.currentTimeMillis()));
		timeCollectionRule.setActive(true);
		timeCollectionRule.setTimeStamp(new Timestamp(Calendar.getInstance().getTimeInMillis()));		
		KNSServiceLocator.getBusinessObjectService().save(timeCollectionRule);
		
		TimeCollectionRule timeCollectionRule2 = new TimeCollectionRule();
		timeCollectionRule2.setDept("TEST-ME");
		timeCollectionRule2.setWorkArea(234L);
		timeCollectionRule2.setEffDate(new Date(System.currentTimeMillis()));
		timeCollectionRule2.setActive(true);
		timeCollectionRule2.setTimeStamp(new Timestamp(Calendar.getInstance().getTimeInMillis()));		
		KNSServiceLocator.getBusinessObjectService().save(timeCollectionRule2);
	}
}
