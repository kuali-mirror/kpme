package org.kuali.kpme.edo.reviewlayerdef.service;

import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.HrBusinessObjectMaintainableImpl;
import org.kuali.kpme.edo.reviewlayerdef.EdoReviewLayerDefinitionBo;
import org.kuali.kpme.edo.reviewlayerdef.EdoSuppReviewLayerDefinitionBo;
import org.kuali.kpme.edo.service.EdoServiceLocator;

public class EdoReviewLayerDefinitionMaintainableImpl extends HrBusinessObjectMaintainableImpl  { //EdoBusinessObjectMaintainableImpl

    private static final long serialVersionUID = 1L;
  
    @Override
	public HrBusinessObject getObjectById(String id) {
		return EdoReviewLayerDefinitionBo.from(EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinitionById(id));
	}
    
    @Override
	public void customSaveLogic(HrBusinessObject hrObj){
    	EdoReviewLayerDefinitionBo aReviewLayerDef = (EdoReviewLayerDefinitionBo) hrObj;
		for(EdoSuppReviewLayerDefinitionBo suppReviewLayerDefinition : aReviewLayerDef.getSuppReviewLayerDefinitions()) {
			suppReviewLayerDefinition.setEdoReviewLayerDefinitionId(aReviewLayerDef.getEdoReviewLayerDefinitionId());
			suppReviewLayerDefinition.setEdoSuppReviewLayerDefinitionId(null);
			suppReviewLayerDefinition.setEdoWorkflowId(aReviewLayerDef.getWorkflowId());
			suppReviewLayerDefinition.setWorkflowQualifier(aReviewLayerDef.getWorkflowQualifier());
		}
    }
}
