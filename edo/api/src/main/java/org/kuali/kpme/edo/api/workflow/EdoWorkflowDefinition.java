package org.kuali.kpme.edo.api.workflow;

import java.io.Serializable;
import java.util.Collection;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.joda.time.DateTime;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.w3c.dom.Element;

@XmlRootElement(name = EdoWorkflowDefinition.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = EdoWorkflowDefinition.Constants.TYPE_NAME, propOrder = {
    EdoWorkflowDefinition.Elements.ACTION_FULL_DATE_TIME,
    EdoWorkflowDefinition.Elements.EDO_WORKFLOW_ID,
    EdoWorkflowDefinition.Elements.WORKFLOW_NAME,
    EdoWorkflowDefinition.Elements.USER_PRINCIPAL_ID,
    EdoWorkflowDefinition.Elements.WORKFLOW_DESCRIPTION,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class EdoWorkflowDefinition
    extends AbstractDataTransferObject
    implements EdoWorkflowDefinitionContract
{

    @XmlElement(name = Elements.ACTION_FULL_DATE_TIME, required = false)
    private final DateTime actionFullDateTime;
    @XmlElement(name = Elements.EDO_WORKFLOW_ID, required = false)
    private final String edoWorkflowId;
    @XmlElement(name = Elements.WORKFLOW_NAME, required = false)
    private final String workflowName;
    @XmlElement(name = Elements.USER_PRINCIPAL_ID, required = false)
    private final String userPrincipalId;
    @XmlElement(name = Elements.WORKFLOW_DESCRIPTION, required = false)
    private final String workflowDescription;
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
    private EdoWorkflowDefinition() {
        this.actionFullDateTime = null;
        this.edoWorkflowId = null;
        this.workflowName = null;
        this.userPrincipalId = null;
        this.workflowDescription = null;
        this.versionNumber = null;
        this.objectId = null;
    }

    private EdoWorkflowDefinition(Builder builder) {
        this.actionFullDateTime = builder.getActionFullDateTime();
        this.edoWorkflowId = builder.getEdoWorkflowId();
        this.workflowName = builder.getWorkflowName();
        this.userPrincipalId = builder.getUserPrincipalId();
        this.workflowDescription = builder.getWorkflowDescription();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
    }

    @Override
    public DateTime getActionFullDateTime() {
        return this.actionFullDateTime;
    }

    @Override
    public String getEdoWorkflowId() {
        return this.edoWorkflowId;
    }

    @Override
    public String getWorkflowName() {
        return this.workflowName;
    }

    @Override
    public String getUserPrincipalId() {
        return this.userPrincipalId;
    }

    @Override
    public String getWorkflowDescription() {
        return this.workflowDescription;
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
     * A builder which can be used to construct {@link EdoWorkflowDefinition} instances.  Enforces the constraints of the {@link EdoWorkflowDefinitionContract}.
     * 
     */
    public final static class Builder
        implements Serializable, EdoWorkflowDefinitionContract, ModelBuilder
    {

        private DateTime actionFullDateTime;
        private String edoWorkflowId;
        private String workflowName;
        private String userPrincipalId;
        private String workflowDescription;
        private Long versionNumber;
        private String objectId;

        private Builder() {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        }

        public static Builder create() {
            // TODO modify as needed to pass any required values and add them to the signature of the 'create' method
            return new Builder();
        }

        public static Builder create(EdoWorkflowDefinitionContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            // TODO if create() is modified to accept required parameters, this will need to be modified
            Builder builder = create();
            builder.setActionFullDateTime(contract.getActionFullDateTime());
            builder.setEdoWorkflowId(contract.getEdoWorkflowId());
            builder.setWorkflowName(contract.getWorkflowName());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            builder.setWorkflowDescription(contract.getWorkflowDescription());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            return builder;
        }

        public EdoWorkflowDefinition build() {
            return new EdoWorkflowDefinition(this);
        }

        @Override
        public DateTime getActionFullDateTime() {
            return this.actionFullDateTime;
        }

        @Override
        public String getEdoWorkflowId() {
            return this.edoWorkflowId;
        }

        @Override
        public String getWorkflowName() {
            return this.workflowName;
        }

        @Override
        public String getUserPrincipalId() {
            return this.userPrincipalId;
        }

        @Override
        public String getWorkflowDescription() {
            return this.workflowDescription;
        }

        @Override
        public Long getVersionNumber() {
            return this.versionNumber;
        }

        @Override
        public String getObjectId() {
            return this.objectId;
        }

        public void setActionFullDateTime(DateTime actionFullDateTime) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.actionFullDateTime = actionFullDateTime;
        }

        public void setEdoWorkflowId(String edoWorkflowId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.edoWorkflowId = edoWorkflowId;
        }

        public void setWorkflowName(String workflowName) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.workflowName = workflowName;
        }

        public void setUserPrincipalId(String userPrincipalId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.userPrincipalId = userPrincipalId;
        }

        public void setWorkflowDescription(String workflowDescription) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.workflowDescription = workflowDescription;
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

        final static String ROOT_ELEMENT_NAME = "edoWorkflowDefinition";
        final static String TYPE_NAME = "EdoWorkflowDefinitionType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

        final static String ACTION_FULL_DATE_TIME = "actionFullDateTime";
        final static String EDO_WORKFLOW_ID = "edoWorkflowId";
        final static String WORKFLOW_NAME = "workflowName";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";
        final static String WORKFLOW_DESCRIPTION = "workflowDescription";

    }

}

