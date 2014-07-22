package org.kuali.kpme.edo.api.reviewlayerdef;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.w3c.dom.Element;

@XmlRootElement(name = EdoReviewLayerDefinition.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = EdoReviewLayerDefinition.Constants.TYPE_NAME, propOrder = {
    EdoReviewLayerDefinition.Elements.WORKFLOW_QUALIFIER,
    EdoReviewLayerDefinition.Elements.WORKFLOW_ID,
    EdoReviewLayerDefinition.Elements.REVIEW_LEVEL,
    EdoReviewLayerDefinition.Elements.ROUTE_LEVEL,
    EdoReviewLayerDefinition.Elements.REVIEW_LETTER,
    EdoReviewLayerDefinition.Elements.DESCRIPTION,
    EdoReviewLayerDefinition.Elements.EDO_REVIEW_LAYER_DEFINITION_ID,
    EdoReviewLayerDefinition.Elements.SUPP_REVIEW_LAYER_DEFINITIONS,
    EdoReviewLayerDefinition.Elements.NODE_NAME,
    EdoReviewLayerDefinition.Elements.VOTE_TYPE,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    EdoReviewLayerDefinition.Elements.ACTIVE,
    EdoReviewLayerDefinition.Elements.ID,
    EdoReviewLayerDefinition.Elements.EFFECTIVE_LOCAL_DATE,
    EdoReviewLayerDefinition.Elements.CREATE_TIME,
    EdoReviewLayerDefinition.Elements.USER_PRINCIPAL_ID,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class EdoReviewLayerDefinition
    extends AbstractDataTransferObject
    implements EdoReviewLayerDefinitionContract
{

    @XmlElement(name = Elements.WORKFLOW_QUALIFIER, required = false)
    private final String workflowQualifier;
    @XmlElement(name = Elements.WORKFLOW_ID, required = false)
    private final String workflowId;
    @XmlElement(name = Elements.REVIEW_LEVEL, required = false)
    private final String reviewLevel;
    @XmlElement(name = Elements.ROUTE_LEVEL, required = false)
    private final String routeLevel;
    @XmlElement(name = Elements.REVIEW_LETTER, required = false)
    private final boolean reviewLetter;
    @XmlElement(name = Elements.DESCRIPTION, required = false)
    private final String description;
    @XmlElement(name = Elements.EDO_REVIEW_LAYER_DEFINITION_ID, required = false)
    private final String edoReviewLayerDefinitionId;
    @XmlElement(name = Elements.SUPP_REVIEW_LAYER_DEFINITIONS, required = false)
    private final List suppReviewLayerDefinitions;
    @XmlElement(name = Elements.NODE_NAME, required = false)
    private final String nodeName;
    @XmlElement(name = Elements.VOTE_TYPE, required = false)
    private final String voteType;
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
    @SuppressWarnings("unused")
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * Private constructor used only by JAXB.
     * 
     */
    private EdoReviewLayerDefinition() {
        this.workflowQualifier = null;
        this.workflowId = null;
        this.reviewLevel = null;
        this.routeLevel = null;
        this.reviewLetter = false;
        this.description = null;
        this.edoReviewLayerDefinitionId = null;
        this.suppReviewLayerDefinitions = null;
        this.nodeName = null;
        this.voteType = null;
        this.versionNumber = null;
        this.objectId = null;
        this.active = false;
        this.id = null;
        this.effectiveLocalDate = null;
        this.createTime = null;
        this.userPrincipalId = null;
    }

    private EdoReviewLayerDefinition(Builder builder) {
        this.workflowQualifier = builder.getWorkflowQualifier();
        this.workflowId = builder.getWorkflowId();
        this.reviewLevel = builder.getReviewLevel();
        this.routeLevel = builder.getRouteLevel();
        this.reviewLetter = builder.isReviewLetter();
        this.description = builder.getDescription();
        this.edoReviewLayerDefinitionId = builder.getEdoReviewLayerDefinitionId();
        this.suppReviewLayerDefinitions = builder.getSuppReviewLayerDefinitions();
        this.nodeName = builder.getNodeName();
        this.voteType = builder.getVoteType();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.active = builder.isActive();
        this.id = builder.getId();
        this.effectiveLocalDate = builder.getEffectiveLocalDate();
        this.createTime = builder.getCreateTime();
        this.userPrincipalId = builder.getUserPrincipalId();
    }

    @Override
    public String getWorkflowQualifier() {
        return this.workflowQualifier;
    }

    @Override
    public String getWorkflowId() {
        return this.workflowId;
    }

    @Override
    public String getReviewLevel() {
        return this.reviewLevel;
    }

    @Override
    public String getRouteLevel() {
        return this.routeLevel;
    }

    @Override
    public boolean isReviewLetter() {
        return this.reviewLetter;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getEdoReviewLayerDefinitionId() {
        return this.edoReviewLayerDefinitionId;
    }

    @Override
    public List getSuppReviewLayerDefinitions() {
        return this.suppReviewLayerDefinitions;
    }

    @Override
    public String getNodeName() {
        return this.nodeName;
    }

    @Override
    public String getVoteType() {
        return this.voteType;
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
     * A builder which can be used to construct {@link EdoReviewLayerDefinition} instances.  Enforces the constraints of the {@link EdoReviewLayerDefinitionContract}.
     * 
     */
    public final static class Builder
        implements Serializable, EdoReviewLayerDefinitionContract, ModelBuilder
    {

        private String workflowQualifier;
        private String workflowId;
        private String reviewLevel;
        private String routeLevel;
        private boolean reviewLetter;
        private String description;
        private String edoReviewLayerDefinitionId;
        private List suppReviewLayerDefinitions;
        private String nodeName;
        private String voteType;
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
        
        private Builder(String nodeName, String reviewLevel, String routeLevel, String workflowId) {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        	setNodeName(nodeName);
        	setReviewLevel(reviewLevel);
        	setRouteLevel(routeLevel);
        	setWorkflowId(workflowId);
        }

        public static Builder create(String nodeName, String reviewLevel, String routeLevel, String workflowId) {
            // TODO modify as needed to pass any required values and add them to the signature of the 'create' method
            return new Builder(nodeName, reviewLevel, routeLevel, workflowId);
        }

        public static Builder create(EdoReviewLayerDefinitionContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            // TODO if create() is modified to accept required parameters, this will need to be modified
            Builder builder = create();
            builder.setWorkflowQualifier(contract.getWorkflowQualifier());
            builder.setWorkflowId(contract.getWorkflowId());
            builder.setReviewLevel(contract.getReviewLevel());
            builder.setRouteLevel(contract.getRouteLevel());
            builder.setReviewLetter(contract.isReviewLetter());
            builder.setDescription(contract.getDescription());
            builder.setEdoReviewLayerDefinitionId(contract.getEdoReviewLayerDefinitionId());
            builder.setSuppReviewLayerDefinitions(contract.getSuppReviewLayerDefinitions());
            builder.setNodeName(contract.getNodeName());
            builder.setVoteType(contract.getVoteType());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
            builder.setActive(contract.isActive());
            builder.setId(contract.getId());
            builder.setEffectiveLocalDate(contract.getEffectiveLocalDate());
            builder.setCreateTime(contract.getCreateTime());
            builder.setUserPrincipalId(contract.getUserPrincipalId());
            return builder;
        }

        public EdoReviewLayerDefinition build() {
            return new EdoReviewLayerDefinition(this);
        }

        @Override
        public String getWorkflowQualifier() {
            return this.workflowQualifier;
        }

        @Override
        public String getWorkflowId() {
            return this.workflowId;
        }

        @Override
        public String getReviewLevel() {
            return this.reviewLevel;
        }

        @Override
        public String getRouteLevel() {
            return this.routeLevel;
        }

        @Override
        public boolean isReviewLetter() {
            return this.reviewLetter;
        }

        @Override
        public String getDescription() {
            return this.description;
        }

        @Override
        public String getEdoReviewLayerDefinitionId() {
            return this.edoReviewLayerDefinitionId;
        }

        @Override
        public List getSuppReviewLayerDefinitions() {
            return this.suppReviewLayerDefinitions;
        }

        @Override
        public String getNodeName() {
            return this.nodeName;
        }

        @Override
        public String getVoteType() {
            return this.voteType;
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

        public void setWorkflowQualifier(String workflowQualifier) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.workflowQualifier = workflowQualifier;
        }

        public void setWorkflowId(String workflowId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.workflowId = workflowId;
        }

        public void setReviewLevel(String reviewLevel) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.reviewLevel = reviewLevel;
        }

        public void setRouteLevel(String routeLevel) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.routeLevel = routeLevel;
        }

        public void setReviewLetter(boolean reviewLetter) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.reviewLetter = reviewLetter;
        }

        public void setDescription(String description) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.description = description;
        }

        public void setEdoReviewLayerDefinitionId(String edoReviewLayerDefinitionId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.edoReviewLayerDefinitionId = edoReviewLayerDefinitionId;
        }

        public void setSuppReviewLayerDefinitions(List suppReviewLayerDefinitions) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.suppReviewLayerDefinitions = suppReviewLayerDefinitions;
        }

        public void setNodeName(String nodeName) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.nodeName = nodeName;
        }

        public void setVoteType(String voteType) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.voteType = voteType;
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

        final static String ROOT_ELEMENT_NAME = "edoReviewLayerDefinition";
        final static String TYPE_NAME = "EdoReviewLayerDefinitionType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

        final static String WORKFLOW_QUALIFIER = "workflowQualifier";
        final static String WORKFLOW_ID = "workflowId";
        final static String REVIEW_LEVEL = "reviewLevel";
        final static String ROUTE_LEVEL = "routeLevel";
        final static String REVIEW_LETTER = "reviewLetter";
        final static String DESCRIPTION = "description";
        final static String EDO_REVIEW_LAYER_DEFINITION_ID = "edoReviewLayerDefinitionId";
        final static String SUPP_REVIEW_LAYER_DEFINITIONS = "suppReviewLayerDefinitions";
        final static String NODE_NAME = "nodeName";
        final static String VOTE_TYPE = "voteType";
        final static String ACTIVE = "active";
        final static String ID = "id";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
        final static String CREATE_TIME = "createTime";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";

    }

}

