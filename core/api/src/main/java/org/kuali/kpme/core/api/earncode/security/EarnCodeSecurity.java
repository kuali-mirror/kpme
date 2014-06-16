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
package org.kuali.kpme.core.api.earncode.security;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.department.Department;
import org.kuali.kpme.core.api.earncode.EarnCode;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.kpme.core.api.job.Job;
import org.kuali.kpme.core.api.location.Location;
import org.kuali.kpme.core.api.salarygroup.SalaryGroup;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Collection;

@XmlRootElement(name = EarnCodeSecurity.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = EarnCodeSecurity.Constants.TYPE_NAME, propOrder = {
    EarnCodeSecurity.Elements.SALARY_GROUP_OBJ,
    EarnCodeSecurity.Elements.DEPARTMENT_OBJ,
    EarnCodeSecurity.Elements.EMPLOYEE,
    EarnCodeSecurity.Elements.APPROVER,
    EarnCodeSecurity.Elements.PAYROLL_PROCESSOR,
    EarnCodeSecurity.Elements.EARN_CODE_OBJ,
    EarnCodeSecurity.Elements.EARN_CODE_TYPE,
    EarnCodeSecurity.Elements.DEPT,
    EarnCodeSecurity.Elements.HR_SAL_GROUP,
    EarnCodeSecurity.Elements.EARN_CODE,
    EarnCodeSecurity.Elements.JOB_OBJ,
    EarnCodeSecurity.Elements.HR_EARN_CODE_SECURITY_ID,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    EarnCodeSecurity.Elements.ACTIVE,
    EarnCodeSecurity.Elements.ID,
    EarnCodeSecurity.Elements.EFFECTIVE_LOCAL_DATE,
    EarnCodeSecurity.Elements.CREATE_TIME,
    EarnCodeSecurity.Elements.USER_PRINCIPAL_ID,
    EarnCodeSecurity.Elements.GROUP_KEY_CODE,
    EarnCodeSecurity.Elements.GROUP_KEY,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class EarnCodeSecurity
    extends AbstractDataTransferObject
    implements EarnCodeSecurityContract
{

    @XmlElement(name = Elements.SALARY_GROUP_OBJ, required = false)
    private final SalaryGroup salaryGroupObj;
    @XmlElement(name = Elements.DEPARTMENT_OBJ, required = false)
    private final Department departmentObj;
    @XmlElement(name = Elements.EMPLOYEE, required = false)
    private final boolean employee;
    @XmlElement(name = Elements.APPROVER, required = false)
    private final boolean approver;
    @XmlElement(name = Elements.PAYROLL_PROCESSOR, required = false)
    private final boolean payrollProcessor;
    @XmlElement(name = Elements.EARN_CODE_OBJ, required = false)
    private final EarnCode earnCodeObj;
    @XmlElement(name = Elements.EARN_CODE_TYPE, required = false)
    private final String earnCodeType;
    @XmlElement(name = Elements.DEPT, required = false)
    private final String dept;
    @XmlElement(name = Elements.HR_SAL_GROUP, required = false)
    private final String hrSalGroup;
    @XmlElement(name = Elements.EARN_CODE, required = false)
    private final String earnCode;
    @XmlElement(name = Elements.JOB_OBJ, required = false)
    private final Job jobObj;
    @XmlElement(name = Elements.HR_EARN_CODE_SECURITY_ID, required = false)
    private final String hrEarnCodeSecurityId;
    @XmlElement(name = CoreConstants.CommonElements.VERSION_NUMBER, required = false)
    private final Long versionNumber;
    @XmlElement(name = CoreConstants.CommonElements.OBJECT_ID, required = false)
    private final String objectId;
    @XmlElement(name = Elements.ACTIVE, required = false)
    private final boolean active;
    @XmlElement(name = Elements.ID, required = false)
    private final String id;
    @XmlElement(name = Elements.EFFECTIVE_LOCAL_DATE, required = false)
    private final LocalDate effectiveLocalDate;
    @XmlElement(name = Elements.CREATE_TIME, required = false)
    private final DateTime createTime;
    @XmlElement(name = Elements.USER_PRINCIPAL_ID, required = false)
    private final String userPrincipalId;
    @XmlElement(name = Elements.GROUP_KEY_CODE, required = false)
    private final String groupKeyCode;
    @XmlElement(name = Elements.GROUP_KEY, required = false)
    private final HrGroupKey groupKey;
    @SuppressWarnings("unused")
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * Private constructor used only by JAXB.
     * 
     */
    private EarnCodeSecurity() {
        this.salaryGroupObj = null;
        this.departmentObj = null;
        this.employee = false;
        this.approver = false;
        this.payrollProcessor = false;
        this.earnCodeObj = null;
        this.earnCodeType = null;
        this.dept = null;
        this.hrSalGroup = null;
        this.earnCode = null;
        this.jobObj = null;
        this.hrEarnCodeSecurityId = null;
        this.versionNumber = null;
        this.objectId = null;
        this.active = false;
        this.id = null;
        this.effectiveLocalDate = null;
        this.createTime = null;
        this.userPrincipalId = null;
        this.groupKeyCode = null;
        this.groupKey = null;
    }

    private EarnCodeSecurity(Builder builder) {
        this.salaryGroupObj = builder.getSalaryGroupObj() == null ? null : builder.getSalaryGroupObj().build();
        this.departmentObj = builder.getDepartmentObj() == null ? null : builder.getDepartmentObj().build();
        this.employee = builder.isEmployee();
        this.approver = builder.isApprover();
        this.payrollProcessor = builder.isPayrollProcessor();
        this.earnCodeObj = builder.getEarnCodeObj() == null ? null : builder.getEarnCodeObj().build();
        this.earnCodeType = builder.getEarnCodeType();
        this.dept = builder.getDept();
        this.hrSalGroup = builder.getHrSalGroup();
        this.earnCode = builder.getEarnCode();
        this.jobObj = builder.getJobObj() == null ? null : builder.getJobObj().build();
        this.hrEarnCodeSecurityId = builder.getHrEarnCodeSecurityId();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.active = builder.isActive();
        this.id = builder.getId();
        this.effectiveLocalDate = builder.getEffectiveLocalDate();
        this.createTime = builder.getCreateTime();
        this.userPrincipalId = builder.getUserPrincipalId();
        this.groupKeyCode = builder.getGroupKeyCode();
        this.groupKey = builder.getGroupKey() == null ? null : builder.getGroupKey().build();
    }

    @Override
    public SalaryGroup getSalaryGroupObj() {
        return this.salaryGroupObj;
    }

    @Override
    public Department getDepartmentObj() {
        return this.departmentObj;
    }

    @Override
    public boolean isEmployee() {
        return this.employee;
    }

    @Override
    public boolean isApprover() {
        return this.approver;
    }

    @Override
    public boolean isPayrollProcessor() {
        return this.payrollProcessor;
    }

    @Override
    public EarnCode getEarnCodeObj() {
        return this.earnCodeObj;
    }

    @Override
    public String getEarnCodeType() {
        return this.earnCodeType;
    }

    @Override
    public String getDept() {
        return this.dept;
    }

    @Override
    public String getHrSalGroup() {
        return this.hrSalGroup;
    }

    @Override
    public String getEarnCode() {
        return this.earnCode;
    }

    @Override
    public Job getJobObj() {
        return this.jobObj;
    }

    @Override
    public String getHrEarnCodeSecurityId() {
        return this.hrEarnCodeSecurityId;
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

    @Override
    public String getGroupKeyCode() {
        return this.groupKeyCode;
    }

    @Override
    public HrGroupKey getGroupKey() {
        return this.groupKey;
    }


    /**
     * A builder which can be used to construct {@link EarnCodeSecurity} instances.  Enforces the constraints of the {@link EarnCodeSecurityContract}.
     * 
     */
    public final static class Builder
        implements Serializable, EarnCodeSecurityContract, ModelBuilder
    {

        private SalaryGroup.Builder salaryGroupObj;
        private Department.Builder departmentObj;
        private boolean employee;
        private boolean approver;
        private boolean payrollProcessor;
        private EarnCode.Builder earnCodeObj;
        private String earnCodeType;
        private String dept;
        private String hrSalGroup;
        private String earnCode;
        private Job.Builder jobObj;
        private String hrEarnCodeSecurityId;
        private Long versionNumber;
        private String objectId;
        private boolean active;
        private String id;
        private LocalDate effectiveLocalDate;
        private DateTime createTime;
        private String userPrincipalId;
        private String groupKeyCode;
        private HrGroupKey.Builder groupKey;

        private Builder() {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        }

        public static Builder create() {
            // TODO modify as needed to pass any required values and add them to the signature of the 'create' method
            return new Builder();
        }

        public static Builder create(EarnCodeSecurityContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            // TODO if create() is modified to accept required parameters, this will need to be modified
            Builder builder = create();
            builder.setSalaryGroupObj(contract.getSalaryGroupObj() == null ? null : SalaryGroup.Builder.create(contract.getSalaryGroupObj()));
            builder.setDepartmentObj(contract.getDepartmentObj() == null ? null : Department.Builder.create(contract.getDepartmentObj()));
            builder.setEmployee(contract.isEmployee());
            builder.setApprover(contract.isApprover());
            builder.setPayrollProcessor(contract.isPayrollProcessor());
            builder.setEarnCodeObj(contract.getEarnCodeObj() == null ? null : EarnCode.Builder.create(contract.getEarnCodeObj()));
            builder.setEarnCodeType(contract.getEarnCodeType());
            builder.setDept(contract.getDept());
            builder.setHrSalGroup(contract.getHrSalGroup());
            builder.setEarnCode(contract.getEarnCode());
            builder.setJobObj(contract.getJobObj() == null ? null : Job.Builder.create(contract.getJobObj()));
            builder.setHrEarnCodeSecurityId(contract.getHrEarnCodeSecurityId());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            builder.setActive(contract.isActive());
            builder.setId(contract.getId());
            builder.setEffectiveLocalDate(contract.getEffectiveLocalDate());
            builder.setCreateTime(contract.getCreateTime());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            builder.setGroupKeyCode(contract.getGroupKeyCode());
            builder.setGroupKey(contract.getGroupKey() == null ? null : HrGroupKey.Builder.create(contract.getGroupKey()));
            return builder;
        }

        public EarnCodeSecurity build() {
            return new EarnCodeSecurity(this);
        }

        @Override
        public SalaryGroup.Builder getSalaryGroupObj() {
            return this.salaryGroupObj;
        }

        @Override
        public Department.Builder getDepartmentObj() {
            return this.departmentObj;
        }

        @Override
        public boolean isEmployee() {
            return this.employee;
        }

        @Override
        public boolean isApprover() {
            return this.approver;
        }

        @Override
        public boolean isPayrollProcessor() {
            return this.payrollProcessor;
        }

        @Override
        public EarnCode.Builder getEarnCodeObj() {
            return this.earnCodeObj;
        }

        @Override
        public String getEarnCodeType() {
            return this.earnCodeType;
        }

        @Override
        public String getDept() {
            return this.dept;
        }

        @Override
        public String getHrSalGroup() {
            return this.hrSalGroup;
        }

        @Override
        public String getEarnCode() {
            return this.earnCode;
        }

        @Override
        public Job.Builder getJobObj() {
            return this.jobObj;
        }

        @Override
        public String getHrEarnCodeSecurityId() {
            return this.hrEarnCodeSecurityId;
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

        @Override
        public String getGroupKeyCode() {
            return this.groupKeyCode;
        }

        @Override
        public HrGroupKey.Builder getGroupKey() {
            return this.groupKey;
        }

        public void setSalaryGroupObj(SalaryGroup.Builder salaryGroupObj) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.salaryGroupObj = salaryGroupObj;
        }

        public void setDepartmentObj(Department.Builder departmentObj) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.departmentObj = departmentObj;
        }

        public void setEmployee(boolean employee) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.employee = employee;
        }

        public void setApprover(boolean approver) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.approver = approver;
        }

        public void setPayrollProcessor(boolean payrollProcessor) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.payrollProcessor = payrollProcessor;
        }

        public void setEarnCodeObj(EarnCode.Builder earnCodeObj) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.earnCodeObj = earnCodeObj;
        }

        public void setEarnCodeType(String earnCodeType) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.earnCodeType = earnCodeType;
        }

        public void setDept(String dept) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.dept = dept;
        }

        public void setHrSalGroup(String hrSalGroup) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.hrSalGroup = hrSalGroup;
        }

        public void setEarnCode(String earnCode) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.earnCode = earnCode;
        }

        public void setJobObj(Job.Builder jobObj) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.jobObj = jobObj;
        }

        public void setHrEarnCodeSecurityId(String hrEarnCodeSecurityId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.hrEarnCodeSecurityId = hrEarnCodeSecurityId;
        }

        public void setVersionNumber(Long versionNumber) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.versionNumber = versionNumber;
        }

        public void setObjectId(String objectId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.objectId = objectId;
        }

        public void setActive(boolean active) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.active = active;
        }

        public void setId(String id) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.id = id;
        }

        public void setEffectiveLocalDate(LocalDate effectiveLocalDate) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.effectiveLocalDate = effectiveLocalDate;
        }

        public void setCreateTime(DateTime createTime) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.createTime = createTime;
        }

        public void setUserPrincipalId(String userPrincipalId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.userPrincipalId = userPrincipalId;
        }

        public void setGroupKeyCode(String groupKeyCode) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.groupKeyCode = groupKeyCode;
        }

        public void setGroupKey(HrGroupKey.Builder groupKey) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.groupKey = groupKey;
        }

    }


    /**
     * Defines some internal constants used on this class.
     * 
     */
    static class Constants {

        final static String ROOT_ELEMENT_NAME = "earnCodeSecurity";
        final static String TYPE_NAME = "EarnCodeSecurityType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

        final static String SALARY_GROUP_OBJ = "salaryGroupObj";
        final static String DEPARTMENT_OBJ = "departmentObj";
        final static String EMPLOYEE = "employee";
        final static String APPROVER = "approver";
        final static String PAYROLL_PROCESSOR = "payrollProcessor";
        final static String EARN_CODE_OBJ = "earnCodeObj";
        final static String EARN_CODE_TYPE = "earnCodeType";
        final static String DEPT = "dept";
        final static String HR_SAL_GROUP = "hrSalGroup";
        final static String EARN_CODE = "earnCode";
        final static String JOB_OBJ = "jobObj";
        final static String HR_EARN_CODE_SECURITY_ID = "hrEarnCodeSecurityId";
        final static String ACTIVE = "active";
        final static String ID = "id";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
        final static String CREATE_TIME = "createTime";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";
        final static String GROUP_KEY_CODE = "groupKeyCode";
        final static String GROUP_KEY = "groupKey";
    }

}

