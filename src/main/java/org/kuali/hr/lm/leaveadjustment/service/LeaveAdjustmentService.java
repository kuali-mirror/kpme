package org.kuali.hr.lm.leaveadjustment.service;

import java.sql.Date;
import java.util.List;
import org.kuali.hr.lm.leaveadjustment.LeaveAdjustment;

public interface LeaveAdjustmentService {
	public List<LeaveAdjustment> getLeaveAdjustments(String principalId, Date asOfDate);
	
	public LeaveAdjustment getLeaveAdjustment(String tkLeaveAdjustmentId);
}
