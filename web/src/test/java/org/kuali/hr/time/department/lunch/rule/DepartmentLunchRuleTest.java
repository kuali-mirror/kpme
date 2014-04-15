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
package org.kuali.hr.time.department.lunch.rule;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.KPMEWebTestCase;
import org.kuali.kpme.core.FunctionalTest;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.api.time.timehourdetail.TimeHourDetail;
import org.kuali.kpme.tklm.time.rules.lunch.department.DeptLunchRule;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timeblock.TimeBlockBo;
import org.kuali.kpme.tklm.time.timehourdetail.TimeHourDetailBo;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.kpme.tklm.utils.TkTestUtils;
import org.kuali.rice.krad.service.KRADServiceLocator;

@FunctionalTest
public class DepartmentLunchRuleTest extends KPMEWebTestCase {
    private DateTime JAN_AS_OF_DATE = new DateTime(2010, 1, 1, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());

	@Test
	public void testDepartmentLunchRuleFetch() throws Exception{
		DeptLunchRule deptLunchRule = new DeptLunchRule();
		deptLunchRule.setActive(true);
		deptLunchRule.setDept("TEST");
		deptLunchRule.setWorkArea(1234L);
		deptLunchRule.setEffectiveLocalDate(JAN_AS_OF_DATE.toLocalDate());
		deptLunchRule.setJobNumber(0L);
		deptLunchRule.setPrincipalId("admin");
		deptLunchRule.setDeductionMins(new BigDecimal(30));
		deptLunchRule.setShiftHours(new BigDecimal(6));
		deptLunchRule.setTkDeptLunchRuleId("1001");
        deptLunchRule.setUserPrincipalId("admin");

		KRADServiceLocator.getBusinessObjectService().save(deptLunchRule);

		deptLunchRule = TkServiceLocator.getDepartmentLunchRuleService().getDepartmentLunchRule("TEST",
											1234L, "admin", 0L, JAN_AS_OF_DATE.toLocalDate());
		Assert.assertTrue("dept lunch rule fetched ", deptLunchRule!=null);

	}

	/**
	 * Test if the minute deduction rule is applied correctly if there is a valid department lunch rule
	 */

	@Test
	public void testDepartmentLunchRule() throws Exception {
		// create a dept lunch rule
		DeptLunchRule deptLunchRule = new DeptLunchRule();
		deptLunchRule.setActive(true);
		deptLunchRule.setDept("TEST-DEPT");
		deptLunchRule.setWorkArea(1234L);
		deptLunchRule.setEffectiveLocalDate(JAN_AS_OF_DATE.toLocalDate());
		deptLunchRule.setJobNumber(1L);
		deptLunchRule.setPrincipalId("edna");
		deptLunchRule.setDeductionMins(new BigDecimal(30));
		deptLunchRule.setShiftHours(new BigDecimal(6));
		deptLunchRule.setTkDeptLunchRuleId("1001");
        deptLunchRule.setUserPrincipalId("admin");

		KRADServiceLocator.getBusinessObjectService().save(deptLunchRule);

		deptLunchRule = TkServiceLocator.getDepartmentLunchRuleService().getDepartmentLunchRule("TEST-DEPT",
											1234L, "edna", 1L, JAN_AS_OF_DATE.toLocalDate());
		Assert.assertTrue("dept lunch rule fetched ", deptLunchRule!=null);

		TimesheetDocument doc = TkTestUtils.populateTimesheetDocument(JAN_AS_OF_DATE, "edna");

        List<TimeBlock> timeBlocks = new ArrayList<TimeBlock>(doc.getTimeBlocks());
		for(TimeBlock tb : doc.getTimeBlocks()){
            TimeBlock.Builder b = TimeBlock.Builder.create(tb);
			b.setClockLogCreated(true);
            timeBlocks.add(b.build());
		}
		for(TimeBlock tb : timeBlocks) {
			if(tb.getHours().compareTo(deptLunchRule.getShiftHours()) == 1) {
				for(TimeHourDetail thd : tb.getTimeHourDetails()){
					// 	this assumes the hours for the dummy timeblocks are always 10
					if(!StringUtils.equals(thd.getEarnCode(), HrConstants.LUNCH_EARN_CODE)){
						Assert.assertEquals(new BigDecimal(9.50).setScale(2), tb.getHours());
					}
				}
			}
		}

	}

	@Test
	public void testSearchDepartmentLunchRules() throws Exception {
		List<DeptLunchRule> allResults = TkServiceLocator.getDepartmentLunchRuleService().getDepartmentLunchRules("admin", null, null, null, null, null, null, "Y", "N");
		Assert.assertEquals("Search returned the wrong number of results.", 2, allResults.size());
		
		List<DeptLunchRule> restrictedResults = TkServiceLocator.getDepartmentLunchRuleService().getDepartmentLunchRules("fran", null, null, null, null, null, null, "Y", "N");
		Assert.assertEquals("Search returned the wrong number of results.", 0, restrictedResults.size());
	}
	
}