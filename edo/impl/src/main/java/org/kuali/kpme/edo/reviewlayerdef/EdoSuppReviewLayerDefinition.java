package org.kuali.kpme.edo.reviewlayerdef;

import org.kuali.kpme.edo.EdoBusinessObject;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.rice.krad.util.ObjectUtils;

import java.math.BigDecimal;

public class EdoSuppReviewLayerDefinition extends EdoBusinessObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9121537077110752118L;
	private BigDecimal suppReviewLayerDefinitionId;
	private BigDecimal reviewLayerDefinitionId;
	private String suppNodeName;
    private boolean acknowledgeFlag;
    private String workflowId;
    private String workflowQualifier;
    
    private EdoReviewLayerDefinitionBo reviewLayerDefinition;
    
    public BigDecimal getSuppReviewLayerDefinitionId() {
		return suppReviewLayerDefinitionId;
	}

	public void setSuppReviewLayerDefinitionId(
			BigDecimal suppReviewLayerDefinitionId) {
		this.suppReviewLayerDefinitionId = suppReviewLayerDefinitionId;
	}
	

	public BigDecimal getReviewLayerDefinitionId() {
		return reviewLayerDefinitionId;
	}

	public void setReviewLayerDefinitionId(BigDecimal reviewLayerDefinitionId) {
		this.reviewLayerDefinitionId = reviewLayerDefinitionId;
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

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public String getWorkflowQualifier() {
        return workflowQualifier;
    }

    public void setWorkflowQualifier(String workflowQualifier) {
        this.workflowQualifier = workflowQualifier;
    }

    @Override
    protected BigDecimal getUniqueKey() {
        return suppReviewLayerDefinitionId;
    }

    @Override
    public BigDecimal getId() {
        return getSuppReviewLayerDefinitionId();
    }

    @Override
    public void setId(BigDecimal id) {
        setSuppReviewLayerDefinitionId(id);
    }
    
    /*As a general piece of information.  The BusinessObjectService should never be called from a Business Object.
    public EdoReviewLayerDefinitionBo getReviewLayerDefinition() {
        if (ObjectUtils.isNull(reviewLayerDefinition) && reviewLayerDefinitionId != null) {
            this.reviewLayerDefinition = EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinition(workflowId, reviewLayerDefinitionId);
        }
        return reviewLayerDefinition;
    }*/

}
