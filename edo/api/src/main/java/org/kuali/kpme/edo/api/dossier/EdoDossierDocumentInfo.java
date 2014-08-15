
package org.kuali.kpme.edo.api.dossier;

import java.io.Serializable;
import java.util.Collection;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.w3c.dom.Element;

@XmlRootElement(name = EdoDossierDocumentInfo.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = EdoDossierDocumentInfo.Constants.TYPE_NAME, propOrder = {
    EdoDossierDocumentInfo.Elements.DOCUMENT_TYPE_NAME,
    EdoDossierDocumentInfo.Elements.EDO_DOCUMENT_ID,
    EdoDossierDocumentInfo.Elements.PRINCIPAL_ID,
    EdoDossierDocumentInfo.Elements.DOCUMENT_STATUS,
    EdoDossierDocumentInfo.Elements.EDO_DOSSIER_ID,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class EdoDossierDocumentInfo
    extends AbstractDataTransferObject
    implements EdoDossierDocumentInfoContract
{

    @XmlElement(name = Elements.DOCUMENT_TYPE_NAME, required = false)
    private final String documentTypeName;
    @XmlElement(name = Elements.EDO_DOCUMENT_ID, required = false)
    private final String edoDocumentId;
    @XmlElement(name = Elements.PRINCIPAL_ID, required = false)
    private final String principalId;
    @XmlElement(name = Elements.DOCUMENT_STATUS, required = false)
    private final String documentStatus;
    @XmlElement(name = Elements.EDO_DOSSIER_ID, required = false)
    private final String edoDossierId;
    @XmlElement(name = CoreConstants.CommonElements.VERSION_NUMBER, required = false)
    private final Long versionNumber;
    @XmlElement(name = CoreConstants.CommonElements.OBJECT_ID, required = false)
    private final String objectId;
    @SuppressWarnings("unused")
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * Private constructor used only by JAXB.
     * 
     */
    private EdoDossierDocumentInfo() {
        this.documentTypeName = null;
        this.edoDocumentId = null;
        this.principalId = null;
        this.documentStatus = null;
        this.edoDossierId = null;
        this.versionNumber = null;
        this.objectId = null;
    }

    private EdoDossierDocumentInfo(Builder builder) {
        this.documentTypeName = builder.getDocumentTypeName();
        this.edoDocumentId = builder.getEdoDocumentId();
        this.principalId = builder.getPrincipalId();
        this.documentStatus = builder.getDocumentStatus();
        this.edoDossierId = builder.getEdoDossierId();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
    }

    @Override
    public String getDocumentTypeName() {
        return this.documentTypeName;
    }

    @Override
    public String getEdoDocumentId() {
        return this.edoDocumentId;
    }

    @Override
    public String getPrincipalId() {
        return this.principalId;
    }

    @Override
    public String getDocumentStatus() {
        return this.documentStatus;
    }

    @Override
    public String getEdoDossierId() {
        return this.edoDossierId;
    }

    @Override
    public Long getVersionNumber() {
        return this.versionNumber;
    }

    @Override
    public String getObjectId() {
        return this.objectId;
    }


    /**
     * A builder which can be used to construct {@link EdoDossierDocumentInfo} instances.  Enforces the constraints of the {@link EdoDossierDocumentInfoContract}.
     * 
     */
    public final static class Builder
        implements Serializable, EdoDossierDocumentInfoContract, ModelBuilder
    {

        private String documentTypeName;
        private String edoDocumentId;
        private String principalId;
        private String documentStatus;
        private String edoDossierId;
        private Long versionNumber;
        private String objectId;

        private Builder() {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        }

        public static Builder create() {
            // TODO modify as needed to pass any required values and add them to the signature of the 'create' method
            return new Builder();
        }

        public static Builder create(EdoDossierDocumentInfoContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            // TODO if create() is modified to accept required parameters, this will need to be modified
            Builder builder = create();
            builder.setDocumentTypeName(contract.getDocumentTypeName());
            builder.setEdoDocumentId(contract.getEdoDocumentId());
            builder.setPrincipalId(contract.getPrincipalId());
            builder.setDocumentStatus(contract.getDocumentStatus());
            builder.setEdoDossierId(contract.getEdoDossierId());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            return builder;
        }

        public EdoDossierDocumentInfo build() {
            return new EdoDossierDocumentInfo(this);
        }

        @Override
        public String getDocumentTypeName() {
            return this.documentTypeName;
        }

        @Override
        public String getEdoDocumentId() {
            return this.edoDocumentId;
        }

        @Override
        public String getPrincipalId() {
            return this.principalId;
        }

        @Override
        public String getDocumentStatus() {
            return this.documentStatus;
        }

        @Override
        public String getEdoDossierId() {
            return this.edoDossierId;
        }

        @Override
        public Long getVersionNumber() {
            return this.versionNumber;
        }

        @Override
        public String getObjectId() {
            return this.objectId;
        }

        public void setDocumentTypeName(String documentTypeName) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.documentTypeName = documentTypeName;
        }

        public void setEdoDocumentId(String edoDocumentId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.edoDocumentId = edoDocumentId;
        }

        public void setPrincipalId(String principalId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.principalId = principalId;
        }

        public void setDocumentStatus(String documentStatus) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.documentStatus = documentStatus;
        }

        public void setEdoDossierId(String edoDossierId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.edoDossierId = edoDossierId;
        }

        public void setVersionNumber(Long versionNumber) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.versionNumber = versionNumber;
        }

        public void setObjectId(String objectId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.objectId = objectId;
        }

    }


    /**
     * Defines some internal constants used on this class.
     * 
     */
    static class Constants {

        final static String ROOT_ELEMENT_NAME = "edoDossierDocumentInfo";
        final static String TYPE_NAME = "EdoDossierDocumentInfoType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

        final static String DOCUMENT_TYPE_NAME = "documentTypeName";
        final static String EDO_DOCUMENT_ID = "edoDocumentId";
        final static String PRINCIPAL_ID = "principalId";
        final static String DOCUMENT_STATUS = "documentStatus";
        final static String EDO_DOSSIER_ID = "edoDossierId";

    }

}

