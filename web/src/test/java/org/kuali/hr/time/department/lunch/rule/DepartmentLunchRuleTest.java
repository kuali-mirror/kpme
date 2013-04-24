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
package org.kuali.hr.time.department.lunch.rule;

import java.math.BigDecimal;
import java.util.Calendar;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.test.TkTestUtils;
import org.kuali.hr.tklm.time.rules.lunch.department.DeptLunchRule;
import org.kuali.hr.tklm.time.service.base.TkServiceLocator;
import org.kuali.hr.tklm.time.timeblock.TimeBlock;
import org.kuali.hr.tklm.time.timeblock.TimeHourDetail;
import org.kuali.hr.tklm.time.timesheet.TimesheetDocument;
import org.kuali.hr.tklm.time.util.TKContext;
import org.kuali.hr.tklm.time.util.TKUtils;
import org.kuali.hr.tklm.time.util.TkConstants;
import org.kuali.rice.krad.service.KRADServiceLocator;

public class DepartmentLunchRuleTest extends KPMETestCase {
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
		Calendar cal = Calendar.getInstance();
		cal.set(2010, 1, 1);
		deptLunchRule.setEffectiveLocalDate(JAN_AS_OF_DATE.toLocalDate());
		deptLunchRule.setJobNumber(1L);
		deptLunchRule.setPrincipalId("edna");
		deptLunchRule.setDeductionMins(new BigDecimal(30));
		deptLunchRule.setShiftHours(new BigDecimal(6));
		deptLunchRule.setTkDeptLunchRuleId("1001");

		KRADServiceLocator.getBusinessObjectService().save(deptLunchRule);

		deptLunchRule = TkServiceLocator.getDepartmentLunchRuleService().getDepartmentLunchRule("TEST-DEPT",
											1234L, "edna", 1L, JAN_AS_OF_DATE.toLocalDate());
		Assert.assertTrue("dept lunch rule fetched ", deptLunchRule!=null);

        TKContext.setTargetPrincipalId("edna");
		TimesheetDocument doc = TkTestUtils.populateTimesheetDocument(JAN_AS_OF_DATE);

		for(TimeBlock tb : doc.getTimeBlocks()){
			tb.setClockLogCreated(true);
		}
        //reset time block
        //TkServiceLocator.getTimesheetService().resetTimeBlock(doc.getTimeBlocks());
		//TkServiceLocator.getTkRuleControllerService().applyRules(TkConstants.ACTIONS.ADD_TIME_BLOCK, doc.getTimeBlocks(), doc.getCalendarEntry(), doc, "admin");
		for(TimeBlock tb : doc.getTimeBlocks()) {
			if(tb.getHours().compareTo(deptLunchRule.getShiftHours()) == 1) {
				for(TimeHourDetail thd : tb.getTimeHourDetails()){
					// 	this assumes the hours for the dummy timeblocks are always 10
					if(!StringUtils.equals(thd.getEarnCode(), TkConstants.LUNCH_EARN_CODE)){
						Assert.assertEquals(new BigDecimal(9.50).setScale(2), tb.getHours());
					}
				}
			}
		}

	}

    /*@Override
    public void tearDown() throws Exception {
        TKUser.clearTargetUser();
        super.tearDown();
    }*/
}
