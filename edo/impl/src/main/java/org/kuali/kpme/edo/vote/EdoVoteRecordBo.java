package org.kuali.kpme.edo.vote;

import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.edo.api.vote.EdoVoteRecord;
import org.kuali.kpme.edo.api.vote.EdoVoteRecordContract;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class EdoVoteRecordBo extends PersistableBusinessObjectBase implements EdoVoteRecordContract {
	
	private static final long serialVersionUID = 3095972270492358821L;


	static class KeyFields {
		private static final String EDO_DOSSIER_ID = "edoDossierId";
		private static final String EDO_REVIEW_LAYER_DEFINITION_ID = "edoReviewLayerDefinitionId";
		private static final String VOTE_TYPE = "voteType";
		private static final String VOTE_ROUND = "voteRound";
		private static final String VOTE_SUB_ROUND = "voteSubRound";
	}
	
    private String edoVoteRecordId;
    private String edoDossierId;
    private String edoReviewLayerDefinitionId;
    private String voteType;
    private String aoeCode;
    private String aoeDescription;
    
    private Integer yesCount;
    private Integer noCount;
    private Integer absentCount;
    private Integer abstainCount;
    
    private Integer voteRound;
    private Integer voteSubRound;
    
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String createdBy;
   
    public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
    		.add(KeyFields.EDO_DOSSIER_ID)
    		.add(KeyFields.EDO_REVIEW_LAYER_DEFINITION_ID)
    		.add(KeyFields.VOTE_TYPE)
    		.add(KeyFields.VOTE_ROUND)
    		.add(KeyFields.VOTE_SUB_ROUND)
			.build();
	
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
		return  new ImmutableMap.Builder<String, Object>()
				.put(KeyFields.EDO_DOSSIER_ID, this.getEdoDossierId())
				.put(KeyFields.EDO_REVIEW_LAYER_DEFINITION_ID, this.getEdoReviewLayerDefinitionId())
				.put(KeyFields.VOTE_TYPE, this.getVoteType())
				.put(KeyFields.VOTE_ROUND, this.getVoteRound())
				.put(KeyFields.VOTE_SUB_ROUND, this.getVoteSubRound())
				.build();
	}
    
	public String getId() {
		return getEdoVoteRecordId();
	}
	
    public String getEdoVoteRecordId() {
		return edoVoteRecordId;
	}

	public void setEdoVoteRecordId(String edoVoteRecordId) {
		this.edoVoteRecordId = edoVoteRecordId;
	}

	public String getEdoDossierId() {
		return edoDossierId;
	}

	public void setEdoDossierId(String edoDossierId) {
		this.edoDossierId = edoDossierId;
	}

	public String getEdoReviewLayerDefinitionId() {
		return edoReviewLayerDefinitionId;
	}

	public void setEdoReviewLayerDefinitionId(String edoReviewLayerDefinitionId) {
		this.edoReviewLayerDefinitionId = edoReviewLayerDefinitionId;
	}

	public String getVoteType() {
        return voteType;
    }

    public void setVoteType(String voteType) {
        this.voteType = voteType;
    }
   
    public String getAoeCode() {
        return aoeCode;
    }

    public void setAoeCode(String aoeCode) {
        this.aoeCode = aoeCode;
    }

    public String getAoeDescription() {
        String aoeDescription = StringUtils.EMPTY;

        if (StringUtils.isNotEmpty(aoeCode)) {
            aoeDescription = EdoConstants.AREA_OF_EXCELLENCE.get(aoeCode);
        }

        return aoeDescription;
    }

    public Integer getVoteRound() {
        return voteRound;
    }

    public void setVoteRound(Integer voteRound) {
        this.voteRound = voteRound;
    }

    public Integer getVoteSubRound() {
        return voteSubRound;
    }

    public void setVoteSubRound(Integer voteSubRound) {
        this.voteSubRound = voteSubRound;
    }

    public Integer getYesCount() {
		return yesCount;
	}

	public void setYesCount(Integer yesCount) {
		this.yesCount = yesCount;
	}

	public Integer getNoCount() {
		return noCount;
	}

	public void setNoCount(Integer noCount) {
		this.noCount = noCount;
	}

	public Integer getAbsentCount() {
		return absentCount;
	}

	public void setAbsentCount(Integer absentCount) {
		this.absentCount = absentCount;
	}

	public Integer getAbstainCount() {
		return abstainCount;
	}

	public void setAbstainCount(Integer abstainCount) {
		this.abstainCount = abstainCount;
	}
   
	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public static EdoVoteRecordBo from(EdoVoteRecord edoVoteRecord) {
        if (edoVoteRecord == null) {
            return null;
        }
        EdoVoteRecordBo edoVoteRecordBo = new EdoVoteRecordBo();
        
        edoVoteRecordBo.setEdoVoteRecordId(edoVoteRecord.getEdoVoteRecordId());
        
        edoVoteRecordBo.setEdoDossierId(edoVoteRecord.getEdoDossierId());
        edoVoteRecordBo.setEdoReviewLayerDefinitionId(edoVoteRecord.getEdoReviewLayerDefinitionId());
        edoVoteRecordBo.setVoteType(edoVoteRecord.getVoteType());
        edoVoteRecordBo.setAoeCode(edoVoteRecord.getAoeCode());
        
        edoVoteRecordBo.setYesCount(edoVoteRecord.getYesCount());
        edoVoteRecordBo.setNoCount(edoVoteRecord.getNoCount());
       
        edoVoteRecordBo.setAbsentCount(edoVoteRecord.getAbsentCount());
        edoVoteRecordBo.setAbstainCount(edoVoteRecord.getAbstainCount());
        
        edoVoteRecordBo.setVoteRound(edoVoteRecord.getVoteRound());
        edoVoteRecordBo.setVoteSubRound(edoVoteRecord.getVoteSubRound());

        edoVoteRecordBo.setCreatedAt(edoVoteRecord.getCreatedAt());
        edoVoteRecordBo.setUpdatedAt(edoVoteRecord.getUpdatedAt());
        edoVoteRecordBo.setCreatedBy(edoVoteRecord.getCreatedBy());
        
        edoVoteRecordBo.setVersionNumber(edoVoteRecord.getVersionNumber());
        edoVoteRecordBo.setObjectId(edoVoteRecord.getObjectId()); 
        
        // finally copy over the common fields into phra from im
        //copyCommonFields(edoVoteRecordBo, edoVoteRecord);
     
        return edoVoteRecordBo;
    } 
    
    public static EdoVoteRecord to(EdoVoteRecordBo bo) {
        if (bo == null) {
            return null;
        }
        return EdoVoteRecord.Builder.create(bo).build();
    }
    
    public static final ModelObjectUtils.Transformer<EdoVoteRecordBo, EdoVoteRecord> toImmutable = new ModelObjectUtils.Transformer<EdoVoteRecordBo, EdoVoteRecord>() {
        public EdoVoteRecord transform(EdoVoteRecordBo input) {
            return EdoVoteRecordBo.to(input);
        };
    };
            
    public static final ModelObjectUtils.Transformer<EdoVoteRecord, EdoVoteRecordBo> toBo = new ModelObjectUtils.Transformer<EdoVoteRecord, EdoVoteRecordBo>() {
        public EdoVoteRecordBo transform(EdoVoteRecord input) {
            return EdoVoteRecordBo.from(input);
        };
    };
    
}
