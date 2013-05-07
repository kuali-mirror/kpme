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
package org.kuali.kpme.core.bo.assignment;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.KPMEConstants;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.assignment.account.AssignmentAccount;
import org.kuali.kpme.core.bo.job.Job;
import org.kuali.kpme.core.bo.task.Task;
import org.kuali.kpme.core.bo.workarea.WorkArea;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.time.rules.lunch.department.DeptLunchRule;
import org.kuali.kpme.tklm.time.rules.timecollection.TimeCollectionRule;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

import com.google.common.collect.ImmutableList;

public class Assignment extends HrBusinessObject {

	private static final long serialVersionUID = 6347435053054442195L;
	//KPME-2273/1965 Primary Business Keys List. 
	public static final ImmutableList<String> EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
            .add("jobNumber")
            .add("workArea")
            .add("task")
            .add("principalId")
            .build();

   
	public static final String CACHE_NAME = KPMEConstants.APPLICATION_NAMESPACE_CODE + "/" + "Assignment";

	private String tkAssignmentId;
	private String principalId;
	private Long jobNumber;
	private String hrJobId;
	private transient Job job;
	private Long workArea;
	//private Long tkWorkAreaId;
	private Long task;
	private String dept;
	private transient TimeCollectionRule timeCollectionRule;
	private transient DeptLunchRule deptLunchRule;
	private transient WorkArea workAreaObj;
	private Boolean history;
    private String assignmentKey;

	private transient Person principal;

	private transient Task taskObj;

    private String calGroup;

	private List<AssignmentAccount> assignmentAccounts = new LinkedList<AssignmentAccount>();

	public List<AssignmentAccount> getAssignmentAccounts() {
		return assignmentAccounts;
	}

	public void setAssignmentAccounts(List<AssignmentAccount> assignmentAccounts) {
		this.assignmentAccounts = assignmentAccounts;
	}

	public String getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
		this.setPrincipal(KimApiServiceLocator.getPersonService().getPerson(this.principalId));
	}

	public String getName() {
		if (principal == null) {
        principal = KimApiServiceLocator.getPersonService().getPerson(this.principalId);
		}
		return (principal != null) ? principal.getName() : "";
	}

	public Job getJob() {
		if(job == null && this.getJobNumber() != null) {
			this.setJob(HrServiceLocator.getJobService().getJob(this.getPrincipalId(), this.getJobNumber(), this.getEffectiveLocalDate()));
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

	public String getHrJobId() {
		return hrJobId;
	}

	public void setHrJobId(String hrJobId) {
		this.hrJobId = hrJobId;
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

	public String getTkAssignmentId() {
		return tkAssignmentId;

    }
	public void setTkAssignmentId(String tkAssignmentId) {
		this.tkAssignmentId = tkAssignmentId;
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
					this.setJob(HrServiceLocator.getJobService().getJob(this.getPrincipalId(), this.getJobNumber(), this.getEffectiveLocalDate(), false));
				}else{
					this.setJob(HrServiceLocator.getJobService().getJob(this.getPrincipalId(), this.getJobNumber(), LocalDate.now(), false));
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
			this.setWorkAreaObj(HrServiceLocator.getWorkAreaService().getWorkArea(this.getWorkArea(), this.getEffectiveLocalDate()));
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
			return Long.valueOf(0);	// default task to 0 if task not provided
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
		return timeCollectionRule == null || timeCollectionRule.isClockUserFl();
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

	/*public Long getTkWorkAreaId() {
		return tkWorkAreaId;
	}

	public void setTkWorkAreaId(Long tkWorkAreaId) {
		this.tkWorkAreaId = tkWorkAreaId;
	}*/
	public Boolean getHistory() {
		return history;
	}
	public void setHistory(Boolean history) {
		this.history = history;
	}

	@Override
	public String getUniqueKey() {
		String jobKey = getPrincipalId()+"_"+getJobNumber()+"_"+getWorkArea()+"_"+
			(getTask() != null ? getTask().toString() : "");
		return jobKey;

	}

	@Override
	public String getId() {
		return getTkAssignmentId();
	}

	@Override
	public void setId(String id) {
		setTkAssignmentId(id);
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

    public String getAssignmentKey() {
        return new AssignmentDescriptionKey(this).toAssignmentKeyString();
    }
}
