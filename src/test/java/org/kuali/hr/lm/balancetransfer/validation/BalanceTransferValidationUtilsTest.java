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
package org.kuali.hr.lm.balancetransfer.validation;

import java.sql.Date;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.lm.balancetransfer.BalanceTransfer;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.krad.util.GlobalVariables;

public class BalanceTransferValidationUtilsTest extends KPMETestCase {

	@Test
	public void TestValidateSstoTranser() {
		Date leaveDate = new Date((new DateTime(2013, 1, 11, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
		BalanceTransfer bt = new BalanceTransfer();
		bt.setEffectiveDate(leaveDate);
		bt.setPrincipalId("testUser");
		
		boolean flag = BalanceTransferValidationUtils.validateSstoTranser(bt);
		Assert.assertFalse("Validation should be false", flag);
		Assert.assertTrue("There should be 1 error messages",  GlobalVariables.getMessageMap().getErrorCount() == 1);
		Assert.assertTrue("There should be error messages for SSTO does not exist", GlobalVariables.getMessageMap().containsMessageKey("balanceTransfer.transferSSTO.sstoDoesNotExis"));
		
		GlobalVariables.getMessageMap().clearErrorMessages();
		bt.setSstoId("5000");
		bt.setFromAccrualCategory("fromAC");
		flag = BalanceTransferValidationUtils.validateSstoTranser(bt);
		Assert.assertFalse("Validation should be false", flag);
		Assert.assertTrue("There should be 1 error messages",  GlobalVariables.getMessageMap().getErrorCount() == 1);
		Assert.assertTrue("There should be error messages for From Accrual category being wrong", GlobalVariables.getMessageMap().containsMessageKey("balanceTransfer.transferSSTO.fromACWrong"));
		
		GlobalVariables.getMessageMap().clearErrorMessages();
		bt.setFromAccrualCategory("ISU-HOL");
		bt.setToAccrualCategory("ISU-HOL");
		flag = BalanceTransferValidationUtils.validateSstoTranser(bt);
		Assert.assertFalse("Validation should be false", flag);
		Assert.assertTrue("There should be 1 error messages",  GlobalVariables.getMessageMap().getErrorCount() == 1);
		Assert.assertTrue("There should be error messages for From and To Accrual categories are the same", GlobalVariables.getMessageMap().containsMessageKey("balanceTransfer.transferSSTO.fromAndToACTheSame"));
		
		GlobalVariables.getMessageMap().clearErrorMessages();
		bt.setToAccrualCategory("ISU-HOL-TRF");
		flag = BalanceTransferValidationUtils.validateSstoTranser(bt);
		Assert.assertFalse("Validation should be false", flag);
		Assert.assertTrue("There should be 1 error messages",  GlobalVariables.getMessageMap().getErrorCount() == 1);
		Assert.assertTrue("There should be error messages for Accrual categories does not exist", GlobalVariables.getMessageMap().containsMessageKey("balanceTransfer.transferSSTO.acDoesNotExist"));
		
		leaveDate = new Date((new DateTime(2013, 2, 11, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
		bt.setEffectiveDate(leaveDate);
		GlobalVariables.getMessageMap().clearErrorMessages();
		flag = BalanceTransferValidationUtils.validateSstoTranser(bt);
		Assert.assertTrue("Validation should be true", flag);
		Assert.assertTrue("There should be NO error messages",  GlobalVariables.getMessageMap().getErrorCount() == 0);
	}
	
}
