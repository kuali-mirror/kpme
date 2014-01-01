/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.tklm.time.rule.graceperiod;

import java.math.BigDecimal;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.kpme.core.IntegrationTest;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.TKLMIntegrationTestCase;
import org.kuali.kpme.tklm.time.rules.graceperiod.GracePeriodRule;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.rice.krad.service.KRADServiceLocator;

@IntegrationTest
public class GracePeriodRuleServiceTest extends TKLMIntegrationTestCase{

	@Test
	public void testGracePeriodRuleFetch() throws Exception{
		GracePeriodRule gpr = new GracePeriodRule();
		gpr.setActive(true);
		gpr.setEffectiveLocalDate(LocalDate.now());
		gpr.setHourFactor(new BigDecimal(0.1));
		
		KRADServiceLocator.getBusinessObjectService().save(gpr);
		gpr = TkServiceLocator.getGracePeriodService().getGracePeriodRule(LocalDate.now());
		Assert.assertTrue("fetched one rule", gpr != null);

        //cleanup
        KRADServiceLocator.getBusinessObjectService().delete(gpr);
	}
	
	@Test
    @Ignore
	public void testGracePeriodFetchValidation() throws Exception{
		//TODO: Sai - confirm maintenance page renders
		//TODO: Sai - confirm if hour factor is less than or equal 0 and greater than 1 it throws 
		//appropriate error
		
	}
	
	@Test
	public void testGracePeriodRuleTest() throws Exception{
		GracePeriodRule gpr = new GracePeriodRule();
		gpr.setActive(true);
		gpr.setEffectiveLocalDate(LocalDate.now());
		gpr.setHourFactor(new BigDecimal(3));
		
		KRADServiceLocator.getBusinessObjectService().save(gpr);
		gpr = TkServiceLocator.getGracePeriodService().getGracePeriodRule(LocalDate.now());
		Assert.assertTrue("fetched one rule", gpr != null);

		DateTime beginDateTime = new DateTime(2012, 10, 16, 12, 3, 0, 0, TKUtils.getSystemDateTimeZone());
		DateTime derivedDateTime = TkServiceLocator.getGracePeriodService().processGracePeriodRule(beginDateTime, LocalDate.now());

		Assert.assertTrue("rounded to 1:03", derivedDateTime.getMinuteOfHour() == 3);
		
		beginDateTime = new DateTime(2012, 10, 16, 12, 56, 0, 0, TKUtils.getSystemDateTimeZone());
		derivedDateTime = TkServiceLocator.getGracePeriodService().processGracePeriodRule(beginDateTime, LocalDate.now());

		Assert.assertTrue("rounded to 1:56", derivedDateTime.getMinuteOfHour() == 57);

        //cleanup
        KRADServiceLocator.getBusinessObjectService().delete(gpr);
	}
	
	@Test
	public void testKPMEQA_332() throws Exception{
		/**
		 * In order to be consistent with the jira listed in the test method name,
		 * these tests would need to mimick a clock action, which calls ClockLogService::processClockLog(...).
		 * It should then be verified that GracePeriodService::processGracePeriodRule is called once, and the resulting
		 * clock log's clockTimestamp is consistent with the expected value under the grace period rule.
		 */
		GracePeriodRule gpr = new GracePeriodRule();
		gpr.setActive(true);
		gpr.setEffectiveLocalDate(LocalDate.now());
		gpr.setHourFactor(new BigDecimal(0));
		
		KRADServiceLocator.getBusinessObjectService().save(gpr);
		gpr = TkServiceLocator.getGracePeriodService().getGracePeriodRule(LocalDate.now());
		Assert.assertTrue("fetched one rule", gpr != null);

		DateTime beginDateTime = new DateTime(2012, 10, 16, 12, 3, 0, 0, TKUtils.getSystemDateTimeZone());
		DateTime derivedDateTime = TkServiceLocator.getGracePeriodService().processGracePeriodRule(beginDateTime, LocalDate.now());

		Assert.assertTrue("no rounding should have been done.", derivedDateTime.compareTo(beginDateTime) == 0);

        //cleanup
        KRADServiceLocator.getBusinessObjectService().delete(gpr);
	}
	
	@Test
	public void testKPMEQA_335() throws Exception{
		GracePeriodRule gpr = new GracePeriodRule();
		gpr.setActive(true);
		gpr.setEffectiveLocalDate(LocalDate.now());
		gpr.setHourFactor(new BigDecimal(15));
		
		KRADServiceLocator.getBusinessObjectService().save(gpr);
		gpr = TkServiceLocator.getGracePeriodService().getGracePeriodRule(LocalDate.now());
		Assert.assertTrue("fetched one rule", gpr != null);

		DateTime beginDateTime = new DateTime(2012, 10, 16, 12, 7, 0, 0, TKUtils.getSystemDateTimeZone());
		DateTime derivedDateTime = TkServiceLocator.getGracePeriodService().processGracePeriodRule(beginDateTime, LocalDate.now());

		Assert.assertTrue("should round down to 12:00", derivedDateTime.getMinuteOfHour() == 0);
		
		KRADServiceLocator.getBusinessObjectService().delete(gpr);
	}
	
	@Test
	public void testKPMEQA_336() throws Exception{
		GracePeriodRule gpr = new GracePeriodRule();
		gpr.setActive(true);
		gpr.setEffectiveLocalDate(LocalDate.now());
		gpr.setHourFactor(new BigDecimal(15));
		
		KRADServiceLocator.getBusinessObjectService().save(gpr);
		gpr = TkServiceLocator.getGracePeriodService().getGracePeriodRule(LocalDate.now());
		Assert.assertTrue("fetched one rule", gpr != null);

		DateTime beginDateTime = new DateTime(2012, 10, 16, 12, 53, 0, 0, TKUtils.getSystemDateTimeZone());
		DateTime derivedDateTime = TkServiceLocator.getGracePeriodService().processGracePeriodRule(beginDateTime, LocalDate.now());

		Assert.assertTrue("should round up to 12:00", derivedDateTime.getMinuteOfHour() == 0);

        //cleanup
        KRADServiceLocator.getBusinessObjectService().delete(gpr);
	}
	
	@Test
	public void testKPMEQA_337() throws Exception{
		GracePeriodRule gpr = new GracePeriodRule();
		gpr.setActive(true);
		gpr.setEffectiveLocalDate(LocalDate.now());
		gpr.setHourFactor(new BigDecimal(15));
		
		KRADServiceLocator.getBusinessObjectService().save(gpr);
		gpr = TkServiceLocator.getGracePeriodService().getGracePeriodRule(LocalDate.now());
		Assert.assertTrue("fetched one rule", gpr != null);

		DateTime beginDateTime = new DateTime(2012, 10, 16, 12, 52, 0, 0, TKUtils.getSystemDateTimeZone());
		DateTime derivedDateTime = TkServiceLocator.getGracePeriodService().processGracePeriodRule(beginDateTime, LocalDate.now());

		Assert.assertTrue("should round down to 12:45", derivedDateTime.getMinuteOfHour() == 45);

        //cleanup
        KRADServiceLocator.getBusinessObjectService().delete(gpr);
	}
	
	@Test
	public void testKPMEQA_338() throws Exception{
		GracePeriodRule gpr = new GracePeriodRule();
		gpr.setActive(true);
		gpr.setEffectiveLocalDate(LocalDate.now());
		gpr.setHourFactor(new BigDecimal(15));
		
		KRADServiceLocator.getBusinessObjectService().save(gpr);
		gpr = TkServiceLocator.getGracePeriodService().getGracePeriodRule(LocalDate.now());
		Assert.assertTrue("fetched one rule", gpr != null);

		DateTime beginDateTime = new DateTime(2012, 10, 16, 12, 8, 0, 0, TKUtils.getSystemDateTimeZone());
		DateTime derivedDateTime = TkServiceLocator.getGracePeriodService().processGracePeriodRule(beginDateTime, LocalDate.now());

		Assert.assertTrue("should round up to 12:15", derivedDateTime.getMinuteOfHour() == 15);

        //cleanup
        KRADServiceLocator.getBusinessObjectService().delete(gpr);
	}
	
	@Test
	public void testKPMEQA_344() throws Exception{
		GracePeriodRule gpr = new GracePeriodRule();
		gpr.setActive(true);
		gpr.setEffectiveLocalDate(LocalDate.now());
		gpr.setHourFactor(new BigDecimal(6));
		
		KRADServiceLocator.getBusinessObjectService().save(gpr);
		gpr = TkServiceLocator.getGracePeriodService().getGracePeriodRule(LocalDate.now());
		Assert.assertTrue("fetched one rule", gpr != null);

		DateTime beginDateTime = new DateTime(2012, 10, 16, 12, 2, 0, 0, TKUtils.getSystemDateTimeZone());
		DateTime derivedDateTime = TkServiceLocator.getGracePeriodService().processGracePeriodRule(beginDateTime, LocalDate.now());

		Assert.assertTrue("should round to 12:00", derivedDateTime.getMinuteOfHour() == 0);

        //cleanup
        KRADServiceLocator.getBusinessObjectService().delete(gpr);
	}
	
	@Test
	public void testKPMEQA_345() throws Exception{
		GracePeriodRule gpr = new GracePeriodRule();
		gpr.setActive(true);
		gpr.setEffectiveLocalDate(LocalDate.now());
		gpr.setHourFactor(new BigDecimal(6));
		
		KRADServiceLocator.getBusinessObjectService().save(gpr);
		gpr = TkServiceLocator.getGracePeriodService().getGracePeriodRule(LocalDate.now());
		Assert.assertTrue("fetched one rule", gpr != null);

		DateTime beginDateTime = new DateTime(2012, 10, 16, 12, 58, 0, 0, TKUtils.getSystemDateTimeZone());
		DateTime derivedDateTime = TkServiceLocator.getGracePeriodService().processGracePeriodRule(beginDateTime, LocalDate.now());

		Assert.assertTrue("should round to 12:00", derivedDateTime.getMinuteOfHour() == 0);

        //cleanup
        KRADServiceLocator.getBusinessObjectService().delete(gpr);
	}
	
	@Test
	public void testKPMEQA_346() throws Exception{
		GracePeriodRule gpr = new GracePeriodRule();
		gpr.setActive(true);
		gpr.setEffectiveLocalDate(LocalDate.now());
		gpr.setHourFactor(new BigDecimal(6));
		
		KRADServiceLocator.getBusinessObjectService().save(gpr);
		gpr = TkServiceLocator.getGracePeriodService().getGracePeriodRule(LocalDate.now());
		Assert.assertTrue("fetched one rule", gpr != null);

		DateTime beginDateTime = new DateTime(2012, 10, 16, 12, 4, 0, 0, TKUtils.getSystemDateTimeZone());
		DateTime derivedDateTime = TkServiceLocator.getGracePeriodService().processGracePeriodRule(beginDateTime, LocalDate.now());

		Assert.assertTrue("should round to 12:06", derivedDateTime.getMinuteOfHour() == 6);

        //cleanup
        KRADServiceLocator.getBusinessObjectService().delete(gpr);
	}
	
	@Test
	public void testKPMEQA_346b() throws Exception{
		GracePeriodRule gpr = new GracePeriodRule();
		gpr.setActive(true);
		gpr.setEffectiveLocalDate(LocalDate.now());
		gpr.setHourFactor(new BigDecimal(6));
		
		KRADServiceLocator.getBusinessObjectService().save(gpr);
		gpr = TkServiceLocator.getGracePeriodService().getGracePeriodRule(LocalDate.now());
		Assert.assertTrue("fetched one rule", gpr != null);

		DateTime beginDateTime = new DateTime(2012, 10, 16, 12, 8, 0, 0, TKUtils.getSystemDateTimeZone());
		DateTime derivedDateTime = TkServiceLocator.getGracePeriodService().processGracePeriodRule(beginDateTime, LocalDate.now());

		Assert.assertTrue("should round to 12:06", derivedDateTime.getMinuteOfHour() == 6);

        //cleanup
        KRADServiceLocator.getBusinessObjectService().delete(gpr);
	}

	@Test
	public void testKPMEQA_347() throws Exception{
		GracePeriodRule gpr = new GracePeriodRule();
		gpr.setActive(true);
		gpr.setEffectiveLocalDate(LocalDate.now());
		gpr.setHourFactor(new BigDecimal(6));
		
		KRADServiceLocator.getBusinessObjectService().save(gpr);
		gpr = TkServiceLocator.getGracePeriodService().getGracePeriodRule(LocalDate.now());
		Assert.assertTrue("fetched one rule", gpr != null);

		DateTime beginDateTime = new DateTime(2012, 10, 16, 12, 10, 0, 0, TKUtils.getSystemDateTimeZone());
		DateTime derivedDateTime = TkServiceLocator.getGracePeriodService().processGracePeriodRule(beginDateTime, LocalDate.now());

		Assert.assertTrue("should round to 12:12", derivedDateTime.getMinuteOfHour() == 12);

        //cleanup
        KRADServiceLocator.getBusinessObjectService().delete(gpr);
	}
	
	@Test
	public void testRoundingWhenEquidistentToHourFactor() throws Exception{
		GracePeriodRule gpr = new GracePeriodRule();
		gpr.setActive(true);
		gpr.setEffectiveLocalDate(LocalDate.now());
		gpr.setHourFactor(new BigDecimal(6));
		
		KRADServiceLocator.getBusinessObjectService().save(gpr);
		gpr = TkServiceLocator.getGracePeriodService().getGracePeriodRule(LocalDate.now());
		Assert.assertTrue("fetched one rule", gpr != null);

		DateTime beginDateTime = new DateTime(2012, 10, 16, 12, 3, 0, 0, TKUtils.getSystemDateTimeZone());
		DateTime derivedDateTime = TkServiceLocator.getGracePeriodService().processGracePeriodRule(beginDateTime, LocalDate.now());

		Assert.assertTrue("should round to 12::06", derivedDateTime.getMinuteOfHour() == 6);

        //cleanup
        KRADServiceLocator.getBusinessObjectService().delete(gpr);
	}

    @Test
    public void testRoundingToMidnight() throws Exception {
        GracePeriodRule gpr = new GracePeriodRule();
        gpr.setActive(true);
        gpr.setEffectiveLocalDate(LocalDate.now());
        gpr.setHourFactor(new BigDecimal(3));

        KRADServiceLocator.getBusinessObjectService().save(gpr);
        gpr = TkServiceLocator.getGracePeriodService().getGracePeriodRule(LocalDate.now());
        Assert.assertTrue("fetched one rule", gpr != null);

        DateTime beginDateTime = new DateTime(2012, 10, 16, 23, 59, 0, 0, TKUtils.getSystemDateTimeZone());
        DateTime derivedDateTime = TkServiceLocator.getGracePeriodService().processGracePeriodRule(beginDateTime, LocalDate.now());

        Assert.assertTrue("rounded to midnight",derivedDateTime.equals(new DateTime(2012,10,17,0,0,0,0,TKUtils.getSystemDateTimeZone())));

        beginDateTime = new DateTime(2012, 10, 16, 0, 1, 0, 0, TKUtils.getSystemDateTimeZone());
        derivedDateTime = TkServiceLocator.getGracePeriodService().processGracePeriodRule(beginDateTime, LocalDate.now());
        Assert.assertTrue("rounded to midnight",derivedDateTime.equals(new DateTime(2012,10,16,0,0,0,0,TKUtils.getSystemDateTimeZone())));

        //cleanup
        KRADServiceLocator.getBusinessObjectService().delete(gpr);
    }
}
