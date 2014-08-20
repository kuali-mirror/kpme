package org.kuali.kpme.edo.api.reviewernote;

import java.sql.Timestamp;
import java.util.Date;

import org.kuali.kpme.core.api.mo.KpmeDataTransferObject;


public interface EdoReviewerNoteContract extends KpmeDataTransferObject {
   
	public String getEdoReviewerNoteId();

	public String getEdoDossierId();

	public String getUserPrincipalId();

	public String getNote();

	public Date getReviewDateVal();

	public Timestamp getCreatedAtVal();

}
