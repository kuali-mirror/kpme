package org.kuali.hr.lm.leaveadjustment.dao;

import java.sql.Date;
import java.util.List;
import org.kuali.hr.lm.leaveadjustment.LeaveAdjustment;

public interface LeaveAdjustmentDao {

	public List<LeaveAdjustment> getLeaveAdjustments(String principalId, Date asOfDate);
	
	public LeaveAdjustment getLeaveAdjustment(String tkLeaveAdjustmentId);
}
