package org.kuali.kpme.edo.vote;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.edo.api.vote.EdoVoteRecord;
import org.kuali.kpme.edo.api.vote.EdoVoteRecordContract;
import org.kuali.kpme.edo.reviewlayerdef.EdoReviewLayerDefinitionBo;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.krad.util.ObjectUtils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class EdoVoteRecordBo extends HrBusinessObject implements EdoVoteRecordContract {
	
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
   
    private EdoReviewLayerDefinitionBo reviewLayerDefinition;

    
    public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
    		.add(KeyFields.EDO_DOSSIER_ID)
    		.add(KeyFields.EDO_REVIEW_LAYER_DEFINITION_ID)
    		.add(KeyFields.VOTE_TYPE)
    		.add(KeyFields.VOTE_ROUND)
    		.add(KeyFields.VOTE_SUB_ROUND)
			.build();
	
    @Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
		return  new ImmutableMap.Builder<String, Object>()
				.put(KeyFields.EDO_DOSSIER_ID, this.getEdoDossierId())
				.put(KeyFields.EDO_REVIEW_LAYER_DEFINITION_ID, this.getEdoReviewLayerDefinitionId())
				.put(KeyFields.VOTE_TYPE, this.getVoteType())
				.put(KeyFields.VOTE_ROUND, this.getVoteRound())
				.put(KeyFields.VOTE_SUB_ROUND, this.getVoteSubRound())
				.build();
	}
    
	@Override
	public String getId() {
		return getEdoVoteRecordId();
	}

	@Override
	public void setId(String edoVoteRecordId) {
		setEdoVoteRecordId(edoVoteRecordId);
	}
	
	@Override
	protected String getUniqueKey() {
		return this.getEdoVoteRecordId();
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

	/*
	public EdoReviewLayerDefinitionBo getReviewLayerDefinition() {
        if (ObjectUtils.isNull(reviewLayerDefinition) && edoReviewLayerDefinitionId != null) {
            String workflowID = EdoServiceLocator.getEdoDossierService().getEdoDossierById(this.edoDossierId.toString()).getWorkflowId();
            this.reviewLayerDefinition = EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinition(workflowID, edoReviewLayerDefinitionId);
        }
        return reviewLayerDefinition;
    }*/
    
   
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

        // finally copy over the common fields into phra from im
        copyCommonFields(edoVoteRecordBo, edoVoteRecord);
     
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
