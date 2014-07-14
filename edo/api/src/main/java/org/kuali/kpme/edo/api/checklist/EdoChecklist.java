package org.kuali.kpme.edo.api.checklist;

import java.io.Serializable;
import java.util.Collection;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.groupkey.HrGroupKey;
import org.kuali.kpme.core.api.groupkey.HrGroupKeyContract;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.w3c.dom.Element;

@XmlRootElement(name = EdoChecklist.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = EdoChecklist.Constants.TYPE_NAME, propOrder = {
    EdoChecklist.Elements.DESCRIPTION,
    EdoChecklist.Elements.EDO_CHECKLIST_I_D,
    EdoChecklist.Elements.DOSSIER_TYPE_CODE,
    EdoChecklist.Elements.ORGANIZATION_CODE,
    EdoChecklist.Elements.DEPARTMENT_I_D,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    EdoChecklist.Elements.ACTIVE,
    EdoChecklist.Elements.ID,
    EdoChecklist.Elements.EFFECTIVE_LOCAL_DATE,
    EdoChecklist.Elements.CREATE_TIME,
    EdoChecklist.Elements.USER_PRINCIPAL_ID,
    EdoChecklist.Elements.GROUP_KEY_CODE,
    EdoChecklist.Elements.GROUP_KEY,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class EdoChecklist
    extends AbstractDataTransferObject
    implements EdoChecklistContract
{

    @XmlElement(name = Elements.DESCRIPTION, required = false)
    private final String description;
    @XmlElement(name = Elements.EDO_CHECKLIST_I_D, required = false)
    private final String edoChecklistId;
    @XmlElement(name = Elements.DOSSIER_TYPE_CODE, required = false)
    private final String dossierTypeCode;
    @XmlElement(name = Elements.ORGANIZATION_CODE, required = false)
    private final String organizationCode;
    @XmlElement(name = Elements.DEPARTMENT_I_D, required = false)
    private final String departmentID;
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
    private EdoChecklist() {
        this.description = null;
        this.edoChecklistId = null;
        this.dossierTypeCode = null;
        this.organizationCode = null;
        this.departmentID = null;
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

    private EdoChecklist(Builder builder) {
        this.description = builder.getDescription();
        this.edoChecklistId = builder.getEdoChecklistId();
        this.dossierTypeCode = builder.getDossierTypeCode();
        this.organizationCode = builder.getOrganizationCode();
        this.departmentID = builder.getDepartmentID();
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
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getEdoChecklistId() {
        return this.edoChecklistId;
    }

    @Override
    public String getDossierTypeCode() {
        return this.dossierTypeCode;
    }

    @Override
    public String getOrganizationCode() {
        return this.organizationCode;
    }

    @Override
    public String getDepartmentID() {
        return this.departmentID;
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
     * A builder which can be used to construct {@link EdoChecklist} instances.  Enforces the constraints of the {@link EdoChecklistContract}.
     * 
     */
    public final static class Builder
        implements Serializable, EdoChecklistContract, ModelBuilder
    {

        private String description;
        private String edoChecklistId;
        private String dossierTypeCode;
        private String organizationCode;
        private String departmentID;
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

        public static Builder create(EdoChecklistContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            // TODO if create() is modified to accept required parameters, this will need to be modified
            Builder builder = create();
            builder.setDescription(contract.getDescription());
            builder.setEdoChecklistId(contract.getEdoChecklistId());
            builder.setDossierTypeCode(contract.getDossierTypeCode());
            builder.setOrganizationCode(contract.getOrganizationCode());
            builder.setDepartmentID(contract.getDepartmentID());
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

        public EdoChecklist build() {
            return new EdoChecklist(this);
        }

        @Override
        public String getDescription() {
            return this.description;
        }

        @Override
        public String getEdoChecklistId() {
            return this.edoChecklistId;
        }

        @Override
        public String getDossierTypeCode() {
            return this.dossierTypeCode;
        }

        @Override
        public String getOrganizationCode() {
            return this.organizationCode;
        }

        @Override
        public String getDepartmentID() {
            return this.departmentID;
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

        public void setDescription(String description) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.description = description;
        }

        public void setEdoChecklistId(String edoChecklistId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.edoChecklistId = edoChecklistId;
        }

        public void setDossierTypeCode(String dossierTypeCode) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.dossierTypeCode = dossierTypeCode;
        }

        public void setOrganizationCode(String organizationCode) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.organizationCode = organizationCode;
        }

        public void setDepartmentID(String departmentID) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.departmentID = departmentID;
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

        final static String ROOT_ELEMENT_NAME = "edoChecklist";
        final static String TYPE_NAME = "EdoChecklistType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

        final static String DESCRIPTION = "description";
        final static String EDO_CHECKLIST_I_D = "edoChecklistId";
        final static String DOSSIER_TYPE_CODE = "dossierTypeCode";
        final static String ORGANIZATION_CODE = "organizationCode";
        final static String DEPARTMENT_I_D = "departmentID";
        final static String ACTIVE = "active";
        final static String ID = "id";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
        final static String CREATE_TIME = "createTime";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";
        final static String GROUP_KEY_CODE = "groupKeyCode";
        final static String GROUP_KEY = "groupKey";

    }

}

