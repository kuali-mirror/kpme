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
		gpr.setEffectiveDate(new Date(System.currentTimeMillis()));
		gpr.setHourFactor(new BigDecimal(0.1));
		
		KNSServiceLocator.getBusinessObjectService().save(gpr);
		gpr = TkServiceLocator.getGracePeriodService().getGracePeriodRule(new Date(System.currentTimeMillis()));
		assertTrue("fetched one rule", gpr != null);
	}
	
	@Test
	public void testGracePeriodFetchValidation() throws Exception{
		//TODO: Sai - confirm maintenance page renders
		//TODO: Sai - confirm if hour factor is less than or equal 0 and greater than 1 it throws 
		//appropriate error
		
	}
	
	@Test
	public void testGracePeriodRuleTest() throws Exception{
		GracePeriodRule gpr = new GracePeriodRule();
		gpr.setActive(true);
		gpr.setEffectiveDate(new Date(System.currentTimeMillis()));
		gpr.setHourFactor(new BigDecimal(3));
		
		KNSServiceLocator.getBusinessObjectService().save(gpr);
		gpr = TkServiceLocator.getGracePeriodService().getGracePeriodRule(new Date(System.currentTimeMillis()));
		assertTrue("fetched one rule", gpr != null);

		Timestamp beginDateTime = new Timestamp((new DateTime(2012, 10, 16, 12, 3, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		Timestamp derivedTimestamp = TkServiceLocator.getGracePeriodService().processGracePeriodRule(beginDateTime, new Date(System.currentTimeMillis()));

		assertTrue("rounded to 1:03", derivedTimestamp.getMinutes()==3);
		
		beginDateTime = new Timestamp((new DateTime(2012, 10, 16, 12, 56, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		derivedTimestamp = TkServiceLocator.getGracePeriodService().processGracePeriodRule(beginDateTime, new Date(System.currentTimeMillis()));

		assertTrue("rounded to 1:56", derivedTimestamp.getMinutes()==57);
	}
}
