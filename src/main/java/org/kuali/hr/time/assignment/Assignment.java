package org.kuali.hr.time.assignment;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.collection.rule.TimeCollectionRule;
import org.kuali.hr.time.dept.lunch.DeptLunchRule;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.task.Task;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class Assignment extends HrBusinessObject {

	/**
     *
     */
	private static final long serialVersionUID = -3408305833805778653L;
	//database id
	private Long tkAssignmentId;
	private String principalId;
	private Long jobNumber;
	private Long hrJobId;
	private Job job;
	private Long workArea;
	private Long tkWorkAreaId;
	private Long task;
	private String dept;
	private TimeCollectionRule timeCollectionRule;
	private DeptLunchRule deptLunchRule;
	private WorkArea workAreaObj;
	private Boolean history;

	private Person principal;

	private Task taskObj;

    private String calGroup;

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

	@SuppressWarnings({ "rawtypes" })
	@Override
	protected LinkedHashMap toStringMapper() {
		LinkedHashMap<String, Object> toStringMap = new LinkedHashMap<String, Object>();
		toStringMap.put("tkAssignmentId", tkAssignmentId);
		toStringMap.put("principalId", principalId);
		toStringMap.put("jobNumber", jobNumber);
		toStringMap.put("workArea", workArea);
		toStringMap.put("task", task);

		return toStringMap;
	}

	public String getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}

	public String getName() {
		if (principal == null) {
        principal = KIMServiceLocator.getPersonService().getPerson(this.principalId);
		}
		return (principal != null) ? principal.getName() : "";
	}

	public Job getJob() {
		if(job == null && this.getJobNumber() != null) {
			this.setJob(TkServiceLocator.getJobSerivce().getJob(this.getPrincipalId(), this.getJobNumber(), this.getEffectiveDate()));
		}
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

	public Long getHrJobId() {
		return hrJobId;
	}

	public void setHrJobId(Long hrJobId) {
		this.hrJobId = hrJobId;
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

	public String getDept() {
		if(this.getJobNumber()!= null) {
			if(this.getJob() == null || !this.getJobNumber().equals(this.getJob().getJobNumber())) {
				if(this.getEffectiveDate()!=null){
					this.setJob(TkServiceLocator.getJobSerivce().getJob(this.getPrincipalId(), this.getJobNumber(), this.getEffectiveDate(), false));
				}else{
					this.setJob(TkServiceLocator.getJobSerivce().getJob(this.getPrincipalId(), this.getJobNumber(), TKUtils.getCurrentDate(), false));
				}
			}
			setDept((this.getJob() != null) ? this.getJob().getDept() : "");
		}
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public WorkArea getWorkAreaObj() {
		if(workAreaObj == null && workArea != null) {
			this.setWorkAreaObj(TkServiceLocator.getWorkAreaService().getWorkArea(this.getWorkArea(), this.getEffectiveDate()));
		}
		return workAreaObj;
	}

	public void setWorkAreaObj(WorkArea workAreaObj) {
		this.workAreaObj = workAreaObj;
	}

	public Long getWorkArea() {
		return workArea;
	}

	public Long getTask() {
		if(task == null) {
			return new Long(0);	// default task to 0 if task not provided
		}
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

	public Person getPrincipal() {
		return principal;
	}

	public void setPrincipal(Person principal) {
		this.principal = principal;
	}

	public Task getTaskObj() {
		return taskObj;
	}

	public void setTaskObj(Task taskObj) {
		this.taskObj = taskObj;
	}

	public Long getTkWorkAreaId() {
		return tkWorkAreaId;
	}

	public void setTkWorkAreaId(Long tkWorkAreaId) {
		this.tkWorkAreaId = tkWorkAreaId;
	}
	public Boolean getHistory() {
		return history;
	}
	public void setHistory(Boolean history) {
		this.history = history;
	}

	@Override
	protected String getUniqueKey() {
		String jobKey = getPrincipalId()+"_"+getJobNumber()+"_"+getWorkArea()+"_"+
			(getTask() != null ? getTask().toString() : "");
		return jobKey;

	}

	@Override
	public Long getId() {
		return getHrJobId();
	}

	@Override
	public void setId(Long id) {
		setHrJobId(id);
	}

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj.getClass() != getClass())
            return false;

        Assignment rhs = (Assignment)obj;
        return new EqualsBuilder().append(principalId, rhs.principalId).append(jobNumber, rhs.jobNumber)
                .append(workArea, rhs.workArea).append(task, rhs.task).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 781).append(principalId).append(jobNumber).append(workArea).append(task).toHashCode();
    }

    public String getCalGroup() {
        return calGroup;
    }

    public void setCalGroup(String calGroup) {
        this.calGroup = calGroup;
    }
}
