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
package org.kuali.kpme.core.api.workarea;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.department.Department;
import org.kuali.kpme.core.api.earncode.EarnCode;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.kuali.rice.core.api.util.jaxb.DateTimeAdapter;
import org.kuali.rice.core.api.util.jaxb.LocalDateAdapter;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.Collection;

@XmlRootElement(name = WorkArea.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = WorkArea.Constants.TYPE_NAME, propOrder = {
        WorkArea.Elements.USER_PRINCIPAL_ID,
        WorkArea.Elements.DEFAULT_OVERTIME_EARN_CODE_OBJ,
        WorkArea.Elements.DEPARTMENT,
        //WorkArea.Elements.TASKS,
        WorkArea.Elements.TK_WORK_AREA_ID,
        WorkArea.Elements.DESCRIPTION,
        WorkArea.Elements.HRS_DISTRIBUTION_F,
        WorkArea.Elements.OVERTIME_EDIT_ROLE,
        WorkArea.Elements.OVT_EARN_CODE,
        WorkArea.Elements.ADMIN_DESCR,
        WorkArea.Elements.DEFAULT_OVERTIME_EARN_CODE,
        WorkArea.Elements.DEPT,
        WorkArea.Elements.WORK_AREA,
        WorkArea.Elements.ACTIVE,
        WorkArea.Elements.ID,
        WorkArea.Elements.CREATE_TIME,
        WorkArea.Elements.EFFECTIVE_LOCAL_DATE,
        CoreConstants.CommonElements.VERSION_NUMBER,
        CoreConstants.CommonElements.OBJECT_ID,
        CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class WorkArea
        extends AbstractDataTransferObject
        implements WorkAreaContract
{

    @XmlElement(name = Elements.USER_PRINCIPAL_ID, required = false)
    private final String userPrincipalId;
    @XmlElement(name = Elements.DEFAULT_OVERTIME_EARN_CODE_OBJ, required = false)
    private final EarnCode defaultOvertimeEarnCodeObj;
    @XmlElement(name = Elements.DEPARTMENT, required = false)
    private final Department department;
    //@XmlElement(name = Elements.TASKS, required = false)
    //private final List<Task> tasks;
    @XmlElement(name = Elements.TK_WORK_AREA_ID, required = false)
    private final String tkWorkAreaId;
    @XmlElement(name = Elements.DESCRIPTION, required = false)
    private final String description;
    @XmlElement(name = Elements.HRS_DISTRIBUTION_F, required = false)
    private final boolean hrsDistributionF;
    @XmlElement(name = Elements.OVERTIME_EDIT_ROLE, required = false)
    private final String overtimeEditRole;
    @XmlElement(name = Elements.OVT_EARN_CODE, required = false)
    private final Boolean ovtEarnCode;
    @XmlElement(name = Elements.ADMIN_DESCR, required = false)
    private final String adminDescr;
    @XmlElement(name = Elements.DEFAULT_OVERTIME_EARN_CODE, required = false)
    private final String defaultOvertimeEarnCode;
    @XmlElement(name = Elements.DEPT, required = false)
    private final String dept;
    @XmlElement(name = Elements.WORK_AREA, required = false)
    private final Long workArea;
    @XmlElement(name = CoreConstants.CommonElements.VERSION_NUMBER, required = false)
    private final Long versionNumber;
    @XmlElement(name = CoreConstants.CommonElements.OBJECT_ID, required = false)
    private final String objectId;
    @XmlElement(name = Elements.ACTIVE, required = false)
    private final boolean active;
    @XmlElement(name = Elements.ID, required = false)
    private final String id;
    @XmlElement(name = Elements.CREATE_TIME, required = false)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private final DateTime createTime;
    @XmlElement(name = Elements.EFFECTIVE_LOCAL_DATE, required = false)
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private final LocalDate effectiveLocalDate;
    @SuppressWarnings("unused")
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * Private constructor used only by JAXB.
     *
     */
    private WorkArea() {
        this.userPrincipalId = null;
        this.defaultOvertimeEarnCodeObj = null;
        this.department = null;
        //this.tasks = null;
        this.tkWorkAreaId = null;
        this.description = null;
        this.hrsDistributionF = false;
        this.overtimeEditRole = null;
        this.ovtEarnCode = null;
        this.adminDescr = null;
        this.defaultOvertimeEarnCode = null;
        this.dept = null;
        this.workArea = null;
        this.versionNumber = null;
        this.objectId = null;
        this.active = false;
        this.id = null;
        this.createTime = null;
        this.effectiveLocalDate = null;
    }

    private WorkArea(Builder builder) {
        this.userPrincipalId = builder.getUserPrincipalId();
        this.defaultOvertimeEarnCodeObj = builder.getDefaultOvertimeEarnCodeObj() == null ? null : builder.getDefaultOvertimeEarnCodeObj().build();
        this.department = builder.getDepartment() == null ? null : builder.getDepartment().build();
        //this.tasks = ModelObjectUtils.buildImmutableCopy(builder.getTasks());
        this.tkWorkAreaId = builder.getTkWorkAreaId();
        this.description = builder.getDescription();
        this.hrsDistributionF = builder.isHrsDistributionF();
        this.overtimeEditRole = builder.getOvertimeEditRole();
        this.ovtEarnCode = builder.isOvtEarnCode();
        this.adminDescr = builder.getAdminDescr();
        this.defaultOvertimeEarnCode = builder.getDefaultOvertimeEarnCode();
        this.dept = builder.getDept();
        this.workArea = builder.getWorkArea();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.active = builder.isActive();
        this.id = builder.getId();
        this.createTime = builder.getCreateTime();
        this.effectiveLocalDate = builder.getEffectiveLocalDate();
    }

    @Override
    public String getUserPrincipalId() {
        return this.userPrincipalId;
    }

    @Override
    public EarnCode getDefaultOvertimeEarnCodeObj() {
        return this.defaultOvertimeEarnCodeObj;
    }

    @Override
    public Department getDepartment() {
        return this.department;
    }

    /*@Override
    public List<Task> getTasks() {
        return this.tasks;
    }*/

    @Override
    public String getTkWorkAreaId() {
        return this.tkWorkAreaId;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public boolean isHrsDistributionF() {
        return this.hrsDistributionF;
    }

    @Override
    public String getOvertimeEditRole() {
        return this.overtimeEditRole;
    }

    @Override
    public Boolean isOvtEarnCode() {
        return this.ovtEarnCode;
    }

    @Override
    public String getAdminDescr() {
        return this.adminDescr;
    }

    @Override
    public String getDefaultOvertimeEarnCode() {
        return this.defaultOvertimeEarnCode;
    }

    @Override
    public String getDept() {
        return this.dept;
    }

    @Override
    public Long getWorkArea() {
        return this.workArea;
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
    public DateTime getCreateTime() {
        return this.createTime;
    }

    @Override
    public LocalDate getEffectiveLocalDate() {
        return this.effectiveLocalDate;
    }


    /**
     * A builder which can be used to construct {@link WorkArea} instances.  Enforces the constraints of the {@link WorkAreaContract}.
     *
     */
    public final static class Builder
            implements Serializable, WorkAreaContract, ModelBuilder
    {

        private String userPrincipalId;
        private EarnCode.Builder defaultOvertimeEarnCodeObj;
        private Department.Builder department;
        //private List<Task.Builder> tasks;
        private String tkWorkAreaId;
        private String description;
        private boolean hrsDistributionF;
        private String overtimeEditRole;
        private Boolean ovtEarnCode;
        private String adminDescr;
        private String defaultOvertimeEarnCode;
        private String dept;
        private Long workArea;
        private Long versionNumber;
        private String objectId;
        private boolean active;
        private String id;
        private DateTime createTime;
        private LocalDate effectiveLocalDate;
        /*private static final ModelObjectUtils.Transformer<TaskContract, Task.Builder> toTaskBuilder =
                new ModelObjectUtils.Transformer<TaskContract, Task.Builder>() {
                    public Task.Builder transform(TaskContract input) {
                        return Task.Builder.create(input);
                    };
                };*/

        private Builder(Long workArea) {
            setWorkArea(workArea);
        }

        public static Builder create(Long workArea) {
            return new Builder(workArea);
        }

        public static Builder create(WorkAreaContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            Builder builder = create(contract.getWorkArea());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            builder.setDefaultOvertimeEarnCodeObj(contract.getDefaultOvertimeEarnCodeObj() == null ? null : EarnCode.Builder.create(contract.getDefaultOvertimeEarnCodeObj()));
            builder.setDepartment(contract.getDepartment() == null ? null : Department.Builder.create(contract.getDepartment()));
            //builder.setTasks(contract.getTasks() == null ? Collections.<Task.Builder>emptyList() : ModelObjectUtils.transform(contract.getTasks(), toTaskBuilder));
            builder.setTkWorkAreaId(contract.getTkWorkAreaId());
            builder.setDescription(contract.getDescription());
            builder.setHrsDistributionF(contract.isHrsDistributionF());
            builder.setOvertimeEditRole(contract.getOvertimeEditRole());
            builder.setOvtEarnCode(contract.isOvtEarnCode());
            builder.setAdminDescr(contract.getAdminDescr());
            builder.setDefaultOvertimeEarnCode(contract.getDefaultOvertimeEarnCode());
            builder.setDept(contract.getDept());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            builder.setActive(contract.isActive());
            builder.setId(contract.getId());
            builder.setCreateTime(contract.getCreateTime());
            builder.setEffectiveLocalDate(contract.getEffectiveLocalDate());
            return builder;
        }

        public WorkArea build() {
            return new WorkArea(this);
        }

        @Override
        public String getUserPrincipalId() {
            return this.userPrincipalId;
        }

        @Override
        public EarnCode.Builder getDefaultOvertimeEarnCodeObj() {
            return this.defaultOvertimeEarnCodeObj;
        }

        @Override
        public Department.Builder getDepartment() {
            return this.department;
        }

        /*@Override
        public List<Task.Builder> getTasks() {
            return this.tasks;
        }*/

        @Override
        public String getTkWorkAreaId() {
            return this.tkWorkAreaId;
        }

        @Override
        public String getDescription() {
            return this.description;
        }

        @Override
        public boolean isHrsDistributionF() {
            return this.hrsDistributionF;
        }

        @Override
        public String getOvertimeEditRole() {
            return this.overtimeEditRole;
        }

        @Override
        public Boolean isOvtEarnCode() {
            return this.ovtEarnCode;
        }

        public Boolean getOvtEarnCode() {
            return this.ovtEarnCode;
        }

        @Override
        public String getAdminDescr() {
            return this.adminDescr;
        }

        @Override
        public String getDefaultOvertimeEarnCode() {
            return this.defaultOvertimeEarnCode;
        }

        @Override
        public String getDept() {
            return this.dept;
        }

        @Override
        public Long getWorkArea() {
            return this.workArea;
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
        public DateTime getCreateTime() {
            return this.createTime;
        }

        @Override
        public LocalDate getEffectiveLocalDate() {
            return this.effectiveLocalDate;
        }

        public void setUserPrincipalId(String userPrincipalId) {
            this.userPrincipalId = userPrincipalId;
        }

        public void setDefaultOvertimeEarnCodeObj(EarnCode.Builder defaultOvertimeEarnCodeObj) {
            this.defaultOvertimeEarnCodeObj = defaultOvertimeEarnCodeObj;
        }

        public void setDepartment(Department.Builder department) {
            this.department = department;
        }

        /*public void setTasks(List<Task.Builder> tasks) {
            this.tasks = tasks;
        }*/

        public void setTkWorkAreaId(String tkWorkAreaId) {
            this.tkWorkAreaId = tkWorkAreaId;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setHrsDistributionF(boolean hrsDistributionF) {
            this.hrsDistributionF = hrsDistributionF;
        }

        public void setOvertimeEditRole(String overtimeEditRole) {
            this.overtimeEditRole = overtimeEditRole;
        }

        public void setOvtEarnCode(Boolean ovtEarnCode) {
            this.ovtEarnCode = ovtEarnCode;
        }

        public void setAdminDescr(String adminDescr) {
            this.adminDescr = adminDescr;
        }

        public void setDefaultOvertimeEarnCode(String defaultOvertimeEarnCode) {
            this.defaultOvertimeEarnCode = defaultOvertimeEarnCode;
        }

        public void setDept(String dept) {
            this.dept = dept;
        }

        public void setWorkArea(Long workArea) {
            if (workArea == null) {
                throw new IllegalArgumentException("workArea is null");
            }
            this.workArea = workArea;
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

        public void setCreateTime(DateTime createTime) {
            this.createTime = createTime;
        }

        public void setEffectiveLocalDate(LocalDate effectiveLocalDate) {
            this.effectiveLocalDate = effectiveLocalDate;
        }

    }


    /**
     * Defines some internal constants used on this class.
     *
     */
    static class Constants {

        final static String ROOT_ELEMENT_NAME = "workArea";
        final static String TYPE_NAME = "WorkAreaType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     *
     */
    static class Elements {

        final static String USER_PRINCIPAL_ID = "userPrincipalId";
        final static String DEFAULT_OVERTIME_EARN_CODE_OBJ = "defaultOvertimeEarnCodeObj";
        final static String DEPARTMENT = "department";
        //final static String TASKS = "tasks";
        final static String TK_WORK_AREA_ID = "tkWorkAreaId";
        final static String DESCRIPTION = "description";
        final static String HRS_DISTRIBUTION_F = "hrsDistributionF";
        final static String OVERTIME_EDIT_ROLE = "overtimeEditRole";
        final static String OVT_EARN_CODE = "ovtEarnCode";
        final static String ADMIN_DESCR = "adminDescr";
        final static String DEFAULT_OVERTIME_EARN_CODE = "defaultOvertimeEarnCode";
        final static String DEPT = "dept";
        final static String WORK_AREA = "workArea";
        final static String ACTIVE = "active";
        final static String ID = "id";
        final static String CREATE_TIME = "createTime";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";

    }

}