package org.kuali.hr.time.assignment;

import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.kuali.hr.time.task.Task;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class Assignment extends PersistableBusinessObjectBase {

	/**
     *
     */
	private static final long serialVersionUID = -3408305833805778653L;

	private Long assignmentId;
	private String principalId;
	private BigInteger jobNumber;
	private Date effectiveDate;
	private String earnCode;
	private BigInteger workAreaId;
	private BigInteger taskId;
	private boolean active;

	private Task task;
	private WorkArea workArea;
	private List<AssignmentAccount> assignAccount = new ArrayList<AssignmentAccount>();

	public Assignment() {

	}

	public Assignment(String principalId, BigInteger jobNumber, Date effectiveDate,
			String earnCode, BigInteger workAreaId, BigInteger taskId) {
		this.principalId = principalId;
		this.jobNumber = jobNumber;
		this.effectiveDate = effectiveDate;
		this.earnCode = earnCode;
		this.workAreaId = workAreaId;
		this.taskId = taskId;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected LinkedHashMap toStringMapper() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}

	public BigInteger getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(BigInteger jobNumber) {
		this.jobNumber = jobNumber;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getEarnCode() {
		return earnCode;
	}

	public void setEarnCode(String earnCode) {
		this.earnCode = earnCode;
	}

	public BigInteger getTaskId() {
		return taskId;
	}

	public void setTaskId(BigInteger taskId) {
		this.taskId = taskId;
	}

	public Long getAssignmentId() {
		return assignmentId;
	}

	public void setAssignmentId(Long assignmentId) {
		this.assignmentId = assignmentId;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public BigInteger getWorkAreaId() {
		return workAreaId;
	}

	public void setWorkAreaId(BigInteger workAreaId) {
		this.workAreaId = workAreaId;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public WorkArea getWorkArea() {
		return workArea;
	}

	public void setWorkArea(WorkArea workArea) {
		this.workArea = workArea;
	}

	/**
	 * Provides us with the text to display to the user for clock actions on
	 * this assignment.
	 *
	 * @return
	 */
	public String getClockText() {
		StringBuilder sb = new StringBuilder("example assignment clock text");

		return sb.toString();
	}

	public List<AssignmentAccount> getAssignAccount() {
		return assignAccount;
	}

	public void setAssignAccount(List<AssignmentAccount> assignAccount) {
		this.assignAccount = assignAccount;
	}
}
