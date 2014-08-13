package org.kuali.kpme.edo.api.dossier.type;

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

@XmlRootElement(name = EdoDossierType.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = EdoDossierType.Constants.TYPE_NAME, propOrder = {
    
    EdoDossierType.Elements.EDO_DOSSIER_TYPE_ID,
    EdoDossierType.Elements.DOSSIER_TYPE_CODE,
    EdoDossierType.Elements.DOCUMENT_TYPE_NAME,
    EdoDossierType.Elements.DOSSIER_TYPE_NAME,
    
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    EdoDossierType.Elements.ACTIVE,
    EdoDossierType.Elements.ID,
    EdoDossierType.Elements.EFFECTIVE_LOCAL_DATE,
    EdoDossierType.Elements.CREATE_TIME,
    EdoDossierType.Elements.USER_PRINCIPAL_ID,
    
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class EdoDossierType
    extends AbstractDataTransferObject
    implements EdoDossierTypeContract
{

    @XmlElement(name = Elements.EDO_DOSSIER_TYPE_ID, required = false)
    private final String edoDossierTypeId;
    @XmlElement(name = Elements.DOSSIER_TYPE_CODE, required = false)
    private final String dossierTypeCode;
    @XmlElement(name = Elements.DOCUMENT_TYPE_NAME, required = false)
    private final String documentTypeName;
    @XmlElement(name = Elements.DOSSIER_TYPE_NAME, required = false)
    private final String dossierTypeName;
    
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
    private EdoDossierType() { 
        this.edoDossierTypeId = null;
        this.dossierTypeCode = null;
        this.documentTypeName = null;
        this.dossierTypeName = null; 
        this.versionNumber = null;
        this.objectId = null;
        this.active = false;
        this.id = null;
        this.effectiveLocalDate = null;
        this.createTime = null;
        this.userPrincipalId = null;
    }

    private EdoDossierType(Builder builder) {
        
        this.edoDossierTypeId = builder.getEdoDossierTypeId();
        this.dossierTypeCode = builder.getDossierTypeCode();
        this.documentTypeName = builder.getDocumentTypeName();
        this.dossierTypeName = builder.getDossierTypeName();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.active = builder.isActive();
        this.id = builder.getId();
        this.effectiveLocalDate = builder.getEffectiveLocalDate();
        this.createTime = builder.getCreateTime();
        this.userPrincipalId = builder.getUserPrincipalId();
    }

    @Override
    public String getEdoDossierTypeId() {
        return this.edoDossierTypeId;
    }

    @Override
    public String getDossierTypeCode() {
        return this.dossierTypeCode;
    }

    @Override
    public String getDocumentTypeName() {
        return this.documentTypeName;
    }

    @Override
    public String getDossierTypeName() {
        return this.dossierTypeName;
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
     * A builder which can be used to construct {@link EdoDossierType} instances.  Enforces the constraints of the {@link EdoDossierTypeContract}.
     * 
     */
    public final static class Builder
        implements Serializable, EdoDossierTypeContract, ModelBuilder
    { 
        private String edoDossierTypeId;
        private String dossierTypeCode;
        private String documentTypeName;
        private String dossierTypeName;
        private Long versionNumber;
        private String objectId;
        private boolean active;
        private String id;
        private LocalDate effectiveLocalDate;
        private DateTime createTime;
        private String userPrincipalId;
       
        private Builder(String dossierTypeCode) {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        	
        	setDossierTypeCode(dossierTypeCode);
        }

        public static Builder create(String dossierTypeCode) {
            // TODO modify as needed to pass any required values and add them to the signature of the 'create' method
            return new Builder(dossierTypeCode);
        }

        public static Builder create(EdoDossierTypeContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            // TODO if create() is modified to accept required parameters, this will need to be modified
            Builder builder = create(contract.getDossierTypeCode());

            builder.setEdoDossierTypeId(contract.getEdoDossierTypeId());
            builder.setDossierTypeCode(contract.getDossierTypeCode());
            builder.setDocumentTypeName(contract.getDocumentTypeName());
            builder.setDossierTypeName(contract.getDossierTypeName());
            
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            builder.setActive(contract.isActive());
            builder.setId(contract.getId());
            builder.setEffectiveLocalDate(contract.getEffectiveLocalDate());
            builder.setCreateTime(contract.getCreateTime());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            return builder;
        }

        public EdoDossierType build() {
            return new EdoDossierType(this);
        }


        @Override
        public String getEdoDossierTypeId() {
            return this.edoDossierTypeId;
        }

        @Override
        public String getDossierTypeCode() {
            return this.dossierTypeCode;
        }

        @Override
        public String getDocumentTypeName() {
            return this.documentTypeName;
        }

        @Override
        public String getDossierTypeName() {
            return this.dossierTypeName;
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

        public void setEdoDossierTypeId(String edoDossierTypeId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.edoDossierTypeId = edoDossierTypeId;
        }

        public void setDossierTypeCode(String dossierTypeCode) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.dossierTypeCode = dossierTypeCode;
        }

        public void setDocumentTypeName(String documentTypeName) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.documentTypeName = documentTypeName;
        }

        public void setDossierTypeName(String dossierTypeName) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.dossierTypeName = dossierTypeName;
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

        final static String ROOT_ELEMENT_NAME = "edoDossierType";
        final static String TYPE_NAME = "EdoDossierTypeType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

        final static String EDO_DOSSIER_TYPE_ID = "edoDossierTypeId";
        final static String DOSSIER_TYPE_CODE = "dossierTypeCode";
        final static String DOCUMENT_TYPE_NAME = "documentTypeName";
        final static String DOSSIER_TYPE_NAME = "dossierTypeName";
        final static String ACTIVE = "active";
        final static String ID = "id";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
        final static String CREATE_TIME = "createTime";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";
    }

}

