package org.kuali.hr.time.assignment;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.kuali.hr.job.Job;
import org.kuali.hr.time.collection.rule.TimeCollectionRule;
import org.kuali.hr.time.dept.lunch.DeptLunchRule;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class Assignment extends PersistableBusinessObjectBase {

	/**
     *
     */
	private static final long serialVersionUID = -3408305833805778653L;

	private Long tkAssignmentId;
	private String principalId;
	private Long jobNumber;
	private Job job;
	private Date effectiveDate;
	private Long workArea;
	private Long task;
	private boolean active;
	private Timestamp timestamp;
	private TimeCollectionRule timeCollectionRule;
	private DeptLunchRule deptLunchRule;
	private String assignmentDescription;

	private WorkArea workAreaObj;

	private List<AssignmentAccount> assignmentAccounts = new LinkedList<AssignmentAccount>();

	public List<AssignmentAccount> getAssignmentAccounts() {
		return assignmentAccounts;
	}

	public void setAssignmentAccounts(List<AssignmentAccount> assignmentAccounts) {
		this.assignmentAccounts = assignmentAccounts;
	}
	
	public Assignment(){}

	public Assignment(String principalId, Long jobNumber, Date effectiveDate,
			String earnCode, Long workAreaId, Long taskId) {
		this.principalId = principalId;
		this.jobNumber = jobNumber;
		this.effectiveDate = effectiveDate;
		this.workArea = workAreaId;
		this.task = taskId;
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

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public Long getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(Long jobNumber) {
		this.jobNumber = jobNumber;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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

	public Long getTkAssignmentId() {
		return tkAssignmentId;
	}

	public void setTkAssignmentId(Long tkAssignmentId) {
		this.tkAssignmentId = tkAssignmentId;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public void setWorkArea(Long workArea) {
		this.workArea = workArea;
	}

	public void setTask(Long task) {
		this.task = task;
	}
	
	public WorkArea getWorkAreaObj() {
		return workAreaObj;
	}

	public void setWorkAreaObj(WorkArea workAreaObj) {
		this.workAreaObj = workAreaObj;
	}

	public Long getWorkArea() {
		return workArea;
	}

	public Long getTask() {
		return task;
	}

	public void setTimeCollectionRule(TimeCollectionRule timeCollectionRule) {
		this.timeCollectionRule = timeCollectionRule;
	}

	public TimeCollectionRule getTimeCollectionRule() {
		return timeCollectionRule;
	}

	public boolean isSynchronous() {
		return timeCollectionRule.isClockUserFl();
	}

	public DeptLunchRule getDeptLunchRule() {
		return deptLunchRule;
	}

	public void setDeptLunchRule(DeptLunchRule deptLunchRule) {
		this.deptLunchRule = deptLunchRule;
	}

	public String getAssignmentDescription() {
		return TKUtils.getAssignmentString(this);
	}

	public void setAssignmentDescription(String assignmentDescription) {
		this.assignmentDescription = assignmentDescription;
	}
	
}
