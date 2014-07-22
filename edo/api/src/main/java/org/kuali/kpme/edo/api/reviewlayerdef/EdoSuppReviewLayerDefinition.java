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
    EdoSuppReviewLayerDefinition.Elements.EDO_SUPP_REVIEW_LAYER_DEFINITION_ID,
    EdoSuppReviewLayerDefinition.Elements.EDO_REVIEW_LAYER_DEFINITION_ID,
    EdoSuppReviewLayerDefinition.Elements.SUPP_NODE_NAME,
    EdoSuppReviewLayerDefinition.Elements.ACKNOWLEDGE_FLAG,
    EdoSuppReviewLayerDefinition.Elements.WORKFLOW_QUALIFIER,
    EdoSuppReviewLayerDefinition.Elements.EDO_WORKFLOW_ID,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class EdoSuppReviewLayerDefinition
    extends AbstractDataTransferObject
    implements EdoSuppReviewLayerDefinitionContract
{

    @XmlElement(name = Elements.EDO_SUPP_REVIEW_LAYER_DEFINITION_ID, required = false)
    private final String edoSuppReviewLayerDefinitionId;
    @XmlElement(name = Elements.EDO_REVIEW_LAYER_DEFINITION_ID, required = false)
    private final String edoReviewLayerDefinitionId;
    @XmlElement(name = Elements.SUPP_NODE_NAME, required = false)
    private final String suppNodeName;
    @XmlElement(name = Elements.ACKNOWLEDGE_FLAG, required = false)
    private final boolean acknowledgeFlag;
    @XmlElement(name = Elements.WORKFLOW_QUALIFIER, required = false)
    private final String workflowQualifier;
    @XmlElement(name = Elements.EDO_WORKFLOW_ID, required = false)
    private final String edoWorkflowId;
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
        this.edoSuppReviewLayerDefinitionId = null;
        this.edoReviewLayerDefinitionId = null;
        this.suppNodeName = null;
        this.acknowledgeFlag = false;
        this.workflowQualifier = null;
        this.edoWorkflowId = null;
        this.versionNumber = null;
        this.objectId = null;
    }

    private EdoSuppReviewLayerDefinition(Builder builder) {
        this.edoSuppReviewLayerDefinitionId = builder.getEdoSuppReviewLayerDefinitionId();
        this.edoReviewLayerDefinitionId = builder.getEdoReviewLayerDefinitionId();
        this.suppNodeName = builder.getSuppNodeName();
        this.acknowledgeFlag = builder.isAcknowledgeFlag();
        this.workflowQualifier = builder.getWorkflowQualifier();
        this.edoWorkflowId = builder.getEdoWorkflowId();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
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
    public String getSuppNodeName() {
        return this.suppNodeName;
    }

    @Override
    public boolean isAcknowledgeFlag() {
        return this.acknowledgeFlag;
    }

    @Override
    public String getWorkflowQualifier() {
        return this.workflowQualifier;
    }

    @Override
    public String getEdoWorkflowId() {
        return this.edoWorkflowId;
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

        private String edoSuppReviewLayerDefinitionId;
        private String edoReviewLayerDefinitionId;
        private String suppNodeName;
        private boolean acknowledgeFlag;
        private String workflowQualifier;
        private String edoWorkflowId;
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
            builder.setEdoSuppReviewLayerDefinitionId(contract.getEdoSuppReviewLayerDefinitionId());
            builder.setEdoReviewLayerDefinitionId(contract.getEdoReviewLayerDefinitionId());
            builder.setSuppNodeName(contract.getSuppNodeName());
            builder.setAcknowledgeFlag(contract.isAcknowledgeFlag());
            builder.setWorkflowQualifier(contract.getWorkflowQualifier());
            builder.setEdoWorkflowId(contract.getEdoWorkflowId());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            return builder;
        }

        public EdoSuppReviewLayerDefinition build() {
            return new EdoSuppReviewLayerDefinition(this);
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
        public String getSuppNodeName() {
            return this.suppNodeName;
        }

        @Override
        public boolean isAcknowledgeFlag() {
            return this.acknowledgeFlag;
        }

        @Override
        public String getWorkflowQualifier() {
            return this.workflowQualifier;
        }

        @Override
        public String getEdoWorkflowId() {
            return this.edoWorkflowId;
        }

        @Override
        public Long getVersionNumber() {
            return this.versionNumber;
        }

        @Override
        public String getObjectId() {
            return this.objectId;
        }

        public void setEdoSuppReviewLayerDefinitionId(String edoSuppReviewLayerDefinitionId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.edoSuppReviewLayerDefinitionId = edoSuppReviewLayerDefinitionId;
        }

        public void setEdoReviewLayerDefinitionId(String edoReviewLayerDefinitionId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.edoReviewLayerDefinitionId = edoReviewLayerDefinitionId;
        }

        public void setSuppNodeName(String suppNodeName) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.suppNodeName = suppNodeName;
        }

        public void setAcknowledgeFlag(boolean acknowledgeFlag) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.acknowledgeFlag = acknowledgeFlag;
        }

        public void setWorkflowQualifier(String workflowQualifier) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.workflowQualifier = workflowQualifier;
        }

        public void setEdoWorkflowId(String edoWorkflowId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.edoWorkflowId = edoWorkflowId;
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

        final static String EDO_SUPP_REVIEW_LAYER_DEFINITION_ID = "edoSuppReviewLayerDefinitionId";
        final static String EDO_REVIEW_LAYER_DEFINITION_ID = "edoReviewLayerDefinitionId";
        final static String SUPP_NODE_NAME = "suppNodeName";
        final static String ACKNOWLEDGE_FLAG = "acknowledgeFlag";
        final static String WORKFLOW_QUALIFIER = "workflowQualifier";
        final static String EDO_WORKFLOW_ID = "edoWorkflowId";

    }

}

