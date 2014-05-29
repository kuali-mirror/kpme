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
package org.kuali.hr.core.accrualcategory;

import java.util.List;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.KPMEWebTestCase;
import org.kuali.kpme.core.FunctionalTest;
import org.kuali.kpme.core.api.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.api.accrualcategory.AccrualCategoryService;
import org.kuali.kpme.core.api.accrualcategory.rule.AccrualCategoryRule;
import org.kuali.kpme.core.api.accrualcategory.rule.AccrualCategoryRuleService;
import org.kuali.kpme.core.service.HrServiceLocator;

@FunctionalTest
public class AccrualCategoryRuleTest extends KPMEWebTestCase {

	private static String accrualCategoryId = "3000";
	private static String lmAccrualCategoryRuleId = "3000";
	private static final LocalDate TODAY_DATE = new LocalDate(2014, 5, 27);
	private static final LocalDate serviceDate1 = new LocalDate(2014, 5, 20);
	private static final LocalDate serviceDate2 = new LocalDate(2013, 10, 20);
	private static final LocalDate serviceDate3 = new LocalDate(2010, 10, 20);
	private AccrualCategoryRuleService accrualCategoryRuleService = null;
	private AccrualCategoryService accrualCategoryService = null;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();	
		accrualCategoryRuleService = HrServiceLocator.getAccrualCategoryRuleService();
		accrualCategoryService = HrServiceLocator.getAccrualCategoryService();
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testGetActiveRulesForAccrualCategoryId() throws Exception {
		List <AccrualCategoryRule> results = accrualCategoryRuleService.getActiveAccrualCategoryRules(accrualCategoryId);

		Assert.assertEquals("Search returned the number of results.", 4, results.size());
	}
	
	@Test
	public void testGetInActiveRulesForAccrualCategoryId() throws Exception {
		List <AccrualCategoryRule> results = accrualCategoryRuleService.getInActiveRulesForAccrualCategoryId(accrualCategoryId);
																		
		Assert.assertEquals("Search returned the number of results.", 1, results.size());
	}
	
	@Test
	public void testGetAccrualCategoryRule() throws Exception {
		AccrualCategoryRule result = accrualCategoryRuleService.getAccrualCategoryRule(lmAccrualCategoryRuleId);

		Assert.assertEquals("Should have AccrualCategoryRule with Id 3000.", lmAccrualCategoryRuleId, result.getLmAccrualCategoryRuleId());
	}
	
	@Test
	public void testGetAccrualCategoryRuleForDate() throws Exception {
		AccrualCategory accrualCategory = accrualCategoryService.getAccrualCategory(accrualCategoryId);
		
		//TODAY_DATE=(2014, 5, 27), serviceDate1=(2014, 5, 20), serviced for less than 6 months, should have LmAccrualCategoryRuleId=3000
		AccrualCategoryRule result = accrualCategoryRuleService.getAccrualCategoryRuleForDate(accrualCategory, TODAY_DATE, serviceDate1);
		Assert.assertEquals("Should have AccrualCategoryRule with Id 3000.", "3000", result.getLmAccrualCategoryRuleId());
		
		//TODAY_DATE=(2014, 5, 27), serviceDate2=(2013, 10, 20), serviced for more than 6 months but less than 13 months, should have LmAccrualCategoryRuleId=3001
		AccrualCategoryRule result2 = accrualCategoryRuleService.getAccrualCategoryRuleForDate(accrualCategory, TODAY_DATE, serviceDate2);
		Assert.assertEquals("Should have AccrualCategoryRule with Id 3001.", "3001", result2.getLmAccrualCategoryRuleId());
		
		//TODAY_DATE=(2014, 5, 27), serviceDate3=(2010, 10, 20), serviced for more than 24 months, should have LmAccrualCategoryRuleId=3003
		AccrualCategoryRule result3 = accrualCategoryRuleService.getAccrualCategoryRuleForDate(accrualCategory, TODAY_DATE, serviceDate3);
		Assert.assertEquals("Should have AccrualCategoryRule with Id 3003.", "3003", result3.getLmAccrualCategoryRuleId());
	}
}
