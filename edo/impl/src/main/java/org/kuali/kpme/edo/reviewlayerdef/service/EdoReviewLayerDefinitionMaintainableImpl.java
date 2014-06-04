package org.kuali.kpme.edo.reviewlayerdef.service;

import org.kuali.kpme.edo.EdoBusinessObject;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.util.EdoBusinessObjectMaintainableImpl;

import java.math.BigDecimal;

public class EdoReviewLayerDefinitionMaintainableImpl extends EdoBusinessObjectMaintainableImpl {

    private static final long serialVersionUID = 1L;

    @Override
    public EdoBusinessObject getObjectById(BigDecimal id) {
        return EdoServiceLocator.getEdoReviewLayerDefinitionService().getReviewLayerDefinition(id);
    }
}
