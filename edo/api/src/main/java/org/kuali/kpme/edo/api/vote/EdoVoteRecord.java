
package org.kuali.kpme.edo.api.vote;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.edo.api.dossier.EdoDossier.Builder;
import org.kuali.rice.core.api.CoreConstants;
import org.kuali.rice.core.api.mo.AbstractDataTransferObject;
import org.kuali.rice.core.api.mo.ModelBuilder;
import org.w3c.dom.Element;

@XmlRootElement(name = EdoVoteRecord.Constants.ROOT_ELEMENT_NAME)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = EdoVoteRecord.Constants.TYPE_NAME, propOrder = {
    EdoVoteRecord.Elements.VOTE_SUB_ROUND,
    EdoVoteRecord.Elements.ABSTAIN_COUNT,
    EdoVoteRecord.Elements.EDO_VOTE_RECORD_ID,
    EdoVoteRecord.Elements.EDO_DOSSIER_ID,
    EdoVoteRecord.Elements.ABSENT_COUNT,
    EdoVoteRecord.Elements.EDO_REVIEW_LAYER_DEFINITION_ID,
    EdoVoteRecord.Elements.NO_COUNT,
    EdoVoteRecord.Elements.VOTE_ROUND,
    EdoVoteRecord.Elements.YES_COUNT,
    EdoVoteRecord.Elements.AOE_CODE,
    EdoVoteRecord.Elements.VOTE_TYPE,
    EdoVoteRecord.Elements.CREATED_AT,
    EdoVoteRecord.Elements.UPDATED_AT,
    EdoVoteRecord.Elements.CREATED_BY,
    
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    CoreConstants.CommonElements.FUTURE_ELEMENTS
})
public final class EdoVoteRecord
    extends AbstractDataTransferObject
    implements EdoVoteRecordContract
{

    @XmlElement(name = Elements.VOTE_SUB_ROUND, required = false)
    private final Integer voteSubRound;
    @XmlElement(name = Elements.ABSTAIN_COUNT, required = false)
    private final Integer abstainCount;
    @XmlElement(name = Elements.EDO_VOTE_RECORD_ID, required = false)
    private final String edoVoteRecordId;
    @XmlElement(name = Elements.EDO_DOSSIER_ID, required = false)
    private final String edoDossierId;
    @XmlElement(name = Elements.ABSENT_COUNT, required = false)
    private final Integer absentCount;
    @XmlElement(name = Elements.EDO_REVIEW_LAYER_DEFINITION_ID, required = false)
    private final String edoReviewLayerDefinitionId;
    @XmlElement(name = Elements.NO_COUNT, required = false)
    private final Integer noCount;
    @XmlElement(name = Elements.VOTE_ROUND, required = false)
    private final Integer voteRound;
    @XmlElement(name = Elements.YES_COUNT, required = false)
    private final Integer yesCount;
    @XmlElement(name = Elements.AOE_CODE, required = false)
    private final String aoeCode;
    @XmlElement(name = Elements.VOTE_TYPE, required = false)
    private final String voteType;
    @XmlElement(name = CoreConstants.CommonElements.VERSION_NUMBER, required = false)
    private final Long versionNumber;
    @XmlElement(name = CoreConstants.CommonElements.OBJECT_ID, required = false)
    private final String objectId;
  
    @XmlElement(name = Elements.CREATED_AT, required = false)
    private final Timestamp createdAt;
    @XmlElement(name = Elements.UPDATED_AT, required = false)
    private final Timestamp updatedAt;
    @XmlElement(name = Elements.CREATED_BY, required = false)
    private final String createdBy;
    @SuppressWarnings("unused")
    @XmlAnyElement
    private final Collection<Element> _futureElements = null;

    /**
     * Private constructor used only by JAXB.
     * 
     */
    private EdoVoteRecord() {
        this.voteSubRound = null;
        this.abstainCount = null;
        this.edoVoteRecordId = null;
        this.edoDossierId = null;
        this.absentCount = null;
        this.edoReviewLayerDefinitionId = null;
        this.noCount = null;
        this.voteRound = null;
        this.yesCount = null;
        this.aoeCode = null;
        this.voteType = null;
        this.versionNumber = null;
        this.objectId = null;
        this.createdAt = null;
        this.updatedAt = null;
        this.createdBy = null;
    }

    private EdoVoteRecord(Builder builder) {
        this.voteSubRound = builder.getVoteSubRound();
        this.abstainCount = builder.getAbstainCount();
        this.edoVoteRecordId = builder.getEdoVoteRecordId();
        this.edoDossierId = builder.getEdoDossierId();
        this.absentCount = builder.getAbsentCount();
        this.edoReviewLayerDefinitionId = builder.getEdoReviewLayerDefinitionId();
        this.noCount = builder.getNoCount();
        this.voteRound = builder.getVoteRound();
        this.yesCount = builder.getYesCount();
        this.aoeCode = builder.getAoeCode();
        this.voteType = builder.getVoteType();
        this.versionNumber = builder.getVersionNumber();
        this.objectId = builder.getObjectId();
        this.createdAt = builder.getCreatedAt();
        this.updatedAt = builder.getUpdatedAt();
        this.createdBy = builder.getCreatedBy();
    }

    @Override
    public Integer getVoteSubRound() {
        return this.voteSubRound;
    }

    @Override
    public Integer getAbstainCount() {
        return this.abstainCount;
    }

    @Override
    public String getEdoVoteRecordId() {
        return this.edoVoteRecordId;
    }

    @Override
    public String getEdoDossierId() {
        return this.edoDossierId;
    }

    @Override
    public Integer getAbsentCount() {
        return this.absentCount;
    }

    @Override
    public String getEdoReviewLayerDefinitionId() {
        return this.edoReviewLayerDefinitionId;
    }

    @Override
    public Integer getNoCount() {
        return this.noCount;
    }

    @Override
    public Integer getVoteRound() {
        return this.voteRound;
    }

    @Override
    public Integer getYesCount() {
        return this.yesCount;
    }

    @Override
    public String getAoeCode() {
        return this.aoeCode;
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
    public Timestamp getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public Timestamp getUpdatedAt() {
        return this.updatedAt;
    }

    @Override
    public String getCreatedBy() {
        return this.createdBy;
    }


    /**
     * A builder which can be used to construct {@link EdoVoteRecord} instances.  Enforces the constraints of the {@link EdoVoteRecordContract}.
     * 
     */
    public final static class Builder
        implements Serializable, EdoVoteRecordContract, ModelBuilder
    {

        private Integer voteSubRound;
        private Integer abstainCount;
        private String edoVoteRecordId;
        private String edoDossierId;
        private Integer absentCount;
        private String edoReviewLayerDefinitionId;
        private Integer noCount;
        private Integer voteRound;
        private Integer yesCount;
        private String aoeCode;
        private String voteType;
        private Long versionNumber;
        private String objectId;
        
        private Timestamp updatedAt;
        private Timestamp createdAt;
        private String createdBy;

        private Builder() {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        }

        public static Builder create() {
            // TODO modify as needed to pass any required values and add them to the signature of the 'create' method
            return new Builder();
        }

        private Builder(String edoDossierId, String edoReviewLayerDefinitionId, String voteType, Integer voteRound, Integer voteSubRound) {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        	setEdoDossierId(edoDossierId);
        	setEdoReviewLayerDefinitionId(edoReviewLayerDefinitionId);
        	setVoteType(voteType);
        	setVoteRound(voteRound);
        	setVoteSubRound(voteSubRound);
        }

        public static Builder create(String edoDossierId, String edoReviewLayerDefinitionId, String voteType, Integer voteRound, Integer voteSubRound) {
            // TODO modify as needed to pass any required values and add them to the signature of the 'create' method
            return new Builder(edoDossierId, edoReviewLayerDefinitionId, voteType, voteRound, voteSubRound);
        }
        
        
        public static Builder create(EdoVoteRecordContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            // TODO if create() is modified to accept required parameters, this will need to be modified
            Builder builder = create();
            builder.setVoteSubRound(contract.getVoteSubRound());
            builder.setAbstainCount(contract.getAbstainCount());
            builder.setEdoVoteRecordId(contract.getEdoVoteRecordId());
            builder.setEdoDossierId(contract.getEdoDossierId());
            builder.setAbsentCount(contract.getAbsentCount());
            builder.setEdoReviewLayerDefinitionId(contract.getEdoReviewLayerDefinitionId());
            builder.setNoCount(contract.getNoCount());
            builder.setVoteRound(contract.getVoteRound());
            builder.setYesCount(contract.getYesCount());
            builder.setAoeCode(contract.getAoeCode());
            builder.setVoteType(contract.getVoteType());
            builder.setVersionNumber(contract.getVersionNumber());
            builder.setObjectId(contract.getObjectId());
           
            builder.setCreatedAt(contract.getCreatedAt());
            builder.setUpdatedAt(contract.getUpdatedAt());
            builder.setCreatedBy(contract.getCreatedBy());
            return builder;
        }

        public EdoVoteRecord build() {
            return new EdoVoteRecord(this);
        }

        @Override
        public Integer getVoteSubRound() {
            return this.voteSubRound;
        }

        @Override
        public Integer getAbstainCount() {
            return this.abstainCount;
        }

        @Override
        public String getEdoVoteRecordId() {
            return this.edoVoteRecordId;
        }

        @Override
        public String getEdoDossierId() {
            return this.edoDossierId;
        }

        @Override
        public Integer getAbsentCount() {
            return this.absentCount;
        }

        @Override
        public String getEdoReviewLayerDefinitionId() {
            return this.edoReviewLayerDefinitionId;
        }

        @Override
        public Integer getNoCount() {
            return this.noCount;
        }

        @Override
        public Integer getVoteRound() {
            return this.voteRound;
        }

        @Override
        public Integer getYesCount() {
            return this.yesCount;
        }

        @Override
        public String getAoeCode() {
            return this.aoeCode;
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
        public Timestamp getCreatedAt() {
            return this.createdAt;
        }

        @Override
        public Timestamp getUpdatedAt() {
            return this.updatedAt;
        }

        @Override
        public String getCreatedBy() {
            return this.createdBy;
        }


        public void setVoteSubRound(Integer voteSubRound) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.voteSubRound = voteSubRound;
        }

        public void setAbstainCount(Integer abstainCount) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.abstainCount = abstainCount;
        }

        public void setEdoVoteRecordId(String edoVoteRecordId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.edoVoteRecordId = edoVoteRecordId;
        }

        public void setEdoDossierId(String edoDossierId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.edoDossierId = edoDossierId;
        }

        public void setAbsentCount(Integer absentCount) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.absentCount = absentCount;
        }

        public void setEdoReviewLayerDefinitionId(String edoReviewLayerDefinitionId) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.edoReviewLayerDefinitionId = edoReviewLayerDefinitionId;
        }

        public void setNoCount(Integer noCount) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.noCount = noCount;
        }

        public void setVoteRound(Integer voteRound) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.voteRound = voteRound;
        }

        public void setYesCount(Integer yesCount) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.yesCount = yesCount;
        }

        public void setAoeCode(String aoeCode) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.aoeCode = aoeCode;
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

        public void setCreatedAt(Timestamp createdAt) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.createdAt = createdAt;
        }

        public void setUpdatedAt(Timestamp updatedAt) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.updatedAt = updatedAt;
        }

        public void setCreatedBy(String createdBy) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.createdBy = createdBy;
        }

    }


    /**
     * Defines some internal constants used on this class.
     * 
     */
    static class Constants {

        final static String ROOT_ELEMENT_NAME = "edoVoteRecord";
        final static String TYPE_NAME = "EdoVoteRecordType";

    }


    /**
     * A private class which exposes constants which define the XML element names to use when this object is marshalled to XML.
     * 
     */
    static class Elements {

        final static String VOTE_SUB_ROUND = "voteSubRound";
        final static String ABSTAIN_COUNT = "abstainCount";
        final static String EDO_VOTE_RECORD_ID = "edoVoteRecordId";
        final static String EDO_DOSSIER_ID = "edoDossierId";
        final static String ABSENT_COUNT = "absentCount";
        final static String EDO_REVIEW_LAYER_DEFINITION_ID = "edoReviewLayerDefinitionId";
        final static String NO_COUNT = "noCount";
        final static String VOTE_ROUND = "voteRound";
        final static String YES_COUNT = "yesCount";
        final static String AOE_CODE = "aoeCode";
        final static String VOTE_TYPE = "voteType";
        final static String CREATED_AT = "createdAt";
        final static String UPDATED_AT = "updatedAt";
        final static String CREATED_BY = "createdBy";

    }

}

