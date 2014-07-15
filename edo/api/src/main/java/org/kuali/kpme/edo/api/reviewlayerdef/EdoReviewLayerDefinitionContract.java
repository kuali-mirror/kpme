/**
 * 
 */
/**
 * @author xichen
 *
 */
package org.kuali.kpme.edo.api.reviewlayerdef;

import org.kuali.kpme.core.api.mo.KpmeEffectiveDataTransferObject;

public interface EdoReviewLayerDefinitionContract extends KpmeEffectiveDataTransferObject {

	public String getEdoReviewLayerDefinitionId();

	public String getReviewLevel();

	public String getRouteLevel();

	public String getNodeName();
   
    public String getVoteType();

    public String getDescription();

    public boolean isReviewLetter();
    
    public String getWorkflowId();

    public String getWorkflowQualifier();
     
}