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
package org.kuali.kpme.core.api.assignment;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.assignment.account.AssignmentAccount;
import org.kuali.kpme.core.api.assignment.account.AssignmentAccountContract;
import org.kuali.kpme.core.api.job.Job;
import org.kuali.kpme.core.api.task.Task;
import org.kuali.kpme.core.api.workarea.WorkArea;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.core.api.util.jaxb.DateTimeAdapter;
import org.kuali.rice.core.api.util.jaxb.LocalDateAdapter;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@XmlRootElement(name = Assignment.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = Assignment.Constants.TYPE_NAME, propOrder = {
        Assignment.Elements.NAME,
        Assignment.Elements.ASSIGNMENT_ACCOUNTS,
        Assignment.Elements.PRINCIPAL_ID,
        Assignment.Elements.JOB,
        Assignment.Elements.JOB_NUMBER,
        Assignment.Elements.CLOCK_TEXT,
        Assignment.Elements.TK_ASSIGNMENT_ID,
        Assignment.Elements.DEPT,
        Assignment.Elements.WORK_AREA_OBJ,
        Assignment.Elements.WORK_AREA,
        Assignment.Elements.TASK,
        Assignment.Elements.TASK_OBJ,
        Assignment.Elements.CAL_GROUP,
        Assignment.Elements.ASSIGNMENT_KEY,
        Assignment.Elements.PRIMARY_ASSIGN,
        Assignment.Elements.ASSIGNMENT_DESCRIPTION,
        Assignment.Elements.ACTIVE,
        Assignment.Elements.ID,
        Assignment.Elements.EFFECTIVE_LOCAL_DATE,
        Assignment.Elements.CREATE_TIME,
        Assignment.Elements.USER_PRINCIPAL_ID,
        CoreConstants.CommonElements.VERSION_NUMBER,
        CoreConstants.CommonElements.OBJECT_ID,
        CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class Assignment
        extends AbstractDataTransferObject
        implements AssignmentContract
{

    private static final long serialVersionUID = -8869596129477285844L;
    @XmlElement(name = Elements.NAME, required = false)
    private final String name;
    @XmlElement(name = Elements.ASSIGNMENT_ACCOUNTS, required = false)
    private final List<AssignmentAccount> assignmentAccounts;
    @XmlElement(name = Elements.PRINCIPAL_ID, required = false)
    private final String principalId;
    @XmlElement(name = Elements.JOB, required = false)
    private final Job job;
    @XmlElement(name = Elements.JOB_NUMBER, required = false)
    private final Long jobNumber;
    @XmlElement(name = Elements.CLOCK_TEXT, required = false)
    private final String clockText;
    @XmlElement(name = Elements.TK_ASSIGNMENT_ID, required = false)
    private final String tkAssignmentId;
    @XmlElement(name = Elements.DEPT, required = false)
    private final String dept;
    @XmlElement(name = Elements.WORK_AREA_OBJ, required = false)
    private final WorkArea workAreaObj;
    @XmlElement(name = Elements.WORK_AREA, required = false)
    private final Long workArea;
    @XmlElement(name = Elements.TASK, required = false)
    private final Long task;
    @XmlElement(name = Elements.TASK_OBJ, required = false)
    private final Task taskObj;
    @XmlElement(name = Elements.CAL_GROUP, required = false)
    private final String calGroup;
    @XmlElement(name = Elements.ASSIGNMENT_KEY, required = false)
    private final String assignmentKey;
    @XmlElement(name = Elements.PRIMARY_ASSIGN, required = false)
    private final boolean primaryAssign;
    @XmlElement(name = Elements.ASSIGNMENT_DESCRIPTION, required = false)
    private final String assignmentDescription;
    @XmlElement(name = CoreConstants.CommonElements.VERSION_NUMBER, required = false)
    private final Long versionNumber;
    @XmlElement(name = CoreConstants.CommonElements.OBJECT_ID, required = false)
    private final String objectId;
    @XmlElement(name = Elements.ACTIVE, required = false)
    private final boolean active;
    @XmlElement(name = Elements.ID, required = false)
    private final String id;
    @XmlElement(name = Elements.EFFECTIVE_LOCAL_DATE, required = false)
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private final LocalDate effectiveLocalDate;
    @XmlElement(name = Elements.CREATE_TIME, required = false)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private final DateTime createTime;
    @XmlElement(name = Elements.USER_PRINCIPAL_ID, required = false)
    private final String userPrincipalId;
    @SuppressWarnings("unused")
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * Private constructor used only by JAXB.
     *
     */
    private Assignment() {
        this.name = null;
        this.assignmentAccounts = null;
        this.principalId = null;
        this.job = null;
        this.jobNumber = null;
        this.clockText = null;
        this.tkAssignmentId = null;
        this.dept = null;
        this.workAreaObj = null;
        this.workArea = null;
        this.task = null;
        this.taskObj = null;
        this.calGroup = null;
        this.assignmentKey = null;
        this.primaryAssign = false;
        this.assignmentDescription = null;
        this.versionNumber = null;
        this.objectId = null;
        this.active = false;
        this.id = null;
        this.effectiveLocalDate = null;
        this.createTime = null;
        this.userPrincipalId = null;
    }

    private Assignment(Builder builder) {
        this.name = builder.getName();
        this.assignmentAccounts = CollectionUtils.isEmpty(builder.getAssignmentAccounts()) ? Collections.<AssignmentAccount>emptyList() : ModelObjectUtils.<AssignmentAccount>buildImmutableCopy(builder.getAssignmentAccounts());
        this.principalId = builder.getPrincipalId();
        this.job = builder.getJob() == null ? null : builder.getJob().build();
        this.jobNumber = builder.getJobNumber();
        this.clockText = builder.getClockText();
        this.tkAssignmentId = builder.getTkAssignmentId();
        this.dept = builder.getDept();
        this.workAreaObj = builder.getWorkAreaObj() == null ? null : builder.getWorkAreaObj().build();
        this.workArea = builder.getWorkArea();
        this.task = builder.getTask();
        this.taskObj = builder.getTaskObj() == null ? null : builder.getTaskObj().build();
        this.calGroup = builder.getCalGroup();
        this.assignmentKey = builder.getAssignmentKey();
        this.primaryAssign = builder.isPrimaryAssign();
        this.assignmentDescription = builder.getAssignmentDescription();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.active = builder.isActive();
        this.id = builder.getId();
        this.effectiveLocalDate = builder.getEffectiveLocalDate();
        this.createTime = builder.getCreateTime();
        this.userPrincipalId = builder.getUserPrincipalId();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public List<AssignmentAccount> getAssignmentAccounts() {
        return this.assignmentAccounts;
    }

    @Override
    public String getPrincipalId() {
        return this.principalId;
    }

    @Override
    public Job getJob() {
        return this.job;
    }

    @Override
    public Long getJobNumber() {
        return this.jobNumber;
    }

    @Override
    public String getClockText() {
        return this.clockText;
    }

    @Override
    public String getTkAssignmentId() {
        return this.tkAssignmentId;
    }

    @Override
    public String getDept() {
        return this.dept;
    }

    @Override
    public WorkArea getWorkAreaObj() {
        return this.workAreaObj;
    }

    @Override
    public Long getWorkArea() {
        return this.workArea;
    }

    @Override
    public Long getTask() {
        return this.task;
    }

    @Override
    public Task getTaskObj() {
        return this.taskObj;
    }

    @Override
    public String getCalGroup() {
        return this.calGroup;
    }

    @Override
    public String getAssignmentKey() {
        return this.assignmentKey;
    }

    @Override
    public boolean isPrimaryAssign() {
        return this.primaryAssign;
    }

    @Override
    public String getAssignmentDescription() {
        return this.assignmentDescription;
    }

    @Override
    public Long getVersionNumber() {
        return this.versionNumber;
    }

    @Override
    public String getObjectId() {
        return this.objectId;
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public LocalDate getEffectiveLocalDate() {
        return this.effectiveLocalDate;
    }

    @Override
    public DateTime getCreateTime() {
        return this.createTime;
    }

    @Override
    public String getUserPrincipalId() {
        return this.userPrincipalId;
    }


    /**
     * A builder which can be used to construct {@link Assignment} instances.  Enforces the constraints of the {@link AssignmentContract}.
     *
     */
    public final static class Builder
            implements Serializable, AssignmentContract, ModelBuilder
    {

        private static final long serialVersionUID = 4303332490877015437L;
        private String name;
        private List<AssignmentAccount.Builder> assignmentAccounts;
        private String principalId;
        private Job.Builder job;
        private Long jobNumber;
        private String clockText;
        private String tkAssignmentId;
        private String dept;
        private WorkArea.Builder workAreaObj;
        private Long workArea;
        private Long task;
        private Task.Builder taskObj;
        private String calGroup;
        private String assignmentKey;
        private boolean primaryAssign;
        private String assignmentDescription;
        private Long versionNumber;
        private String objectId;
        private boolean active;
        private String id;
        private LocalDate effectiveLocalDate;
        private DateTime createTime;
        private String userPrincipalId;
        private static final ModelObjectUtils.Transformer<AssignmentAccountContract, AssignmentAccount.Builder> toAssignmentAccountBuilder =
                new ModelObjectUtils.Transformer<AssignmentAccountContract, AssignmentAccount.Builder>() {
                    public AssignmentAccount.Builder transform(AssignmentAccountContract input) {
                        return AssignmentAccount.Builder.create(input);
                    }
                };
        private Builder(String principalId, Long workArea, Long jobNumber, Long task) {
            setPrincipalId(principalId);
            setWorkArea(workArea);
            setJobNumber(jobNumber);
            setTask(task);
        }

        public static Builder create(String principalId, Long workArea, Long jobNumber, Long task) {
            return new Builder(principalId, workArea, jobNumber, task);
        }

        public static Builder create(AssignmentContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            Builder builder = create(contract.getPrincipalId(), contract.getWorkArea(), contract.getJobNumber(), contract.getTask());
            builder.setName(contract.getName());
            if (CollectionUtils.isEmpty(contract.getAssignmentAccounts())) {
                builder.setAssignmentAccounts(Collections.<AssignmentAccount.Builder>emptyList());
            } else {
                builder.setAssignmentAccounts(ModelObjectUtils.transform(contract.getAssignmentAccounts(), toAssignmentAccountBuilder));
            }
            builder.setJob(contract.getJob() == null ? null : Job.Builder.create(contract.getJob()));
            builder.setClockText(contract.getClockText());
            builder.setTkAssignmentId(contract.getTkAssignmentId());
            builder.setDept(contract.getDept());
            builder.setWorkAreaObj(contract.getWorkAreaObj() == null ? null : WorkArea.Builder.create(contract.getWorkAreaObj()));
            builder.setTaskObj(contract.getTaskObj() == null ? null : Task.Builder.create(contract.getTaskObj()));
            builder.setCalGroup(contract.getCalGroup());
            builder.setAssignmentKey(contract.getAssignmentKey());
            builder.setPrimaryAssign(contract.isPrimaryAssign());
            builder.setAssignmentDescription(contract.getAssignmentDescription());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            builder.setActive(contract.isActive());
            builder.setId(contract.getId());
            builder.setEffectiveLocalDate(contract.getEffectiveLocalDate());
            builder.setCreateTime(contract.getCreateTime());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            return builder;
        }

        public Assignment build() {
            return new Assignment(this);
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public List<AssignmentAccount.Builder> getAssignmentAccounts() {
            return this.assignmentAccounts;
        }

        @Override
        public String getPrincipalId() {
            return this.principalId;
        }

        @Override
        public Job.Builder getJob() {
            return this.job;
        }

        @Override
        public Long getJobNumber() {
            return this.jobNumber;
        }

        @Override
        public String getClockText() {
            return this.clockText;
        }

        @Override
        public String getTkAssignmentId() {
            return this.tkAssignmentId;
        }

        @Override
        public String getDept() {
            return this.dept;
        }

        @Override
        public WorkArea.Builder getWorkAreaObj() {
            return this.workAreaObj;
        }

        @Override
        public Long getWorkArea() {
            return this.workArea;
        }

        @Override
        public Long getTask() {
            return this.task;
        }

        @Override
        public Task.Builder getTaskObj() {
            return this.taskObj;
        }

        @Override
        public String getCalGroup() {
            return this.calGroup;
        }

        @Override
        public String getAssignmentKey() {
            return this.assignmentKey;
        }

        @Override
        public boolean isPrimaryAssign() {
            return this.primaryAssign;
        }

        @Override
        public String getAssignmentDescription() {
            return this.assignmentDescription;
        }

        @Override
        public Long getVersionNumber() {
            return this.versionNumber;
        }

        @Override
        public String getObjectId() {
            return this.objectId;
        }

        @Override
        public boolean isActive() {
            return this.active;
        }

        @Override
        public String getId() {
            return this.id;
        }

        @Override
        public LocalDate getEffectiveLocalDate() {
            return this.effectiveLocalDate;
        }

        @Override
        public DateTime getCreateTime() {
            return this.createTime;
        }

        @Override
        public String getUserPrincipalId() {
            return this.userPrincipalId;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAssignmentAccounts(List<AssignmentAccount.Builder> assignmentAccounts) {
            this.assignmentAccounts = assignmentAccounts;
        }

        public void setPrincipalId(String principalId) {
            if (StringUtils.isBlank(principalId)) {
                throw new IllegalArgumentException("principalId is blank");
            }
            this.principalId = principalId;
        }

        public void setJob(Job.Builder job) {
            this.job = job;
        }

        public void setJobNumber(Long jobNumber) {
            if (jobNumber == null) {
                throw new IllegalArgumentException("jobNumber is null");
            }
            this.jobNumber = jobNumber;
        }

        public void setClockText(String clockText) {
            this.clockText = clockText;
        }

        public void setTkAssignmentId(String tkAssignmentId) {
            this.tkAssignmentId = tkAssignmentId;
        }

        public void setDept(String dept) {
            this.dept = dept;
        }

        public void setWorkAreaObj(WorkArea.Builder workAreaObj) {
            this.workAreaObj = workAreaObj;
        }

        public void setWorkArea(Long workArea) {
            if (workArea == null) {
                throw new IllegalArgumentException("workArea is null");
            }
            this.workArea = workArea;
        }

        public void setTask(Long task) {
            if (task == null) {
                throw new IllegalArgumentException("task is null");
            }
            this.task = task;
        }

        public void setTaskObj(Task.Builder taskObj) {
            this.taskObj = taskObj;
        }

        public void setCalGroup(String calGroup) {
            this.calGroup = calGroup;
        }

        public void setAssignmentKey(String assignmentKey) {
            this.assignmentKey = assignmentKey;
        }

        public void setPrimaryAssign(boolean primaryAssign) {
            this.primaryAssign = primaryAssign;
        }

        public void setAssignmentDescription(String assignmentDescription) {
            this.assignmentDescription = assignmentDescription;
        }

        public void setVersionNumber(Long versionNumber) {
            this.versionNumber = versionNumber;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setEffectiveLocalDate(LocalDate effectiveLocalDate) {
            this.effectiveLocalDate = effectiveLocalDate;
        }

        public void setCreateTime(DateTime createTime) {
            this.createTime = createTime;
        }

        public void setUserPrincipalId(String userPrincipalId) {
            this.userPrincipalId = userPrincipalId;
        }

    }


    /**
     * Defines some internal constants used on this class.
     *
     */
    static class Constants {

        final static String ROOT_ELEMENT_NAME = "assignment";
        final static String TYPE_NAME = "AssignmentType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     *
     */
    static class Elements {

        final static String NAME = "name";
        final static String ASSIGNMENT_ACCOUNTS = "assignmentAccounts";
        final static String PRINCIPAL_ID = "principalId";
        final static String JOB = "job";
        final static String JOB_NUMBER = "jobNumber";
        final static String CLOCK_TEXT = "clockText";
        final static String TK_ASSIGNMENT_ID = "tkAssignmentId";
        final static String DEPT = "dept";
        final static String WORK_AREA_OBJ = "workAreaObj";
        final static String WORK_AREA = "workArea";
        final static String TASK = "task";
        final static String TASK_OBJ = "taskObj";
        final static String CAL_GROUP = "calGroup";
        final static String ASSIGNMENT_KEY = "assignmentKey";
        final static String PRIMARY_ASSIGN = "primaryAssign";
        final static String ASSIGNMENT_DESCRIPTION = "assignmentDescription";
        final static String ACTIVE = "active";
        final static String ID = "id";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
        final static String CREATE_TIME = "createTime";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";

    }

}