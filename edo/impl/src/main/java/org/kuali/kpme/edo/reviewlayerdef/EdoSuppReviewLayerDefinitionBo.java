package org.kuali.kpme.edo.reviewlayerdef;

import org.kuali.kpme.edo.api.reviewlayerdef.EdoSuppReviewLayerDefinition;
import org.kuali.kpme.edo.api.reviewlayerdef.EdoSuppReviewLayerDefinitionContract;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class EdoSuppReviewLayerDefinitionBo extends PersistableBusinessObjectBase implements EdoSuppReviewLayerDefinitionContract {
	
	private static final long serialVersionUID = -9121537077110752118L;
	
	static class KeyFields {
		private static final String EDO_REVIEW_LAYER_DEFINITION_ID = "edoReviewLayerDefinitionId";
		private static final String SUPP_NODE_NAME = "suppNodeName";
	}
	
	private String edoSuppReviewLayerDefinitionId;
	private String edoReviewLayerDefinitionId;
	private String suppNodeName;
    private boolean acknowledgeFlag;
    private String edoWorkflowId;
    private String workflowQualifier;

    //private EdoReviewLayerDefinitionBo reviewLayerDefinition;
    
    public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
			.add(KeyFields.EDO_REVIEW_LAYER_DEFINITION_ID)
			.add(KeyFields.SUPP_NODE_NAME)
			.build();
    
    public String getEdoSuppReviewLayerDefinitionId() {
		return edoSuppReviewLayerDefinitionId;
	}

	public void setEdoSuppReviewLayerDefinitionId(String edoSuppReviewLayerDefinitionId) {
		this.edoSuppReviewLayerDefinitionId = edoSuppReviewLayerDefinitionId;
	}

	public String getEdoReviewLayerDefinitionId() {
		return edoReviewLayerDefinitionId;
	}

	public void setEdoReviewLayerDefinitionId(String edoReviewLayerDefinitionId) {
		this.edoReviewLayerDefinitionId = edoReviewLayerDefinitionId;
	}

	public String getSuppNodeName() {
		return suppNodeName;
	}

	public void setSuppNodeName(String suppNodeName) {
		this.suppNodeName = suppNodeName;
	}

	public boolean isAcknowledgeFlag() {
		return acknowledgeFlag;
	}

	public void setAcknowledgeFlag(boolean acknowledgeFlag) {
		this.acknowledgeFlag = acknowledgeFlag;
	}

    public String getEdoWorkflowId() {
        return edoWorkflowId;
    }

    public void setEdoWorkflowId(String edoWorkflowId) {
        this.edoWorkflowId = edoWorkflowId;
    }

    public String getWorkflowQualifier() {
        return workflowQualifier;
    }

    public void setWorkflowQualifier(String workflowQualifier) {
        this.workflowQualifier = workflowQualifier;
    }

    public String getId() {
        return getEdoSuppReviewLayerDefinitionId();
    }

    public void setId(String id) {
        setEdoSuppReviewLayerDefinitionId(id);
    }
    
    public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
		return  new ImmutableMap.Builder<String, Object>()
				.put(KeyFields.EDO_REVIEW_LAYER_DEFINITION_ID, this.getEdoReviewLayerDefinitionId())
				.put(KeyFields.SUPP_NODE_NAME, this.getSuppNodeName())
				.build();
	}
 
    /*As a general piece of information.  The BusinessObjectService should never be called from a Business Object.
    public EdoReviewLayerDefinitionBo getReviewLayerDefinition() {
        if (ObjectUtils.isNull(reviewLayerDefinition) && reviewLayerDefinitionId != null) {
            this.reviewLayerDefinition = EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinition(workflowId, reviewLayerDefinitionId);
        }
        return reviewLayerDefinition;
    }*/
    
    public static EdoSuppReviewLayerDefinitionBo from(EdoSuppReviewLayerDefinition edoSuppReviewLayerDefinition) {
        if (edoSuppReviewLayerDefinition == null) {
            return null;
        }
        EdoSuppReviewLayerDefinitionBo edoSuppReviewLayerDefinitionBo = new EdoSuppReviewLayerDefinitionBo();
        edoSuppReviewLayerDefinitionBo.setEdoSuppReviewLayerDefinitionId(edoSuppReviewLayerDefinition.getEdoSuppReviewLayerDefinitionId());
        edoSuppReviewLayerDefinitionBo.setEdoReviewLayerDefinitionId(edoSuppReviewLayerDefinition.getEdoReviewLayerDefinitionId());
        edoSuppReviewLayerDefinitionBo.setSuppNodeName(edoSuppReviewLayerDefinition.getSuppNodeName());
        edoSuppReviewLayerDefinitionBo.setAcknowledgeFlag(edoSuppReviewLayerDefinition.isAcknowledgeFlag());
        edoSuppReviewLayerDefinitionBo.setEdoWorkflowId(edoSuppReviewLayerDefinition.getEdoWorkflowId());
        edoSuppReviewLayerDefinitionBo.setWorkflowQualifier(edoSuppReviewLayerDefinition.getWorkflowQualifier());
        edoSuppReviewLayerDefinitionBo.setVersionNumber(edoSuppReviewLayerDefinition.getVersionNumber());
        edoSuppReviewLayerDefinitionBo.setObjectId(edoSuppReviewLayerDefinition.getObjectId());
      
        return edoSuppReviewLayerDefinitionBo;
    } 
    
    public static EdoSuppReviewLayerDefinition to(EdoSuppReviewLayerDefinitionBo bo) {
        if (bo == null) {
            return null;
        }
        return EdoSuppReviewLayerDefinition.Builder.create(bo).build();
    }
    
    public static final ModelObjectUtils.Transformer<EdoSuppReviewLayerDefinitionBo, EdoSuppReviewLayerDefinition> toImmutable = new ModelObjectUtils.Transformer<EdoSuppReviewLayerDefinitionBo, EdoSuppReviewLayerDefinition>() {
        public EdoSuppReviewLayerDefinition transform(EdoSuppReviewLayerDefinitionBo input) {
            return EdoSuppReviewLayerDefinitionBo.to(input);
        };
    };
            
    public static final ModelObjectUtils.Transformer<EdoSuppReviewLayerDefinition, EdoSuppReviewLayerDefinitionBo> toBo = new ModelObjectUtils.Transformer<EdoSuppReviewLayerDefinition, EdoSuppReviewLayerDefinitionBo>() {
        public EdoSuppReviewLayerDefinitionBo transform(EdoSuppReviewLayerDefinition input) {
            return EdoSuppReviewLayerDefinitionBo.from(input);
        };
    };

}
