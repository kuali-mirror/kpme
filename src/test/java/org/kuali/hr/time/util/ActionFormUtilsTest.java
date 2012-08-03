package org.kuali.hr.time.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.time.detail.web.ActionFormUtils;
import org.kuali.hr.time.test.TkTestCase;

public class ActionFormUtilsTest extends TkTestCase {
	
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
		assertTrue("There should be 1 warning message ", warningMess.size()== 1);
		String warning = warningMess.get(0);
		assertTrue("Warning message should be 'Test Message'", warning.equals("Test Message"));
		
	}

}
