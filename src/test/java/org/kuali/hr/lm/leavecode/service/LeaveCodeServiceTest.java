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
package org.kuali.hr.lm.leavecode.service;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.lm.leavecode.LeaveCode;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.service.base.TkServiceLocator;

public class LeaveCodeServiceTest extends KPMETestCase {
	
	@Test
	public void testRoundHrsWithLeaveCode() {
		LeaveCode leaveCode = new LeaveCode();
		leaveCode.setLeaveCode("testLC");
		leaveCode.setEligibleForAccrual("Y");
		leaveCode.setAccrualCategory("testAC");
		leaveCode.setLeavePlan("testLP");
		leaveCode.setUnitOfTime("M");
		
		leaveCode.setFractionalTimeAllowed("99.999");
		// Traditional rounding option
		leaveCode.setRoundingOption("T");
		
		BigDecimal hours = new BigDecimal("12.3846");
		BigDecimal roundedHours = TkServiceLocator.getLeaveCodeService().roundHrsWithLeaveCode(hours, leaveCode);
		Assert.assertTrue("Rounded hours should be 12.385", roundedHours.equals(new BigDecimal("12.385")));
		hours = new BigDecimal("5.1234");
		roundedHours = TkServiceLocator.getLeaveCodeService().roundHrsWithLeaveCode(hours, leaveCode);
		Assert.assertTrue("Rounded hours should be 5.123", roundedHours.equals(new BigDecimal("5.123")));
		hours = new BigDecimal("3.0");
		roundedHours = TkServiceLocator.getLeaveCodeService().roundHrsWithLeaveCode(hours, leaveCode);
		Assert.assertTrue("Rounded hours should be 3.000", roundedHours.equals(new BigDecimal("3.000")));
		
		// Truncate rounding option
		leaveCode.setRoundingOption("R");
		
		hours = new BigDecimal("12.3846");
		roundedHours = TkServiceLocator.getLeaveCodeService().roundHrsWithLeaveCode(hours, leaveCode);
		Assert.assertTrue("Rounded hours should be 12.384", roundedHours.equals(new BigDecimal("12.384")));
		hours = new BigDecimal("5.1234");
		roundedHours = TkServiceLocator.getLeaveCodeService().roundHrsWithLeaveCode(hours, leaveCode);
		Assert.assertTrue("Rounded hours should be 5.123", roundedHours.equals(new BigDecimal("5.123")));
		hours = new BigDecimal("3.0");
		roundedHours = TkServiceLocator.getLeaveCodeService().roundHrsWithLeaveCode(hours, leaveCode);
		Assert.assertTrue("Rounded hours should be 3.000", roundedHours.equals(new BigDecimal("3.000")));
	}

}
