package org.kuali.hr.time.timecollection.rule;

import java.sql.Date;

import org.junit.Test;
import org.kuali.hr.time.collection.rule.TimeCollectionRule;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.TkTestCase;

public class TimeCollectionRuleTest extends TkTestCase{

	@Test
	public void testTimeCollectionRuleFetch() throws Exception{
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
}
