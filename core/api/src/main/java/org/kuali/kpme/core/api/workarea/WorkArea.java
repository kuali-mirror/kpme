package org.kuali.kpme.core.api.workarea;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.department.Department;
import org.kuali.kpme.core.api.earncode.EarnCode;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Collection;

@XmlRootElement(name = WorkArea.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = WorkArea.Constants.TYPE_NAME, propOrder = {
        WorkArea.Elements.DESCRIPTION,
        WorkArea.Elements.OVERTIME_EDIT_ROLE,
        WorkArea.Elements.DEFAULT_OVERTIME_EARN_CODE,
        WorkArea.Elements.OVT_EARN_CODE,
        WorkArea.Elements.TK_WORK_AREA_ID,
        WorkArea.Elements.ADMIN_DESCR,
        WorkArea.Elements.USER_PRINCIPAL_ID,
        WorkArea.Elements.DEFAULT_OVERTIME_EARN_CODE_OBJ,
        WorkArea.Elements.DEPARTMENT,
        WorkArea.Elements.HRS_DISTRIBUTION_F,
        WorkArea.Elements.DEPT,
        WorkArea.Elements.WORK_AREA,
        CoreConstants.CommonElements.VERSION_NUMBER,
        CoreConstants.CommonElements.OBJECT_ID,
        WorkArea.Elements.ACTIVE,
        WorkArea.Elements.ID,
        WorkArea.Elements.EFFECTIVE_LOCAL_DATE,
        WorkArea.Elements.CREATE_TIME,
        WorkArea.Elements.GROUP_KEY_CODE,
        WorkArea.Elements.GROUP_KEY,
        CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class WorkArea
        extends AbstractDataTransferObject
        implements WorkAreaContract
{

    @XmlElement(name = Elements.DESCRIPTION, required = false)
    private final String description;
    @XmlElement(name = Elements.OVERTIME_EDIT_ROLE, required = false)
    private final String overtimeEditRole;
    @XmlElement(name = Elements.DEFAULT_OVERTIME_EARN_CODE, required = false)
    private final String defaultOvertimeEarnCode;
    @XmlElement(name = Elements.OVT_EARN_CODE, required = false)
    private final Boolean ovtEarnCode;
    @XmlElement(name = Elements.TK_WORK_AREA_ID, required = false)
    private final String tkWorkAreaId;
    @XmlElement(name = Elements.ADMIN_DESCR, required = false)
    private final String adminDescr;
    @XmlElement(name = Elements.USER_PRINCIPAL_ID, required = false)
    private final String userPrincipalId;
    @XmlElement(name = Elements.DEFAULT_OVERTIME_EARN_CODE_OBJ, required = false)
    private final EarnCode defaultOvertimeEarnCodeObj;
    @XmlElement(name = Elements.DEPARTMENT, required = false)
    private final Department department;
    @XmlElement(name = Elements.HRS_DISTRIBUTION_F, required = false)
    private final boolean hrsDistributionF;
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
    @XmlElement(name = Elements.EFFECTIVE_LOCAL_DATE, required = false)
    private final LocalDate effectiveLocalDate;
    @XmlElement(name = Elements.CREATE_TIME, required = false)
    private final DateTime createTime;
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
    private WorkArea() {
        this.description = null;
        this.overtimeEditRole = null;
        this.defaultOvertimeEarnCode = null;
        this.ovtEarnCode = null;
        this.tkWorkAreaId = null;
        this.adminDescr = null;
        this.userPrincipalId = null;
        this.defaultOvertimeEarnCodeObj = null;
        this.department = null;
        this.hrsDistributionF = false;
        this.dept = null;
        this.workArea = null;
        this.versionNumber = null;
        this.objectId = null;
        this.active = false;
        this.id = null;
        this.effectiveLocalDate = null;
        this.createTime = null;
        this.groupKeyCode = null;
        this.groupKey = null;
    }

    private WorkArea(Builder builder) {
        this.description = builder.getDescription();
        this.overtimeEditRole = builder.getOvertimeEditRole();
        this.defaultOvertimeEarnCode = builder.getDefaultOvertimeEarnCode();
        this.ovtEarnCode = builder.isOvtEarnCode();
        this.tkWorkAreaId = builder.getTkWorkAreaId();
        this.adminDescr = builder.getAdminDescr();
        this.userPrincipalId = builder.getUserPrincipalId();
        this.defaultOvertimeEarnCodeObj = builder.getDefaultOvertimeEarnCodeObj() == null ? null: builder.getDefaultOvertimeEarnCodeObj().build();
        this.department = builder.getDepartment() == null ? null : builder.getDepartment().build();
        this.hrsDistributionF = builder.isHrsDistributionF();
        this.dept = builder.getDept();
        this.workArea = builder.getWorkArea();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.active = builder.isActive();
        this.id = builder.getId();
        this.effectiveLocalDate = builder.getEffectiveLocalDate();
        this.createTime = builder.getCreateTime();
        this.groupKeyCode = builder.getGroupKeyCode();
        this.groupKey = builder.getGroupKey() == null ? null : builder.getGroupKey().build();
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getOvertimeEditRole() {
        return this.overtimeEditRole;
    }

    @Override
    public String getDefaultOvertimeEarnCode() {
        return this.defaultOvertimeEarnCode;
    }

    @Override
    public Boolean isOvtEarnCode() {
        return this.ovtEarnCode;
    }

    @Override
    public String getTkWorkAreaId() {
        return this.tkWorkAreaId;
    }

    @Override
    public String getAdminDescr() {
        return this.adminDescr;
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

    @Override
    public boolean isHrsDistributionF() {
        return this.hrsDistributionF;
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
    public LocalDate getEffectiveLocalDate() {
        return this.effectiveLocalDate;
    }

    @Override
    public DateTime getCreateTime() {
        return this.createTime;
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
     * A builder which can be used to construct {@link WorkArea} instances.  Enforces the constraints of the {@link WorkAreaContract}.
     *
     */
    public final static class Builder
            implements Serializable, WorkAreaContract, ModelBuilder
    {

        private String description;
        private String overtimeEditRole;
        private String defaultOvertimeEarnCode;
        private Boolean ovtEarnCode;
        private String tkWorkAreaId;
        private String adminDescr;
        private String userPrincipalId;
        private EarnCode.Builder defaultOvertimeEarnCodeObj;
        private Department.Builder department;
        private boolean hrsDistributionF;
        private String dept;
        private Long workArea;
        private Long versionNumber;
        private String objectId;
        private boolean active;
        private String id;
        private LocalDate effectiveLocalDate;
        private DateTime createTime;
        private String groupKeyCode;
        private HrGroupKey.Builder groupKey;

        private Builder() {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        }

        public static Builder create() {
            // TODO modify as needed to pass any required values and add them to the signature of the 'create' method
            return new Builder();
        }

        public static Builder create(WorkAreaContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            // TODO if create() is modified to accept required parameters, this will need to be modified
            Builder builder = create();
            builder.setDescription(contract.getDescription());
            builder.setOvertimeEditRole(contract.getOvertimeEditRole());
            builder.setDefaultOvertimeEarnCode(contract.getDefaultOvertimeEarnCode());
            builder.setOvtEarnCode(contract.isOvtEarnCode());
            builder.setTkWorkAreaId(contract.getTkWorkAreaId());
            builder.setAdminDescr(contract.getAdminDescr());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            builder.setDefaultOvertimeEarnCodeObj(contract.getDefaultOvertimeEarnCodeObj() == null ? null : EarnCode.Builder.create(contract.getDefaultOvertimeEarnCodeObj()));
            builder.setDepartment(contract.getDepartment() == null ? null : Department.Builder.create(contract.getDepartment()));
            builder.setHrsDistributionF(contract.isHrsDistributionF());
            builder.setDept(contract.getDept());
            builder.setWorkArea(contract.getWorkArea());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            builder.setActive(contract.isActive());
            builder.setId(contract.getId());
            builder.setEffectiveLocalDate(contract.getEffectiveLocalDate());
            builder.setCreateTime(contract.getCreateTime());
            builder.setGroupKeyCode(contract.getGroupKeyCode());
            builder.setGroupKey(contract.getGroupKey() == null ? null : HrGroupKey.Builder.create(contract.getGroupKey()));
            return builder;
        }

        public WorkArea build() {
            return new WorkArea(this);
        }

        @Override
        public String getDescription() {
            return this.description;
        }

        @Override
        public String getOvertimeEditRole() {
            return this.overtimeEditRole;
        }

        @Override
        public String getDefaultOvertimeEarnCode() {
            return this.defaultOvertimeEarnCode;
        }

        @Override
        public Boolean isOvtEarnCode() {
            return this.ovtEarnCode;
        }

        @Override
        public String getTkWorkAreaId() {
            return this.tkWorkAreaId;
        }

        @Override
        public String getAdminDescr() {
            return this.adminDescr;
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

        @Override
        public boolean isHrsDistributionF() {
            return this.hrsDistributionF;
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
        public LocalDate getEffectiveLocalDate() {
            return this.effectiveLocalDate;
        }

        @Override
        public DateTime getCreateTime() {
            return this.createTime;
        }

        @Override
        public String getGroupKeyCode() {
            return this.groupKeyCode;
        }

        @Override
        public HrGroupKey.Builder getGroupKey() {
            return this.groupKey;
        }

        public void setDescription(String description) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.description = description;
        }

        public void setOvertimeEditRole(String overtimeEditRole) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.overtimeEditRole = overtimeEditRole;
        }

        public void setDefaultOvertimeEarnCode(String defaultOvertimeEarnCode) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.defaultOvertimeEarnCode = defaultOvertimeEarnCode;
        }

        public void setOvtEarnCode(Boolean ovtEarnCode) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.ovtEarnCode = ovtEarnCode;
        }

        public void setTkWorkAreaId(String tkWorkAreaId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.tkWorkAreaId = tkWorkAreaId;
        }

        public void setAdminDescr(String adminDescr) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.adminDescr = adminDescr;
        }

        public void setUserPrincipalId(String userPrincipalId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.userPrincipalId = userPrincipalId;
        }

        public void setDefaultOvertimeEarnCodeObj(EarnCode.Builder defaultOvertimeEarnCodeObj) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.defaultOvertimeEarnCodeObj = defaultOvertimeEarnCodeObj;
        }

        public void setDepartment(Department.Builder department) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.department = department;
        }

        public void setHrsDistributionF(boolean hrsDistributionF) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.hrsDistributionF = hrsDistributionF;
        }

        public void setDept(String dept) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.dept = dept;
        }

        public void setWorkArea(Long workArea) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.workArea = workArea;
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

        final static String ROOT_ELEMENT_NAME = "workArea";
        final static String TYPE_NAME = "WorkAreaType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     *
     */
    static class Elements {

        final static String DESCRIPTION = "description";
        final static String OVERTIME_EDIT_ROLE = "overtimeEditRole";
        final static String DEFAULT_OVERTIME_EARN_CODE = "defaultOvertimeEarnCode";
        final static String OVT_EARN_CODE = "ovtEarnCode";
        final static String TK_WORK_AREA_ID = "tkWorkAreaId";
        final static String ADMIN_DESCR = "adminDescr";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";
        final static String DEFAULT_OVERTIME_EARN_CODE_OBJ = "defaultOvertimeEarnCodeObj";
        final static String DEPARTMENT = "department";
        final static String HRS_DISTRIBUTION_F = "hrsDistributionF";
        final static String DEPT = "dept";
        final static String WORK_AREA = "workArea";
        final static String ACTIVE = "active";
        final static String ID = "id";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
        final static String CREATE_TIME = "createTime";
        final static String GROUP_KEY_CODE = "groupKeyCode";
        final static String GROUP_KEY = "groupKey";

    }

}


