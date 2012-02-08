package org.kuali.hr.lm.leaveadjustment.service;

import java.sql.Date;
import java.util.List;
import org.kuali.hr.lm.leaveadjustment.LeaveAdjustment;
import org.kuali.hr.lm.leaveadjustment.dao.LeaveAdjustmentDao;

public class LeaveAdjustmentServiceImpl implements LeaveAdjustmentService {
	
	private LeaveAdjustmentDao leaveAdjustmentDao;

	@Override
	public List<LeaveAdjustment> getLeaveAdjustments(String principalId, Date asOfDate) {
		return leaveAdjustmentDao.getLeaveAdjustments(principalId, asOfDate);
	}

	@Override
	public LeaveAdjustment getLeaveAdjustment(String lmLeaveAdjustmentId) {
		return leaveAdjustmentDao.getLeaveAdjustment(lmLeaveAdjustmentId);
	}

	public LeaveAdjustmentDao getLeaveAdjustmentDao() {
		return leaveAdjustmentDao;
	}

	public void setLeaveAdjustmentDao(LeaveAdjustmentDao leaveAdjustmentDao) {
		this.leaveAdjustmentDao = leaveAdjustmentDao;
	}

}
