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
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.kuali.rice.core.api.util.jaxb.DateTimeAdapter;
import org.kuali.rice.core.api.util.jaxb.LocalDateAdapter;
import org.w3c.dom.Element;

@XmlRootElement(name = EdoGroupDefinition.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = EdoGroupDefinition.Constants.TYPE_NAME, propOrder = {
    EdoGroupDefinition.Elements.KIM_ROLE_NAME,
    EdoGroupDefinition.Elements.KIM_TYPE_NAME,
    EdoGroupDefinition.Elements.WORKFLOW_TYPE,
    EdoGroupDefinition.Elements.EDO_GROUP_ID,
    EdoGroupDefinition.Elements.EDO_WORKFLOW_ID,
    EdoGroupDefinition.Elements.DOSSIER_TYPE,
    EdoGroupDefinition.Elements.WORKFLOW_LEVEL,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    EdoGroupDefinition.Elements.ACTIVE,
    EdoGroupDefinition.Elements.ID,
    EdoGroupDefinition.Elements.EFFECTIVE_LOCAL_DATE,
    EdoGroupDefinition.Elements.CREATE_TIME,
    EdoGroupDefinition.Elements.USER_PRINCIPAL_ID,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class EdoGroupDefinition
    extends AbstractDataTransferObject
    implements EdoGroupDefinitionContract
{

    @XmlElement(name = Elements.KIM_ROLE_NAME, required = false)
    private final String kimRoleName;
    @XmlElement(name = Elements.KIM_TYPE_NAME, required = false)
    private final String kimTypeName;
    @XmlElement(name = Elements.WORKFLOW_TYPE, required = false)
    private final String workflowType;
    @XmlElement(name = Elements.EDO_GROUP_ID, required = false)
    private final String edoGroupId;
    @XmlElement(name = Elements.EDO_WORKFLOW_ID, required = false)
    private final String edoWorkflowId;
    @XmlElement(name = Elements.DOSSIER_TYPE, required = false)
    private final String dossierType;
    @XmlElement(name = Elements.WORKFLOW_LEVEL, required = false)
    private final String workflowLevel;
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
    @SuppressWarnings("unused")
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * Private constructor used only by JAXB.
     * 
     */
    private EdoGroupDefinition() {
        this.kimRoleName = null;
        this.kimTypeName = null;
        this.workflowType = null;
        this.edoGroupId = null;
        this.edoWorkflowId = null;
        this.dossierType = null;
        this.workflowLevel = null;
        this.versionNumber = null;
        this.objectId = null;
        this.active = false;
        this.id = null;
        this.effectiveLocalDate = null;
        this.createTime = null;
        this.userPrincipalId = null;
    }

    private EdoGroupDefinition(Builder builder) {
        this.kimRoleName = builder.getKimRoleName();
        this.kimTypeName = builder.getKimTypeName();
        this.workflowType = builder.getWorkflowType();
        this.edoGroupId = builder.getEdoGroupId();
        this.edoWorkflowId = builder.getEdoWorkflowId();
        this.dossierType = builder.getDossierType();
        this.workflowLevel = builder.getWorkflowLevel();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.active = builder.isActive();
        this.id = builder.getId();
        this.effectiveLocalDate = builder.getEffectiveLocalDate();
        this.createTime = builder.getCreateTime();
        this.userPrincipalId = builder.getUserPrincipalId();
    }

    @Override
    public String getKimRoleName() {
        return this.kimRoleName;
    }

    @Override
    public String getKimTypeName() {
        return this.kimTypeName;
    }

    @Override
    public String getWorkflowType() {
        return this.workflowType;
    }

    @Override
    public String getEdoGroupId() {
        return this.edoGroupId;
    }

    @Override
    public String getEdoWorkflowId() {
        return this.edoWorkflowId;
    }

    @Override
    public String getDossierType() {
        return this.dossierType;
    }

    @Override
    public String getWorkflowLevel() {
        return this.workflowLevel;
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
     * A builder which can be used to construct {@link EdoGroupDefinition} instances.  Enforces the constraints of the {@link EdoGroupDefinitionContract}.
     * 
     */
    public final static class Builder
        implements Serializable, EdoGroupDefinitionContract, ModelBuilder
    {

        private String kimRoleName;
        private String kimTypeName;
        private String workflowType;
        private String edoGroupId;
        private String edoWorkflowId;
        private String dossierType;
        private String workflowLevel;
        private Long versionNumber;
        private String objectId;
        private boolean active;
        private String id;
        private LocalDate effectiveLocalDate;
        private DateTime createTime;
        private String userPrincipalId;

        private Builder() {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        }

        public static Builder create() {
            // TODO modify as needed to pass any required values and add them to the signature of the 'create' method
            return new Builder();
        }
        
        private Builder(String edoWorkflowId, String workflowLevel, String dossierType) {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        	setEdoWorkflowId(edoWorkflowId);
        	setWorkflowLevel(workflowLevel);
        	setDossierType(dossierType);
        }

        public static Builder create(String edoWorkflowId, String workflowLevel, String dossierType) {
            // TODO modify as needed to pass any required values and add them to the signature of the 'create' method
            return new Builder(edoWorkflowId, workflowLevel, dossierType);
        }

        public static Builder create(EdoGroupDefinitionContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            // TODO if create() is modified to accept required parameters, this will need to be modified
            Builder builder = create();
            builder.setKimRoleName(contract.getKimRoleName());
            builder.setKimTypeName(contract.getKimTypeName());
            builder.setWorkflowType(contract.getWorkflowType());
            builder.setEdoGroupId(contract.getEdoGroupId());
            builder.setEdoWorkflowId(contract.getEdoWorkflowId());
            builder.setDossierType(contract.getDossierType());
            builder.setWorkflowLevel(contract.getWorkflowLevel());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            builder.setActive(contract.isActive());
            builder.setId(contract.getId());
            builder.setEffectiveLocalDate(contract.getEffectiveLocalDate());
            builder.setCreateTime(contract.getCreateTime());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            return builder;
        }

        public EdoGroupDefinition build() {
            return new EdoGroupDefinition(this);
        }

        @Override
        public String getKimRoleName() {
            return this.kimRoleName;
        }

        @Override
        public String getKimTypeName() {
            return this.kimTypeName;
        }

        @Override
        public String getWorkflowType() {
            return this.workflowType;
        }

        @Override
        public String getEdoGroupId() {
            return this.edoGroupId;
        }

        @Override
        public String getEdoWorkflowId() {
            return this.edoWorkflowId;
        }

        @Override
        public String getDossierType() {
            return this.dossierType;
        }

        @Override
        public String getWorkflowLevel() {
            return this.workflowLevel;
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

        public void setKimRoleName(String kimRoleName) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.kimRoleName = kimRoleName;
        }

        public void setKimTypeName(String kimTypeName) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.kimTypeName = kimTypeName;
        }

        public void setWorkflowType(String workflowType) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.workflowType = workflowType;
        }

        public void setEdoGroupId(String edoGroupId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.edoGroupId = edoGroupId;
        }

        public void setEdoWorkflowId(String edoWorkflowId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.edoWorkflowId = edoWorkflowId;
        }

        public void setDossierType(String dossierType) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.dossierType = dossierType;
        }

        public void setWorkflowLevel(String workflowLevel) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.workflowLevel = workflowLevel;
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

    }


    /**
     * Defines some internal constants used on this class.
     * 
     */
    static class Constants {

        final static String ROOT_ELEMENT_NAME = "edoGroupDefinition";
        final static String TYPE_NAME = "EdoGroupDefinitionType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

        final static String KIM_ROLE_NAME = "kimRoleName";
        final static String KIM_TYPE_NAME = "kimTypeName";
        final static String WORKFLOW_TYPE = "workflowType";
        final static String EDO_GROUP_ID = "edoGroupId";
        final static String EDO_WORKFLOW_ID = "edoWorkflowId";
        final static String DOSSIER_TYPE = "dossierType";
        final static String WORKFLOW_LEVEL = "workflowLevel";
        final static String ACTIVE = "active";
        final static String ID = "id";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
        final static String CREATE_TIME = "createTime";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";

    }

}

