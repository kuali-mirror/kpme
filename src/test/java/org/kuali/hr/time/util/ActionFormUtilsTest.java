package org.kuali.hr.time.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.detail.web.ActionFormUtils;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.service.base.TkServiceLocator;

public class ActionFormUtilsTest extends KPMETestCase {
	
	@Test
	public void testFmlaWarningTextForLeaveBlocks() throws Exception {
		// create two leave blocks with two different earn codes
		// earn code "ECA" has fmla=Y
		// earn Code "ECB" has fmla = N
		
		List<LeaveBlock> leaveBlocs = new ArrayList<LeaveBlock>();
		LeaveBlock lbA = new LeaveBlock();
		lbA.setEarnCode("ECA");
		lbA.setLeaveDate(TKUtils.getCurrentDate());
		leaveBlocs.add(lbA);
		
		LeaveBlock lbB = new LeaveBlock();
		lbB.setEarnCode("ECB");
		lbA.setLeaveDate(TKUtils.getCurrentDate());
		leaveBlocs.add(lbB);
		
		List<String> warningMess = ActionFormUtils.fmlaWarningTextForLeaveBlocks(leaveBlocs);
		Assert.assertTrue("There should be 1 warning message ", warningMess.size()== 1);
		String warning = warningMess.get(0);
		Assert.assertTrue("Warning message should be 'Test Message'", warning.equals("Test Message"));
		
	}
	@Test
	public void testGetUnitOfTimeForEarnCode() throws Exception {
		// earn code with an existing Accrual category
		EarnCode earnCode = TkServiceLocator.getEarnCodeService().getEarnCodeById("5000");
		String unitOfTime = ActionFormUtils.getUnitOfTimeForEarnCode(earnCode);
		Assert.assertTrue("Unit of Time should be 'D', not " + unitOfTime, unitOfTime.equals("D"));
		// earn code without an existing accrual category
		earnCode = TkServiceLocator.getEarnCodeService().getEarnCodeById("5002");
		unitOfTime = ActionFormUtils.getUnitOfTimeForEarnCode(earnCode);
		Assert.assertTrue("Unit of Time should be 'H', not " + unitOfTime, unitOfTime.equals("H"));
		
	}
	

}
