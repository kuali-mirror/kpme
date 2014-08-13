package org.kuali.kpme.edo.api.vote;

import java.sql.Timestamp;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.mo.KpmeDataTransferObject;


public interface EdoVoteRecordContract extends KpmeDataTransferObject {
   
	public String getEdoVoteRecordId();

	public String getEdoDossierId();

	public String getEdoReviewLayerDefinitionId();

    public String getVoteType();
    
    public String getAoeCode();

    public Integer getYesCount();

    public Integer getNoCount();

    public Integer getAbsentCount();

    public Integer getAbstainCount();

    public Integer getVoteRound();

    public Integer getVoteSubRound();
    
    public DateTime getCreatedAt();

	public DateTime getUpdatedAt();

	public String getCreatedBy();

}
