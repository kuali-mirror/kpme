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
package org.kuali.kpme.core.api.department;

import java.io.Serializable;
import java.util.Collection;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.kuali.rice.core.api.util.jaxb.DateTimeAdapter;
import org.kuali.rice.core.api.util.jaxb.LocalDateAdapter;
import org.w3c.dom.Element;

@XmlRootElement(name = Department.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = Department.Constants.TYPE_NAME, propOrder = {
        Department.Elements.LOCATION,
        Department.Elements.HR_DEPT_ID,
        Department.Elements.DEPT,
        Department.Elements.GROUP_KEY_CODE,
        Department.Elements.DESCRIPTION,
        Department.Elements.INSTITUTION,
        Department.Elements.CHART,
        Department.Elements.ORG,
        Department.Elements.PAYROLL_APPROVAL,
        CoreConstants.CommonElements.VERSION_NUMBER,
        CoreConstants.CommonElements.OBJECT_ID,
        Department.Elements.ACTIVE,
        Department.Elements.ID,
        Department.Elements.GROUP_KEY,
        Department.Elements.CREATE_TIME,
        Department.Elements.EFFECTIVE_LOCAL_DATE,
        Department.Elements.USER_PRINCIPAL_ID,
        CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class Department
        extends AbstractDataTransferObject
        implements DepartmentContract
{

    @XmlElement(name = Elements.LOCATION, required = false)
    private final String location;
    @XmlElement(name = Elements.HR_DEPT_ID, required = false)
    private final String hrDeptId;
    @XmlElement(name = Elements.DEPT, required = true)
    private final String dept;
    @XmlElement(name = Elements.GROUP_KEY_CODE, required = true)
    private final String groupKeyCode;
    @XmlElement(name = Elements.GROUP_KEY, required = true)
    private final HrGroupKey groupKey;
    @XmlElement(name = Elements.DESCRIPTION, required = false)
    private final String description;
    @XmlElement(name = Elements.INSTITUTION, required = false)
    private final String institution;
    @XmlElement(name = Elements.CHART, required = false)
    private final String chart;
    @XmlElement(name = Elements.ORG, required = false)
    private final String org;
    @XmlElement(name = Elements.PAYROLL_APPROVAL, required = false)
    private final boolean payrollApproval;
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
    @XmlElement(name = Elements.USER_PRINCIPAL_ID, required = false)
    private final String userPrincipalId;
    @SuppressWarnings("unused")
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * Private constructor used only by JAXB.
     *
     */
    private Department() {
        this.location = null;
        this.hrDeptId = null;
        this.dept = null;
        this.groupKeyCode = null;
        this.groupKey = null;
        this.description = null;
        this.institution = null;
        this.chart = null;
        this.org = null;
        this.payrollApproval = false;
        this.versionNumber = null;
        this.objectId = null;
        this.active = false;
        this.id = null;
        this.createTime = null;
        this.effectiveLocalDate = null;
        this.userPrincipalId = null;
    }

    private Department(Builder builder) {
        this.location = builder.getLocation();
        this.hrDeptId = builder.getHrDeptId();
        this.dept = builder.getDept();
        this.groupKeyCode = builder.getGroupKeyCode();
        this.groupKey = builder.getGroupKey() == null ? null : builder.getGroupKey().build();
        this.description = builder.getDescription();
        this.institution = builder.getInstitution();
        this.chart = builder.getChart();
        this.org = builder.getOrg();
        this.payrollApproval = builder.isPayrollApproval();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.active = builder.isActive();
        this.id = builder.getId();
        this.createTime = builder.getCreateTime();
        this.effectiveLocalDate = builder.getEffectiveLocalDate();
        this.userPrincipalId = builder.getUserPrincipalId();
    }

    @Override
    public String getLocation() {
        return this.location;
    }

    @Override
    public String getHrDeptId() {
        return this.hrDeptId;
    }

    @Override
    public String getDept() {
        return this.dept;
    }

    @Override
    public String getGroupKeyCode() {
    return this.groupKeyCode;
}

    @Override
    public HrGroupKey getGroupKey() {
        return this.groupKey;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getInstitution() {
        return this.institution;
    }

    @Override
    public String getChart() {
        return this.chart;
    }

    @Override
    public String getOrg() {
        return this.org;
    }

    @Override
    public boolean isPayrollApproval() {
        return this.payrollApproval;
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

    @Override
    public String getUserPrincipalId() {
        return this.userPrincipalId;
    }


    /**
     * A builder which can be used to construct {@link Department} instances.  Enforces the constraints of the {@link DepartmentContract}.
     *
     */
    public final static class Builder
            implements Serializable, DepartmentContract, ModelBuilder
    {

        private String location;
        private String hrDeptId;
        private String dept;
        private String groupKeyCode;
        private HrGroupKey.Builder groupKey;
        private String description;
        private String institution;
        private String chart;
        private String org;
        private boolean payrollApproval;
        private Long versionNumber;
        private String objectId;
        private boolean active;
        private String id;
        private DateTime createTime;
        private LocalDate effectiveLocalDate;
        private String userPrincipalId;

        private Builder(String groupKeyCode, String dept) {
            setGroupKeyCode(groupKeyCode);
            setDept(dept);
        }

        public static Builder create(String groupKeyCode, String dept) {
            return new Builder(groupKeyCode, dept);
        }

        public static Builder create(DepartmentContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            Builder builder = create(contract.getGroupKeyCode(), contract.getDept());
            builder.setLocation(contract.getLocation());
            builder.setGroupKey(contract.getGroupKey() == null ? null : HrGroupKey.Builder.create(contract.getGroupKey()));
            builder.setHrDeptId(contract.getHrDeptId());
            builder.setDescription(contract.getDescription());
            builder.setInstitution(contract.getInstitution());
            builder.setChart(contract.getChart());
            builder.setOrg(contract.getOrg());
            builder.setPayrollApproval(contract.isPayrollApproval());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            builder.setActive(contract.isActive());
            builder.setId(contract.getId());
            builder.setCreateTime(contract.getCreateTime());
            builder.setEffectiveLocalDate(contract.getEffectiveLocalDate());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            return builder;
        }

        public Department build() {
            return new Department(this);
        }

        @Override
        public String getLocation() {
            return this.location;
        }

        @Override
        public String getHrDeptId() {
            return this.hrDeptId;
        }

        @Override
        public String getDept() {
            return this.dept;
        }

        @Override
        public String getGroupKeyCode() {
            return this.groupKeyCode;
        }

        @Override
        public HrGroupKey.Builder getGroupKey() {
            return this.groupKey;
        }

        @Override
        public String getDescription() {
            return this.description;
        }

        @Override
        public String getInstitution() {
            return this.institution;
        }

        @Override
        public String getChart() {
            return this.chart;
        }

        @Override
        public String getOrg() {
            return this.org;
        }

        @Override
        public boolean isPayrollApproval() {
            return this.payrollApproval;
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

        @Override
        public String getUserPrincipalId() {
            return this.userPrincipalId;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public void setHrDeptId(String hrDeptId) {
            this.hrDeptId = hrDeptId;
        }

        public void setDept(String dept) {
            if (StringUtils.isWhitespace(dept)) {
                throw new IllegalArgumentException("dept is blank");
            }
            this.dept = dept;
        }

        public void setGroupKeyCode(String groupKeyCode) {
            if (StringUtils.isWhitespace(groupKeyCode)) {
                throw new IllegalArgumentException("groupKeyCode is blank");
            }
            this.groupKeyCode = groupKeyCode;
        }

        public void setGroupKey(HrGroupKey.Builder groupKey) {
            this.groupKey = groupKey;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setInstitution(String institution) {
            this.institution = institution;
        }

        public void setChart(String chart) {
            this.chart = chart;
        }

        public void setOrg(String org) {
            this.org = org;
        }

        public void setPayrollApproval(boolean payrollApproval) {
            this.payrollApproval = payrollApproval;
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

        public void setUserPrincipalId(String userPrincipalId) {
            this.userPrincipalId = userPrincipalId;
        }

    }


    /**
     * Defines some internal constants used on this class.
     *
     */
    static class Constants {

        final static String ROOT_ELEMENT_NAME = "department";
        final static String TYPE_NAME = "DepartmentType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     *
     */
    static class Elements {

        final static String LOCATION = "location";
        final static String HR_DEPT_ID = "hrDeptId";
        final static String DEPT = "dept";
        final static String GROUP_KEY_CODE = "groupKeyCode";
        final static String GROUP_KEY = "groupKey";
        final static String DESCRIPTION = "description";
        final static String INSTITUTION = "institution";
        final static String CHART = "chart";
        final static String ORG = "org";
        final static String PAYROLL_APPROVAL = "payrollApproval";
        final static String ACTIVE = "active";
        final static String ID = "id";
        final static String CREATE_TIME = "createTime";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";

    }

}