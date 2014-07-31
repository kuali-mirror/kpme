package org.kuali.kpme.edo.api.reviewlayerdef;

import org.kuali.kpme.core.api.mo.KpmeDataTransferObject;

/**
 * <p>EdoSuppReviewLayerDefinitionContract interface</p>
 *
 */
public interface EdoSuppReviewLayerDefinitionContract extends KpmeDataTransferObject {

	/**
	 * The identifier of the EdoSuppReviewLayerDefinition
	 * 
	 * <p>
	 * edoSuppReviewLayerDefinitionId of the EdoSuppReviewLayerDefinition
	 * <p>
	 * 
	 * @return edoSuppReviewLayerDefinitionId for EdoSuppReviewLayerDefinition
	 */
    public String getEdoSuppReviewLayerDefinitionId();

    /**
	 * The review layer definition id the EdoSuppReviewLayerDefinition is associated with
	 * 
	 * <p>
	 * edoReviewLayerDefinitionId of the EdoSuppReviewLayerDefinition
	 * <p>
	 * 
	 * @return edoReviewLayerDefinitionId for EdoSuppReviewLayerDefinition
	 */
	public String getEdoReviewLayerDefinitionId();

	/**
	 * The name of the EdoSuppReviewLayerDefinition
	 * 
	 * <p>
	 * suppNodeName of the EdoSuppReviewLayerDefinition
	 * <p>
	 * 
	 * @return suppNodeName for EdoSuppReviewLayerDefinition
	 */
	public String getSuppNodeName();
	
	/**
	 * Indicates whether the EdoSuppReviewLayerDefinition is acknowledged
	 * 
	 * <p>
	 * acknowledgeFlag of the EdoSuppReviewLayerDefinition
	 * <p>
	 * 
	 * @return Y for Yes, N for No
	 */  
	public boolean isAcknowledgeFlag();
}
