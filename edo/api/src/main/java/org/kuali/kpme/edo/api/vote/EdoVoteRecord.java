
package org.kuali.kpme.edo.api.vote;

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
    EdoVoteRecord.Elements.EDO_VOTE_RECORD_I_D,
    EdoVoteRecord.Elements.EDO_DOSSIER_I_D,
    EdoVoteRecord.Elements.ABSENT_COUNT,
    EdoVoteRecord.Elements.EDO_REVIEW_LAYER_DEFINITION_I_D,
    EdoVoteRecord.Elements.NO_COUNT,
    EdoVoteRecord.Elements.VOTE_ROUND,
    EdoVoteRecord.Elements.YES_COUNT,
    EdoVoteRecord.Elements.AOE_CODE,
    EdoVoteRecord.Elements.VOTE_TYPE,
    CoreConstants.CommonElements.VERSION_NUMBER,
    CoreConstants.CommonElements.OBJECT_ID,
    EdoVoteRecord.Elements.ACTIVE,
    EdoVoteRecord.Elements.ID,
    EdoVoteRecord.Elements.EFFECTIVE_LOCAL_DATE,
    EdoVoteRecord.Elements.CREATE_TIME,
    EdoVoteRecord.Elements.USER_PRINCIPAL_ID,
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
    @XmlElement(name = Elements.EDO_VOTE_RECORD_I_D, required = false)
    private final String edoVoteRecordID;
    @XmlElement(name = Elements.EDO_DOSSIER_I_D, required = false)
    private final String edoDossierID;
    @XmlElement(name = Elements.ABSENT_COUNT, required = false)
    private final Integer absentCount;
    @XmlElement(name = Elements.EDO_REVIEW_LAYER_DEFINITION_I_D, required = false)
    private final String edoReviewLayerDefinitionID;
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
    private EdoVoteRecord() {
        this.voteSubRound = null;
        this.abstainCount = null;
        this.edoVoteRecordID = null;
        this.edoDossierID = null;
        this.absentCount = null;
        this.edoReviewLayerDefinitionID = null;
        this.noCount = null;
        this.voteRound = null;
        this.yesCount = null;
        this.aoeCode = null;
        this.voteType = null;
        this.versionNumber = null;
        this.objectId = null;
        this.active = false;
        this.id = null;
        this.effectiveLocalDate = null;
        this.createTime = null;
        this.userPrincipalId = null;
    }

    private EdoVoteRecord(Builder builder) {
        this.voteSubRound = builder.getVoteSubRound();
        this.abstainCount = builder.getAbstainCount();
        this.edoVoteRecordID = builder.getEdoVoteRecordID();
        this.edoDossierID = builder.getEdoDossierID();
        this.absentCount = builder.getAbsentCount();
        this.edoReviewLayerDefinitionID = builder.getEdoReviewLayerDefinitionID();
        this.noCount = builder.getNoCount();
        this.voteRound = builder.getVoteRound();
        this.yesCount = builder.getYesCount();
        this.aoeCode = builder.getAoeCode();
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
    public Integer getVoteSubRound() {
        return this.voteSubRound;
    }

    @Override
    public Integer getAbstainCount() {
        return this.abstainCount;
    }

    @Override
    public String getEdoVoteRecordID() {
        return this.edoVoteRecordID;
    }

    @Override
    public String getEdoDossierID() {
        return this.edoDossierID;
    }

    @Override
    public Integer getAbsentCount() {
        return this.absentCount;
    }

    @Override
    public String getEdoReviewLayerDefinitionID() {
        return this.edoReviewLayerDefinitionID;
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
     * A builder which can be used to construct {@link EdoVoteRecord} instances.  Enforces the constraints of the {@link EdoVoteRecordContract}.
     * 
     */
    public final static class Builder
        implements Serializable, EdoVoteRecordContract, ModelBuilder
    {

        private Integer voteSubRound;
        private Integer abstainCount;
        private String edoVoteRecordID;
        private String edoDossierID;
        private Integer absentCount;
        private String edoReviewLayerDefinitionID;
        private Integer noCount;
        private Integer voteRound;
        private Integer yesCount;
        private String aoeCode;
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

        private Builder(String edoDossierID, String edoReviewLayerDefinitionID, String voteType, Integer voteRound, Integer voteSubRound) {
            // TODO modify this constructor as needed to pass any required values and invoke the appropriate 'setter' methods
        	setEdoDossierID(edoDossierID);
        	setEdoReviewLayerDefinitionID(edoReviewLayerDefinitionID);
        	setVoteType(voteType);
        	setVoteRound(voteRound);
        	setVoteSubRound(voteSubRound);
        }

        public static Builder create(String edoDossierID, String edoReviewLayerDefinitionID, String voteType, Integer voteRound, Integer voteSubRound) {
            // TODO modify as needed to pass any required values and add them to the signature of the 'create' method
            return new Builder(edoDossierID, edoReviewLayerDefinitionID, voteType, voteRound, voteSubRound);
        }
        
        
        public static Builder create(EdoVoteRecordContract contract) {
            if (contract == null) {
                throw new IllegalArgumentException("contract was null");
            }
            // TODO if create() is modified to accept required parameters, this will need to be modified
            Builder builder = create();
            builder.setVoteSubRound(contract.getVoteSubRound());
            builder.setAbstainCount(contract.getAbstainCount());
            builder.setEdoVoteRecordID(contract.getEdoVoteRecordID());
            builder.setEdoDossierID(contract.getEdoDossierID());
            builder.setAbsentCount(contract.getAbsentCount());
            builder.setEdoReviewLayerDefinitionID(contract.getEdoReviewLayerDefinitionID());
            builder.setNoCount(contract.getNoCount());
            builder.setVoteRound(contract.getVoteRound());
            builder.setYesCount(contract.getYesCount());
            builder.setAoeCode(contract.getAoeCode());
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
        public String getEdoVoteRecordID() {
            return this.edoVoteRecordID;
        }

        @Override
        public String getEdoDossierID() {
            return this.edoDossierID;
        }

        @Override
        public Integer getAbsentCount() {
            return this.absentCount;
        }

        @Override
        public String getEdoReviewLayerDefinitionID() {
            return this.edoReviewLayerDefinitionID;
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

        public void setVoteSubRound(Integer voteSubRound) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.voteSubRound = voteSubRound;
        }

        public void setAbstainCount(Integer abstainCount) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.abstainCount = abstainCount;
        }

        public void setEdoVoteRecordID(String edoVoteRecordID) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.edoVoteRecordID = edoVoteRecordID;
        }

        public void setEdoDossierID(String edoDossierID) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.edoDossierID = edoDossierID;
        }

        public void setAbsentCount(Integer absentCount) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.absentCount = absentCount;
        }

        public void setEdoReviewLayerDefinitionID(String edoReviewLayerDefinitionID) {
            // TODO add validation of input value if required and throw IllegalArgumentException if needed
            this.edoReviewLayerDefinitionID = edoReviewLayerDefinitionID;
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
        final static String EDO_VOTE_RECORD_I_D = "edoVoteRecordID";
        final static String EDO_DOSSIER_I_D = "edoDossierID";
        final static String ABSENT_COUNT = "absentCount";
        final static String EDO_REVIEW_LAYER_DEFINITION_I_D = "edoReviewLayerDefinitionID";
        final static String NO_COUNT = "noCount";
        final static String VOTE_ROUND = "voteRound";
        final static String YES_COUNT = "yesCount";
        final static String AOE_CODE = "aoeCode";
        final static String VOTE_TYPE = "voteType";
        final static String ACTIVE = "active";
        final static String ID = "id";
        final static String EFFECTIVE_LOCAL_DATE = "effectiveLocalDate";
        final static String CREATE_TIME = "createTime";
        final static String USER_PRINCIPAL_ID = "userPrincipalId";

    }

}

