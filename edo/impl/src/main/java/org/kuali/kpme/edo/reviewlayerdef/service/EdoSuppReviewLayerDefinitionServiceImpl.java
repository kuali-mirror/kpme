package org.kuali.kpme.edo.reviewlayerdef.service;

import java.util.List;

import org.kuali.kpme.edo.api.reviewlayerdef.EdoSuppReviewLayerDefinition;
import org.kuali.kpme.edo.reviewlayerdef.EdoSuppReviewLayerDefinitionBo;
import org.kuali.kpme.edo.reviewlayerdef.dao.EdoSuppReviewLayerDefinitionDao;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

public class EdoSuppReviewLayerDefinitionServiceImpl implements EdoSuppReviewLayerDefinitionService {

    private EdoSuppReviewLayerDefinitionDao edoSuppReviewLayerDefinitionDao;

    protected List<EdoSuppReviewLayerDefinition> convertToImmutable(List<EdoSuppReviewLayerDefinitionBo> bos) {
		return ModelObjectUtils.transform(bos, EdoSuppReviewLayerDefinitionBo.toImmutable);
	}

    public EdoSuppReviewLayerDefinitionDao getEdoSuppReviewLayerDefinitionDao() {
        return edoSuppReviewLayerDefinitionDao;
    }

    public void setEdoSuppReviewLayerDefinitionDao(EdoSuppReviewLayerDefinitionDao edoSuppReviewLayerDefinitionDao) {
        this.edoSuppReviewLayerDefinitionDao = edoSuppReviewLayerDefinitionDao;
    }
 
    public List<String> getAuthorizedSupplementalNodes(String edoReviewLayerDefinitionId) {
    
    	return this.edoSuppReviewLayerDefinitionDao.getAuthorizedSupplementalNodes(edoReviewLayerDefinitionId);	
    }
    
    public List<EdoSuppReviewLayerDefinition> getSuppReviewLayerDefinitions(String edoReviewLayerDefinitionId) {
    	List<EdoSuppReviewLayerDefinitionBo> bos = edoSuppReviewLayerDefinitionDao.getSuppReviewLayerDefinitions(edoReviewLayerDefinitionId);
    	return convertToImmutable(bos);
    } 

    public String getSuppLevelQualifierByWorkflowId(String edoWorkflowId, String nodeName) {
        return this.edoSuppReviewLayerDefinitionDao.getSuppLevelQualifierByWorkflowId(edoWorkflowId, nodeName);
    }

}
