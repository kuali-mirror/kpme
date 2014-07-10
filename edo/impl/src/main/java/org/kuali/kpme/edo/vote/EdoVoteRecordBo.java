package org.kuali.kpme.edo.vote;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.edo.api.vote.EdoVoteRecord;
import org.kuali.kpme.edo.api.vote.EdoVoteRecordContract;
import org.kuali.kpme.edo.reviewlayerdef.EdoReviewLayerDefinition;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.krad.util.ObjectUtils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class EdoVoteRecordBo extends HrBusinessObject implements EdoVoteRecordContract {
	
	private static final long serialVersionUID = 3095972270492358821L;


	static class KeyFields {
		private static final String EDO_DOSSIER_ID = "edoDossierID";
		private static final String EDO_REVIEW_LAYER_DEFINITION_ID = "edoReviewLayerDefinitionID";
		private static final String VOTE_TYPE = "voteType";
		private static final String VOTE_ROUND = "voteRound";
		private static final String VOTE_SUB_ROUND = "voteSubRound";
	}
	
    private String edoVoteRecordID;
    private String edoDossierID;
    private String edoReviewLayerDefinitionID;
    private String voteType;
    private String aoeCode;
    private String aoeDescription;
    
    private Integer yesCount;
    private Integer noCount;
    private Integer absentCount;
    private Integer abstainCount;
    
    private Integer voteRound;
    private Integer voteSubRound;
   
    private EdoReviewLayerDefinition reviewLayerDefinition;

    
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
				.put(KeyFields.EDO_DOSSIER_ID, this.getEdoDossierID())
				.put(KeyFields.EDO_REVIEW_LAYER_DEFINITION_ID, this.getEdoReviewLayerDefinitionID())
				.put(KeyFields.VOTE_TYPE, this.getVoteType())
				.put(KeyFields.VOTE_ROUND, this.getVoteRound())
				.put(KeyFields.VOTE_SUB_ROUND, this.getVoteSubRound())
				.build();
	}
    
	@Override
	public String getId() {
		return getEdoVoteRecordID();
	}

	@Override
	public void setId(String edoVoteRecordID) {
		setEdoVoteRecordID(edoVoteRecordID);
	}
	
	@Override
	protected String getUniqueKey() {
		return this.getEdoVoteRecordID();
	}
	
    public String getEdoVoteRecordID() {
		return edoVoteRecordID;
	}

	public void setEdoVoteRecordID(String edoVoteRecordID) {
		this.edoVoteRecordID = edoVoteRecordID;
	}

	public String getEdoDossierID() {
		return edoDossierID;
	}

	public void setEdoDossierID(String edoDossierID) {
		this.edoDossierID = edoDossierID;
	}

	public String getEdoReviewLayerDefinitionID() {
		return edoReviewLayerDefinitionID;
	}

	public void setEdoReviewLayerDefinitionID(String edoReviewLayerDefinitionID) {
		this.edoReviewLayerDefinitionID = edoReviewLayerDefinitionID;
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

	public EdoReviewLayerDefinition getReviewLayerDefinition() {
        if (ObjectUtils.isNull(reviewLayerDefinition) && edoReviewLayerDefinitionID != null) {
            String workflowID = EdoServiceLocator.getEdoDossierService().getEdoDossierById(this.edoDossierID.toString()).getWorkflowId();
            this.reviewLayerDefinition = EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinition(workflowID, edoReviewLayerDefinitionID);
        }
        return reviewLayerDefinition;
    }
    
   
	public static EdoVoteRecordBo from(EdoVoteRecord edoVoteRecord) {
        if (edoVoteRecord == null) {
            return null;
        }
        EdoVoteRecordBo edoVoteRecordBo = new EdoVoteRecordBo();
        
        edoVoteRecordBo.setEdoVoteRecordID(edoVoteRecord.getEdoVoteRecordID());
        
        edoVoteRecordBo.setEdoDossierID(edoVoteRecord.getEdoDossierID());
        edoVoteRecordBo.setEdoReviewLayerDefinitionID(edoVoteRecord.getEdoReviewLayerDefinitionID());
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
