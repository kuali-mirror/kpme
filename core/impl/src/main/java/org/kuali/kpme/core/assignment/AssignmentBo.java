/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.core.assignment;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.assignment.Assignment;
import org.kuali.kpme.core.api.assignment.AssignmentContract;
import org.kuali.kpme.core.api.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.api.block.CalendarBlockPermissions;
import org.kuali.kpme.core.assignment.account.AssignmentAccountBo;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.job.JobBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.task.TaskBo;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.workarea.WorkAreaBo;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class AssignmentBo extends HrBusinessObject implements AssignmentContract {
    public static final ModelObjectUtils.Transformer<AssignmentBo, Assignment> toAssignment =
            new ModelObjectUtils.Transformer<AssignmentBo, Assignment>() {
                public Assignment transform(AssignmentBo input) {
                    return AssignmentBo.to(input);
                };
            };
    public static final ModelObjectUtils.Transformer<Assignment, AssignmentBo> toAssignmentBo =
            new ModelObjectUtils.Transformer<Assignment, AssignmentBo>() {
                public AssignmentBo transform(Assignment input) {
                    return AssignmentBo.from(input);
                };
            };
	private static final String PRINCIPAL_ID = "principalId";
	private static final String TASK = "task";
	private static final String WORK_AREA = "workArea";
	private static final String JOB_NUMBER = "jobNumber";
	
	private static final long serialVersionUID = 6347435053054442195L;
	//KPME-2273/1965 Primary Business Keys List. 
	public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
            .add(JOB_NUMBER)
            .add(WORK_AREA)
            .add(TASK)
            .add(PRINCIPAL_ID)
            .build();

    public static final ImmutableList<String> CACHE_FLUSH = new ImmutableList.Builder<String>()
            .add(AssignmentBo.CACHE_NAME)
            .add(CalendarBlockPermissions.CACHE_NAME)
            .build();
	public static final String CACHE_NAME = HrConstants.CacheNamespace.NAMESPACE_PREFIX + "Assignment";

	private String tkAssignmentId;
	private String principalId;
	private Long jobNumber;
	private String hrJobId;
	private transient JobBo job;
	private Long workArea;
	private Long task;
	private String dept;
	private boolean primaryAssign;
    private String calGroup;

	private transient WorkAreaBo workAreaObj;
	private transient Person principal;
	private transient TaskBo taskObj;


	private List<AssignmentAccountBo> assignmentAccounts = new LinkedList<AssignmentAccountBo>();

	
	@Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
		return new ImmutableMap.Builder<String, Object>()
				.put(JOB_NUMBER, this.getJobNumber())
				.put(WORK_AREA, this.getWorkArea())
				.put(TASK, this.getTask())
				.put(PRINCIPAL_ID, this.getPrincipalId())
				.build();
	}
	
	
	public List<AssignmentAccountBo> getAssignmentAccounts() {
		return assignmentAccounts;
	}

	public void setAssignmentAccounts(List<AssignmentAccountBo> assignmentAccounts) {
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

	public JobBo getJob() {
		if(job == null && this.getJobNumber() != null) {
			this.setJob(JobBo.from(HrServiceLocator.getJobService().getJob(this.getPrincipalId(), this.getJobNumber(), this.getEffectiveLocalDate())));
		}
		return job;
	}

	public void setJob(JobBo job) {
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
			if(this.getJob() == null || !this.getJobNumber().equals(this.getJob().getJobNumber()) || !this.getPrincipalId().equals(this.getJob().getPrincipalId())) {
				if(this.getEffectiveDate()!=null){
					this.setJob(JobBo.from(HrServiceLocator.getJobService().getJob(this.getPrincipalId(), this.getJobNumber(), this.getEffectiveLocalDate(), false)));
				}else{
					this.setJob(JobBo.from(HrServiceLocator.getJobService().getJob(this.getPrincipalId(), this.getJobNumber(), LocalDate.now(), false)));
				}
			}
			setDept((this.getJob() != null) ? this.getJob().getDept() : "");
		}
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public WorkAreaBo getWorkAreaObj() {
		if(workAreaObj == null && workArea != null) {
			this.setWorkAreaObj(WorkAreaBo.from(HrServiceLocator.getWorkAreaService().getWorkArea(this.getWorkArea(), this.getEffectiveLocalDate())));
		}
		return workAreaObj;
	}

	public void setWorkAreaObj(WorkAreaBo workAreaObj) {
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

	public String getAssignmentDescription() {
        return HrServiceLocator.getAssignmentService().getAssignmentDescription(getPrincipalId(), getJobNumber(), getWorkArea(), getTask(), getEffectiveLocalDate());
	}

	public Person getPrincipal() {
		return principal;
	}

	public void setPrincipal(Person principal) {
		this.principal = principal;
	}

	public TaskBo getTaskObj() {
		return taskObj;
	}

	public void setTaskObj(TaskBo taskObj) {
		this.taskObj = taskObj;
	}

	/*public Long getTkWorkAreaId() {
		return tkWorkAreaId;
	}

	public void setTkWorkAreaId(Long tkWorkAreaId) {
		this.tkWorkAreaId = tkWorkAreaId;
	}*/

	@Override
	public String getUniqueKey() {
		return getPrincipalId()+"_"+getJobNumber()+"_"+getWorkArea()+"_"+
			(getTask() != null ? getTask().toString() : "");

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
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }

        AssignmentBo rhs = (AssignmentBo)obj;
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

    public AssignmentDescriptionKey getAssignmentDescriptionKey() {
        return new AssignmentDescriptionKey(this);
    }


	public boolean isPrimaryAssign() {
		return primaryAssign;
	}


	public void setPrimaryAssign(boolean primaryAssign) {
		this.primaryAssign = primaryAssign;
	}

    public static AssignmentBo from(Assignment im) {
        if (im == null) {
            return null;
        }
        AssignmentBo assign = new AssignmentBo();

        assign.setTkAssignmentId(im.getTkAssignmentId());
        assign.setPrincipalId(im.getPrincipalId());
        assign.setJobNumber(im.getJobNumber());
        assign.setWorkArea(im.getWorkArea());
        assign.setTask(im.getTask());
        assign.setDept(im.getDept());
        assign.setPrimaryAssign(im.isPrimaryAssign());
        assign.setCalGroup(im.getCalGroup());
        assign.setJob(JobBo.from(im.getJob()));
        assign.setWorkAreaObj(WorkAreaBo.from(im.getWorkAreaObj()));
        assign.setTaskObj(TaskBo.from(im.getTaskObj()));
        if (CollectionUtils.isEmpty(im.getAssignmentAccounts())) {
            assign.setAssignmentAccounts(Collections.<AssignmentAccountBo>emptyList());
        } else {
            assign.setAssignmentAccounts(ModelObjectUtils.transform(im.getAssignmentAccounts(), AssignmentAccountBo.toAssignmentAccountBo));
        }

        assign.setEffectiveDate(im.getEffectiveLocalDate() == null ? null : im.getEffectiveLocalDate().toDate());
        assign.setActive(im.isActive());
        if (im.getCreateTime() != null) {
            assign.setTimestamp(new Timestamp(im.getCreateTime().getMillis()));
        }
        assign.setUserPrincipalId(im.getUserPrincipalId());
        assign.setVersionNumber(im.getVersionNumber());
        assign.setObjectId(im.getObjectId());

        return assign;
    }

    public static Assignment to(AssignmentBo bo) {
        if (bo == null) {
            return null;
        }

        return Assignment.Builder.create(bo).build();
    }

}
