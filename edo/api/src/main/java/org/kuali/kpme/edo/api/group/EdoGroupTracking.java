package org.kuali.kpme.edo.api.group;

import java.io.Serializable;
import java.util.Collection;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.kpme.core.api.groupkey.HrGroupKeyContract;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.kuali.rice.core.api.util.jaxb.DateTimeAdapter;
import org.kuali.rice.core.api.util.jaxb.LocalDateAdapter;
import org.w3c.dom.Element;

@XmlRootElement(name = EdoGroupTracking.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = EdoGroupTracking.Constants.TYPE_NAME, propOrder = {
    EdoGroupTracking.Elements.EDO_WORKFLOW_ID,
    EdoGroupTracking.Elements.EDO_GROUP_TRACKING_ID,
    EdoGroupTracking.Elements.DEPARTMENT_ID,
    EdoGroupTracking.Elements.REVIEW_LEVEL_NAME,
    EdoGroupTracking.Elements.ORGANIZATION_CODE,
    EdoGroupTracking.Elements.GROUP_NAME,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    EdoGroupTracking.Elements.ACTIVE,
    EdoGroupTracking.Elements.ID,
    EdoGroupTracking.Elements.EFFECTIVE_LOCAL_DATE,
    EdoGroupTracking.Elements.CREATE_TIME,
    EdoGroupTracking.Elements.USER_PRINCIPAL_ID,
    EdoGroupTracking.Elements.GROUP_KEY_CODE,
    EdoGroupTracking.Elements.GROUP_KEY,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class EdoGroupTracking
    extends AbstractDataTransferObject
    implements EdoGroupTrackingContract
{

    @XmlElement(name = Elements.EDO_WORKFLOW_ID, required = false)
    private final String edoWorkflowId;
    @XmlElement(name = Elements.EDO_GROUP_TRACKING_ID, required = false)
    private final String edoGroupTrackingId;
    @XmlElement(name = Elements.DEPARTMENT_ID, required = false)
    private final String departmentId;
    @XmlElement(name = Elements.REVIEW_LEVEL_NAME, required = false)
    private final String reviewLevelName;
    @XmlElement(name = Elements.ORGANIZATION_CODE, required = false)
    private final String organizationCode;
    @XmlElement(name = Elements.GROUP_NAME, required = false)
    private final String groupName;
    @XmlElement(name = CoreConstants.CommonElements.VERSION_NUMBER, required = false)
    private final Long versionNumber;
    @XmlElement(name = CoreConstants.CommonElements.OBJECT_ID, required = false)
    private final String objectId;
    @XmlElement(name = Elements.ACTIVE, required = false)
    private final boolean active;
    @XmlElement(name = Elements.ID, required = false)
    private final String id;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @XmlElement(name = Elements.EFFECTIVE_LOCAL_DATE, required = false)
    private final LocalDate effectiveLocalDate;
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
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
    private EdoGroupTracking() {
        this.edoWorkflowId = null;
        this.edoGroupTrackingId = null;
        this.departmentId = null;
        this.reviewLevelName = null;
        this.organizationCode = null;
        this.groupName = null;
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

    private EdoGroupTracking(Builder builder) {
        this.edoWorkflowId = builder.getEdoWorkflowId();
        this.edoGroupTrackingId = builder.getEdoGroupTrackingId();
        this.departmentId = builder.getDepartmentId();
        this.reviewLevelName = builder.getReviewLevelName();
        this.organizationCode = builder.getOrganizationCode();
        this.groupName = builder.getGroupName();
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
    public String getEdoWorkflowId() {
        return this.edoWorkflowId;
    }

    @Override
    public String getEdoGroupTrackingId() {
        return this.edoGroupTrackingId;
    }

    @Override
    public String getDepartmentId() {
        return this.departmentId;
    }

    @Override
    public String getReviewLevelName() {
        return this.reviewLevelName;
    }

    @Override
    public String getOrganizationCode() {
        return this.organizationCode;
    }

    @Override
    public String getGroupName() {
        return this.groupName;
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
     * A builder which can be used to construct {@link EdoGroupTracking} instances.  Enforces the constraints of the {@link EdoGroupTrackingContract}.
     * 
     */
    public final static class Builder
        implements Serializable, EdoGroupTrackingContract, ModelBuilder
    {

        private String edoWorkflowId;
        private String edoGroupTrackingId;
        private String departmentId;
        private String reviewLevelName;
        private String organizationCode;
        private String groupName;
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

        public static Builder create(EdoGroupTrackingContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            // TODO if create() is modified to accept required parameters, this will need to be modified
            Builder builder = create();
            builder.setEdoWorkflowId(contract.getEdoWorkflowId());
            builder.setEdoGroupTrackingId(contract.getEdoGroupTrackingId());
            builder.setDepartmentId(contract.getDepartmentId());
            builder.setReviewLevelName(contract.getReviewLevelName());
            builder.setOrganizationCode(contract.getOrganizationCode());
            builder.setGroupName(contract.getGroupName());
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

        public EdoGroupTracking build() {
            return new EdoGroupTracking(this);
        }

        @Override
        public String getEdoWorkflowId() {
            return this.edoWorkflowId;
        }

        @Override
        public String getEdoGroupTrackingId() {
            return this.edoGroupTrackingId;
        }

        @Override
        public String getDepartmentId() {
            return this.departmentId;
        }

        @Override
        public String getReviewLevelName() {
            return this.reviewLevelName;
        }

        @Override
        public String getOrganizationCode() {
            return this.organizationCode;
        }

        @Override
        public String getGroupName() {
            return this.groupName;
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

        public void setEdoWorkflowId(String edoWorkflowId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.edoWorkflowId = edoWorkflowId;
        }

        public void setEdoGroupTrackingId(String edoGroupTrackingId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.edoGroupTrackingId = edoGroupTrackingId;
        }

        public void setDepartmentId(String departmentId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.departmentId = departmentId;
        }

        public void setReviewLevelName(String reviewLevelName) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.reviewLevelName = reviewLevelName;
        }

        public void setOrganizationCode(String organizationCode) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.organizationCode = organizationCode;
        }

        public void setGroupName(String groupName) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.groupName = groupName;
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

        final static String ROOT_ELEMENT_NAME = "edoGroupTracking";
        final static String TYPE_NAME = "EdoGroupTrackingType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

        final static String EDO_WORKFLOW_ID = "edoWorkflowId";
        final static String EDO_GROUP_TRACKING_ID = "edoGroupTrackingId";
        final static String DEPARTMENT_ID = "departmentId";
        final static String REVIEW_LEVEL_NAME = "reviewLevelName";
        final static String ORGANIZATION_CODE = "organizationCode";
        final static String GROUP_NAME = "groupName";
        final static String ACTIVE = "active";
        final static String ID = "id";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
        final static String CREATE_TIME = "createTime";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";
        final static String GROUP_KEY_CODE = "groupKeyCode";
        final static String GROUP_KEY = "groupKey";

    }

}

