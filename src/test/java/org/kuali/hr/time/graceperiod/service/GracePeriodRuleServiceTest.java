package org.kuali.hr.time.graceperiod.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import org.kuali.hr.time.graceperiod.rule.GracePeriodRule;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.rice.kns.service.KNSServiceLocator;

public class GracePeriodRuleServiceTest extends TkTestCase{

	@Test
	public void testGracePeriodRuleFetch() throws Exception{
		GracePeriodRule gpr = new GracePeriodRule();
		gpr.setActive(true);
		gpr.setEffDt(new Date(System.currentTimeMillis()));
		gpr.setGraceMins(new BigDecimal(3));
		gpr.setHourFactor(new BigDecimal(0.1));
		
		KNSServiceLocator.getBusinessObjectService().save(gpr);
		gpr = TkServiceLocator.getGracePeriodService().getGracePeriodRule(new Date(System.currentTimeMillis()));
		assertTrue("fetched one rule", gpr != null);
	}
	
	
	@Test
	public void testGracePeriodRuleTest() throws Exception{
		GracePeriodRule gpr = new GracePeriodRule();
		gpr.setActive(true);
		gpr.setEffDt(new Date(System.currentTimeMillis()));
		gpr.setGraceMins(new BigDecimal(3));
		gpr.setHourFactor(new BigDecimal(0.1));
		
		KNSServiceLocator.getBusinessObjectService().save(gpr);
		gpr = TkServiceLocator.getGracePeriodService().getGracePeriodRule(new Date(System.currentTimeMillis()));
		assertTrue("fetched one rule", gpr != null);

		Timestamp beginDateTime = new Timestamp((new DateTime(2010, 10, 16, 12, 3, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		Timestamp derivedTimestamp = TkServiceLocator.getGracePeriodService().processGracePeriodRule(beginDateTime, new Date(System.currentTimeMillis()));

		assertTrue("rounded to 1:06", derivedTimestamp.getMinutes()==6);
		
		beginDateTime = new Timestamp((new DateTime(2010, 10, 16, 12, 56, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		derivedTimestamp = TkServiceLocator.getGracePeriodService().processGracePeriodRule(beginDateTime, new Date(System.currentTimeMillis()));

		assertTrue("rounded to 1:56", derivedTimestamp.getMinutes()==54);
		
		beginDateTime = new Timestamp((new DateTime(2010, 10, 16, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		derivedTimestamp = TkServiceLocator.getGracePeriodService().processGracePeriodRule(beginDateTime, new Date(System.currentTimeMillis()));

		assertTrue("rounded to 2:00", derivedTimestamp.getMinutes()==0 && derivedTimestamp.getHours()==1);


	}
}
