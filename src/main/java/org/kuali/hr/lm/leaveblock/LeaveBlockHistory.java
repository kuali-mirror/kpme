package org.kuali.hr.lm.leaveblock;

import java.sql.Timestamp;
import java.util.LinkedHashMap;

import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.task.Task;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workarea.WorkArea;

public class LeaveBlockHistory extends LeaveBlock {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String lmLeaveBlockHistoryId;
	private String action;
	private String principalIdDeleted;
	private Timestamp timestampDeleted;
	
	public LeaveBlockHistory() {
	}

	public LeaveBlockHistory(LeaveBlock lb) {
		this.setLmLeaveBlockId(lb.getLmLeaveBlockId());
		this.setDocumentId(lb.getDocumentId());
		this.setAccrualCategoryId(lb.getAccrualCategoryId());
		this.setAccrualGenerated(lb.getAccrualGenerated());
		this.setApplyToYtdUsed(lb.getApplyToYtdUsed());
		this.setDescription(lb.getDescription());
		this.setLeaveAmount(lb.getLeaveAmount());
		this.setEarnCodeId(lb.getEarnCodeId());
		this.setEarnCode(lb.getEarnCode());
		this.setLeaveDate(lb.getLeaveDate());
		this.setPrincipalId(lb.getPrincipalId());
//		this.setPrincipalIdModified(lb.getPrincipalIdModified());
		this.setRequestStatus(lb.getRequestStatus());
		this.setTimestamp(lb.getTimestamp());
		this.setTkAssignmentId(lb.getTkAssignmentId());
		this.setScheduleTimeOffId(lb.getScheduleTimeOffId());
		
	}
	
	
	public String getLmLeaveBlockHistoryId() {
		return lmLeaveBlockHistoryId;
	}

	public void setLmLeaveBlockHistoryId(String lmLeaveBlockHistoryId) {
		this.lmLeaveBlockHistoryId = lmLeaveBlockHistoryId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getPrincipalIdDeleted() {
		return principalIdDeleted;
	}

	public void setPrincipalIdDeleted(String principalIdDeleted) {
		this.principalIdDeleted = principalIdDeleted;
	}

	public Timestamp getTimestampDeleted() {
		return timestampDeleted;
	}

	public void setTimestampDeleted(Timestamp timestampDeleted) {
		this.timestampDeleted = timestampDeleted;
	}

//	@Override
//	protected LinkedHashMap toStringMapper() {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	public String getAssignmentTitle() {
		StringBuilder b = new StringBuilder();
		LeaveBlock lb = TkServiceLocator.getLeaveBlockService().getLeaveBlock(Long.parseLong(super.getLmLeaveBlockId()));
		if(lb != null){
			if (lb.getWorkArea() != null) {
				WorkArea wa = TkServiceLocator.getWorkAreaService().getWorkArea(
					lb.getWorkArea(), TKUtils.getCurrentDate());
				if (wa != null) {
					b.append(wa.getDescription());
				}
				Task task = TkServiceLocator.getTaskService().getTask(
						this.getTask(), this.getLeaveDate());
				if (task != null) {
					// do not display task description if the task is the default
					// one
					// default task is created in getTask() of TaskService
					if (!task.getDescription()
						.equals(TkConstants.TASK_DEFAULT_DESP)) {
						b.append("-" + task.getDescription());
					}
				}
			}
		}
		return b.toString();
	}

}
