package org.kuali.kpme.edo.api.reviewlayerdef;

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

@XmlRootElement(name = EdoSuppReviewLayerDefinition.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = EdoSuppReviewLayerDefinition.Constants.TYPE_NAME, propOrder = {
    EdoSuppReviewLayerDefinition.Elements.SUPP_NODE_NAME,
    EdoSuppReviewLayerDefinition.Elements.ACKNOWLEDGE_FLAG,
    EdoSuppReviewLayerDefinition.Elements.EDO_SUPP_REVIEW_LAYER_DEFINITION_ID,
    EdoSuppReviewLayerDefinition.Elements.EDO_REVIEW_LAYER_DEFINITION_ID,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class EdoSuppReviewLayerDefinition
    extends AbstractDataTransferObject
    implements EdoSuppReviewLayerDefinitionContract
{

    @XmlElement(name = Elements.SUPP_NODE_NAME, required = false)
    private final String suppNodeName;
    @XmlElement(name = Elements.ACKNOWLEDGE_FLAG, required = false)
    private final boolean acknowledgeFlag;
    @XmlElement(name = Elements.EDO_SUPP_REVIEW_LAYER_DEFINITION_ID, required = false)
    private final String edoSuppReviewLayerDefinitionId;
    @XmlElement(name = Elements.EDO_REVIEW_LAYER_DEFINITION_ID, required = false)
    private final String edoReviewLayerDefinitionId;
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
    private EdoSuppReviewLayerDefinition() {
        this.suppNodeName = null;
        this.acknowledgeFlag = false;
        this.edoSuppReviewLayerDefinitionId = null;
        this.edoReviewLayerDefinitionId = null;
        this.versionNumber = null;
        this.objectId = null;
    }

    private EdoSuppReviewLayerDefinition(Builder builder) {
        this.suppNodeName = builder.getSuppNodeName();
        this.acknowledgeFlag = builder.isAcknowledgeFlag();
        this.edoSuppReviewLayerDefinitionId = builder.getEdoSuppReviewLayerDefinitionId();
        this.edoReviewLayerDefinitionId = builder.getEdoReviewLayerDefinitionId();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
    }

    @Override
    public String getSuppNodeName() {
        return this.suppNodeName;
    }

    @Override
    public boolean isAcknowledgeFlag() {
        return this.acknowledgeFlag;
    }

    @Override
    public String getEdoSuppReviewLayerDefinitionId() {
        return this.edoSuppReviewLayerDefinitionId;
    }

    @Override
    public String getEdoReviewLayerDefinitionId() {
        return this.edoReviewLayerDefinitionId;
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
     * A builder which can be used to construct {@link EdoSuppReviewLayerDefinition} instances.  Enforces the constraints of the {@link EdoSuppReviewLayerDefinitionContract}.
     * 
     */
    public final static class Builder
        implements Serializable, EdoSuppReviewLayerDefinitionContract, ModelBuilder
    {

        private String suppNodeName;
        private boolean acknowledgeFlag;
        private String edoSuppReviewLayerDefinitionId;
        private String edoReviewLayerDefinitionId;
        private Long versionNumber;
        private String objectId;

        private Builder() {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        }

        public static Builder create() {
            // TODO modify as needed to pass any required values and add them to the signature of the 'create' method
            return new Builder();
        }

        public static Builder create(EdoSuppReviewLayerDefinitionContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            // TODO if create() is modified to accept required parameters, this will need to be modified
            Builder builder = create();
            builder.setSuppNodeName(contract.getSuppNodeName());
            builder.setAcknowledgeFlag(contract.isAcknowledgeFlag());
            builder.setEdoSuppReviewLayerDefinitionId(contract.getEdoSuppReviewLayerDefinitionId());
            builder.setEdoReviewLayerDefinitionId(contract.getEdoReviewLayerDefinitionId());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            return builder;
        }

        public EdoSuppReviewLayerDefinition build() {
            return new EdoSuppReviewLayerDefinition(this);
        }

        @Override
        public String getSuppNodeName() {
            return this.suppNodeName;
        }

        @Override
        public boolean isAcknowledgeFlag() {
            return this.acknowledgeFlag;
        }

        @Override
        public String getEdoSuppReviewLayerDefinitionId() {
            return this.edoSuppReviewLayerDefinitionId;
        }

        @Override
        public String getEdoReviewLayerDefinitionId() {
            return this.edoReviewLayerDefinitionId;
        }

        @Override
        public Long getVersionNumber() {
            return this.versionNumber;
        }

        @Override
        public String getObjectId() {
            return this.objectId;
        }

        public void setSuppNodeName(String suppNodeName) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.suppNodeName = suppNodeName;
        }

        public void setAcknowledgeFlag(boolean acknowledgeFlag) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.acknowledgeFlag = acknowledgeFlag;
        }

        public void setEdoSuppReviewLayerDefinitionId(String edoSuppReviewLayerDefinitionId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.edoSuppReviewLayerDefinitionId = edoSuppReviewLayerDefinitionId;
        }

        public void setEdoReviewLayerDefinitionId(String edoReviewLayerDefinitionId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.edoReviewLayerDefinitionId = edoReviewLayerDefinitionId;
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

        final static String ROOT_ELEMENT_NAME = "edoSuppReviewLayerDefinition";
        final static String TYPE_NAME = "EdoSuppReviewLayerDefinitionType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

        final static String SUPP_NODE_NAME = "suppNodeName";
        final static String ACKNOWLEDGE_FLAG = "acknowledgeFlag";
        final static String EDO_SUPP_REVIEW_LAYER_DEFINITION_ID = "edoSuppReviewLayerDefinitionId";
        final static String EDO_REVIEW_LAYER_DEFINITION_ID = "edoReviewLayerDefinitionId";

    }

}

