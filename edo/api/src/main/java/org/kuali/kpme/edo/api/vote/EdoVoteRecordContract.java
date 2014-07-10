package org.kuali.kpme.edo.api.vote;

import org.kuali.kpme.core.api.mo.KpmeEffectiveDataTransferObject;


public interface EdoVoteRecordContract extends KpmeEffectiveDataTransferObject {
   
	public String getEdoVoteRecordID();

	public String getEdoDossierID();

	public String getEdoReviewLayerDefinitionID();

    public String getVoteType();
    
    public String getAoeCode();

    public Integer getYesCount();

    public Integer getNoCount();

    public Integer getAbsentCount();

    public Integer getAbstainCount();

    public Integer getVoteRound();

    public Integer getVoteSubRound();

}
