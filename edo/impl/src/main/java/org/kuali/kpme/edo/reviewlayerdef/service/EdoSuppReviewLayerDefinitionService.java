package org.kuali.kpme.edo.reviewlayerdef.service;

import java.util.List;

import org.kuali.kpme.edo.api.reviewlayerdef.EdoSuppReviewLayerDefinition;

public interface EdoSuppReviewLayerDefinitionService {

	// KPME-3711 Moved from EdoReviewLayerDefinitionService Per Catherine's request
	
	// This method needs to return a list of EdoSuppReviewLayerDefinition objects per Maria's email (7/18)
    //public EdoSuppReviewLayerDefinition getSuppReviewLayerDefinition(String reviewLayerDefinitionId);
	public List<EdoSuppReviewLayerDefinition> getSuppReviewLayerDefinitions(String edoReviewLayerDefinitionId);
    public List<String> getAuthorizedSupplementalNodes(String edoReviewLayerDefinitionId);
    public String getSuppLevelQualifierByWorkflowId(String edoWorkflowId, String nodeName);
}
