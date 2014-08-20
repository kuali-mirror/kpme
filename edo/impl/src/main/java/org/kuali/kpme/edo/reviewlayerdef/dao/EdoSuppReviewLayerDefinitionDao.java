package org.kuali.kpme.edo.reviewlayerdef.dao;

import java.util.List;

import org.kuali.kpme.edo.reviewlayerdef.EdoSuppReviewLayerDefinitionBo;

public interface EdoSuppReviewLayerDefinitionDao {

	// KPME-3711 Moved from EdoReviewLayerDefinitionDao
    public List<String> getAuthorizedSupplementalNodes(String edoReviewLayerDefinitionId);
    public List<EdoSuppReviewLayerDefinitionBo> getSuppReviewLayerDefinitions(String edoReviewLayerDefinitionId);
    public String getSuppLevelQualifierByWorkflowId(String edoWorkflowId, String nodeName);
}
