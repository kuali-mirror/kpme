package org.kuali.kpme.edo.reviewlayerdef;

import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.edo.api.reviewlayerdef.EdoSuppReviewLayerDefinition;
import org.kuali.kpme.edo.api.reviewlayerdef.EdoSuppReviewLayerDefinitionContract;

import com.google.common.collect.ImmutableMap;

public class EdoSuppReviewLayerDefinitionBo extends HrBusinessObject implements EdoSuppReviewLayerDefinitionContract {
	
	private static final long serialVersionUID = -9121537077110752118L;
	
	static class KeyFields {
		private static final String SUPP_NODE_NAME = "suppNodeName";
		private static final String EDO_WORK_FLOW_ID = "edoWorkflowId";
	}
	
	private String edoSuppReviewLayerDefinitionId;
	private String edoReviewLayerDefinitionId;
	private String suppNodeName;
    private boolean acknowledgeFlag;
    private String edoWorkflowId;
    private String workflowQualifier;
    
    @Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
		return  new ImmutableMap.Builder<String, Object>()
				.put(KeyFields.SUPP_NODE_NAME, this.getSuppNodeName())
				.put(KeyFields.EDO_WORK_FLOW_ID, this.getEdoWorkflowId())
				.build();
	}
    
    //private EdoReviewLayerDefinitionBo reviewLayerDefinition;
    
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

    @Override
    protected String getUniqueKey() {
        return edoSuppReviewLayerDefinitionId;
    }

    @Override
    public String getId() {
        return getEdoSuppReviewLayerDefinitionId();
    }

    @Override
    public void setId(String id) {
        setEdoSuppReviewLayerDefinitionId(id);
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
        
        // finally copy over the common fields into phra from im
        copyCommonFields(edoSuppReviewLayerDefinitionBo, edoSuppReviewLayerDefinition);
     
        return edoSuppReviewLayerDefinitionBo;
    } 
    
    public static EdoSuppReviewLayerDefinition to(EdoSuppReviewLayerDefinitionBo bo) {
        if (bo == null) {
            return null;
        }
        return EdoSuppReviewLayerDefinition.Builder.create(bo).build();
    }

}
